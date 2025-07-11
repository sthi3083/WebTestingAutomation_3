package com.sysco.qe.util;
import com.sysco.qe.common.Constants;
import com.sysco.qe.functions.Home;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.playwright.ui.BaseBrowser;
import com.syscolab.qe.core.reporting.SyscoLabListener;
import com.syscolab.qe.core.reporting.SyscoLabQCenter;
import com.syscolab.qe.core.reporting.SyscoLabReporting;
import static com.sysco.qe.common.Constants.IS_CREATE_BUILD_ENABLED;
import static com.syscolab.qe.core.reporting.SyscoLabReporting.generateBuild;
import org.json.JSONException;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

@Listeners(SyscoLabListener.class)
public class TestBase extends BaseBrowser {

    public SoftAssert softAssert;
    private SyscoLabListener testListeners;
    protected SyscoLabQCenter syscoLabQCenter;

    @BeforeSuite(alwaysRun = true)
    public void configureReporting() {
        if (Constants.UPDATE_DASHBOARD) {
            System.setProperty("update.dashboard", String.valueOf(Constants.UPDATE_DASHBOARD));
            System.setProperty("daily.weekly.build", String.valueOf(Constants.QLYTICS_DAILY_WEEKLY_BUILD));
            System.setProperty("build.completion", String.valueOf(Constants.QLYTICS_BUILD_COMPLETION));
            System.setProperty("test.layers", String.valueOf(Constants.TEST_LAYER));
            System.setProperty("test.project", String.valueOf(Constants.TEST_PROJECT));
            System.setProperty("test.env", String.valueOf(Constants.TEST_ENV));
            System.setProperty("test.release", String.valueOf(Constants.TEST_RELEASE));
        }
        if (Constants.UPDATE_QMETRY) {
            System.setProperty("update.qmetry", String.valueOf(Constants.UPDATE_QMETRY));
            System.setProperty("qmetry.project.name", Constants.QMETRY_PROJECT_NAME);
            System.setProperty("qmetry.test.cycle.key", Constants.QMETRY_TEST_CYCLE_KEY);
            System.setProperty("qmetry.token", Constants.SYSCO_QMETRY_TOKEN);
            System.setProperty("qmetry.default.cycle.name", Constants.QMETRY_DEFAULT_CYCLE_NAME);
            System.setProperty("qmetry.dynamic.cycle", Constants.QMETRY_DYNAMIC_CYCLE);
            System.setProperty("qmetry.custom.fields.map", Constants.QMETRY_CUSTOM_FIELD_MAP);
        }
    }

    public void createQCenterBuild() throws JSONException {
        if (IS_CREATE_BUILD_ENABLED) {
            generateBuild();
        }
    }

    @BeforeSuite
    public void runBeforeSuite() {
        configureReporting();
        createQCenterBuild();
    }

//    @BeforeSuite(alwaysRun = true)
//    public void createBuild(){
//        System.setProperty("daily.weekly.build", Constants.QLYTICS_DAILY_WEEKLY_BUILD);
//        System.setProperty("update.dashboard", String.valueOf(Constants.UPDATE_DASHBOARD));
//        Constants.TEST_RELEASE = QCenterUtil.generateBuild(Constants.TEST_PROJECT, Constants.TEST_ENV, Constants.TEST_RELEASE);
//    }

    @BeforeClass(alwaysRun = true)
    public void init() {
        testListeners = new SyscoLabListener();
        syscoLabQCenter = new SyscoLabQCenter();
        //Home.loadURl();
    }

    @BeforeMethod(alwaysRun = true)
    public void initSoftAssert() {
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void updateQCenter(ITestContext iTestContext) {
        try {

            syscoLabQCenter.setProjectName(Constants.TEST_PROJECT);
            syscoLabQCenter.setEnvironment(Constants.TEST_ENV);
            syscoLabQCenter.setRelease(Constants.TEST_RELEASE);
            syscoLabQCenter.setModule(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setFeature(iTestContext.getAttribute("feature").toString());
            syscoLabQCenter.setClassName(iTestContext.getClass().getName());

            if (Constants.UPDATE_DASHBOARD)
                SyscoLabReporting.generateJsonFile(SyscoLabListener.getResults(), syscoLabQCenter);
            RestUtil.setPort(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass(alwaysRun = true)
    public void cleanUp(ITestContext iTestContext) {
        try {
            Home.quitApplication();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
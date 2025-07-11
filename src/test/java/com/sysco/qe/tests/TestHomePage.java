package com.sysco.qe.tests;

import com.sysco.qe.functions.Home;
import com.sysco.qe.util.TestBase;
import com.sysco.qe.util.TestFeatures;
import com.sysco.qe.util.TestModules;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestHomePage extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void init(ITestContext iTestContext) {
        iTestContext.setAttribute("feature", TestModules.REGRESSION_DAY03 +" - "+ TestFeatures.PDPTEST);
    }

    @Test(description = "QMetry TC ID 01")
    public void loadHomePage(){
        Home.loadURl();
        softAssert.assertTrue(Home.isHomePageTitleDisplayed(),"Home Page error");
        System.out.println("TC ID 1 done>>>>>>>>>>>>>>>>>>>>");

    }


}
package com.sysco.qe.pages;

import com.sysco.qe.common.Constants;
import com.syscolab.qe.core.ui.SyscoLabUI;
import com.syscolab.qe.core.ui.web.SyscoLabWUI;
import org.openqa.selenium.By;

public class HomePage {

    static SyscoLabUI syscoLabUI;
    private By homePgTitle = By.xpath("//title[normalize-space()='Brakes Catering Equipment | Commercial Catering Supplies']");

    /**
     * Execute test on Chrome browser
     */
    public static void loadURL() {
        syscoLabUI = new SyscoLabWUI(Constants.BROWSER);
        syscoLabUI.maximizeWindow();
        syscoLabUI.navigateTo(Constants.APP_URL);
        syscoLabUI.setTimeOut(45);
    }

    public boolean isHomePageTitleDisplayed() {
        return syscoLabUI.isDisplayed(homePgTitle);
    }


    public void quitApplication() {
        if (syscoLabUI != null)
            syscoLabUI.quit();
    }

    public void sleep() {
        syscoLabUI.sleep(10);
    }
}

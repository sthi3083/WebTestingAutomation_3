package com.sysco.qe.functions;

import com.sysco.qe.pages.HomePage;

public class Home {
    static HomePage homePage = new HomePage();

    public static void loadURl() {
        homePage.loadURL();
        homePage.sleep();
    }

    public static boolean isHomePageTitleDisplayed() {
        return homePage.isHomePageTitleDisplayed();
    }

    public static void quitApplication() {
        homePage.quitApplication();
    }
}

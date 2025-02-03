package tests;

import base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.CareersPage;


public class HomePageTest extends BasePage {
	
    @Parameters()
    @Test
    public void verifyHomePageIsOpened() {
        navigateToURL();
        Assert.assertTrue(getDriver().getTitle().contains("Insider"), "Insider homepage title is not correct");
    }

    
}

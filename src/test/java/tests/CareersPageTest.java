package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BasePage;
import pages.CareersPage;
import pages.HomePage;

public class CareersPageTest extends BasePage {


    public void clickCompanyAndCareersPage() throws InterruptedException {
        navigateToURL();
        Hp.navigateToCareersPage();
    }

    @Test
    public void verifyCareerPageBlocks() throws InterruptedException {
    	verifyCareersPageIsOpened();
        Assert.assertTrue(Cp.isLocationsBlockVisible(), "Locations block is not visible");
        Assert.assertTrue(Cp.isTeamsBlockVisible(), "Teams block is not visible");
        Assert.assertTrue(Cp.isLifeAtInsiderBlockVisible(), "Life at Insider block is not visible");
    }

    @Test
    public void verifyCareersPageIsOpened() throws InterruptedException {
    	clickCompanyAndCareersPage();
    	String currentUrl = getDriver().getCurrentUrl();
    	String expectedUrl = "https://useinsider.com/careers/";
    	String baseUrl = currentUrl.substring(0, expectedUrl.length()); 

    	Assert.assertEquals(baseUrl, expectedUrl, "Careers page didn't load correctly");
    }
}

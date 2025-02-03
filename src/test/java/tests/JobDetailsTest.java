package tests;

import base.BaseFunctions;
import base.BasePage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.JobDetailsPage;
import pages.JobFilteringPage;

public class JobDetailsTest extends BasePage {
    

    @Test
    public void verifyJobDetailsPage() throws InterruptedException {
        navigateToURL();
        Jfp.acceptCookies();
        Jfp.navigateToQAJobsPage();
        Jfp.applyFilters();
        Jdp.verifyJobDetails();
    }
}

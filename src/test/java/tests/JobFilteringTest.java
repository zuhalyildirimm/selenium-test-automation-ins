package tests;
import org.testng.annotations.Test;
import base.BasePage;

public class JobFilteringTest extends BasePage {
	


    @Test
    public void verifyQAJobsFilter() throws InterruptedException {
        navigateToURL();
        Jfp.acceptCookies();
        Jfp.verifyURL("https://useinsider.com/careers/quality-assurance/");
        Jfp.navigateToQAJobsPage();
        Jfp.applyFilters();
    }
}

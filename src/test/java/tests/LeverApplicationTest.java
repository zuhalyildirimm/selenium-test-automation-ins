package tests;

import base.BasePage;
import org.testng.annotations.Test;

public class LeverApplicationTest extends BasePage {

    @Test
    public void verifyLeverApplicationRedirect() throws InterruptedException {
        navigateToURL();
        acceptCookiesAndApplyFilters();
        verifyJobDetailsAndRedirect();
        verifyLeverApplicationPage();
    }

    private void acceptCookiesAndApplyFilters() throws InterruptedException {
        Jfp.acceptCookies();
        Jfp.navigateToQAJobsPage();
        Jfp.applyFilters();
    }

    private void verifyJobDetailsAndRedirect() throws InterruptedException {
        Jdp.verifyJobDetails();
        Jdp.clickViewRoleButtonAndVerifyRedirect();
    }

    private void verifyLeverApplicationPage() throws InterruptedException {
        Lap.verifyLeverApplicationPage();
    }
}

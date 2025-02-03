package pages;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import base.BaseFunctions;
import base.BasePage;

public class LeverApplicationPage extends BasePage {

    private BaseFunctions bf;

    @FindBy(xpath = "(//a[contains(@class, 'postings-btn') and contains(text(), 'Apply for this job')])[1]")
    private WebElement applyForThisJob;
    
    public LeverApplicationPage(BaseFunctions bf) {
        super();
        this.bf = bf;
        PageFactory.initElements(bf.getDriver(), this);
    }

    public void verifyLeverApplicationPage() throws InterruptedException {
        WebDriver driver = bf.getDriver(); 

        if (driver == null) {
            throw new RuntimeException("Driver is null! Make sure it is initialized.");
        }

        Set<String> windowHandles = driver.getWindowHandles();
        String currentWindow = driver.getWindowHandle();

        for (String window : windowHandles) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://jobs.lever.co/useinsider/";
        String baseUrl = currentUrl.substring(0, expectedUrl.length());

        Assert.assertEquals(baseUrl, expectedUrl, "Careers page didn't load correctly");
    }
}

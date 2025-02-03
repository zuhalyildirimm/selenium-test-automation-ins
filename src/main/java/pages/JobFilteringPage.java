package pages;

import base.BaseFunctions;
import base.BasePage;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class JobFilteringPage extends BasePage {

	private BaseFunctions bf;

    @FindBy(css = "#wt-cli-accept-all-btn:nth-of-type(1)")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//a[contains(text(), 'See all QA jobs') and contains(@class, 'btn') and contains(@class, 'btn-outline-secondary')]")
    private WebElement seeAllQAJobsButton;

    @FindBy(xpath = "//select[@id='filter-by-location']")
    private WebElement locationInput;

    @FindBy(xpath = "//select[@id='filter-by-department']")
    private WebElement departmentInput;
    
    @FindBy(xpath = "//label[@for='filter-by-location' and contains("
    		+ "text(),'Filter by Location')]")
    private WebElement filterlocation;
    
    @FindBy(xpath = "(//option[@class='job-team qualityassurance'])[1]")
    private WebElement Qafilter;
    
    @FindBy(xpath = "//div/span[text()='Filter']")
    private WebElement buttonFilter;
    
    @FindBy(xpath = "(//option[@class='job-location istanbul-turkey'])[1]")
    private WebElement buttonIstanbul;
    
    
    @FindBy(xpath = "(//span[@class='select2-selection__clear' and @title='Remove all items'])[1]")
    private WebElement clearButton;
    
 
    @FindBy(xpath = "//p[@id='resultCounter']//span[contains(@class, 'totalResult')]")  
    private WebElement totalResultElement;


    public JobFilteringPage(BaseFunctions bf) {
    	super();
        this.bf = bf;
        PageFactory.initElements(bf.getDriver(), this);
    }
    
    
    public void acceptCookies() throws InterruptedException {
        bf.clicke(acceptCookiesButton);
    }

    public void applyFilters() throws InterruptedException {
    	bf.scrollToElement(departmentInput);
    	bf.waitElement(locationInput);
    	bf.clickWithJavaScript(locationInput);
    	bf.waitElement(buttonIstanbul);
    	bf.clicke(buttonIstanbul);
    	bf.waitElement(departmentInput);
    	bf.clicke(Qafilter);
    	verifyJobListings();
    	
    }
    


       
       
    public void verifyJobListings() throws InterruptedException {
    	bf.waitElement(totalResultElement);
    	String totalText = totalResultElement.getText().trim();
        int totalJobs = Integer.parseInt(totalText);
        Assert.assertTrue(totalJobs > 0, "No job listings found!");
    }





    
    public void verifyURL(String expectedURL) {
        WebDriver driver = bf.getDriver();
        if (driver != null) {
            Assert.assertEquals(driver.getCurrentUrl(), expectedURL, "The current URL is not as expected.");
        } else {
            Assert.fail("Driver is not initialized.");
        }
    }


    public void navigateToQAJobsPage() throws InterruptedException {
        bf.clicke(seeAllQAJobsButton);
    }
}
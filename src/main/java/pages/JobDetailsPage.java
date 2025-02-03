package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import base.BaseFunctions;
import base.BasePage;

public class JobDetailsPage extends BasePage {
    private BaseFunctions bf;

    @FindBy(xpath = "//span[@class='totalResult']")
    private WebElement totalResult;

    @FindBy(xpath = "//div[@id='jobTitle']")
    private WebElement jobPosition;

    @FindBy(xpath = "//div[@id='jobDescription']")
    private WebElement jobDepartment;

    @FindBy(xpath = "//div[@id='jobDescription']")
    private WebElement jobLocation;

    @FindBy(xpath = "//div[contains(@class, 'position-list-item')]")
    private List<WebElement> jobList;

    @FindBy(xpath = "//p[contains(@class, 'position-title')]")
    private WebElement positionTitle;

    @FindBy(xpath = "//span[contains(@class, 'position-department')]")
    private WebElement positionDepartment;
    

    @FindBy(xpath = "//div[contains(@class, 'position-location')]")
    private WebElement positionLocation;
    
    @FindBy(xpath = "(//a[contains(text(),'View Role')])[3]")
    private WebElement viewRoleButton;

    public JobDetailsPage(BaseFunctions bf) {
        super();
        this.bf = bf;
        PageFactory.initElements(bf.getDriver(), this);
    }

    

    public void verifyJobDetails() throws InterruptedException {
    	
        bf.waitElement(positionTitle);
        String positionText = positionTitle.getText();

        bf.waitElement(positionDepartment);
        String departmentText = positionDepartment.getText();

        bf.waitElement(positionLocation);
        String locationText = positionLocation.getText();

        Assert.assertTrue(positionText.contains("Quality Assurance"), "Position does not contain 'Quality Assurance'. Actual position: " + positionText);
        Assert.assertTrue(departmentText.contains("Quality Assurance"), "Department does not contain 'Quality Assurance'");
        Assert.assertTrue(locationText.contains("Istanbul, Turkey"), "Location does not contain 'Istanbul, Turkey'");
    }
    
    
    
    public void clickViewRoleButtonAndVerifyRedirect() throws InterruptedException {
    	bf.hoverToElement(viewRoleButton);
        bf.waitElement(viewRoleButton);
        bf.clicke(viewRoleButton);
        

    
    
}
}
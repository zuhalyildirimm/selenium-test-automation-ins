package pages;
 
import base.BaseFunctions;
import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
 
public class HomePage extends BasePage {
 
    private BaseFunctions bf;
 
    public HomePage(BaseFunctions bf) {
        super();
        this.bf = bf;
        PageFactory.initElements(bf.getDriver(), this);
    }
 
    @FindBy(xpath = "//a[contains(text(),'Company')]")
    private WebElement companyMenu;
 
    @FindBy(xpath = "//a[@class='dropdown-sub' and contains(text(),'Careers')]")
    private WebElement careersOption;
 
    @FindBy(css = "#wt-cli-reject-btn:nth-of-type(3)")
    private WebElement rejectButton;
 
    public void navigateToCareersPage() {
        try {
            if (rejectButton.isDisplayed()) {
                bf.clicke(rejectButton);
            } else {
                System.out.println("Reject button not found.");
            }
            if (companyMenu.isDisplayed()) {
                bf.clicke(companyMenu);
            } else {
                System.out.println("Company menu not found.");
            }
            if (careersOption.isDisplayed()) {
                bf.clicke(careersOption);
            } else {
                System.out.println("Careers option not found.");
            }
        } catch (Exception e) {
            System.out.println("Error navigating to Careers page: " + e.getMessage());
        }
    }
 
    public void clickCompanyMenu() {
        try {
            if (companyMenu != null && companyMenu.isDisplayed()) {
                bf.clicke(companyMenu);
            } else {
                System.out.println("Company menu element is not available or not displayed.");
            }
        } catch (Exception e) {
            System.out.println("Error clicking Company menu: " + e.getMessage());
        }
    }
 
    public void selectCareersOption() {
        try {
            if (careersOption != null && careersOption.isDisplayed()) {
                bf.clicke(careersOption);
            } else {
                System.out.println("Careers option is not available or not displayed.");
            }
        } catch (Exception e) {
            System.out.println("Error selecting Careers option: " + e.getMessage());
        }
    }
}
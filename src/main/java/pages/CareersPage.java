package pages;

import base.BaseFunctions;
import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CareersPage extends BasePage {
    
    @FindBy(xpath = "//h3[contains(text(),'Our Locations')]")
    private WebElement locationsBlock;

    @FindBy(xpath = "//a[contains(text(),'See all teams')]")
    private WebElement teamsBlock;

    @FindBy(xpath = "//h2[contains(text(),'Life at Insider')]")
    private WebElement lifeAtInsiderBlock;
    
    @FindBy(css = "#wt-cli-reject-btn:nth-of-type(3)")
    private WebElement rejectButton;

    @FindBy(id = "job-opportunities-link")
    private WebElement jobOpportunitiesLink;
    
   
    
    
    
    

    public CareersPage(BaseFunctions bf) {
        super();
        this.bf = bf;
	    PageFactory.initElements(bf.getDriver(), this);

    }
   
    public boolean isLocationsBlockVisible() {
        if (locationsBlock != null) {
        	bf.scrollToElement(locationsBlock);
            return bf.waitForElementToBeVisible(locationsBlock).isDisplayed();
        } else {
            System.out.println("locationsBlock element is null");
            return false;
        }
    }

    public boolean isTeamsBlockVisible() {
    	if (teamsBlock != null) {
        	bf.scrollToElement(teamsBlock);
            return bf.waitForElementToBeVisible(teamsBlock).isDisplayed();
        } else {
            System.out.println("teamsBlock element is null");
            return false;
        }

    }

    public boolean isLifeAtInsiderBlockVisible() {
    	
    	if (lifeAtInsiderBlock != null) {
        	bf.scrollToElement(lifeAtInsiderBlock);
            return bf.waitForElementToBeVisible(lifeAtInsiderBlock).isDisplayed();
        } else {
            System.out.println("lifeAtInsiderBlock element is null");
            return false;
        }

    }
    
    public void cookies() {
    	rejectButton.click();
    }

        
        
        
        
    }


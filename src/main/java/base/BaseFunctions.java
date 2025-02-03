package base;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseFunctions {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions act;

    public BaseFunctions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
    }
    
    public WebDriver getDriver() {
        return this.driver;
    }
    
    public void verifyPageTitle(String expectedTitle) {
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Page title is not as expected.");
    }
    
    
    public void navigateToURL(String url) {
        driver.get(url);
    }
    
    public void clickWithSendKeys(WebElement element, String keysToSend) throws InterruptedException {
        Thread.sleep(1000);
        waitForPageToLoad();
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(keysToSend);
        element.sendKeys(Keys.RETURN);
    }
    
    public boolean isElementDisplayed(WebElement element) {
        try {
            waitForPageToLoad();
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void sendKeys(WebElement element, String keysToSend) throws InterruptedException {
        waitForPageToLoad();
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(keysToSend);
    }


    public void clicke(WebElement element) throws InterruptedException {
    	waitForPageToLoad();
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    
    public WebElement waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        scrollToElement(element);
        return wait.until(ExpectedConditions.visibilityOf(element)); 
    }
    
    public void waitForPageToLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
    }

    public void write(WebElement element, String text) throws InterruptedException {
		Thread.sleep(1000);
		waitForPageToLoad();
		wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(text);
	}
    
    public void scrollPage(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(" + x + "," + y + ");");
    }
    
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    public void scrollBy(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }


    public void hoverOverElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }
    
    public void clickWithJavaScript(WebElement element) throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

    public void hoverToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    
    
    
    public void waitElement(WebElement element) throws InterruptedException {
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

}

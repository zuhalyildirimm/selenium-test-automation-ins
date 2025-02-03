package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;

import pages.CareersPage;
import pages.HomePage;
import pages.JobDetailsPage;
import pages.JobFilteringPage;
import pages.LeverApplicationPage;
import utils.HARRecorder;

import java.util.Collections;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseURL;

    public BaseFunctions bf;
    public CareersPage Cp;
    public HomePage Hp;
    public JobFilteringPage Jfp;
    public JobDetailsPage Jdp;
    public LeverApplicationPage Lap;

    @Parameters("baseURL")
    @BeforeClass
    public void setup(String baseURL) {
        this.baseURL = baseURL;
    }

    @BeforeMethod
    public void startDriver() {
        setupDriver();
        bf = new BaseFunctions(driver);  // 'BaseFunctions' burada 'driver' ile başlatılmalı

        Cp = new CareersPage(bf);
        Hp = new HomePage(bf);
        Jfp = new JobFilteringPage(bf);
        Jdp = new JobDetailsPage(bf);
        Lap = new LeverApplicationPage(bf);

        PageFactory.initElements(driver, this);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        navigateToURL();
    }

    private void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "disable-infobars", "disable-features=IsolateOrigins,SitePerProcess");
        options.addArguments("--disable-extensions", "--disable-site-isolation-trials", "--disable-web-security", "--disable-cookies");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--allow-running-insecure-content", "ignore-certificate-errors", "window-size=1920,1080", "--disable-notifications");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void navigateToURL() {
        driver.get(baseURL);
    }

    public void startHarRecording(String testName) {
        HARRecorder.startRecording(driver);
    }

    public void stopHarRecording(String testName) {
        HARRecorder.stopRecordingAndSave(testName);
    }

    public WebDriverWait getWait() {
        return wait;  
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}

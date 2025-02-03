package listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseFunctions;
import base.BasePage;

public class TestListener implements ITestListener {
    private BaseFunctions bf;
    private StringBuilder reportContent = new StringBuilder();

    @Override
    public void onTestStart(ITestResult result) {
        WebDriver driver = ((BasePage) result.getInstance()).getDriver();
        bf = new BaseFunctions(driver);
        BasePage base = (BasePage) result.getInstance();
        base.startHarRecording(result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = captureScreenshot(result, "fail");
        appendToReport(result, "Fail", screenshotPath);
        BasePage base = (BasePage) result.getInstance();
        base.stopHarRecording(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String screenshotPath = captureScreenshot(result, "pass");
        appendToReport(result, "Pass", screenshotPath);
        BasePage base = (BasePage) result.getInstance();
        base.stopHarRecording(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String screenshotPath = captureScreenshot(result, "skipped");
        appendToReport(result, "Skipped", screenshotPath);
        BasePage base = (BasePage) result.getInstance();
        base.stopHarRecording(result.getName());
    }

    private String captureScreenshot(ITestResult result, String status) {
        WebDriver driver = ((BasePage) result.getInstance()).getDriver();
        Set<String> windowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();
        String newWindowHandle = null;

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                newWindowHandle = handle;
                break;
            }
        }

        if (newWindowHandle != null) {
            driver.switchTo().window(newWindowHandle);
        }

        bf.waitForPageToLoad();
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String testName = result.getName();
        String screenshotDir = "selenium-test-automation-ins/screenshots/" + status + "/";
        String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";

        File screenshotFile = new File(screenshotPath);
        try {
            FileUtils.forceMkdirParent(screenshotFile);
            FileUtils.copyFile(source, screenshotFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.switchTo().window(mainWindowHandle);
        return "screenshots/" + status + "/" + testName + "_" + timestamp + ".png";
    }

    private void appendToReport(ITestResult result, String status, String screenshotPath) {
        String testName = result.getName();
        reportContent.append("<tr>")
            .append("<td>").append(testName).append("</td>")
            .append("<td>").append(status).append("</td>")
            .append("<td>").append("<a href='").append(screenshotPath).append("'>View Screenshot</a>").append("</td>")
            .append("</tr>");
    }

    @Override
    public void onFinish(ITestContext context) {
        String htmlReport = "<html><body><h1>Test Report</h1><table border='1'>" 
                            + "<tr><th>Test Name</th><th>Status</th><th>Screenshot</th></tr>" 
                            + reportContent.toString() 
                            + "</table></body></html>";
        
        try {
            File reportFile = new File(System.getProperty("user.dir") + "/target/emailable-report.html");
            FileUtils.writeStringToFile(reportFile, htmlReport, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

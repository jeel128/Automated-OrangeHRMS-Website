package com.kiwiqa.pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class CommonPage {
	
	 WebDriver driver;
     WebDriverWait wait;
     ExtentTest test;
     protected ExtentReports extent;
    public CommonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public  void driverSetup(WebDriver driver, String link) {
//    	this.driver = driver;
//    	driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(link);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    public void driverClose(WebDriver driver) {
    	driver.quit();
    }
    
    public WebElement waitForVisibility(WebElement element) {
    	return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void click(WebElement element) {
        element.click();
    }

    public void enterText(WebElement element, String text) {
//        WebElement element = waitForVisibility(locator);
        element.sendKeys(text);
    }
    
    public void clear(WebElement element, String fieldText) {
//    	WebElement element = driver.findElement(locator);
    	
    	Actions action = new Actions(driver);
    	action.moveToElement(element);
    	element.click();
    	element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(fieldText);
        
    }
    
    public void actionScroll() {
    	Actions action = new Actions(driver);
        action.scrollByAmount(0, -300).perform();
    }
    
    public void captureScreenshot(WebDriver driver, String screenshotName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
//         
            File destFile = new File("screenshots\\" + screenshotName + ".png");
            FileUtils.copyFile(srcFile, destFile);
            
        } catch (IOException e) {
            e.printStackTrace();
//            test.fail("Failed to capture screenshot: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
//            test.fail("Unexpected error occurred while capturing screenshot: " + e.getMessage());
        }
    }
    
    public ExtentTest reportGenerate() {
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");
		  htmlReporter.config().setTheme(Theme.STANDARD);
	      htmlReporter.config().setDocumentTitle("Automation Report");
	      htmlReporter.config().setReportName("OrangeHRM Myinfo Report");
	      extent = new ExtentReports();
		  extent.attachReporter(htmlReporter);
		  test = extent.createTest("My First Test", "Sample description for this test");
		  return test;
    }
    public void closeExtent(ExtentReports extent) {
    	extent.flush();
    }
    
 // Helper method to wait for an element
    public WebElement waitForElement(By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2));
        
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
	


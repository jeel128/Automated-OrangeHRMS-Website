package com.kiwiqa.extentreport;

import java.io.File;
import java.io.IOException;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.kiwiqa.pages.CommonPage;
import com.kiwiqa.pages.LoginPage;
import com.kiwiqa.pages.MyInfoPage;


public class OrangeHRMExtentReport {
	
	WebDriver driver;
	LoginPage lp;
	MyInfoPage mp ;
	CommonPage bp;
	private ExtentReports extent;
    private ExtentTest test;
    
	@BeforeClass
	  public void setUp() {
		
		 // Initialize ExtentReports
		//create the htmlReporter object 
		  ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");
		  htmlReporter.config().setTheme(Theme.STANDARD);
	      htmlReporter.config().setDocumentTitle("Automation Report");
	      htmlReporter.config().setReportName("OrangeHRM Myinfo Report");
		//create ExtentReports and attach reporter(s)
		  extent = new ExtentReports();
		  extent.attachReporter(htmlReporter);

        // Create a test instance
        test = extent.createTest("My First Test", "Sample description for this test");
        
        driver = new ChromeDriver();
		bp = new CommonPage(driver);
		bp.driverSetup(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/7");
        test.log(Status.INFO, "Starting test case, driver setup done");
        test.pass("Navigate to login page");
	}
	 
	 @BeforeMethod
	  public void beforeMethod() {
		 lp = new LoginPage(driver);
		 mp = new MyInfoPage(driver);
	  }
	
	  @Test(priority =1)
	  public void login() {
		  try {
	            lp.login("Admin", "admin123");
	            test.pass("Logged in successfully");
	            captureScreenshot("login_pass");
	        } catch (Exception e) {
	            test.fail("Login failed: " + e.getMessage());
	            captureScreenshot("login_failure");
	        }
	  }
	  
	  @Test(priority = 2)
	  
	  public void addDetails() throws InterruptedException {
		try {  
		  test.log(Status.INFO, "Moving to MyInfo Page and entering details");
//		  mp.addDetails("Rohit", "R", "Sharma", "45", "3004", "45454545", "December", "2024", "31", "Indian");
		  mp.enterFirstname("Rohit");
		  mp.enterMiddleName("R");
		  mp.enterLastName("Sharma");
		  mp.enterEmpId("45");
		  mp.enterOtherId("3004");
		  mp.enterLicence("RSIND45");
		  mp.selectExpiry("December", "2024", "31");
		  mp.selectNationality("Indian");
		  mp.selectMarital();
		  mp.selectGender();
		  mp.clickSubmit();
		  test.pass( "Details entered successfully");
		  captureScreenshot("addDetails_passed");
	  }
		catch(Exception e) {
			 test.fail("Failed to enter details: " );
	            captureScreenshot("addDetails_failure");
	            System.out.println("Screenshot should be captured");
		}
	  }
	  @AfterMethod
	  public void verify() {
		  
		  System.out.println("Donee");
	  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
		  test.log(Status.INFO, "Driver quit successfully");
		  extent.flush();	
		  }
  
	  private void captureScreenshot(String screenshotName) {
	        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        try {
//	         
	            File destFile = new File("screenshots\\" + screenshotName + ".png");
	            FileHandler.copy(srcFile, destFile);
	            test.addScreenCaptureFromPath("screenshots\\" + screenshotName + ".png");
	        } catch (IOException e) {
	            e.printStackTrace();
	            test.fail("Failed to capture screenshot: " + e.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	            test.fail("Unexpected error occurred while capturing screenshot: " + e.getMessage());
	        }
	    }
}

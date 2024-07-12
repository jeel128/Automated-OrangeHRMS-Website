package com.kiwiqa.testclass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.kiwiqa.pages.CommonPage;
import com.kiwiqa.pages.LoginPage;
import com.kiwiqa.pages.MyInfoPage;


public class MyInfoDirectTestNG extends CommonPage{
	
	
	private WebDriver driver;
	MyInfoPage mp ;
	LoginPage lp;
	FileReader fileReader;
	Properties props;
    ExtentTest test;
    FileInputStream file;
    ArrayList<String> dataList = new ArrayList<>();
	// Added no-argument constructor required for TestNG
    public MyInfoDirectTestNG() {
        super(null);
    }

    // Existing constructor for WebDriver initialization
    public MyInfoDirectTestNG(WebDriver driver) {
        super(driver);
    }
	
    
	@BeforeTest
	public void beforeTest() throws IOException {
		props=new Properties();
		String filePath = "\\src\\test\\java\\com\\kiwiqa\\testclass\\TestData.properties";
	    fileReader = new FileReader(System.getProperty("user.dir")+filePath);
	    props.load(fileReader);
	    test= reportGenerate();
		
	}
	
	@BeforeClass
	@Parameters("browser")
	  public void setUp(String browser) throws FileNotFoundException {
		if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
		driverSetup(driver,props.getProperty("link") );
		test.log(Status.INFO, "Starting test case, driver setup done");
        file = new FileInputStream(props.getProperty("excelFile"));
	  }
	 
	 @BeforeMethod
	  public void beforeMethod() {
		 lp = new LoginPage(driver);
		 mp = new MyInfoPage(driver);
	  }
	 	
		
		
	  @Test(priority =1, description= "This is login test", timeOut = 5000)
	  public void login() throws Exception {
			  test = extent.createTest("Login Test", "This is login test");
			  lp.login(props.getProperty("username"), props.getProperty("password"));
			  
			  test.log(Status.INFO, "Login successful!!!");
//			  Assert.assertTrue(false, "Login failed");
	  }
	  
	  @Test(priority = 2, description="This enters details in My Info Page", dependsOnMethods="login")
	  
	  public void addDetails() throws InterruptedException, IOException {
			  test = extent.createTest("My Info Test", "this is my info test");
			  dataList = mp.readFromExcel(file);
			  mp.addDetails(dataList.get(0),dataList.get(1), dataList.get(2), dataList.get(3), dataList.get(4), dataList.get(5), dataList.get(6), dataList.get(7), dataList.get(8), dataList.get(9));
			  test.log(Status.INFO, "Details Entered!!!");
	}
	  
	  @AfterMethod
	  public void verify(ITestResult result) throws InterruptedException {
		  Thread.sleep(3000);
		  System.out.println(result.getStatus());
		  int status= result.getStatus();
		  if(status == 1) {
			  test.pass("Method run successfully!!");
			  String methodName = result.getMethod().getMethodName()+"_pass";
			  captureScreenshot(driver, methodName );
			  test.addScreenCaptureFromPath("screenshots\\"+ methodName +".png");
		  }
		  else {
			  test.fail("Method failed :(");
			  String methodName = result.getMethod().getMethodName()+"_fail";
			  captureScreenshot(driver, methodName );
			  test.addScreenCaptureFromPath("screenshots\\"+ methodName +".png");
		  }
	  }
	  
	  @AfterTest
	  public void tearDown() {
		    if (driver != null) {
		        driver.quit();
		    }
		    test.log(Status.INFO, "Driver quit successfully");
		    extent.flush();
	  }
	  
	  
  
}

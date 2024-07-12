package com.kiwiqa.testclass;

import org.testng.annotations.Test;

import com.kiwiqa.pages.LoginPage;
import com.kiwiqa.pages.MyInfoPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class MyInfoTestNG {
	
	WebDriver driver;
	LoginPage lp;
	MyInfoPage mp ;
	By firstNameLocator;
	By middleNameLocator;
	By lastNameLocator;
	By empidLocator;
	By otheridLocator;
	By licenceLocator;
	By expiryLocator;
	By nationalityLocator;
	By maritalLocator;
	By genderLocator;
	By submitButtonLocator;
	
	
	
	 @BeforeClass
	  public void setUp() {
		 driver = new ChromeDriver();
	     driver.manage().window().maximize();
	     driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/7");
	     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  }
	 
	 @BeforeMethod
	  public void beforeMethod() {
		  lp = new LoginPage(driver);
		  mp = new MyInfoPage(driver);
	  }
	 
	  @Test(priority = 1)
	  @Parameters({"username","password"})
	  public void login(String username, String password ) {
		  lp.login(username, password);
	  }
	  
	  @Test(priority = 2)
	  public void locators() {
		   firstNameLocator = By.xpath("//input[@name = 'firstName']");
		   middleNameLocator = By.xpath("//input[@name = 'middleName']");
		   lastNameLocator = By.xpath("//input[@name = 'lastName']");
		   empidLocator = By.xpath("//label[contains(text(),'Employee Id')]//parent::div//following-sibling::div/input");
		   otheridLocator = By.xpath("//label[contains(text(),'Other Id')]//parent::div//following-sibling::div/input");
		   licenceLocator = By.xpath("//label[contains(text(),'Driver')]//parent::div//following-sibling::div/input");
		   expiryLocator = By.xpath("//label[contains(text(),'License Expiry Date')]//parent::div//following-sibling::div");
		   nationalityLocator = By.xpath("//label[contains(text(),'Nationality')]//parent::div//following-sibling::div");
		   maritalLocator = By.xpath("//label[contains(text(),'Marital')]//parent::div//following-sibling::div");
		   genderLocator = By.xpath("//input[@value='1']/..");
		   submitButtonLocator = By.xpath("(//button[@type='submit'])[1]");
		  
	  }
	  
	  
	  @Test(priority = 3)
	  @Parameters({"firstName"})
	  public void enterFirstName(String firstName) {
		  
//		  mp.waitForVisibility(firstNameLocator);
//		  mp.waitForClickability(firstNameLocator);
//		  mp.clear(firstNameLocator, firstName);
		  
		  WebElement element = driver.findElement(firstNameLocator);
	      Assert.assertEquals(element.getAttribute("value"), firstName, "First name was not set correctly.");
		  
	  }
	  @Test(priority = 4)
	  @Parameters({"middleName"})
	  public void enterMiddleName(String middleName) {
		  
//		  mp.clear(middleNameLocator, middleName);
		  
	  }
	  @Test(priority = 5)
	  @Parameters({"lastName"})
	  public void enterLastName(String lastName) {
//		  mp.clear(lastNameLocator, lastName);
		  
	  }
	  @Test(priority = 6)
	  @Parameters({"empId"})
	  public void enterEmpId(String empId) {
		  
//		  mp.clear(empidLocator, empId);
		  
	  }
	
	  @Test(priority = 7)
	  @Parameters({"otherId"})
	  public void enterOtherID(String otherId) {
		  
//		  mp.clear(otheridLocator, otherId);
		  
	  }
	  @Test(priority = 8)
	  @Parameters({"licence"})
	  public void enterLicence(String licence) {
		  
//		  mp.clear(licenceLocator,licence );
		  
	  }
	  @Test(priority = 9)
	  @Parameters({"month","year","date" })
	  public void selectionOfExpiry(String month, String year, String date) {
		  
		  mp.selectExpiry( month,  year,  date);
		  
	  }
	  @Test(priority = 10)
	  @Parameters({"nation"})
	  public void selectionOfNationality(String nation) {
		  
		  mp.selectNationality(nation );
		  
	  }
	  @Test(priority = 11)
	  public void selectionOfMarital() {
		  
		  mp.selectMarital();
		  
	  }
	  @Test(priority = 12)
	  public void selectionOfGender() {
		  
		  mp.selectGender();
		  
	  }
	  @Test(priority = 13)
	  public void clickOnSubmit() throws InterruptedException {
		  
		  mp.clickSubmit();
		  
	  }
	  
	  
	  @AfterMethod
	  public void verify() {
		  
		  System.out.println("Donee");
	  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
 

  

 

  

}

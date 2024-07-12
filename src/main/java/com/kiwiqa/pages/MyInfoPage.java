package com.kiwiqa.pages;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MyInfoPage extends CommonPage {

		
	WebDriver driver;
		
		public MyInfoPage(WebDriver driver) {
			super(driver);
			this.driver = driver;
			PageFactory.initElements( driver, this);
		}
		
		public  ArrayList<String> readFromExcel(FileInputStream file) throws IOException {
	    	
	        
		    Workbook workbook = new XSSFWorkbook(file);
		    Sheet sheet = workbook.getSheet("Sheet1");
		    ArrayList<String> dataList = new ArrayList<>();
		    //Getting all values together
		    Iterator<Row> rowIterator = sheet.iterator();
		    DataFormatter dataFormatter = new DataFormatter();
		    while (rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		        Cell keyCell = row.getCell(0);
		        Cell valueCell = row.getCell(1);
	            String key = dataFormatter.formatCellValue(keyCell);
	            String value = dataFormatter.formatCellValue(valueCell);
	                // Only put non-empty keys and values into the list
	                if (!key.isBlank()  ) {
//	           
	                dataList.add(value);
	                }
	        ((FileInputStream) workbook).close();
		    } 
		    return dataList;
		}
		    
		 
		public void addDetails(String firstName, String middleName, String lastName, String empid, String otherid, String licence, String month, String year, String date, String country) throws InterruptedException {
			
			waitForVisibility(firstNameLocator);
			waitForClickability(firstNameLocator);
			
			clear(firstNameLocator, firstName);
			clear(middleNameLocator, middleName);
			clear(lastNameLocator, lastName);
			clear(empidLocator, empid);
			clear(otheridLocator, otherid);
			clear(licenceLocator, licence);
			selectExpiry( month,  year,  date);
			selectNationality( country);
			selectMarital();
			selectGender();
			clickSubmit();
			
		}
		@FindBy(xpath = "//input[@name='firstName']")
	    private WebElement firstNameLocator;

		public void enterFirstname(String firstName) {

			waitForVisibility(firstNameLocator);
			waitForClickability(firstNameLocator);
			clear(firstNameLocator, firstName);
		}
		
		 @FindBy(xpath = "//input[@name='middleName']")
		 private WebElement middleNameLocator;
		 
		public void enterMiddleName(String middleName) {
			clear(middleNameLocator, middleName);
		}
		
		@FindBy(xpath = "//input[@name='lastName']")
	    private WebElement lastNameLocator;
		
		public void enterLastName(String lastName) {
			clear(lastNameLocator, lastName);
		}
		
		@FindBy(xpath = "//label[contains(text(),'Employee Id')]//parent::div//following-sibling::div/input")
	    private WebElement empidLocator;
		public void enterEmpId(String empId) {
			clear(empidLocator, empId);
		}
		
		@FindBy(xpath = "//label[contains(text(),'Other Id')]//parent::div//following-sibling::div/input")
	    private WebElement otheridLocator;
		public void enterOtherId(String otherId) {
			clear(otheridLocator, otherId);
		}
		
		@FindBy(xpath = "//label[contains(text(),'Driver')]//parent::div//following-sibling::div/input")
	    private WebElement licenceLocator;
		
		public void enterLicence(String licence) {
			clear(licenceLocator, licence);
		}
	
		@FindBy(xpath = "//label[contains(text(),'License Expiry Date')]//parent::div//following-sibling::div")
	    private WebElement expiryLocator;
		public void selectExpiry(String month, String year, String date) {
			
				click(expiryLocator);
		        driver.findElement(By.xpath("//div[contains(@class,'oxd-calendar-selector-month-selected')]")).click();
		        actionScroll();
		        driver.findElement(By.xpath("//li[contains(text(),'"+ month + "')]")).click();
		        driver.findElement(By.xpath("//div[contains(@class,'oxd-calendar-selector-year-selected')]")).click();
		        actionScroll();
		        driver.findElement(By.xpath("//li[contains(text(),'"+ year +"')]")).click();
		        driver.findElement(By.xpath("//div[contains(text(),'" + date + "')]")).click();
	        
		}
		
		@FindBy(xpath = "//label[contains(text(),'Nationality')]//parent::div//following-sibling::div")
	    private WebElement nationalityLocator;
		public void selectNationality(String country) {
			click(nationalityLocator);
			driver.findElement(By.xpath("//div/span[contains(text(),'"+ country +"')]")).click();
		}
		
		@FindBy(xpath = "//label[contains(text(),'Marital')]//parent::div//following-sibling::div")
	    private WebElement maritalLocator;
		public void selectMarital() {
			click(maritalLocator);
	        driver.findElement(By.xpath("//div/span[contains(text(),'Married')]")).click();
		}
		
		@FindBy(xpath = "//input[@value='1']/..")
	    private WebElement genderLocator;
		
		public void selectGender() {
			click(genderLocator);
		}
		
		@FindBy(xpath = "(//button[@type='submit'])[1]")
	    private WebElement submitButtonLocator;
		public void clickSubmit() throws InterruptedException {
				waitForClickability(submitButtonLocator);
				click(submitButtonLocator);

				WebElement update = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(//div[contains(@class, 'toast')]//p)[1]"))));
		        String updateText = update.getText();
		        WebElement update2 = driver.findElement(By.xpath("(//div[contains(@class, 'toast')]//p)[2]"));
		        updateText += " " + update2.getText();
		        System.out.println("Text from first button: " + updateText);
		        Assert.assertEquals(updateText, "Success Successfully Updated", "Data not updated successfully");

		}
		
		
	
	
}

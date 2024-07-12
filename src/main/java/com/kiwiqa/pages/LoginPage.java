package com.kiwiqa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends CommonPage {
	
	WebDriver driver;
	
	//Locators
	@FindBy(name = "username")WebElement usernameLocator;
	@FindBy(name = "password")WebElement pswdLocator;
	@FindBy(xpath = "//button[@type='submit']")WebElement loginLocator; 
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements( driver, this);
    }
	
	public void login(String username, String password)  {
		
		enterText(usernameLocator, username);
		enterText(pswdLocator, password);
		click(loginLocator);
	}
	
	
	
}

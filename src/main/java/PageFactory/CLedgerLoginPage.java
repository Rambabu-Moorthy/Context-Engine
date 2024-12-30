package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestUtils.ConfigReader;

public class CLedgerLoginPage {

	WebDriver driver;
	ConfigReader reader;

	public CLedgerLoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		reader = new ConfigReader();
		
	}
	
	@FindBy (id = "username")
	WebElement clederUsername;
	
	@FindBy (id = "password")
	WebElement clederPassword;
	
	@FindBy (id = "login")
	WebElement clederLoginButton;
	
	
	public void loginCledgerAsAdmin() {
		clederUsername.sendKeys(ConfigReader.getProperty("cledger_admin_username"));
		
		clederPassword.sendKeys(ConfigReader.getProperty("cledger_admin_password"));
		
		clederLoginButton.click();
		}
}

package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CLedgerHomePage {

	WebDriver driver;

	public CLedgerHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (xpath = "//a[normalize-space()='Tasks']")
	WebElement Tasks; 
	
	@FindBy (xpath = "//a[normalize-space()='Profile Token List']")
	WebElement ProfileTokenList; 
	
	public void clickTasks() {
		Tasks.click();
	}
	
	public void clickProfileTokenList() {
		ProfileTokenList.click();
	}
}

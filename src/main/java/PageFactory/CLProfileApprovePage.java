package PageFactory;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import TestUtils.ConfigReader;

public class CLProfileApprovePage {

	WebDriver driver;

	public CLProfileApprovePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="activityAccount")
	WebElement ActivityElementDropDown;
	
	@FindBy(xpath="//input[@name='_action_saveFromLotus']")
	WebElement Save;
	
	public void clickSave() {
	    Save.click();
	}
	
	public void selectActivityAccount() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(ActivityElementDropDown));
	    
		Select sel = new Select(ActivityElementDropDown);
		sel.selectByContainsVisibleText(ConfigReader.getProperty("liability_account"));
	}
	
	public void scrollToSaveButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(Save)); // Wait until the element is visible
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView(true);", Save);
	}
	
	
}

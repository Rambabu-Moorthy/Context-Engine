package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CLProfileTokenListPage {

	WebDriver driver;

	public CLProfileTokenListPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (xpath = "//a[@class='create']")
	WebElement NewLotusTokenFile;
	
	@FindBy (xpath = "//tbody/tr[1]/td[6]/a[1]")
	WebElement View;
	
	@FindBy (xpath = "//div[@role='status']")
	WebElement FileUploadSuccess;
	
	public void clickNewLotusTokenFile() {
		NewLotusTokenFile.click();
	}
	
	public void clickView() {
		View.click();
	}
	
	public boolean createdxmlFileUpload() {
		return FileUploadSuccess.isDisplayed();
	}
}

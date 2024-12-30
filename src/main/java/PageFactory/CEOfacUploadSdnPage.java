package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CEOfacUploadSdnPage {

	 WebDriver driver;
		
		public CEOfacUploadSdnPage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		@FindBy (xpath="//a[normalize-space()='Transaction']")
		WebElement Transaction;
		
		@FindBy (partialLinkText = "listAll")
		WebElement ListAll;
		
		public void clickTransaction() {
			Transaction.click();
		}
		
		public void clickListAll() {
			ListAll.click();
		}
}

package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CEOfacUploadPopUp {

	 WebDriver driver;
		
		public CEOfacUploadPopUp(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		@FindBy (id="onuploadClick")
		WebElement uploadClick;
		
		public void OfacUploadClick() {
			uploadClick.click();
		}
}

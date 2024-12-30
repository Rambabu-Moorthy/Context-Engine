package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CELoginPage {
	   WebDriver driver;
		
		public CELoginPage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		@FindBy (id="username")
		WebElement ceUserName;
		
		@FindBy (id="password")
		WebElement cePassword;
		
		@FindBy (id="login")
		WebElement ceLoginButton;
		
		public void enterCeUsername(String username) {
			ceUserName.sendKeys(username);
		}
		
		public void enterCePassword(String password) {
			cePassword.sendKeys(password);
		}
		
		public void clickCeLoginButton() {
			ceLoginButton.click();;
		}
}

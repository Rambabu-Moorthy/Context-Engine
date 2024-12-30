package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CLedgerCreateLotusTokenFilePage {

	 WebDriver driver;
		
		public CLedgerCreateLotusTokenFilePage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}
		
		@FindBy (xpath = "//input[@id='token']")
		WebElement Token;
		
		@FindBy (xpath = "//input[@id='create']")
		WebElement Create;
		
		@FindBy (id = "tokenFile")
		WebElement chooseXml;
		
		public void enterToken(String tokenNum) {
			Token.sendKeys(tokenNum);
		}
		
		public void clickCreate() {
			Create.click();
		}
		
		public void chooseFile(String filePath) {
			chooseXml.sendKeys(filePath);
		}
}

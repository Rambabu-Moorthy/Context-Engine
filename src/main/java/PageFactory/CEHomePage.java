package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CEHomePage {

	WebDriver driver;
	
	public CEHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (xpath = "//a[normalize-space()='List All']")
	WebElement ListAll;
	
	//Customer name

	@FindBy (xpath = "//tbody/child::tr[5]/child::td[2]/p")
	WebElement TransactionTypeTextCustomer;
	
	@FindBy (xpath = "//tbody/child::tr[5]/child::td[3]/p/small")
	WebElement NameTextCustomer;
	
	// Legal Rep one
	
	@FindBy (xpath = "//tbody/child::tr[3]/child::td[2]/p")
	WebElement TransactionTypeTextLegalRepOne;
	
	@FindBy (xpath = "//tbody/child::tr[3]/child::td[3]/p/small")
	WebElement NameTextLegalOne;
	
	//Legal Rep Two
	
	@FindBy (xpath = "//tbody/child::tr[1]/child::td[2]/p")
    WebElement TransactionTypeTextLegalRepTwo;
	
	@FindBy (xpath = "//tbody/child::tr[1]/child::td[3]/p/small")
	WebElement NameTextLegalTwo;
	
	
	public void clickListAll() {
		ListAll.click();
	}
	
	//customer name checking
	public String extractTransactionTypeCustomer() {
		return TransactionTypeTextCustomer.getText().trim();
	}
	
	public String extractNameTextCustomer() {
		return NameTextCustomer.getText().trim();
	}
	
	//LegalRep One
	public String extractTransactionTypeLegalOne() {
		return TransactionTypeTextLegalRepOne.getText().trim();
	}
	
	public String extractNameTextLegalOne() {
		return NameTextLegalOne.getText().trim();
	}
	
	//LegalRep two
		public String extractTransactionTypeLegalTwo() {
			return TransactionTypeTextLegalRepTwo.getText().trim();
		}
		
		public String extractNameTextLegalTwo() {
			return NameTextLegalTwo.getText().trim();
		}
}

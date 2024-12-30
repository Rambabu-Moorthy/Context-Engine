package StepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import PageFactory.CEOfacUploadSdnPage;
import PageFactory.CEHomePage;
import PageFactory.CELoginPage;
import PageFactory.CEOfacUploadPopUp;
import PageFactory.CLProfileApprovePage;
import PageFactory.CLProfileTokenListPage;
import PageFactory.CLedgerCreateLotusTokenFilePage;
import PageFactory.CLedgerHomePage;
import PageFactory.CLedgerLoginPage;
import TestUtils.ConfigReader;
import TestUtils.CustomerOnboardXml;
import TestUtils.ExcelReader;
import TestUtils.WebDriverUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CustomerOnboardStepDef {

	private static final Logger log = LogManager.getLogger(CustomerOnboardStepDef.class);
	WebDriver driver;
	Map<String, String> customerData;
	String firstTabHandle;
	String secondTabHandle = null;

	ConfigReader reader;
	CLedgerLoginPage cledgerLogin;
	CLedgerHomePage cledgerHome;
	CLProfileTokenListPage clprofileTokenList;
	CLedgerCreateLotusTokenFilePage clcreateLotusTokenFile;
	CLProfileApprovePage clprofileApprove;
	CELoginPage celoginPage;
	CEOfacUploadPopUp ceofacUplaodPopup;
	CEOfacUploadSdnPage ceofacUploadSdnPage;
	CEHomePage cehomePage;
//	ExcelReader excelread;

	public CustomerOnboardStepDef() {
		try {
			log.info("get the launched webdriver");
			this.driver = WebDriverUtils.getDriver();

			// Initialize all page objects here
			reader = new ConfigReader();
			cledgerLogin = new CLedgerLoginPage(driver);
			cledgerHome = new CLedgerHomePage(driver);
			clprofileTokenList = new CLProfileTokenListPage(driver);
			clcreateLotusTokenFile = new CLedgerCreateLotusTokenFilePage(driver);
			clprofileApprove = new CLProfileApprovePage(driver);
			celoginPage = new CELoginPage(driver);
			ceofacUplaodPopup = new CEOfacUploadPopUp(driver);
			ceofacUploadSdnPage =  new CEOfacUploadSdnPage(driver);
			cehomePage = new CEHomePage(driver); 

		} catch (Exception e) {
			log.error("Exception in CustomerOnboardStepDef constructor: ", e);
		}
	}

	@Given("launch cledger and login as admin")
	public void launch_cledger_and_login_as_admin() {
		log.info("launching c_ledger url");
		driver.get(ConfigReader.getProperty("cledger_url_qa"));
		
		log.info("getting the first chrome tab handle and store it");
		firstTabHandle = driver.getWindowHandle();

		log.info("entered credentials and click login the c_ledger");
		cledgerLogin.loginCledgerAsAdmin();

		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getProperty("cledger_home_Page_url"));
		log.info("Confirms that we ar in the home page of cledger");
	}

	@And("Prepare with the xml file") // get date from excel and create xml file
	public void prepare_with_the_xml_file() {
		log.info("preparing xml file to upload");
		String excelFilePath = System.getProperty("user.dir") + ConfigReader.getProperty("customer_onboard_excel_data");
		String templateXmlPath = System.getProperty("user.dir") + ConfigReader.getProperty("sample_xml_file_path");
		String outputFolderPath = System.getProperty("user.dir") + ConfigReader.getProperty("output_file_path");

		try {
			// Read data from Excel
			// Map<String, String> customerData =
			// ExcelReader.readCustomerData(excelFilePath);

			customerData = ExcelReader.readCustomerData(excelFilePath);

			// Generate XML
			CustomerOnboardXml.generateXmlFromTemplate(customerData, templateXmlPath, outputFolderPath);
		} catch (IOException e) {
			log.error("Failed to read customer data from Excel file: ", e);
			return; // Stop further execution
		} catch (Exception e) {
			log.error("Error while generating XML file: ", e);
		}
	}

	@When("Upload xml file")
	public void upload_xml_file() throws InterruptedException {
		try {
			cledgerHome.clickTasks();
			log.info("clicked Tasks menu from the home page");

			cledgerHome.clickProfileTokenList();
			log.info("clicked profile token list from Tasks");

			clprofileTokenList.clickNewLotusTokenFile();
			log.info("click New Lotus Token Profile");

			clcreateLotusTokenFile.enterToken(customerData.get("custinfo.TOKEN"));
			log.info("enter token number");

			clcreateLotusTokenFile.chooseFile(selectXmlFileFromProject());
			log.info("select xml file from the project directory");

			clcreateLotusTokenFile.clickCreate();
			Thread.sleep(1000);
			log.info("click create");

			clcreateLotusTokenFile.clickCreate();
			log.info("click continue");

			Assert.assertEquals(true, clprofileTokenList.createdxmlFileUpload());
			// clcreateLotusTokenFile.clickContinue();
		} catch (Exception e) {
			// Handle all exceptions (InterruptedException, NoSuchElementException, etc.)
			log.error("An error occurred during XML file upload: " + e.getMessage());
		}
		
//		System.out.println("skipping for now");
	}

	@And("Approve xml")
	public void approve_xml() {
		try {
			clprofileTokenList.clickView();
			log.info("click VIEW to approve the token xml file");

			clprofileApprove.scrollToSaveButton();                    
			log.info("scroll down until Save button visible");

			clprofileApprove.selectActivityAccount();
			log.info("selet pool account to map to the newly created account");

			clprofileApprove.clickSave();
			log.info("click Save button");
		} catch (Exception e) {
			// Handle all exceptions (InterruptedException, NoSuchElementException, etc.)
			log.error("An error occurred approving the xml file: " + e.getMessage());
		}
	}

	@Then("open another tab to launch ce")
	public void open_another_tab_to_launch_ce() {
		try {
			((JavascriptExecutor) driver)
					.executeScript("window.open('https://trpps.cbwmoney.com/BiReport/login/auth', '_blank');");
			log.info("Open a new tab using JavaScript to navigate to Context Engine URL");
			
			Set<String> allWindows = driver.getWindowHandles();
			
			for(String handle : allWindows) {
				if(!handle.equals(firstTabHandle)) {
					secondTabHandle = handle;
					break;
				}
			}
		} catch (Exception e) {
			log.error("An error occurred in launching ce in new chrome tab: " + e.getMessage());
		}
	}

	@When("login as admin")
	public void login_as_admin() {
		try {
			
			driver.switchTo().window(secondTabHandle);
			log.info("switching to second tab - CE");
			
			celoginPage.enterCeUsername(ConfigReader.getProperty("ceadmin"));
			log.info("enter ce username");

			celoginPage.enterCePassword(ConfigReader.getProperty("cepassword"));
			log.info("enter ce password");

			celoginPage.clickCeLoginButton();
			log.info("click ce login button");
			
			ceofacUplaodPopup.OfacUploadClick();
			log.info("click upload button from pop-up");
			
			ceofacUploadSdnPage.clickTransaction();
			log.info("click Transaction");
			
			cehomePage.clickListAll();
			log.info("click ListAll");

		} catch (Exception e) {
			log.error("An error occurred while login ce as admin: " + e.getMessage());
		}
	}
	
	
	@Then("verify the entry for customer and legalrep onboard")
	public void verify_the_entry_for_customer_and_legalrep_onboard() {
		
		// check the transation type is PROFILE_CREATION and the customer name given as per the excel sheet
		Assert.assertEquals(cehomePage.extractTransactionTypeCustomer(), "PROFILE_CREATION", "Customer Transaction type does not match!");
		Assert.assertEquals(cehomePage.extractNameTextCustomer(), customerData.get("custinfo.ACCOUNTHOLDERNAME"), "Customer name in CE differs from Excel");
		
		Assert.assertEquals(cehomePage.extractTransactionTypeLegalOne(), "PROFILE_CREATION","Legal One Transaction type does not match!" );
		Assert.assertEquals(cehomePage.extractNameTextLegalOne(), customerData.get("legalone.ACCOUNTHOLDERNAME"), "Legal One name in CE differs from Excel");
		

		Assert.assertEquals(cehomePage.extractTransactionTypeLegalTwo(), "PROFILE_CREATION","Legal Two Transaction type does not match!" );
		Assert.assertEquals(cehomePage.extractNameTextLegalTwo(), customerData.get("legaltwo.ACCOUNTHOLDERNAME"));
	}

	public String selectXmlFileFromProject() {
		// Define the path of the dynamicFiles folder
		File dynamicFilesFolder = new File("src/test/resources/dynamicFiles");

		// Get the list of files in the folder
		File[] files = dynamicFilesFolder.listFiles();

		if (files == null || files.length == 0) {
			System.out.println("No files found in the dynamicFiles folder.");
			return null;
		}
		// Choose the first file from the list (you can change this logic if needed)
		File fileToUpload = files[0];
		String filePath = fileToUpload.getAbsolutePath();
		return filePath;

	}
}

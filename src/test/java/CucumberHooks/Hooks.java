package CucumberHooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.MediaEntityBuilder;
//import TestUtils.ExtentReportManager;
//import TestUtils.ScreenshotUtils;
import TestUtils.WebDriverUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	
	private static final Logger log = LogManager.getLogger(Hooks.class);

	private WebDriver driver;

	@Before
	public void driverSetup(Scenario scenario) {
		try {
			if (driver == null) {
				
				driver = WebDriverUtils.getDriver(); // This will use the existing driver or initialize a new one.
			}
			
			// Initialize Extent Reports for this scenario
	        
	        //ExtentReportManager.createTest(scenario.getName()); // Create a test in the Extent Report
	        
		} catch (Exception e) {
			log.error("Exception occurred while initializing the driver: ", e);
		}
	}


//	@After
//	public void tearDown(Scenario scenario) {
//		try {
//			// Log test result in Extent Reports
//		if (scenario.isFailed()) {
//			
//				String screenshotName = scenario.getName().replaceAll(" ", "_") + "_" + System.currentTimeMillis()
//						+ ".png";
//				ScreenshotUtils.captureScreenshot(WebDriverUtils.getDriver(), screenshotName);
//				
//				// Attach the screenshot to Extent Report
//	            String screenshotPath = "./screenshots/" + screenshotName;
//	            ExtentReportManager.getTest().fail("Scenario Failed", 
//	                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
//	            
//	            
//	        } else {
//	            // Mark the scenario as passed in the Extent Report
//	            ExtentReportManager.getTest().pass("Scenario Passed");
//	        }
//			} catch (Exception e) {
//				log.error("Failed to capture screenshot", e);
//			}
//		
//		
//	    WebDriverUtils.quitDriver();
//	    // Flush the Extent Report after each test
//	    ExtentReportManager.flushReports();
//	}

}

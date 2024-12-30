package TestUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverUtils {
	private static final Logger log = LogManager.getLogger(WebDriverUtils.class);
	
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Prevent instantiation
    private WebDriverUtils() {}
    
    // Thread-safe WebDriver initialization
    public static synchronized WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
        	ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            WebDriver driver = new ChromeDriver(options); // Create new driver instance
            driver.manage().window().maximize(); // Maximize window
            driverThreadLocal.set(driver); // Assign driver to the thread
        }
        log.info("returning the thread local webdriver");
        return driverThreadLocal.get(); // Return thread-safe driver
    }
    
    // Thread-safe WebDriver teardown
    public static synchronized void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit(); // Quit driver
            driverThreadLocal.remove(); // Remove from ThreadLocal
        }
    }
    
    
	/*
	 * private static WebDriver driver;
	 * 
	 * 
	 * // Private constructor to prevent instantiation private WebDriverUtils() { }
	 * 
	 * // Synchronized method to ensure thread-safety in parallel execution public
	 * static synchronized WebDriver getDriver() { try { if (driver == null) {
	 * 
	 * log.info("WEBDRIVERUTILS.GETDRIVER method - Initializing new chrome driver");
	 * driver = new ChromeDriver();
	 * 
	 * log.info("WEBDRIVERUTILS.GETDRIVER method - maximizing the chrome browser");
	 * driver.manage().window().maximize(); } } catch (Exception e) {
	 * log.error("Exception occurred while initializing or using the WebDriver: ",
	 * e); } log.info("WEBDRIVERUTILS.GETDRIVER method - returns the driver");
	 * return driver; }
	 * 
	 * // Method to quit the driver public static void quitDriver() { if (driver !=
	 * null) { driver.quit(); driver = null; } }
	 */
}

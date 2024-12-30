package LoggerPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger {

	 private static final Logger logger = LogManager.getLogger(TestLogger.class);

	    public static void main(String[] args) {
	        logger.info("This log will be saved in the project directory under the 'logs' folder.");
	        logger.debug("This is a debug-level message.");
	        logger.error("This is an error-level message.");
	    }
}

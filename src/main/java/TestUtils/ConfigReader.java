package TestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties properties;
	
	 // Static block to load the properties file when the class is loaded
	static {
		try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties")) {
           properties = new Properties();
           properties.load(fileInputStream); // Load properties from the file
       } catch (IOException e) {
           e.printStackTrace(); // Print any IO exceptions if the file is not found or cannot be read
       }
	}
	
	 // Method to get the value of a property by key
   public static String getProperty(String key) {
       return properties.getProperty(key);
   }

}

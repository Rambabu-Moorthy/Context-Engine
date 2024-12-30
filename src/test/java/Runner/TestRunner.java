package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/FeatureFiles/CustomerOnboard.feature",    // Path to feature files
	    glue = "StepDefinitions",    // Path to step definition classes
	    plugin = {"pretty", "html:target/cucumber-reports"}   // Report generation
	)

public class TestRunner extends AbstractTestNGCucumberTests {

}

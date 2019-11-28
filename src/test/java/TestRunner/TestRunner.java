package TestRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(
features = "src/test/java/Features"
,glue= {"src/test/java/StepDefinitions"}
,tags= {"@SmokeTest"}
,dryRun =false
 )
 
public class TestRunner {
 
}
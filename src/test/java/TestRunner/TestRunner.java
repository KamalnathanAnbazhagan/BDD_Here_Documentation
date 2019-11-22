package TestRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(
 features = "src/test/java/Features",
 //dryRun=true
glue={"src/main/java/StepDefinition"}
,tags= {"@SmokeTest"}

 )
 
public class TestRunner {
 
}
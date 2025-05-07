package stepDefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",  // Path to your .feature files
    glue = "stepDefinitions",                  // Path to your step definition classes
    plugin = {"pretty", "html:target/cucumber-report.html"}  // Optional: generate reports
)
public class RunCucumberTest {
}

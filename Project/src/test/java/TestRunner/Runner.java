package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		
		features="src/test/java/Feature/fea.feature",
		glue= "Steps",
		dryRun=false,
		monochrome=true,
				plugin = {"pretty", "html:target/cucumber-reports.html"}
		)
public class Runner  {

}

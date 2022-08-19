package cucumberOptions;

import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//@RunWith(Cucumber.class)
@CucumberOptions

(
features="src/test/java/features/ClientManagement.feature",
//features="src/test/java/features",
glue="stepDefinitions",monochrome=true,tags="@RegressionTest",
//glue="stepDefinitions",stepNotifications=true,tags="@SmokeTest or @RegressionTest",
//glue="stepDefinitions",stepNotifications=true,tags="@SmokeTest and @RegressionTest",
//glue="stepDefinitions",stepNotifications=true,tags="not @SanityTest"
plugin= {"html:target/cucumber.html", "json:target/cucumber.json","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:target/failed_scenarios.txt"}
)


//AbstractTestNGCucumberTests - This is class which provided all wrappers to run our cucumber test through TestNG
public class TestngTestRunnerTest extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel=true)
	public Object[][] scenarios()
	{
		return super.scenarios();
	}
}

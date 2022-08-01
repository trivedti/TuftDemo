package cucumberOptions;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


//Pass feature and step definition file here

@RunWith(Cucumber.class)
@CucumberOptions
(
features="src/test/java/features/SignIn.feature",
glue="stepDefinitions",stepNotifications=true,tags="@MobileTest",
//glue="stepDefinitions",stepNotifications=true,tags="@SmokeTest or @RegressionTest",
//glue="stepDefinitions",stepNotifications=true,tags="@SmokeTest and @RegressionTest",
//glue="stepDefinitions",stepNotifications=true,tags="not @SanityTest",

//monochrome is used to print neat and proper formated output in to eclipse console
monochrome = true,
// Plugin is used for generate execution report either in to HTML , JSON or XML.  Basically XML is using for CICD using Jenkins
plugin= {"pretty","html:target/TT.html","json:target/TT.json","junit:target/TT.xml"}

//dryRun=true
//dryRun= true attribute is used to check whether all features steps are mapped with step definition or not. if not then it will ask you to map with step definition
// it will not go inside and execute the code
)

public class TestRunner {

}
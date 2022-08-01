package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.app.enums.ClientRegistrationPageEnum;
import cucumber.app.enums.FileNames;
import cucumber.app.enums.LoginPageEnum;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utils.GenericUtilsDummy;
import utils.PropertyFileOperations;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;





public class LoginPage {

	public WebDriver driver;
	public LoginPageEnum LoginPage;
	public GenericUtilsDummy genericUtils;
	static Logger log = LogManager.getLogger(LoginPage.class);
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
	}

	
// These are page objects	
	
	By LoginButton=By.xpath("//button[@type='submit']"); 
	By EnterEmailAddress=By.xpath("//input[@type='email']"); 
	By EnterPassword=By.xpath("//input[@type='password']"); 
	By AssertDashboardMenu=By.xpath("//a[contains(text(),'Dashboard')]");
	 
	 
	 
	
// Thease are action methods -- 
	
	 
	  public void user_is_on_login_page() {
	  boolean test=driver.findElement(LoginButton).isEnabled(); 
	  if(test) {
          log.debug("STEP: " + LoginButton + " is present on login screen"); 
    }
    else {
        log.debug("STEP: " + LoginButton + " is not present on login screen"); 
    }
	  }
	 
	 

public String user_enters_email_as(String Useremail) throws Throwable 
{
	
	
	driver.findElement(EnterEmailAddress).sendKeys(Useremail);
	 log.debug("STEP: " + EnterEmailAddress + " is present on login screen"); 
	 log.debug("STEP: " + Useremail + " is set inside element");
	return Useremail;
	
	
	
}
public String user_enters_password_as(String Password) throws Throwable 
{
	
	driver.findElement(EnterPassword).sendKeys(Password);
	log.debug("STEP: " + EnterPassword + " is present on login screen"); 
	 log.debug("STEP: " + Password + " is set inside element");
	return Password;
	
	
	
}

  public void click_on_login() throws Throwable { 
  WebElement element = driver.findElement(LoginButton);
  ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
  driver.findElement(LoginButton).click();
  log.debug("STEP: " + LoginButton + " is present on login screen"); 
  log.debug("STEP: " + LoginButton + " is clicked");
  Thread.sleep(5000);
  
  }
  
  public String user_is_on_Dahboard_page(String Dashboard) {
	 
	  String dashboardmenu=driver.findElement(AssertDashboardMenu).getText(); 
	  
	  if(dashboardmenu.equalsIgnoreCase(Dashboard)) {
          log.debug("STEP: " + AssertDashboardMenu + " text is equal to" + Dashboard);

          
    }
    else {
        log.debug("STEP: " + AssertDashboardMenu + " text is not equal to" + Dashboard);    }
	return Dashboard;
	
	  }
  @Then("^close browser$") 
  public void close_browser() throws Throwable 
  {
  driver.quit();
  }
 
}
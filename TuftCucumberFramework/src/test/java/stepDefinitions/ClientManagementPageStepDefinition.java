package stepDefinitions;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import cucumber.app.constants.ApplicationConstants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.PageObjectManager;
import pageObjects.ClientManagementPage;
import pageObjects.LoginPage;
import utils.GenericUtilsDummy;
import utils.TestContextSetup;


public class ClientManagementPageStepDefinition {
//Initialized and declared driver out side of the method to accessible for entire 
//public WebDriver driver;

PageObjectManager pageObjectManager; 
// Call object of other classes
TestContextSetup testContextSetup;
GenericUtilsDummy genericUtils;
//SearchInsurancePage searchInsurancePage;
//SearchInsurancePage searchInsurancePage=new SearchInsurancePage(testContextSetup.driver);
//pageObjectManager=new PageObjectManager(testContextSetup.driver); 

//Spring Framework,EJB -Cucumber dependancy injection -Cucumber PicoContainer
//Create a new TestContextSetupClass file under Utility
// Will create one constructor with the same class name
// This constructor will automatically called when we create objects of this class
// Will pass instance of TestContextSetup
// Constructor
//=========
public ClientManagementPageStepDefinition (TestContextSetup testContextSetup)
{
	this.testContextSetup= testContextSetup;
}

	@Given("^Validate the browser$")
	public void validate_the_browser() throws IOException {
		 
		 //System.setProperty("webdriver.chrome.driver","D:\\SynovergeAutomation\\CucumberFramework\\src\\test\\resources\\chromedriver.exe");
		// testContextSetup.driver=new ChromeDriver();
		 //testContextSetup.driver.manage().window().maximize();
		//testContextSetup.testBase.WebDriverManager();
		genericUtils=new GenericUtilsDummy();
		genericUtils.initBrowser(ApplicationConstants.browser, ApplicationConstants.URL);
	}

	  @Given("^User is on login page$") 
	  public void user_is_on_login_page() throws Throwable 
	  { 
	   ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	   clientPage.user_is_on_login_page();
	  
	  }
 
  

@When("^User enters Email as (.+)$")
public void user_enters_email_as_and_password_as(String email) throws Throwable 
{
ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
clientPage.user_enters_email_as(email);
}

@And("^User enters Password as (.+)$")
public void user_enters_password_as(String password) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.user_enters_password_as(password);
	}



@And("^Click on Login$")
public void click_on_login() throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.click_on_login();
}
/*
 * @Then("^User moves to Dashboard page and verify present text as (.+) in to present element$"
 * ) public void
 * user_moves_to_dashboard_page_and_verify_present_text_as_in_to_present_element
 * (String dashboard) throws Throwable { ClientManagementPage
 * clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
 * clientPage.user_is_on_Dahboard_page(dashboard); }
 */


/*
 * @Given("^Validate User is on Dashboard Page and verify present text as (.+) in to present element$"
 * ) public void
 * validate_user_is_on_dashboard_page_and_verify_present_text_as_in_to_present_element
 * (String dashboard) throws Throwable { ClientManagementPage
 * clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
 * clientPage.user_is_on_Dahboard_page(dashboard);
 * 
 * }
 */

@And("^User click on Add client menu$")
public void user_click_on_add_client_menu() throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.click_on_AddClient();
}

@And("^User click on Add new client button$")
public void user_click_on_add_new_client_button() throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.click_on_AddNewClient();
}

@Then("^Add or edit client pop up is displayed as (.+)$")
public void add_or_edit_client_pop_up_is_displayed_as(String AddClientpopup) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.user_is_on_AddEditClient_page();
}

@And("^User enters Firs Name as (.+) in to personal details$")
public void user_enters_firs_name_as_in_to_personal_details(String firstname) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Enters_Firstname_as(firstname);
}

@And("^User enters Last Name as (.+) in to personal details$")
public void user_enters_last_name_as_in_to_personal_details(String lastname) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Enters_Lastname_as(lastname);
}

@And("^User enters Email Address as (.+) in to contact details$")
public void user_enters_email_address_as_in_to_contact_details(String emailaddress) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Enters_EmailAddress_as(emailaddress);
}

@And("^User enters Mobile Number as (.+) in to contact details$")
public void user_enters_mobile_number_as_in_to_contact_details(String mobilenumber) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Enters_MobileNumber_as(mobilenumber);
}

@And("^User enters address as (.+) in to contact details$")
public void user_enters_address_as_in_to_contact_details(String address) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Enters_ContactAddress(address);
}

@And("^User enters Emergency FullName as (.+) in to Emergency contact details$")
public void user_enters_emergency_fullname_as_in_to_emergency_contact_details(String efullname) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Emergency_FullName(efullname);
}

@And("^User enters Emergency Telephone as (.+) in to Emergency contact details$")
public void user_enters_emergency_telephone_as_in_to_emergency_contact_details(String etelephone) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Emergency_Telephone(etelephone);
}

@And("^User enters Practice Name as (.+) in to Veterinary Practice details$")
public void user_enters_practice_name_as_in_to_veterinary_practice_details(String vpractice) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.PracticName(vpractice);
	}

@And("^User enters Notes as (.+) in to bottom of the page$")
public void user_enters_notes_as_in_to_bottom_of_the_page(String notes) throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.Notes(notes);
   
}

@And("^Click on Save Change button$")
public void click_on_save_change_button() throws Throwable {
	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
	clientPage.clickSaveChangeButton();
}
    @Then("^newly added client is saved$")
    public void newly_added_client_is_saved(String ClientNameForSearch) throws Throwable {
    	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
    	clientPage.SearchSaveClient(ClientNameForSearch);
    }
    @And("^close browser$")
    public void close_browser() throws Throwable {
    	ClientManagementPage clientPage=testContextSetup.pageObjectManager.getClientRegistrationPage();
    	clientPage.terminateBrowser();
    }
}




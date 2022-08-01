package pageObjects;

import java.awt.Robot;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.app.enums.ClientRegistrationPageEnum;
import cucumber.app.enums.FileNames;

import utils.GenericUtilsDummy;
import utils.PropertyFileOperations;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClientManagementPage extends GenericUtilsDummy {

	public WebDriver driver;
	public ClientRegistrationPageEnum ClientregistrationPage;
	public GenericUtilsDummy genericUtils;
	static Logger log = LogManager.getLogger(ClientManagementPage.class);

	/*
	 * public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	 * public static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();
	 */

	// private Logger log = LogManager.getLogger(this.getClass());

	PropertyFileOperations locators;
	// private static ClientManagementPage clientPage;

	// private ClientManagementPage()
	{
		locators = new PropertyFileOperations(FileNames.RegistrationPage);
		log.info("STEP: Assets Page Locators loaded into memory");
	}

	// public static WebDriver driver;
	// public ClientManagementPage(WebDriver driver) throws IOException {
	public ClientManagementPage() throws IOException {

		// this.driver=getDriver();
		// TODO Auto-generated constructor stub
	}

	/*
	 * public ClientManagementPage(WebDriver driver) {
	 * 
	 * 
	 * //this.driver=driver; super(driver); driver=super.getDriver();
	 * this.driver=driver; }
	 */

// These are page objects	
	/*
	 * String EnterEmailAdddddresss = "[xpath]:-//input[@type='email']"; String
	 * LoginButtons = "[xpath]:-"; By EnterEmailAddress =
	 * By.xpath("//input[@type='email']"); By EnterPassworddddd =
	 * By.xpath("//input[@type='password']");
	 */
	

// Thease are action methods -- 

	public static WebDriver getDriver() {
		return driverThread.get();
	}

	public void user_is_on_login_page() {
		boolean test = isElementDisplay(locators.getKey("EnterEmail"));
		if (test) {
			log.info("STEP: " + "EnterEmail" + " is present on login screen");
		} else {
			log.info("STEP: " + "EnterEmail" + " is not present on login screen");
		}
	}

	public void user_enters_email_as(String Useremail) throws InterruptedException {
		String EmailTextBox = String.format(locators.getKey("EnterEmail"), Useremail);
		enterText(EmailTextBox, Useremail);
		log.info("STEP: Entered User email Successfully" + Useremail);

	}

	public void user_enters_password_as(String Password) throws InterruptedException {
		String PassTextBox = String.format(locators.getKey("EnterPassword"), Password);
		enterText(PassTextBox, Password);
		log.info("STEP:Entered User Password Successfully" + Password);

	}

	public void click_on_login() throws Throwable { 
		clickOnElement(String.format(locators.getKey("LoginButton")));
		log.info("STEP:Click on Login Button Successfully" + "LoginButton");
		Thread.sleep(5000);
	}

	public void user_is_on_Dahboard_page()  {
		boolean test = isElementDisplay(locators.getKey("AssertDashboardMenu"));
		if (test) {
			log.info("STEP: " + "AssertDashboardMenu" + " is present on login screen");
		} else {
			log.info("STEP: " + "AssertDashboardMenu" + " is not present on login screen");
		}

	}
		

	public void click_on_AddClient() throws Throwable {
		clickOnElement(String.format(locators.getKey("ClickAddClientmenu")));
		log.info("STEP:Click on Add Client Menu Successfully" + "ClickAddClientmenu");
		Thread.sleep(4000);

	}

	public void click_on_AddNewClient() throws Throwable {
		clickOnElement(String.format(locators.getKey("AddClientButton")));
		log.info("STEP:Click on Add Client Button Successfully" + "AddClientButton");
		Thread.sleep(5000);

	}

	public void user_is_on_AddEditClient_page()  {
		boolean test = isElementDisplay(locators.getKey("EnterFirstName"));
		if (test) {
			log.info("STEP: " + "EnterFirstName" + " is present on login screen");
		} else {
			log.info("STEP: " + "EnterFirstName" + " is not present on login screen");
		}
	}
	
	
	public void Enters_Firstname_as(String EFName) throws InterruptedException {
		String EnterFirstNameTextBox = String.format(locators.getKey("EnterFirstName"), EFName);
		enterText(EnterFirstNameTextBox, EFName);
		log.info("STEP:Entered User FirstName Successfully" + EFName);
		//Thread.sleep(5000);

	}
	
	public void Enters_Lastname_as(String LSName) throws InterruptedException {
		String EnterLastNameTextBox = String.format(locators.getKey("EnterLastName"), LSName);
		enterText(EnterLastNameTextBox, LSName);
		log.info("STEP:Entered User LastName Successfully" + LSName);
		//Thread.sleep(5000);

	}
	
	public void Enters_EmailAddress_as(String EmailAddress) throws InterruptedException {
		String EnterMailTextBox = String.format(locators.getKey("EnterClientEmail"), EmailAddress);
		enterText(EnterMailTextBox, EmailAddress);
		log.info("STEP:Entered User FirstName Successfully" + EmailAddress);

	}
	
	public void Enters_MobileNumber_as(String CMobile) throws InterruptedException {
		String EnterMailTextBox = String.format(locators.getKey("EnterClientMobile"), CMobile);
		enterText(EnterMailTextBox, CMobile);
		log.info("STEP:Entered User Mobile Successfully" + CMobile);
		//Thread.sleep(5000);

	}
	

	public void Enters_ContactAddress(String CAddress) throws Throwable {
		String EnterAddressTextBox = String.format(locators.getKey("EnterClientAddress"), CAddress);
		EnterWithActionDownEnter(EnterAddressTextBox,CAddress);
		log.info("STEP:Entered Address Successfully" + CAddress);
		//Thread.sleep(5000);

	}

	public void Emergency_FullName(String EmergencyFullName) throws InterruptedException {
		String EnterFullNameTextBox = String.format(locators.getKey("EnterClientEmergency"), EmergencyFullName);
		enterText(EnterFullNameTextBox, EmergencyFullName);
		log.info("STEP:Entered User FirstName Successfully" + EmergencyFullName);
		//Thread.sleep(5000);

	}
	public void Emergency_Telephone(String EmergencyTelephone) throws InterruptedException {
		String EEmergencyTelephoneTextBox = String.format(locators.getKey("EnterClientTelephone"), EmergencyTelephone);
		enterText(EEmergencyTelephoneTextBox, EmergencyTelephone);
		log.info("STEP:Entered User EmergencyTelephone Successfully" + EmergencyTelephone);

	}
	

	public void PracticName(String VPracticeName) throws Throwable {

		String VPTextBox = String.format(locators.getKey("EnterPracticeName"), VPracticeName);
		enterText(VPTextBox, VPracticeName);
		log.info("STEP:Entered User VPracticeName Successfully" + VPracticeName);
		//Thread.sleep(5000);

	}

	public void Notes(String FNotes) throws Throwable {

		String Notes = String.format(locators.getKey("EnterNotes"), FNotes);
		enterText(Notes, FNotes);
		log.info("STEP:Entered User Notes Successfully" + FNotes);
	}

	public void clickSaveChangeButton() throws Throwable {
		clickOnElement(String.format(locators.getKey("ClickOnSaveChanges")));
		log.info("STEP:Click on Add Client Save Button Successfully" + "ClickOnSaveChanges");

		Thread.sleep(2000);

	}

	public void SearchSaveClient(String SearchClient) throws Throwable {
		String SearchTextBox = String.format(locators.getKey("SearchClientText"), SearchClient);
		enterText(SearchTextBox, SearchClient);
		log.info("STEP:Entered Searched Successfully" + SearchClient);
		Thread.sleep(5000);

	}
	

	public void close_browser() throws Throwable {

		terminateBrowser();
		
	}
}
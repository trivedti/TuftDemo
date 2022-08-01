package pageObjects;
// In this file we are going to create all objects of page object files

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import utils.GenericUtilsDummy;

public class PageObjectManager {
	
	
	
	public WebDriver driver;
	public GenericUtilsDummy genericUtils;
	public ClientManagementPage clientManagementPage;
	public LoginPage loginPage;
	
	//public PageObjectManager(WebDriver driver)
	public PageObjectManager()
	{
		//this.driver = driver;
		//this.driver = driver;
	}

	
	public ClientManagementPage getClientRegistrationPage() throws IOException
	{
		//clientManagementPage = new ClientManagementPage(driver);
		clientManagementPage = new ClientManagementPage();

	return clientManagementPage;
	
	}
	
	public LoginPage getLoginPage()
	{
		loginPage = new LoginPage(driver);
	return loginPage;
	
	}
	
	public GenericUtilsDummy genericUtils() throws IOException
	{
		genericUtils=new GenericUtilsDummy();
		return genericUtils;
	}
			
}

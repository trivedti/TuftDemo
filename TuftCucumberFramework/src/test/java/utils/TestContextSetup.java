package utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import pageObjects.PageObjectManager;

public class TestContextSetup {

	public WebDriver driver;
	
	public PageObjectManager pageObjectManager;
	public TestBase testBase;
	public GenericUtilsDummy genericUtils;
	public String expectedPageTitle;
	public String Pagetitle;
	public String InsuranceName;
	public String expectedInsuranceName;
	public boolean visible;
	public TestContextSetup() throws IOException
	{
		testBase = new TestBase();
		//pageObjectManager = new PageObjectManager(testBase.WebDriverManager());
		pageObjectManager = new PageObjectManager();
		genericUtils = new GenericUtilsDummy();
		
		
		

	}
	
}

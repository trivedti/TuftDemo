package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.app.constants.ApplicationConstants;

public class TestBase {

	public WebDriver driver;
	ApplicationConstants applicationConstants;

	/*
	 * public WebDriver WebDriverManager() throws IOException { // Global properties
	 * is used to set dynamic browser and URL //FileInputStream fis = new
	 * FileInputStream(System.getProperty("user.dir")+
	 * "//src//test//resources//global.properties");
	 * 
	 * 
	 * // Global constants is used to set dynamic browser and URL
	 * 
	 * FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+
	 * "//src//main//java//cucumber//app//constants//ApplicationConstants.java");
	 * 
	 * //Properties prop = new Properties(); //prop.load(fis); //String url =
	 * prop.getProperty("QAUrl"); String url = applicationConstants.URL;
	 * 
	 * //String browser_properties = prop.getProperty("browser"); String
	 * browser_properties = applicationConstants.browser;
	 * 
	 * String browser_maven=System.getProperty("browser"); // result = testCondition
	 * ? value1 : value2
	 * 
	 * String browser = browser_maven!=null ? browser_maven : browser_properties;
	 * 
	 * 
	 * // This code is to close browser based notification window or popup
	 * 
	 * 
	 * if(driver == null) { if(browser.equalsIgnoreCase("chrome")) { ChromeOptions
	 * options = new ChromeOptions();
	 * options.addArguments("--disable-notifications");
	 * System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+
	 * "//src//test//resources//chromedriver.exe"); driver = new
	 * ChromeDriver(options);// driver gets the life
	 * driver.manage().window().maximize();
	 * 
	 * } if(browser.equalsIgnoreCase("firefox")) {
	 * System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+
	 * "//src//test//resources//geckodriver"); driver = new FirefoxDriver(); }
	 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	 * driver.get(url); }
	 * 
	 * return driver;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * if(driver == null) { if(browser.equalsIgnoreCase("chrome")) {
	 * System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+
	 * "//src//test//resources//chromedriver.exe"); driver = new ChromeDriver();//
	 * driver gets the life driver.manage().window().maximize(); }
	 * if(browser.equalsIgnoreCase("firefox")) {
	 * System.setProperty("webdriver.gecko.driver",
	 * "//src//test//resources//geckodriver"); driver = new FirefoxDriver(); }
	 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	 * driver.get(url); }
	 * 
	 * return driver;
	 * 
	 * }
	 */

}

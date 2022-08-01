package utils;

import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cucumber.app.constants.ApplicationConstants;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public  class GenericUtilsDummy extends TestBase{
	public static WebDriver driver;
	public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	public static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();
	static Logger log = LogManager.getLogger(GenericUtilsDummy.class);
	TestBase testBase=new TestBase();
	public GenericUtilsDummy() throws IOException
	{
		//this.driver = driver;
		//this.driver = testBase.driver;
	}
	public void initBrowser(String browserName, String url) {
		log.debug("STEP: " + browserName + " browser launched and load " + url + " in browser");
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driverThread.set(new ChromeDriver());
			break;

		default:
			break;
		}
		getDriver().manage().window().maximize();
		getDriver().get(url);
		log.debug("STEP: Explicite wait set on WebDriver " + ApplicationConstants.EXP_WAIT);
		waitThread.set(new WebDriverWait(getDriver(), ApplicationConstants.EXP_WAIT));
	}
	public static WebDriverWait getWait(){
		return waitThread.get();
	}
	

	public WebElement getElement(String locator) {
		WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));

		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element;
	}
	public JavascriptExecutor jsDriver(){
		 return (JavascriptExecutor) getDriver();
	}
	public static WebDriver getDriver() {
		return driverThread.get();
	}
	
	public String getLocatorType(String locator) {
		String type = null;
		try {
			type = locator.split(":-")[0].replace("[", "").replace("]", "").toLowerCase();
			log.debug("STEP: Locatoe type " + type + " in " + locator);
		} catch (Exception e) {
			log.fatal(e.getMessage());
			log.fatal(e.getCause());
		}
		return type;
	}
	public String getLocatorValue(String locator) {
		String value = null;
		try {
			value = locator.split(":-")[1];
			log.debug("STEP: Locatoe value" + value + " in " + locator);
		} catch (Exception e) {
			log.fatal(e.getMessage());
			log.fatal(e.getCause());
		}
		return value;
	}
	
	private By getLocator(String locator){
		String locatorType = getLocatorType(locator);
		String locatorValue = getLocatorValue(locator);
		
		switch(locatorType){
			case "xpath":
				return By.xpath(locatorValue);
			case "css":
				return By.cssSelector(locatorValue);
			case "id":
				return By.id(locatorValue);
			case "name"	:
				return By.name(locatorValue);
			case "className" :
				return By.className(locatorValue);
			case "linkText" :
				return By.linkText(locatorValue);
			case "partialLinkText" :	
				return By.partialLinkText(locatorValue);
		}
		return null;
	}
	
	
	public void SwitchWindowToChild()
	{
		Set<String> s1=driver.getWindowHandles();
		Iterator<String> i1 =s1.iterator();
		String parentWindow = i1.next();
		String childWindow = i1.next();
		driver.switchTo().window(childWindow);
	}
	
	public void clickOnElement(String locator) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		log.debug("STEP: click on element " + getLocatorValue(locator));
	}
	
	
	
	public boolean isElementDisplayed(String locator) {
		WebElement element = null;
		boolean elementDisplay;
		try {
			element = getElement(locator);
			elementDisplay = element.isDisplayed();
			log.debug("STEP: Element displayed in screen viewport");
		} catch (Exception e) {
			//element = getElement(locator);

			scrollTillElementDisplay(element);
			log.debug("STEP: Scroll till element to get visible in screen viewport");
			elementDisplay = element.isDisplayed();
			log.debug("STEP: Retry to element display in screen viewport and status " + element);
		}
		return elementDisplay;
	}
	public boolean isElementDisplay(String locator) {
		WebElement element = null;
		boolean elementDisplay;
		try {
			element = getElement(locator);
			elementDisplay = element.isDisplayed();
			log.debug("STEP: Element displayed in screen viewport");
		} catch (Exception e) {
			//element = getElement(locator);

			
			log.debug("STEP: Scroll till element to get visible in screen viewport");
			elementDisplay = element.isDisplayed();
			log.debug("STEP: Retry to element display in screen viewport and status " + element);
		}
		return elementDisplay;
	}
	
	public void scrollTillElementDisplay(WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(5000);
		je.executeScript("arguments[0].scrollIntoView(true)", element);
		log.debug("STEP: Scroll till element");
	}
	
	
	public void wait(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public String enterText(String locator, String strMessage) {
		getElement(locator).sendKeys(strMessage);
		log.debug("STEP: " + strMessage + " is set inside element");
		return strMessage;
	}
	public void actionEnter() {
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Enter tab", e.getCause());
		}
	}
	public void EnterWithActionDownEnter(String locator, String strMessage) {
		getElement(locator).sendKeys(strMessage);
		Actions actions = new Actions(getDriver());
		try {
			
			actions.sendKeys(Keys.DOWN).build().perform();
			actions.sendKeys(Keys.ENTER).build().perform();

		} catch (Exception e) {
			Assert.fail("Issue While Enter tab", e.getCause());
		}
	}
	
	public void refreshPage() {
		try {
			getDriver().navigate().refresh();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	public  void terminateBrowser() {
		getDriver().quit();
		log.debug("STEP - Browser Quit");
	}
}

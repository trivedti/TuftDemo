package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
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




public  class GenericUtils extends TestBase{
	public static WebDriver driver;
	public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	public static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();
	private static Robot robot = null;
	static Logger log = LogManager.getLogger(GenericUtilsDummy.class);
	TestBase testBase=new TestBase();
	public GenericUtils() throws IOException
	{
		//this.driver = driver;
		//this.driver = testBase.driver;
	}
	protected void initBrowser(String browserName, String url) {
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
	
	private static WebDriver getDriver() {
		return driverThread.get();
	}

	private JavascriptExecutor jsDriver(){
		 return (JavascriptExecutor) getDriver();
	}

	protected void terminateBrowser() {
		getDriver().quit();
		log.debug("STEP - Browser Quit");
	}

	private static WebDriverWait getWait(){
		return waitThread.get();
	}
	
	private String getLocatorType(String locator) {
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

	private String getLocatorValue(String locator) {
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

	protected WebElement getElement(String locator) {
		WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element;
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
	
	protected WebElement waitUntilElementIsClickable(String locator) {
		return getWait().until(ExpectedConditions.elementToBeClickable(getLocator(locator)));
	}
	
	protected List<WebElement> getElements(String locator) {
		return getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getLocator(locator)));
	}

	/**
	 * To find whether the WebElement is displayed on the webpage using
	 * WebElement, should return a boolean based on the evaluation
	 * 
	 * @param locator
	 * @return
	 */
	protected boolean isElementDisplayed(String locator) {
		WebElement element = null;
		boolean elementDisplay;
		try {
			element = getElement(locator);
			elementDisplay = element.isDisplayed();
			log.debug("STEP: Element displayed in screen viewport");
		} catch (Exception e) {
			scrollTillElementDisplay(element);
			log.debug("STEP: Scroll till element to get visible in screen viewport");
			elementDisplay = element.isDisplayed();
			log.debug("STEP: Retry to element display in screen viewport and status " + element);
		}
		return elementDisplay;
	}

	protected String getElementText(String locator) {
		return getElement(locator).getText();
	}

	private void scrollTillElementDisplay(WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(5000);
		je.executeScript("arguments[0].scrollIntoView(true)", element);
		log.debug("STEP: Scroll till element");
	}

	protected void clickOnElement(String locator) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		log.debug("STEP: click on element " + getLocatorValue(locator));
	}

	protected void selectDropDown(String locator, String visibleText) {
		WebElement dropDownOption = getElement(locator);
		Select select = new Select(dropDownOption);
		select.selectByVisibleText(visibleText);
		log.debug("STEP: select " + visibleText + " in drop down");
	}

	protected void enterText(String locator, String strMessage) {
		getElement(locator).sendKeys(strMessage);
		log.debug("STEP: " + strMessage + " is set inside element");
	}

	public static String takeScreenShot() {
		try {
			TakesScreenshot ts = (TakesScreenshot) getDriver();
			return ts.getScreenshotAs(OutputType.BASE64);
		} catch (Exception e) {
			log.debug("FAIL: Unable to screenshot, due to " + e.getCause());
		}
		return null;
	}

	protected void switchToFrameindex(int iIndex) {
		try {
			getDriver().switchTo().frame(iIndex);
			log.debug("STEP: switch on frame " + iIndex);
		} catch (NoSuchFrameException e) {
			log.fatal("FAIL: Issue While Switching frame index " + iIndex);
			Assert.fail("Issue While Switching frame", e.getCause());
		}
	}

	protected void switchToFrameNameorID(String strName) {
		try {
			getDriver().switchTo().frame(strName);
			log.debug("STEP: switch on frame " + strName);
		} catch (Exception e) {
			log.fatal("FAIL: Issue While Switching frame name " + strName);
			Assert.fail("Issue While Switching frame", e.getCause());
		}
	}

	protected void switchToDefaultFrame() {
		try {
			getDriver().switchTo().defaultContent();
			log.debug("STEP: switch to default screen");
		} catch (Exception e) {
			log.debug("FAIL: Unable to switch to main browser, issue: " + e.getCause());
			Assert.fail(e.getMessage());
		}
	}

	protected void actionTab() {
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.TAB).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Clicking TAB", e.getCause());
		}
	}

	protected void actionDoubleTab() {
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.TAB, Keys.TAB).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Clicking Double TAB", e.getCause());
		}
	}

	protected void actionEnter() {
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Enter tab", e.getCause());
		}
	}

	protected void refreshPage() {
		try {
			getDriver().navigate().refresh();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * This is to Wait for an element until it is in selectable state
	 *
	 * @param webeltElement
	 *            : WebElement
	 * @param selected
	 *            [boolean] : Enter true if selection is required
	 */
	protected void WaitforelementSelectionStateToBe(WebElement webeltElement, boolean selected) {
		try {
			getWait().until(ExpectedConditions.elementSelectionStateToBe(webeltElement, selected));
		} catch (Exception e) {
			log.fatal(e.getCause());
		}

	}

	/**
	 * This is to wait for Frame to be available and switch to it.
	 *
	 * @param iElement
	 *            [int] : Frame Index
	 */
	protected void frameToBeAvailableAndSwitchToIt(int iElement) {
		getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iElement));

	}

	/**
	 * This is to Wait until invisibility of all elements
	 *
	 * @param lstwebeltItemInSearchResult
	 *            [List] : WebElement of List of items in search result
	 */
	protected void WaitforinvisibilityOf(List<WebElement> lstwebeltItemInSearchResult) {
		getWait().until(ExpectedConditions.invisibilityOfAllElements(lstwebeltItemInSearchResult));

	}

	/**
	 * This is to Wait until element is selectable
	 *
	 * @param webeltElement
	 *            : WebElement
	 */
	protected void WaitforelementToBeSelected(WebElement webeltElement) {
		getWait().until(ExpectedConditions.elementToBeSelected(webeltElement));
	}

	/**
	 * This is to wait until text is present in the element
	 *
	 * @param webeltElement
	 *            : WebElement
	 * @param strText
	 *            [String] : Text to check
	 */
	protected void waifortextToBePresentInElementValue(WebElement webeltElement, String strText) {
		getWait().until(ExpectedConditions.textToBePresentInElementValue(webeltElement, strText));
	}

	/**
	 * This method is used to wait until the alert is present.
	 */
	protected void waitforAlertToBeDisplayed() {
		try {
			getWait().until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
			Assert.fail("Timeout,as element is not displayed", e.getCause());
		}
	}

	/**
	 * This is to move the mouse cursor to the web element
	 *
	 * @param webeltElement
	 *            : WebElement
	 */

	protected void moveToElementAction(WebElement webeltElement) {
		Actions actions = new Actions(getDriver());
		try {
			actions.moveToElement(webeltElement).click().build().perform();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * Mouse Hover
	 */
	public final void mouseHover(String locator) throws Exception {
		Actions action = new Actions(getDriver());
		action.moveToElement(getElement(locator)).perform();
	}

	/**
	 * This is to upload the file using robot class
	 *
	 * @param strFilepath
	 *            [String] : File path
	 */

	protected void fileUpload(String strFilepath) {
		try {
			Robot rs = new Robot();
			File file = new File(strFilepath);
			String str = file.getAbsolutePath();
			StringSelection selection = new StringSelection(str);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			rs.keyPress(KeyEvent.VK_CONTROL);
			rs.keyPress(KeyEvent.VK_V);
			rs.keyRelease(KeyEvent.VK_V);
			rs.keyRelease(KeyEvent.VK_CONTROL);
			rs.keyPress(KeyEvent.VK_ENTER);
			rs.keyRelease(KeyEvent.VK_ENTER);
			rs.keyRelease(KeyEvent.VK_ENTER);
			rs.delay(5000);
		} catch (Exception e) {
			Assert.fail("Unable to upload File: " + strFilepath, e.getCause());
		}
	}

	/**
	 * Close print window using Robot class.
	 */

	protected void closePrintWindow() {
		try {
			Robot rs = new Robot();
			rs.keyPress(KeyEvent.VK_ESCAPE);
			rs.keyRelease(KeyEvent.VK_ESCAPE);
			// act.sendKeys(Keys.ESCAPE);
		} catch (Exception e) {
			Assert.fail("Unable to close print window", e.getCause());
		}

	}

	/**
	 * Select drop down value by providing its value to select and its locator
	 * 
	 * @param locator
	 * @param strValue
	 */

	protected void selectDropDownValue(String locator, String strValue) {
		try {
			Select select = new Select(getElement(locator));
			select.selectByValue(strValue);
		} catch (Exception e) {
			Assert.fail("Issue While Selecting Dropdown", e.getCause());
		}
	}

	/**
	 * Select drop down by using index attributes
	 * 
	 * @param locator
	 *            - element values
	 * @param iIndex
	 *            - chose the index to switch to frame
	 */
	protected void selectDropDownByIndex(String locator, int iIndex) {
		try {
			Select select = new Select(getElement(locator));
			select.selectByIndex(iIndex);
		} catch (Exception e) {
			Assert.fail("Issue While Selecting Dropdown", e.getCause());
		}
	}

	/**
	 * Switch to the iFrame using its id attributes.
	 *
	 * @param iId
	 *            [int] : id attributes of the iFrame
	 * @return true if switched to iFrame [boolean]
	 */
	protected boolean switchToframeId(int iId) {
		try {
			getDriver().switchTo().frame(iId);
			// log4jInfo("iFrame is switched successfully");
		} catch (Exception e) {
			log.fatal(e.getCause());
			Assert.fail(e.getMessage());
		}
		return true;
	}

	/**
	 * This is to switch to the iframe using its locators.
	 *
	 * @param webeltElement
	 *            : WebElement
	 */
	protected void switchToframeByLocators(WebElement webeltElement) {
		try {
			getDriver().switchTo().frame(webeltElement);

		} catch (StaleElementReferenceException e) {
			// log4jInfo("Page Refreshed Because of Stale element issue ");
			getDriver().navigate().refresh();
			getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			waitForFrameToAvailable(webeltElement);

		} catch (Exception e) {
			log.fatal(e.getCause());
			Assert.fail("Issue While Switching Frame", e.getCause());
		}
	}

	/**
	 * This is to wait until frame is available and switch to it
	 *
	 * @param webeltElement
	 *            : WebElement
	 */
	protected void waitForFrameToAvailable(WebElement webeltElement) {
		try {
			getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(webeltElement));
		} catch (Exception e) {
			log.fatal(e.getCause());
			Assert.fail("Frame not available", e.getCause());
		}

	}

	/**
	 * This is to scroll down the page using Action class
	 */
	protected void actionScrollDown() {
		try {
			Actions actions = new Actions(getDriver());
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).build().perform();
		} catch (Exception e) {
			Assert.fail(e.getMessage());

		}
	}

	/**
	 * This is to scroll up the page using Action class
	 */
	protected void actionScrollUp() {
		try {
			Actions actions = new Actions(getDriver());
			actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).build().perform();
		} catch (Exception e) {
			log.fatal(e.getCause());
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Enter text in the textbox using Action class
	 *
	 * @param webeltElement
	 *            : WebElement
	 * @param strValue
	 *            [String] : String to enter in the text box
	 */
	protected void actionSendKeys(WebElement webeltElement, String strValue) {
		try {
			Actions actions = new Actions(getDriver());
			actions.click(webeltElement).sendKeys(strValue).sendKeys(Keys.TAB).build().perform();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * This is to clear the text box using Action class
	 *
	 * @param webeltElement
	 *            : WebElement
	 */
	protected void actionClearTextBox(WebElement webeltElement) {
		try {
			Actions actions = new Actions(getDriver());
			actions.click(webeltElement).doubleClick().sendKeys(Keys.BACK_SPACE).build().perform();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * This is to click double tab using action class
	 * 
	 * @return true if double tab is clicked [boolean]
	 */
	protected boolean clickDoubleTab() {
		try {
			Actions actions = new Actions(getDriver());
			actions.sendKeys(Keys.TAB, Keys.TAB).build().perform();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return true;
	}

	// NEEDS REFACTORING. SHOULD BE MOVED TO PAGE OBJECTS
	/** The modal drop down field X path. */
	protected String modalDropDownFieldXPath = "XPATH HERE";

	/** The modal drop down elements list X path. */
	protected String modalDropDownElementsListXPath = "XPATH HERE";

	/** The modal drop down list placeholder X path. */
	protected String modalDropDownListPlaceholderXPath = "XPATH HERE";

	/** The x path iframe encompass tools pages. */
	protected String xPath_Iframe_Encompass_Tools_Pages = "XPATH HERE";

	/** The x path iframe encompass conditions page. */
	protected String xPath_Iframe_Encompass_Conditions_Page = "XPATH HERE";

	// }
	/**
	 * This is to perform wait until element is clickable.
	 *
	 * @param ByLocator
	 *            : Locator of the element
	 * @return webElement
	 */

	protected WebElement perform_waitUntilClickable(By ByLocator) {
		return getWait().until(ExpectedConditions.elementToBeClickable(ByLocator));
	}

	/**
	 * This is to perform wait until element is visible.
	 *
	 * @param ByLocator
	 *            : Locator of the element
	 * @return the web element
	 */
	protected WebElement perform_waitUntilVisibility(By ByLocator) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(ByLocator));
	}

	/**
	 * This is to perform wait until element is present.
	 *
	 * @param ByLocator
	 *            : Locator of the element
	 * @return the web element
	 */
	protected WebElement perform_waitUntilPresent(By ByLocator) {
		return getWait().until(ExpectedConditions.presenceOfElementLocated(ByLocator));
	}

	/**
	 * This is to perform wait until element is not visible.
	 *
	 * @param ByLocator
	 *            : Locator of the element
	 * @return the boolean
	 */
	protected Boolean perform_waitUntilNotVisible(By ByLocator) {
		return getWait().until(ExpectedConditions.invisibilityOfElementLocated(ByLocator));
	}

	/**
	 * This is to perform wait until element is not visible.
	 *
	 * @param strXpath
	 *            [String] : Xpath of the element
	 *
	 * @return true when element is invisible [boolean]
	 */
	protected Boolean perform_waitUntilNotVisible(String strXpath) {
		return getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(strXpath)));
	}

	/**
	 * This is to perform wait until element is not visible.
	 *
	 * @param webeltElement
	 *            : WebElement
	 * @return true when element is invisible [boolean]
	 */
	protected Boolean perform_waitUntilNotVisible(WebElement webeltElement) {
		return getWait().until(ExpectedConditions.invisibilityOf(webeltElement));
	}

	/**
	 * This is to perform wait until element is clickable
	 *
	 * @param webeltElement
	 *            : WebElement
	 * @return the web element
	 */
	protected WebElement perform_waitUntilClickable(WebElement webeltElement) {
		return getWait().until(ExpectedConditions.elementToBeClickable(webeltElement));
	}

	/**
	 * This is to perform wait until element is visible.
	 *
	 * @param webeltElement
	 *            : WebElement
	 * @return the web element
	 */
	protected WebElement perform_waitUntilVisibility(WebElement webeltElement) {
		return getWait().until(ExpectedConditions.visibilityOf(webeltElement));
	}

	/**
	 * This is to perform switch to frame by using webelement of frame
	 *
	 * @param webeltElement
	 *            : WebElement
	 */
	protected void switchToframeByIFrameElement(WebElement webeltElement) {
		try {
			getDriver().switchTo().frame(webeltElement);
		} catch (Exception e) {
			log.fatal(e.getCause());
		}
	}

	/**
	 * This is to perform scroll to element by element using java script
	 *
	 * @param webeltElement
	 *            : WebElement
	 */
	protected void perform_scrollToElement_ByElement(WebElement webeltElement) {
		((JavascriptExecutor) driverThread).executeScript("arguments[0].scrollIntoView(true);", webeltElement);
	}

	/**
	 * This is to perform wait until list of web elements present.
	 *
	 * @param ByLocator
	 *            : Locator of element
	 * @return the list of web elements
	 */
	protected List<WebElement> perform_waitUntilListPresent(By ByLocator) {
		List<WebElement> placedReviews_PresentElements;
		try {
			placedReviews_PresentElements = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(ByLocator));
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			placedReviews_PresentElements = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(ByLocator));
		}
		return placedReviews_PresentElements;
	}

	/**
	 * This is to perform wait until list of web elements visible.
	 *
	 * @param ByLocator
	 *            : Locator of element
	 * @return the list of web elements
	 */
	protected List<WebElement> perform_waitUntilListVisible(By ByLocator) {
		List<WebElement> placedReviews_VisibleElements;
		try {
			placedReviews_VisibleElements = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByLocator));
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			placedReviews_VisibleElements = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByLocator));
		}
		return placedReviews_VisibleElements;
	}

	/**
	 * This is to get list of default elements
	 *
	 * @param ByLocator
	 *            : Locator of element
	 * @return the list of web elements
	 */
	protected List<WebElement> perform_waitUntilListDefault(By ByLocator) {
		List<WebElement> placedReviews_FindedByDefaultElements;
		try {
			placedReviews_FindedByDefaultElements = getDriver().findElements(ByLocator);
		} catch (StaleElementReferenceException ex) {
			placedReviews_FindedByDefaultElements = getDriver().findElements(ByLocator);
		}
		return placedReviews_FindedByDefaultElements;
	}

	/**
	 * This is to perform to get random number from boundary.
	 *
	 * @param iMin
	 *            [int] : Minimum number
	 * @param iMax
	 *            [int] : Maximum number
	 * @return random number [int]
	 */
	// -------------=================//////GENERAL\\\\\===============--------------------
	protected int perform_Get_RandomNumber_FromBoundary(int iMin, int iMax) {
		Random random = new Random();
		int randomNumber = iMin + random.nextInt(iMax);
		return randomNumber;
	}

	/**
	 * To convert list of web elements into the list of strings.
	 *
	 * @param webElementsList
	 *            [WebElement] : List of WebElement
	 * 
	 * @return list of strings [ArrayList]
	 */
	protected ArrayList<String> convertListWebElementToStrings(List<WebElement> webElementsList) {
		ArrayList<String> listOfStrings = new ArrayList<>();
		for (WebElement element : webElementsList) {
			listOfStrings.add(element.getText());
		}
		return listOfStrings;
	}

	/**
	 * This is to get current url of the page
	 *
	 * @return current url [String]
	 */

	protected String getCurrentUrl() {
		return getDriver().getCurrentUrl();
	}

	
	/**
	 * This is to perform to get list of web elements by using Xpath of an
	 * element
	 *
	 * @param strXpath
	 *            [String] : Xpath of an element
	 * @return the list of web elements
	 */

	protected List<WebElement> perform_GetListOfElementsByXPath(String strXpath) {
		return perform_waitUntilListVisible(By.xpath(strXpath));
	}

	/**
	 * This is to display message in the log report.
	 *
	 * @param strMessage
	 *            [String] : Message to log in the report
	 */

	protected void perform_LogToReport(String strMessage) {
		// log4jInfo(strMessage);
	}

	/**
	 * To find whether the WebElement is present in the list should return a
	 * boolean based on the evaluation
	 *
	 * @param lstList
	 *            [List] : List of WebElements
	 * @param strElement
	 *            [String] : Element name
	 * @return true if is element present [boolean]
	 * 
	 */
	protected Boolean isElementPresentInList(List<WebElement> lstList, String strElement) {
		Boolean checkIfPresent = false;
		for (int i = 0; i < lstList.size(); i++) {
			if (lstList.get(i).getText().equals(strElement)) {
				lstList.get(i).click();
				checkIfPresent = true;
				// log4jInfo("WebElement " + strElement + " is present");
				break;
			}
		}
		return checkIfPresent;
	}

	/**
	 * This is to perform to check if elements in both the lists are same.
	 *
	 * @param arrLstListA
	 *            [String] : Array List in Strings
	 * @param arrLstListB
	 *            [String] : Array List in Strings
	 * @return true if elements in both lists are same [boolean]
	 */
	protected Boolean perform_CheckIfElementsInTheListsAreSame(ArrayList<String> arrLstListA,
			ArrayList<String> arrLstListB) {
		if (!(arrLstListA.size() == arrLstListB.size())) {
			return false;
		}
		for (int i = 0; i < arrLstListA.size(); i++) {
			for (int j = 0; j < arrLstListB.size(); j++) {
				if (!arrLstListA.get(i).equals(arrLstListB.get(j))) {
					// log4jInfo("THIS ELEMENTS ARE NOT THE SAME: " +
					// arrLstListA.get(i) + " = " + arrLstListB.get(j));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This is to perform to check if elements in both the lists are same.
	 *
	 * @param lstListA
	 *            [List] : List WebElements
	 * @param lstListB
	 *            [List] : List WebElements
	 * @return true if elements in both lists are same [boolean]
	 */
	protected Boolean perform_CheckIfElementsInTheListsAreSame(List<WebElement> lstListA, List<WebElement> lstListB) {
		if (!(lstListA.size() == lstListB.size())) {
			return false;
		}
		for (int i = 0; i < lstListA.size(); i++) {
			for (int j = 0; j < lstListB.size(); j++) {
				if (!lstListA.get(i).getText().equals(lstListB.get(j).getText())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This is to perform to check if elements in both the lists are same.
	 *
	 * @param arrLstListA
	 *            [String] : Array List in Strings
	 * @param lstListB
	 *            [List] : List WebElements
	 * @return true if elements in both lists are same [boolean]
	 */
	protected Boolean perform_CheckIfElementsInTheListsAreSame(ArrayList<String> arrLstListA,
			List<WebElement> lstListB) {
		if (!(arrLstListA.size() == lstListB.size())) {
			return false;
		}
		for (int i = 0; i < arrLstListA.size(); i++) {
			if (!arrLstListA.get(i).equals(lstListB.get(i).getText())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This is to click an element with java script executor.
	 *
	 * @param element
	 *            : WebElement
	 */
	protected void perform_ClickWithJavaScriptExecutor(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driverThread;
		js.executeScript("arguments[0].click();", element);
	}
	// -------------=================\\\\\\\____//////===============--------------------

	// --------=========//////WORK FOR ANY MODALS ON
	// NEXT_GEN_LOS\\\\\==========---------

	/**
	 * This is to perform Wait for new window.
	 *
	 * @param iTimeoutMilisec
	 *            [int] : Time out in milliseconds
	 * @return true, if successful [boolean]
	 */

	protected boolean waitForNewWindow(int iTimeoutMilisec) {
		boolean flag = false;
		int counter = 0;
		while (!flag) {
			try {
				Set<String> winId = getDriver().getWindowHandles();
				// log4jInfo("WINDOW HANDLES SIZE-->> " +
				// driverThread.getWindowHandles().size());
				if (winId.size() > 1) {
					flag = true;
					return flag;
				}
				Thread.sleep(500);
				counter = counter + 500;
				if (counter > iTimeoutMilisec) {
					return flag;
				}
			} catch (Exception e) {
				// log4jInfo(e.getMessage());
				return false;
			}
		}
		return flag;
	}

	/**
	 * This is to get the list of web elements
	 *
	 * @param lstwebeltElement
	 *            [List] : WebElement
	 * @return list of WebElements in String
	 */
	protected List<String> getList(List<WebElement> lstwebeltElement) {

		List<String> list = new ArrayList<String>();

		List<WebElement> elements = lstwebeltElement;
		// log4jInfo("Number of elements:" + elements.size());
		for (int i = 0; i < elements.size(); i++) {

			list.add(elements.get(i).getText());

		}
		return list;
	}

	/**
	 * To split a string into an array of substrings, and return length of the
	 * substrings
	 * 
	 * @param strValue
	 *            [String] : Value in String
	 * @return length of substrings [String]
	 */
	protected int getLengthOfSubStrings(String strValue) {

		String[] splitString = strValue.split(" ");

		for (int i = 0; i < splitString.length; i++) {
			// log4jInfo("Sub strings are: " + splitString[i]);
		}
		// log4jInfo("Length of Sub strings are: " + splitString.length);
		return splitString.length;

	}

	protected static void isFieldPresent(List<WebElement> element) {
		for (int i = 0; i < element.size(); i++) {
			try {
				if (element.get(i).isDisplayed() || element.get(i).isEnabled()) {
					Assert.assertTrue(element.get(i).isEnabled());
					// log4jInfo(element.get(i).getText() + " field(s) is/are
					// enable and displayed on page ");
				} else {
					// log4jError(element.get(i).getText() + "false means
					// Disabled or not present ");
					Assert.fail(element.get(i).getText() + "false means Disabled or not present ");
				}
			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
		}
		// softAssertion.assertAll();
	}

	protected static void isDropdwonPresent(WebElement element, String str) {
		try {
			if (element.isEnabled() || element.isDisplayed()) {
				// log4jInfo(element.getTagName() + "_dropdown_" + str + "_is
				// enble/displayed");
			} else {
				// log4jError(str + "dropdown_is either missing or disabled");
				Assert.fail(str + "dropdown_is either missing or disabled");

			}
		} catch (Exception e) {
			Assert.fail("Eiether Field is not Present or Page not Loaded", e.getCause());
		}

	}

	protected static void validateCheckBoxSelection(WebElement element, String str) {
		try {
			// ((JavascriptExecutor)
			// driverThread).executeScript("arguments[0].scrollIntoView();",
			// element);

			// ((JavascriptExecutor) driverThread).executeScript("window.scrollTo(0,
			// document.body.scrollHeight)");
			System.out.println("Checkbox*****************" + element.isSelected());
			System.out.println("Check Box Attribute value is :-" + element.getAttribute("checked"));
			if (element.isSelected()) {
				// log4jInfo(str + "_Checkbox is selected");
			} else {
				// log4jError(str + "_Check box is not selected");
				Assert.fail(str + "_Check box is not selected");

			}

		} catch (Exception e) {
			Assert.fail("Issue while validating Checkbox selection", e.getCause());
		}

	}

	// ********************************************************************************************
	protected static void islabelPresent(List<WebElement> element, List<String> str) {
		int i, j, k;

		try {
			System.out.println("Size is:" + element.size());
			for (i = 0; i < element.size(); i++) {
				k = 0;
				for (j = 0; j < str.size(); j++) {
					if (element.get(i).getText().contains(str.get(j))) {
						// log4jInfo(str.get(j) + "_is as expected");
						// break;
					} else {
						k++;
					}
					if (k == str.size()) {
						// log4jError(str.get(i) + "_is either misssing or
						// incorrect");
						Assert.fail(str.get(i) + "_is either misssing or incorrect");
					}
				}
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	protected static void isTextPresent(List<WebElement> element, List<String> str) {
		int i, j, k;

		try {
			System.out.println("Size is:" + element.size());
			for (i = 0; i < element.size(); i++) {
				k = 0;
				for (j = 0; j < str.size(); j++) {

					if (element.get(i).getText().equalsIgnoreCase(str.get(j))) {
						// log4jInfo(str.get(j) + "_is as expected");
						break;
					} else {
						k++;
					}
					if (k == str.size()) {
						// log4jError(element.get(i).getText() + "_is either
						// misssing or incorrect");
						Assert.fail(element.get(i).getText() + "_is either misssing or incorrect");
					}
				}
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	protected static String convertDateWithMinToNewFormat(String date) {
		if (date.equalsIgnoreCase("null")) {
			return date;
		}
		LocalDateTime newDate;
		DateTimeFormatter formatOld = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm");
		DateTimeFormatter formatNew = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		try {
			newDate = LocalDateTime.parse(date, formatOld);
		} catch (DateTimeParseException e) {
			return date;
		}
		return formatNew.format(newDate);
	}

	protected static String convertDateWithSecToNewFormat(String date) {
		if (date.equalsIgnoreCase("null")) {
			return date;
		}
		LocalDateTime newDate;
		DateTimeFormatter formatOld = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		DateTimeFormatter formatNew = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		try {
			newDate = LocalDateTime.parse(date, formatOld);
		} catch (DateTimeParseException e) {
			return date;
		}
		return formatNew.format(newDate);
	}

	protected static String convertStringToFloatRepresentation(String number) {
		if (number.equalsIgnoreCase("null")) {
			return number;
		}
		if (!number.contains(".")) {
			return number + ".0";
		}
		return number;
	}

	protected static boolean isValidEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	protected static boolean isValidPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			return false;
		}
		if (phoneNumber.matches("[0-9]+") && phoneNumber.length() == 10) {
			return true;
		}
		return false;
	}

	protected static boolean isValidState(String state) {
		if (state == null || state.isEmpty()) {
			return false;
		}
		if (state.matches("[A-Z]+") && state.length() == 2) {
			return true;
		}
		return false;
	}

	protected static boolean isValidZipCode(String zipCode) {
		if (zipCode == null || zipCode.isEmpty()) {
			return false;
		}
		String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(zipCode);
		return matcher.matches();
	}

	protected static boolean isValidDate(String date) {
		if (date == null || date.isEmpty()) {
			return false;
		}
		String regex = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
				+ "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
				+ "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
				+ "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		return matcher.matches();
	}

	protected static boolean isValidTime(String time) {
		if (time == null || time.isEmpty()) {
			return false;
		}
		// String regex = "(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)";
		String regex = "(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)\\.(?:\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(time);
		return matcher.matches();
	}

	protected static boolean isValidDateTime(String date) {
		if (date == null || date.isEmpty()) {
			return false;
		}
		String[] array = date.split(" ");
		try {
			boolean isDate = isValidDate(array[0]);
			boolean isTime = isValidTime(array[1]);
			if (isDate && isTime) {
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		return false;
	}

	protected static boolean isValidInteger(String intNumber, String lengthLimit) {
		if (intNumber == null || intNumber.isEmpty()) {
			return false;
		}
		// String regex = "[0-9]*";
		String regex = "^-?\\d+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(intNumber);
		if (lengthLimit == null || lengthLimit.isEmpty()) {
			return matcher.matches();
		} else {
			int limit = Integer.parseInt(lengthLimit);
			if (limit < intNumber.length() || limit < 1) {
				return false;
			}
		}
		return matcher.matches();
	}

	protected static boolean isValidFloat(String floatNumber, String lengthLimit) {
		if (floatNumber == null || floatNumber.isEmpty()) {
			return false;
		}
		// String regex = "^(\\d*\\.?\\d*)$";
		String regex = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(floatNumber);
		if (lengthLimit == null || lengthLimit.isEmpty()) {
			return matcher.matches();
		} else {
			int limit = Integer.parseInt(lengthLimit);
			if (limit < floatNumber.length() || limit < 1) {
				return false;
			}
		}
		return matcher.matches();
	}

	public void wait(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is to switch to the new tab.
	 */
	public void switchToTab(int index) {
		try {
			ArrayList<String> newTab = new ArrayList<String>(getDriver().getWindowHandles());
			getDriver().switchTo().window(newTab.get(index));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());

		}
	}

	/**
	 * Declaration method for Robot class
	 *
	 * @return the robot
	 */

	public Robot robot() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return robot;

	}

	/**
	 * This is to upload the file using robot class
	 *
	 * @param strFilepath
	 *            [String] : File path
	 * @return
	 */

	public void assetsfileUpload(String filePath) {
		try {
			wait(5500);
			log.info("Asset File Path: "+filePath);
			StringSelection selection = new StringSelection(filePath);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			robot = robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.delay(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To wait for element text
	 *
	 * @param int seconds
	 *        String text of element
	 *        String locator
	 *        
	 * @return boolean
	 */

	public boolean waitForElementText(Duration seconds,String textOfElement,String locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
		Boolean elementFlag = wait.until(ExpectedConditions.textToBePresentInElementLocated(getLocator(locator), textOfElement));
		return elementFlag;
	}
	
	/**
	 * To wait for element text
	 *
	 * @param int seconds
	 *        String text of element
	 *        String locator
	 *        
	 * @return boolean
	 */

	public boolean waitForElement(Duration seconds,String locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
		WebElement  elementFlag = wait.until(ExpectedConditions.elementToBeClickable(getLocator(locator)));
		return elementFlag.isDisplayed();
	}
	
}

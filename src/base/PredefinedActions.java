package base;

import java.io.*;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

public abstract class PredefinedActions {

	static WebDriver driver;
	private static Actions action;
	private static WebDriverWait wait;

	public static void initializeBrowser(String url) {
		String os = System.getProperty("os.name").toLowerCase();
		System.out.println("os : "+ os);
		String path = os.contains("windows") ? "./resources/windows/chromedriver.exe"
				: os.contains("mac") ? "./resources/mac/chromedriver" : null;

		System.setProperty("webdriver.chrome.driver", path);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		action = new Actions(driver);
		wait = new WebDriverWait(driver,60);
	}

	private String getLocatorType(String locator) {
		return locator.split("]:-")[0].substring(1);
	}

	private String getLocatorValue(String locator) {
		return locator.split("]:-")[1];
	}

	private By byReferenceType(String locatorType, String locatorValue) {
		switch(locatorType.toUpperCase()) {
		case "XPATH" :
			return By.xpath(locatorValue);

		case "ID" :
			return By.id(locatorValue);

		case "LINKTEXT" :
			return By.linkText(locatorValue);

		case "PARTIALLINKTEXT" :
			return By.partialLinkText(locatorValue);

		case "NAME" :
			return By.name(locatorValue);

		case "CLASSNAME" :
			return By.className(locatorValue);

		default :
			System.out.println("Invalid Locator Type");
			return null;
		}		
	}

	protected List<WebElement> getAllElements(String locator, boolean isWaitRequired) {
		String locatorType = getLocatorType(locator);
		String locatorValue = getLocatorValue(locator);

		if(isWaitRequired)
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byReferenceType(locatorType,locatorValue)));
		return driver.findElements(byReferenceType(locatorType,locatorValue));		
	}

	protected List<String> getAllElementsText(String locator, boolean isWaitRequired) {
		List<WebElement> hoverElements= getAllElements(locator, isWaitRequired);
		List<String> hoverElementsValue = new ArrayList<>();

		for(WebElement elements : hoverElements) {
			hoverElementsValue.add(elements.getText());
		}

		return hoverElementsValue;
	}

	protected WebElement getElement(String locator, boolean isWaitRequired) {
		String locatorType = getLocatorType(locator);
		String locatorValue = getLocatorValue(locator);

		if(isWaitRequired)
			return wait.until(ExpectedConditions.visibilityOfElementLocated(byReferenceType(locatorType,locatorValue)));
		return driver.findElement(byReferenceType(locatorType,locatorValue));
	}

	protected WebElement getElement(String locator) {
		return getElement(locator, false);
	}

	protected void hoverToElement(WebElement target) {
		action.moveToElement(target).perform();
	}

	protected void switchToFrameByElement(WebElement frameElement) {
		driver.switchTo().frame(frameElement);
	}

	protected void switchToFrameByElement(String locator, boolean isWaitRequired) {
		WebElement frameElement = getElement(locator, isWaitRequired);
		switchToFrameByElement(frameElement);
	}

	protected void switchToFrameByElement(String locator) {
		WebElement frameElement = getElement(locator, false);
		switchToFrameByElement(frameElement);
	}

	protected String getMainWindowHandleId() {
		return driver.getWindowHandle();
	}

	protected Set<String> getAllWindowHandleId(){
		return driver.getWindowHandles();
	}

	protected void switchToWindow(String windowId) {
		driver.switchTo().window(windowId);
	}

	protected void clickOnElement(String locator, boolean isWaitRequired) {
		getElement(locator,isWaitRequired).click();
	}

	protected void clickOnElement(String locator) {
		getElement(locator,false).click();
	}

	protected void enterTextValue(WebElement element, String textToBeEntered){
		if(element.isEnabled())
			element.sendKeys(textToBeEntered);
		else
			System.out.println("Element is not enabled");
	}

	protected void enterTextValue(String locator, boolean isWaitRequired, String textToBeEntered){
		enterTextValue(getElement(locator,isWaitRequired),textToBeEntered);
	}

	public static void captureScreenshot(String fileName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(src, new File(".//screenshot//" + fileName +".png"));
		} catch (IOException e) {
			System.out.println("Screenshot Filename not found");
			e.printStackTrace();
		}
	}

	public static void closeBrowser() {
		driver.close();
	}
}

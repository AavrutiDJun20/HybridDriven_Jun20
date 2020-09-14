package base;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PredefinedActions {

	private static WebDriver driver;
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
		wait = new WebDriverWait(driver,60000);
	}

	private String getLocatorType(String locator) {
		return locator.split("]:-")[0].substring(1);
	}
	
	private String getLocatorValue(String locator) {
		return locator.split("]:-")[1];
	}

	protected WebElement getElement(String locator, boolean isWaitRequired) {
		String locatorType = getLocatorType(locator);
		String locatorValue = getLocatorValue(locator);
		
		switch(locatorType.toUpperCase()) {
		case "XPATH" :
			if(isWaitRequired)
				return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			return driver.findElement(By.xpath(locatorValue));

		case "ID" :
			if(isWaitRequired)
				return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			return driver.findElement(By.id(locatorValue));	
			
		case "LINKTEXT" :
			if(isWaitRequired)
				return wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			return driver.findElement(By.linkText(locatorValue));
			
		case "PARTIALLINKTEXT" :
			if(isWaitRequired)
				return wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			return driver.findElement(By.partialLinkText(locatorValue));
			
		case "NAME" :
			if(isWaitRequired)
				return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			return driver.findElement(By.name(locatorValue));
			
		case "CLASSNAME" :
			if(isWaitRequired)
				return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			return driver.findElement(By.className(locatorValue));

		default :
			System.out.println("Invalid Locator Type");
			return null;
		}		
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
	
	public static void closeBrowser() {
		driver.close();
	}
}

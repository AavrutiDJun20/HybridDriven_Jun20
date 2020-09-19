package pages;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;

import base.PredefinedActions;
import constant.ConstantPath;
import util.PropertyFileOperation;

class EmailPage extends PredefinedActions{

	private static EmailPage emailPage;
	private PropertyFileOperation propOperation;
	private String mainWindow;
	
	private EmailPage() {
		try {
			propOperation = new PropertyFileOperation(ConstantPath.LOCATORPATH +"EmailPageProp.properties");
		} catch (IOException e) {
			System.out.println("Dashboard Page property file not found");
			//Custom exception --> unchecked exception
		}
	}
	
	static EmailPage getEmailPageObject() {
		if(emailPage==null)
			emailPage = new EmailPage();
		return emailPage;
	}
	
	private void switchToNewTab() {
		ArrayList<String> allWindow = new ArrayList<>(getAllWindowHandleId());
		allWindow.remove(mainWindow);
		switchToWindow(allWindow.get(0));
	}
	
	private void enterCredentials(String userId, String userPassword) {
		clickOnElement(propOperation.propReadValue("yahoo_signInButton"), true);
		enterTextValue(propOperation.propReadValue("yahoo_userName"), true, userId);
		clickOnElement(propOperation.propReadValue("yahoo_nextButton"));
		
		enterTextValue(propOperation.propReadValue("yahoo_password"), true, userPassword);
		clickOnElement(propOperation.propReadValue("yahoo_nextButton"));
		
		String actualName = getElementText(propOperation.propReadValue("yahoo_signInName"),true);
		Assert.assertEquals(actualName,"Aavruti");
	}
	
	private void handleSkipNotification() {
		if(isElementPresent(propOperation.propReadValue("yahoo_notification_skip"), false)) {
			clickOnElement(propOperation.propReadValue("yahoo_notification_skip"));
		}
	}
	
	private String hoverMailAndGetOTP() {
		hoverToElement(getElement(propOperation.propReadValue("yahoo_mailbox_hover")));
		String emailText = getElementText(propOperation.propReadValue("yahoo_mailBox_text"), true);
		String otpText = emailText.split(" ")[0];
		return otpText;
	}
	
	String getOTP(String userId, String userPassword) {
		mainWindow = getMainWindowHandleId();
		openNewTab();	
		
		switchToNewTab();		
		navigateToURL("http://yahoo.com");		
		enterCredentials(userId, userPassword);
		handleSkipNotification();		
		String otpText = hoverMailAndGetOTP();
		closeBrowser();
		switchToWindow(mainWindow);		
		return otpText;
	}	
	
	
}
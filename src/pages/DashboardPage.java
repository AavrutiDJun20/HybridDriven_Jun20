package pages;

import java.io.IOException;
import java.util.*;

import base.PredefinedActions;
import util.PropertyFileOperation;

public class DashboardPage extends PredefinedActions{

	private static DashboardPage dashboardPage;
	private PropertyFileOperation propOperation;
	private String mainWindow;

	private DashboardPage() {
		try {
			propOperation = new PropertyFileOperation(".//resources//config//DashboardPageProp.properties");
		} catch (IOException e) {
			System.out.println("Dashboard Page property file not found");
			//Custom exception --> unchecked exception
		}
	}

	synchronized public static DashboardPage getDashboardPage() {		
		if(dashboardPage == null)
			dashboardPage = new DashboardPage();
		return dashboardPage;
	}

	private void signInHover() {
		hoverToElement(getElement(propOperation.propReadValue("signInMenu")));
	}

	private void clickOnLogin() {			
		clickOnElement(propOperation.propReadValue("loginBtn"),true);
	}

	private void switchToLoginFrame() {
		switchToFrameByElement(propOperation.propReadValue("loginFrame"),true);		
	}

	private void loginUsingFB(String userName, String password) {
		clickOnElement(propOperation.propReadValue("fbUserLoginBtn"));		

		// To handle all new opened window.				
		Set<String> s1= getAllWindowHandleId();	
		Iterator<String> i1=s1.iterator();		

		while(i1.hasNext())			
		{		
			String childWindow=i1.next();	
			if(!mainWindow.equalsIgnoreCase(childWindow))			
			{
				// Switching to Child window
				switchToWindow(childWindow);	 
				enterTextValue(propOperation.propReadValue("fbEmailId"),true, userName);                			
				enterTextValue(getElement(propOperation.propReadValue("fbPassword")), password);
				clickOnElement(propOperation.propReadValue("fbLoginBtn"));
			}		
		}	
	}

	private void switchToMainWindow() {
		// Switching to Parent window i.e Main Window.
		switchToWindow(mainWindow);
	}

	public void signUpUsingFB(String userName, String password) {	
		signInHover();
		clickOnLogin();		
		switchToLoginFrame();
		mainWindow = getMainWindowHandleId();
		loginUsingFB(userName, password);		
		switchToMainWindow();
	}

	public String getSignInUserName() {
		return getElement(propOperation.propReadValue("acctUserName"),true).getText();
	}

	public List<String> getSignInHoverOptions(){
		signInHover();				
		return getAllElementsText(propOperation.propReadValue("hoverOptions"), true);
	}
}

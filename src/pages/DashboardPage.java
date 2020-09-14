package pages;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import base.PredefinedActions;
import util.PropertyFileOperation;

public class DashboardPage extends PredefinedActions{

	private static DashboardPage dashboardPage;
	private PropertyFileOperation propOperation;

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

	public void signUpUsingFB(String userName, String password) {		

		hoverToElement(getElement(propOperation.propReadValue("signInMenu")));	
		clickOnElement(propOperation.propReadValue("loginBtn"),true);		
		switchToFrameByElement(propOperation.propReadValue("loginFrame"),true);		
		clickOnElement(propOperation.propReadValue("fbUserLoginBtn"));		
		String MainWindow= getMainWindowHandleId();

		// To handle all new opened window.				
		Set<String> s1= getAllWindowHandleId();	
		Iterator<String> i1=s1.iterator();		

		while(i1.hasNext())			
		{		
			String ChildWindow=i1.next();	
			if(!MainWindow.equalsIgnoreCase(ChildWindow))			
			{
				// Switching to Child window
				switchToWindow(ChildWindow);	 
				enterTextValue(propOperation.propReadValue("fbEmailId"),true, userName);                			
				enterTextValue(getElement(propOperation.propReadValue("fbPassword")), password);
				clickOnElement(propOperation.propReadValue("fbLoginBtn"));
			}		
		}		
		// Switching to Parent window i.e Main Window.
		switchToWindow(MainWindow);

	}

	public String getSignInUserName() {
		return getElement(propOperation.propReadValue("acctUserName"),true).getText();
	}
}

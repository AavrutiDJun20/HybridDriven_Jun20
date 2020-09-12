package pages;

import java.util.Iterator;
import java.util.Set;

import base.PredefinedActions;

public class DashboardPage extends PredefinedActions{

	public void signUpUsingFB() throws InterruptedException {
		
		hoverToElement(getElement("xpath","//span[text()='Sign In']"));		

		getElement("xpath","//a[text()='login']",true).click();
		
		switchToFrameByElement("xpath","//iframe[@id='loginIframe']",true);
		
		getElement("xpath", "//div[@id='fbUserLogin']").click();
		
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
				getElement("id","email",true).sendKeys("aavrutid@yahoo.com");                			
				getElement("id","pass").sendKeys("Viplove@01");
				getElement("id","loginbutton").click();
			}		
		}		
		// Switching to Parent window i.e Main Window.
		switchToWindow(MainWindow);
	}
	
	public String getSignInUserName() {
		return getElement("xpath","//span[contains(@class,'accountUserName') and text()!='Sign In']",true).getText();
	}
}

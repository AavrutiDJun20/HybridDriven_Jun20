package com.technocredits.snapdeal.testscripts;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.ITestResult;
import org.testng.annotations.*;

import base.PredefinedActions;
import pages.DashboardPage;
import util.PropertyFileOperation;

public class TestBase {
	
	@BeforeMethod
	public void setup() {
		PredefinedActions.initializeBrowser("https://www.snapdeal.com/");
	}
	
	DashboardPage getDashboardPage() {			
		return DashboardPage.getDashboardPage();
	}
	
	ArrayList<String> readCredentials() {
		PropertyFileOperation propOperation;
		ArrayList<String> credentialList = new ArrayList<String>();
		try {
			propOperation = new PropertyFileOperation(".//resources//config//FBCredentials.properties");
			credentialList.add(propOperation.propReadValue("fbUserName"));
			credentialList.add(propOperation.propReadValue("fbPassword"));
			credentialList.add(propOperation.propReadValue("fbMobileNumber"));
			credentialList.add(propOperation.propReadValue("fbMobilePwd"));
		} catch (IOException e) {
			System.out.println("Credentials not found");
		}
		return credentialList;
	}
	
	@AfterMethod
	public void teardown(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			PredefinedActions.captureScreenshot(result.getName());
		}		
		PredefinedActions.closeBrowser();
	}
}

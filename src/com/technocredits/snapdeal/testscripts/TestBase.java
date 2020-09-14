package com.technocredits.snapdeal.testscripts;

import java.io.IOException;
import java.util.ArrayList;

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
		} catch (IOException e) {
			System.out.println("Credentials not found");
		}
		return credentialList;
	}
	
	@AfterMethod
	public void teardown() {
		PredefinedActions.closeBrowser();
	}
}

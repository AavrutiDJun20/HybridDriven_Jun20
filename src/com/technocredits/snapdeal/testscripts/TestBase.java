package com.technocredits.snapdeal.testscripts;

import org.testng.annotations.*;

import base.PredefinedActions;
import pages.DashboardPage;

public class TestBase {

	private DashboardPage dashboardPage;
	
	@BeforeMethod
	public void setup() {
		PredefinedActions.initializeBrowser("https://www.snapdeal.com/");
	}
	
	DashboardPage getDashboardPage() {
		if(dashboardPage == null)
			dashboardPage = new DashboardPage();
		return dashboardPage;
	}
	
	@AfterMethod
	public void teardown() {
		PredefinedActions.closeBrowser();
	}
}

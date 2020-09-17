package com.technocredits.snapdeal.testscripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.*;

import pages.DashboardPage;

public class SnapDealLoginTest extends TestBase{

	@Test
	public void loginVerification() {
		String expectedUserName = "Aavruti";
		DashboardPage dashboardPage = getDashboardPage();		
		ArrayList<String> credentialList = readCredentials();
		dashboardPage.doLogin(credentialList.get(2), credentialList.get(3));
		String actualUserName = dashboardPage.getSignInUserName();
		Assert.assertEquals(actualUserName, expectedUserName,"UserName is not present, expected " + expectedUserName + " but received " + actualUserName);
	}
}

package com.technocredits.snapdeal.testscripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.*;

import pages.DashboardPage;

public class SnapDealSignupTest extends TestBase{	
	
	@Test
	public void snapDealTest() throws InterruptedException {
		
		String expectedUserName = "Aavruti Dalmia";
		DashboardPage dashboardPage = getDashboardPage();
		ArrayList<String> credentialList = readCredentials();
		dashboardPage.signUpUsingFB(credentialList.get(0), credentialList.get(1));
		String actualUserName = dashboardPage.getSignInUserName();
		Assert.assertEquals(actualUserName, expectedUserName);
	}
}
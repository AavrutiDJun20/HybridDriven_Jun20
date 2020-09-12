package com.technocredits.snapdeal.testscripts;

import org.testng.Assert;
import org.testng.annotations.*;

import pages.DashboardPage;

public class SnapDealSignupTest extends TestBase{	
	
	@Test
	public void snapDealTest() throws InterruptedException {
		
		String expectedUserName = "Aavruti Dalmia";
		DashboardPage dashboardPage = getDashboardPage();
		dashboardPage.signUpUsingFB();
		String actualUserName = dashboardPage.getSignInUserName();
		Assert.assertEquals(actualUserName, expectedUserName);
	}
}
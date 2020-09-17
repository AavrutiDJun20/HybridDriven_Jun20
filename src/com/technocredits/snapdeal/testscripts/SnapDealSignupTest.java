package com.technocredits.snapdeal.testscripts;

import java.util.*;

import org.testng.Assert;
import org.testng.annotations.*;

import pages.DashboardPage;

public class SnapDealSignupTest extends TestBase{	
	
	@Test
	public void verifySignUpUsingFBTest() throws InterruptedException {
		
		String expectedUserName = "Aavruti Dalmia";
		DashboardPage dashboardPage = getDashboardPage();
		ArrayList<String> credentialList = readCredentials();
		dashboardPage.signUpUsingFB(credentialList.get(0), credentialList.get(1));
		String actualUserName = dashboardPage.getSignInUserName();
		Assert.assertEquals(actualUserName, expectedUserName,"UserName is not present, expected " + expectedUserName + " but received " + actualUserName);
	}
	
	@Test(enabled=false)
	public void signUpHoverMenuTest() {
		List<String> expectedHoverOptionsList = new ArrayList<>(Arrays.asList("Your Account", "Your Orders", "Shortlist", "SD Cash"));
		DashboardPage dashboardPage = getDashboardPage();
		List<String> actualHoverOptionsList = dashboardPage.getSignInHoverOptions();
		Assert.assertEquals(actualHoverOptionsList, expectedHoverOptionsList);
		System.out.println(expectedHoverOptionsList);
	}
}
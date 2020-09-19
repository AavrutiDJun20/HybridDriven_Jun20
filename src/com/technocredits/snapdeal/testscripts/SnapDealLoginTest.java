package com.technocredits.snapdeal.testscripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.*;

import constant.ConstantPath;
import pages.DashboardPage;
import util.ExcelOperation;

public class SnapDealLoginTest extends TestBase{

	@Test(dataProvider="loginDataProvider")
	public void loginVerification(String userId, String password, String userName) {
		DashboardPage dashboardPage = getDashboardPage();
		dashboardPage.doLogin(userId, password);
		String actualUserName = dashboardPage.getSignInUserName();
		Assert.assertEquals(actualUserName, userName,"UserName is not present, expected " + userName + " but received " + actualUserName);
	}
	
	@DataProvider(name="loginDataProvider")
	public Object[][] loginData() throws IOException{
		return ExcelOperation.getAllRowsData(ConstantPath.LOGINTESTDATA, ConstantPath.LOGINTESTDATASHEET);
	}
}

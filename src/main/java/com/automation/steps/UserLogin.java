package com.automation.steps;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.automation.utils.BaseUtils;
import com.aventstack.extentreports.util.Assert;

public class UserLogin extends BaseUtils{
	
	private static String txtUserName="//input[@id='user-name']";
	private static String txtPassword="//input[@id='password']";
	private static String btnLogin="//input[@id='login-button']";
	private static String product="//div[normalize-space()='~prd~']";
	private static String  errorHeader="//h3[@data-test='error']";
	public void navigateToSwagLab()
	{
		try {
		driver.get(getTestData("url"));
		test.pass("Navigated to Swag lab Successfully").addScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
		}
		catch(Exception e)
		{
			test.fail("Navigated to Swag lab Failed").addScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
		}
		
	}
	public void login()
	{
		try {
		String userName=getTestData("User Name");
		String password=getTestData("Password");
		setText(getElemnt(txtUserName),userName);
		setText(getElemnt(txtPassword),password);
		click(getElemnt(btnLogin));
		test.pass("Login Successful").addScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
		}
		catch(Exception e)
		{
			
			test.fail(e.getMessage()).addScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
		}
		
				
	}
	
	public void addToCartByProductName()
	{
		String productnam=getTestData("abc");
		getElemnt(product.replaceAll("~prd~", productnam)).click();
		
	}
	public void verifyErrorMessage()
	{
		String expectedErrorMessage = getTestData("ErrorMessage");
		String Actual = getElemnt(errorHeader).getText();
		if(Actual.equals(expectedErrorMessage))
		{
			System.out.println("pass");
		}else {
			System.out.println("fail");
		}
	}
}

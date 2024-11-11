package com.automation.steps;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import com.automation.utils.BaseUtils;

public class SwagLab extends BaseUtils{
	
	private static String itemPrice="//div[@class='inventory_item_price']";
	private static String addToCart="//button[text()='Add to cart']";
	private static WebElement maxPriceElement=null;
	public void pickHighestValueItem()
	{
		
		double maxPrice=0;
		List<WebElement> itemPrices=getElemnts(itemPrice);
		for(WebElement itemPrice:itemPrices)
		{
			if(maxPrice<Double.parseDouble(getText(itemPrice).substring(1).trim()))
			{
				maxPriceElement=itemPrice;
				maxPrice=Double.parseDouble(getText(itemPrice).substring(1).trim());
			}
		}
		System.out.println(maxPrice);
		
		String expectedMaxPrice=getTestData("Highest Value");
		scrollToElement(maxPriceElement);
		if(maxPrice==Double.parseDouble(expectedMaxPrice))
			test.pass("Max Price Item found and max price is "+maxPrice  );
		else
			test.fail("Max Price Item not found and we found max price as"+maxPrice +", but expected max price is "+expectedMaxPrice ).addScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
	}
	
	public void addToCart()
	{
		try {
		System.out.println(maxPriceElement);
		WebElement addToCartElement=driver.findElement(RelativeLocator.with(By.xpath(addToCart)).toRightOf(maxPriceElement));
		click(addToCartElement);
		test.pass("Max Price Item found and added to cart "  ).addScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
		}
		catch(Exception e)
		{
			test.fail("Max Price Item failed to and added to cart "  ).addScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
		}
	}
}

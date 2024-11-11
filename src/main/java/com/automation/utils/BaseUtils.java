package com.automation.utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.executor.BaseExecutor;

public class BaseUtils extends BaseExecutor {
	
public String getTestData(String field){
	
	
	return testDataTestCaseWise.get(currentTestCase).get(currentMethodIteration).get(field);
}

public WebElement getElemnt(String locator)
{
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
	return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	
}
public List<WebElement> getElemnts(String locator)
{
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
	 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	 return driver.findElements(By.xpath(locator));
	
}

public void setText(WebElement element,String text)
{
	element.sendKeys(text);
	
}

public String getText(WebElement element)
{
	return element.getText();
	
}

public void click(WebElement element)
{
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
	 wait.until(ExpectedConditions.elementToBeClickable(element));
	 element.click();
}

public void scrollToElement(WebElement element)
{
	JavascriptExecutor executor= (JavascriptExecutor) driver;
	executor.executeScript("arguments[0].scrollIntoView(true);", element);
}

}

package com.automation.utils;


import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	
	private static WebDriver driver;

    public static WebDriver getDriver(String browser,boolean headless,String implicitwaitTime,String pageLoadTimeOut) {
    	{
    		if(driver==null)
    			driver=intializeDriver(browser, headless,implicitwaitTime,pageLoadTimeOut);
    		return driver;
    	}
    		
    	}
    private static WebDriver intializeDriver(String browser,boolean headless, String implicitwaitTime, String pageLoadTimeOut)
    {
    	switch (browser.toLowerCase())
    	{
    	case "chrome":
    		//WebDriverManager.chromiumdriver().setup();
    		ChromeOptions cOptions=new ChromeOptions();
    		if(headless)
    			cOptions.addArguments("--headless");
    		driver=new ChromeDriver(cOptions);
        break;
    	case "firefox":
    		//WebDriverManager.firefoxdriver().setup();
    		FirefoxOptions fOptions=new FirefoxOptions();
    		if(headless)
    			fOptions.addArguments("--headless");
           driver=new FirefoxDriver(fOptions);
        break;
    	case "edge":
    		//WebDriverManager.edgedriver().setup();
    		EdgeOptions options=new EdgeOptions();
    		if(headless)
    			options.addArguments("--headless");
    	driver=new EdgeDriver(options);
    	break;
   
        default:
        	throw new IllegalArgumentException("Check browser column in run sheet");
    	}
        
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(implicitwaitTime)));
    	driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(pageLoadTimeOut)));
    	driver.manage().window().maximize();
        return driver;
    }
    public static void quitDriver()
    {
    	if(driver!=null)
    	{
    		driver.quit();
    		driver=null;
    	}
    }

}

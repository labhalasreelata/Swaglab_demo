package com.automation.executor;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.lang.reflect.Method;

import org.checkerframework.checker.units.qual.m;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

import com.automation.beans.ScenarioBean;
import com.automation.utils.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class DriverScriptExecutror extends BaseExecutor{
	
	public static  Map<String,Integer> methodCount=new HashMap<String, Integer>();

	public DriverScriptExecutror()
	{
		try {
		getTescasesToExecute();
		getKeywordsTestCaseWise();
		getTestDataOfTestCaseAndSequence();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{ 
			
			DriverScriptExecutror exec=new DriverScriptExecutror();
			
		
		
		for(Entry<String, ScenarioBean> entry:testRunsAndBrowser.entrySet())
		{
			String browser=entry.getValue().getBrowser();
			boolean headless=entry.getValue().getIsHeadless();
			String implicitwaitTime=entry.getValue().getImplicitWait();
			String pageLoadTimeOut=entry.getValue().getPageLoadWait();
			String tcName=entry.getValue().getTestCaseName();
			currentTestCase=tcName;
			List<String> keywords=keywordsTestCaseWise.get(entry.getKey());
			getDriver(browser, headless, implicitwaitTime, pageLoadTimeOut);
			htmlReporter=new ExtentSparkReporter(tcName+"-report.html");
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setDocumentTitle(tcName+" Report");
			htmlReporter.config().setReportName(tcName);
			extent=new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Browser",browser);
			test=extent.createTest(tcName);
			test.log(Status.INFO,"Test Case Statring");
			System.out.println(keywords);
		    executeKeyWords(keywords);
		    DriverManager.quitDriver();
		    
		    extent.flush();
		}
		
	}
	private static void executeKeyWords(List<String> keywords) {
		// TODO Auto-generated method stub
	
		for(String method:keywords)
		{
			System.out.println("method "+method);
			if(methodCount.get(method)==null)
				methodCount.put(method,1);
			else
				methodCount.put(method,methodCount.get(method+1));
			currentMethodIteration=methodCount.get(method);
			invokeMethod(method);
		}
	}
	private static void invokeMethod(String method) {
		// TODO Auto-generated method stub
		Reflections reflections=new Reflections("com.automation.steps",Scanners.SubTypes);
		Set<Class<? extends BaseExecutor>> classes=reflections.getSubTypesOf(BaseExecutor.class);
		boolean isMethodFound=false;
		for(Class<?> clazz:classes)
		{
			//System.out.println("class "+clazz.getName());
			try {
			Method methodToExecute=clazz.getDeclaredMethod(method); 
			isMethodFound=true;
			Object instance=clazz.getDeclaredConstructor().newInstance();
			methodToExecute.invoke(instance);
			break;
			}catch(NoSuchMethodException e)
			{
				//System.out.println(method +"Not Exist in class"+clazz.getName());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(!isMethodFound)
		{
			System.out.println("method not exist with name"+method);
		}
	}


}

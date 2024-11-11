package com.automation.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.automation.beans.ScenarioBean;
import com.automation.utils.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseExecutor {
	
	
	public static WebDriver driver;
	public static String currentTestCase;
	public static int currentMethodIteration;
	public static  Map<String,ScenarioBean> testRunsAndBrowser=new LinkedHashMap<String,ScenarioBean>();
	public static Map<String,List<String>> keywordsTestCaseWise=new LinkedHashMap<String,List<String>>();
	public static Map<String,Map<Integer,Map<String,String>>> testDataTestCaseWise=new LinkedHashMap<String,Map<Integer,Map<String,String>>>();
	public static Map<Integer,Map<String,String>> testCaseDataMap=null;
	public static Map<String,String> iterationMap=null;
	public static ExtentReports extent;
	public static ExtentSparkReporter htmlReporter;
    public static ExtentTest test;
	
	public static void getDriver(String browser,boolean headless,String implicitwaitTime,String pageLoadTimeOut) {
		driver=DriverManager.getDriver(browser, headless, implicitwaitTime, pageLoadTimeOut);
	}
	public void getTestDataOfTestCaseAndSequence() throws IOException{
		FileInputStream fInputStream=new FileInputStream(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"StepsAndData.xlsx");
		Workbook book= new XSSFWorkbook(fInputStream);
			Sheet sheet=book.getSheet("Data");
			String currentTestCase="";
			Row headerRow=sheet.getRow(0);
			for(int i=1;i<=sheet.getLastRowNum();i++)
			{
				Row row=sheet.getRow(i);
				if(row!=null)
					if(row.getCell(0)!=null)
					{
						String testCaseName=row.getCell(0).getStringCellValue();
						Integer iteration=(int) row.getCell(1).getNumericCellValue();
						testCaseDataMap=testDataTestCaseWise.getOrDefault(testCaseName, new LinkedHashMap<Integer, Map<String,String>>());
						iterationMap=testCaseDataMap.getOrDefault(iteration, new LinkedHashMap<String, String>());
			
						for(int c=2;c<row.getLastCellNum();c++)
						{
							iterationMap.put(headerRow.getCell(c).getStringCellValue(), row.getCell(c)!=null?row.getCell(c).getStringCellValue():"");
							testCaseDataMap.put(iteration, iterationMap);
							testDataTestCaseWise.put(testCaseName, testCaseDataMap);
						}
					
					}
			}
		
	}
	public void getKeywordsTestCaseWise() throws IOException {
		
		FileInputStream fInputStream=new FileInputStream(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"StepsAndData.xlsx");
		Workbook book= new XSSFWorkbook(fInputStream);
			Sheet sheet=book.getSheet("Flow");
			for(int i=0;i<=sheet.getLastRowNum();i++)
			{
				Row row=sheet.getRow(i);
				if(row!=null)
				{
					
					if(row.getCell(0)!=null && testRunsAndBrowser.get(row.getCell(0).getStringCellValue())!=null)
					{
						ArrayList<String> keywords= new ArrayList<String>();
						
						for(int c=1;c<row.getLastCellNum();c++)
						{
							System.out.println("Sample"+c);
							if(row.getCell(c)!=null)
							keywords.add(row.getCell(c).getStringCellValue());
						}
						
						System.out.println(row.getCell(0).getStringCellValue()+ ": "+keywords);
						keywordsTestCaseWise.put(row.getCell(0).getStringCellValue(), keywords);
					
					}
				}
			}
	}
	public void getTescasesToExecute() throws IOException {
		String runSheetPath=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"Runsheet.xlsx";
	FileInputStream fInputStream=new FileInputStream(runSheetPath);
	Workbook book= new XSSFWorkbook(fInputStream);
		Sheet sheet=book.getSheet("scenarios");
		for(int i=1;i<=sheet.getLastRowNum();i++)
		{
			Row row=sheet.getRow(i);
			if(row!=null)
				if(row.getCell(0)!=null && row.getCell(1)!=null && row.getCell(1).getStringCellValue().equalsIgnoreCase("yes"))
				{
					ScenarioBean bean=new ScenarioBean();
					System.out.println(row.getCell(0).getStringCellValue());
					bean.setTestCaseName(row.getCell(0).getStringCellValue());
					bean.setIsExecute(row.getCell(1).getStringCellValue());
					bean.setBrowser(row.getCell(2).getStringCellValue());
					bean.setIsHeadless(row.getCell(3).getBooleanCellValue());
					bean.setImplicitWait(row.getCell(4).getStringCellValue());
					bean.setPageLoadWait(row.getCell(5).getStringCellValue());
					testRunsAndBrowser.put(row.getCell(0).getStringCellValue(), bean);
				
				}
		}
		if(book!=null)
		{
			book.close();
		}
	}
	
	
}

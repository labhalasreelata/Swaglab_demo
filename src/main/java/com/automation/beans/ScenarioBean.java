package com.automation.beans;

public class ScenarioBean {
	
	private String testCaseName;
	private String isExecute;
	private String browser;
	private boolean isHeadless; 
	private String implicitWait; 
	private String pageLoadWait;
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public String getIsExecute() {
		return isExecute;
	}
	public void setIsExecute(String isExecute) {
		this.isExecute = isExecute;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public boolean getIsHeadless() {
		return isHeadless;
	}
	public void setIsHeadless(boolean b) {
		this.isHeadless = b;
	}
	public String getImplicitWait() {
		return implicitWait;
	}
	public void setImplicitWait(String implicitWait) {
		this.implicitWait = implicitWait;
	}
	public String getPageLoadWait() {
		return pageLoadWait;
	}
	public void setPageLoadWait(String pageLoadWait) {
		this.pageLoadWait = pageLoadWait;
	}
	
	

}

package com.aob.leanft;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.matchers.*;

import com.hp.lft.report.ReportException;
import com.hp.lft.report.Reporter;
import com.hp.lft.report.Status;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.stdwin.Window;
import com.hp.lft.sdk.web.Browser;
import com.hp.lft.sdk.web.BrowserDescription;
import com.hp.lft.sdk.web.BrowserFactory;
import com.hp.lft.sdk.web.BrowserType;
import com.hp.lft.sdk.insight.*;

public abstract  class myCustomBrowser implements com.hp.lft.sdk.web.Browser {

	static Browser browser;
	static BrowserType browserType;
	
	// Retrieve browser
	public Browser getBrowser()
	{
		return this;
	}

	// Instantiate browser type
	public static BrowserType launchBrowserType(String browserTypeUser)
	{
		switch(browserTypeUser)
		{
		case "CHROME"	:
			browserType = BrowserType.CHROME;
			break;
		case "IE"		:
			browserType = BrowserType.INTERNET_EXPLORER;
			break;
		case "FIREFOX"	:
			browserType = BrowserType.FIREFOX;
			break;
		default			:
			browserType = BrowserType.INTERNET_EXPLORER;
			break;
		}
		
		return browserType;	
	}
	
	// Launch browser with specific url
	public static Browser launchBrowserWithUrl(String userURL, String browserTypeUser) throws GeneralLeanFtException, ReportException
	{
		browser = BrowserFactory.launch(launchBrowserType(browserTypeUser));
		browser.navigate(userURL);
		try
		{
			org.hamcrest.MatcherAssert.assertThat(userURL,org.junit.matchers.JUnitMatchers.containsString(browser.getOpenURL()));
		} 
		catch (AssertionError e)
		{
			e.printStackTrace();
			Reporter.reportEvent("Open the browser with valid URL", "The specified browser type should be launched with the provided URL", Status.Failed);
		}
		
		Reporter.reportEvent("Open the browser with valid URL", "The specified browser type should be launched with the provided URL", Status.Passed);
		return browser;
	} 
	
	// Close browser
	public static void closeOpenedBrowser(com.hp.lft.sdk.web.Browser b) throws ReportException
	{
		try 
		{
			b.close();
			Reporter.reportEvent("Close the browser", "The specified browser type should be closed", Status.Passed);
		} 
		catch (GeneralLeanFtException e) 
		{
			e.printStackTrace();
		}
	}
	

}

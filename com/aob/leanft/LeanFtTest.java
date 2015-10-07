package com.aob.leanft;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.hp.lft.report.*;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.web.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeanFtTest {
	
	static myCustomBrowser myLeanFtBrowser;
	static Browser browser;
	
	public static ModifiableReportConfiguration myReportCong(ModifiableReportConfiguration config)
	{
		config.setTitle("AOB");
		config.setDescription("This run results contain the steps performed on the Advantage Online Banking application");
		config.setTargetDirectory("C:/Program Files (x86)/Jenkins/jobs/LeanFT JUnit Test/htmlreports");
		config.setReportFolder("RunResults");
		config.setOverrideExisting(true);
		config.setReportLevel(ReportLevel.All);
		config.setSnapshotsLevel(CaptureLevel.All);
		
		return config;
	}
	
	static ModifiableReportConfiguration config = new ModifiableReportConfiguration();
	static Reporter myReport;

	@BeforeClass
	public  static void beforeClass() throws Exception 
	{
		//globalSetup(LeanFtTest.class);
		ModifiableSDKConfiguration SDKConfig = new ModifiableSDKConfiguration();
		SDKConfig.setServerAddress(new URI("ws://localhost:5095"));		
		SDK.init(SDKConfig);
		Reporter.init(myReportCong(config));
	}

	@AfterClass
	public  static void afterClass() throws Exception 
	{
		//globalTearDown();
		Reporter.generateReport();
		SDK.cleanup();
	}
	
	@Test
	public void ALaunchApplication() throws GeneralLeanFtException, ReportException
	{
		// Opening the Advantage Online Banking Web Application
		browser = myLeanFtBrowser.launchBrowserWithUrl("http://172.16.239.243:47001/advantage/", "CHROME");
	}
 
	@Test
	public void BLogin() throws GeneralLeanFtException, ReportException {
		try 
		{			
			// Set Username jojo in the username field
			EditField usernameEditField = browser.describe(EditField.class, new EditFieldDescription.Builder()
			.type("text").tagName("INPUT").name("j_username").build());
			usernameEditField.setValue("jojo");
			
			// Set password passw0RD in the password field
			EditField passwordEditField = browser.describe(EditField.class, new EditFieldDescription.Builder()
			.type("password").xpath("//INPUT[@id=\"password\"]").tagName("INPUT").name("j_password").build());
			passwordEditField.setValue("passw0RD");
			
			// Click on the login button
			Button loginButton = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Login").build());
			loginButton.click();
			
			// Verify that login was successful
			WebElement welcomeMessage = browser.describe(WebElement.class, new WebElementDescription.Builder().className("welcome").tagName("DIV").innerText(new RegExpProperty("Welcome.*")).build());
			if(welcomeMessage.exists())
			{
					Reporter.reportEvent("Succesful Login", "Verify that the user was able to log in using his credentials", Status.Passed);
			}
			else
			{
					Reporter.reportEvent("Unsuccesfull Login", "Verify that the user was able to log in using his credentials", Status.Failed);
			}
				
		} 
		catch (GeneralLeanFtException e) 
		{
			e.printStackTrace();
		}	
	}

	@Test
	public void CreateAccount() throws GeneralLeanFtException {
		
			// Click the Create Account link
			Link createAccountLink = browser.describe(Link.class, new LinkDescription.Builder().tagName("A").innerText("Create Account ").build());
			createAccountLink.click();
			
			// Set last name of the customer
			EditField lastNameEditField = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("lastName").build());
			lastNameEditField.setValue("John");
			
			// Set first name of the customer
			EditField firstNameEditField = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("firstName").build());
			firstNameEditField.setValue("Alex");
			
			// Set address of the customer
			EditField addressEditField = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("address").build()); 
			addressEditField.setValue("20 rue de la glacière");
			
			// Click on the continue button
			Button continueButton = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Continue").build()); 
			continueButton.click();
			
			// Set the initial amount
			EditField initialAmount = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("amount").build());
			initialAmount.setValue("25");
			
			ListBox fromAccount = browser.describe(ListBox.class, new ListBoxDescription.Builder().tagName("SELECT").name("account").build());
			fromAccount.select(2);		
			
			// Click on the create account link
			Button continueButton1 = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Continue").build());
			continueButton1.click();
			
			Button createAccountButton = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Create account").build());
			createAccountButton.click();
	}

	@Test
	public void DLogout() throws GeneralLeanFtException, ReportException {			
				
			// Click the logout link
			Link logoutLink = browser.describe(Link.class, new LinkDescription.Builder().tagName("A").innerText("Logout").build());
			logoutLink.click();
				
			// Verify that the logout was successful
			Link signUp = browser.describe(Link.class, new LinkDescription.Builder().tagName("A").innerText("Sign Up").build());
			if(signUp.exists())
			{
				Reporter.reportEvent("Successful logout", "Verify that the user was able to log out", Status.Passed);
			}
			else
			{
				Reporter.reportEvent("Successful logout", "Verify that the user was able to log out", Status.Failed);
			} 
	}
	
	@Test
	public void ECloseBrowser() throws GeneralLeanFtException {
		//Close browser
		browser.close();
	}
	
	
}
 
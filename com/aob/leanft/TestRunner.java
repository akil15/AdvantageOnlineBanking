package com.aob.leanft;

import java.net.URI;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import com.hp.lft.report.*;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;

public class TestRunner {
	



	public  static void main(String[] args) {
			
		try
		{

			
		    Result result = JUnitCore.runClasses(LeanFtTest.class);
			System.exit(0);
		}
		catch (Exception e)
		{
		   System.exit(1);
		}

   }
   

}  	
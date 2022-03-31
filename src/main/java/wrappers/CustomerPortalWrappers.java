package wrappers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentTest;

import utils.DataInputProvider;

public class CustomerPortalWrappers extends GenericWrappers {
	
	public String browserName;
	public String dataSheetName;

	@BeforeSuite
	public void beforeSuite(){
		startResult();
	}

	@BeforeTest
	public void beforeTest(){
		loadObjects();
	}
	
	@BeforeMethod
	public void beforeMethod(){
//		test = startTestCase(testCaseName, testDescription);
//		test.assignCategory(category);
//		test.assignAuthor(authors);
//		invokeApp(browserName);
		//invokeApp(browserName, true);
	}
		
	@AfterSuite
	public void afterSuite(){
		endResult();
	}

	@AfterTest
	public void afterTest(){
		unloadObjects();
		//driver.quit();
	}
	
	@AfterMethod
	public void afterMethod(){
		endTestcase();
		//quitBrowser();
		
	}
	
	@DataProvider(name="fetchData")
	public Object[][] getData(){
//		System.out.println(dataSheetName);
		return DataInputProvider.getSheet(dataSheetName);		
	}	
	
}
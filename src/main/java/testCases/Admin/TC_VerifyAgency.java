package testCases.Admin;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.Admin.adminAgencyManagement;
import wrappers.CustomerPortalWrappers;


public class TC_VerifyAgency extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "Agency";
		testCaseName  = "Search Agency User";
		testDescription = "Search Agency User and verify the search results.";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void verifyAgencyDetails(String agencyCode, String name , String region, String email,String mobile,String postalCode, String salesOffice, String branch) throws Exception {
		new adminAgencyManagement(driver,test)
		.clickAgencyMenu()
		.enterAgencyCode(agencyCode)
		.verifyAgencyData(agencyCode, name, region,email,mobile);
		
	
	}
	
	
}

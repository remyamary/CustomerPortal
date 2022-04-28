package testCases.Admin;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.Admin.adminAgencyManagement;
import wrappers.CustomerPortalWrappers;


public class TC_VerifyBranch extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "BranchData";
		testCaseName  = "Verify agency data with branches";
		testDescription = "Search Agency User and verify the agency data that has branch information.";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void verifyBranchDet(String agencyCode, String branchCode, String name, String country,
			String region) throws Exception {
		new adminAgencyManagement(driver,test)
		.clickAgencyMenu()
		.enterAgencyCode(agencyCode)
		.clickViewMore()
		.verifyBranchData(agencyCode, branchCode, name, country, region);
		driver.quit();
	
	}
	
	
}

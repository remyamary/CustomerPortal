
package testCases.Admin;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.Admin.adminAgencyManagement;
import wrappers.CustomerPortalWrappers;


public class TC_VerifyAgencyAddress extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "Agency";
		testCaseName  = "Verify agency data with no branches";
		testDescription = "Search Agency User and verify the agency data has no branch information.";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void verifyAgencyDet(String agencyCode, String name , String region, String email,String mobile,String postalCode, String salesOffice, String branch) throws Exception {
		boolean noBranch = true;
		new adminAgencyManagement(driver,test)
		.clickViewMore()
		.verifyNoBranchInfo(noBranch);
		
	
	}
	
	
}

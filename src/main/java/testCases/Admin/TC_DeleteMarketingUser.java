package testCases.Admin;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.Admin.createMarketingUserPage;
import wrappers.CustomerPortalWrappers;


public class TC_DeleteMarketingUser extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "ManageMarketingUser";
		testCaseName  = "Delete Marketing User";
		testDescription = "Delete marketing user and verify.";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void deleteMarketingUser(String empCode, String name , String userRole, String editedRole) throws Exception {
		new createMarketingUserPage(driver,test)
		.clickDeleteMarketingUserLink()
		.clickdeleteConfirmButton()
		.verifyDeleteMarketingEmployee(empCode);
			
	}
	
	
}


package testCases.Admin;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.Admin.createMarketingUserPage;
import wrappers.CustomerPortalWrappers;


public class TC_EditMarketingUser extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "ManageMarketingUser";
		testCaseName  = "Edit Marketing User";
		testDescription = "Edit marketing user and verify the edited created user.";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void editMarketingUser(String empCode, String name , String userRole, String editedRole) throws Exception {
		boolean editFlag = true;
		new createMarketingUserPage(driver,test)
		.clickEditMarketingUserIcon()
		.clickEmployeeRole(editedRole)
		.clickSubmitEmployee()
	    .verifyMarketingEmployee(empCode, name, editedRole, editFlag);
			
	}
	
	
}


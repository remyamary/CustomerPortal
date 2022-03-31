package testCases.Admin;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.Admin.createMarketingUserPage;
import wrappers.CustomerPortalWrappers;


public class TC_CreateMarketingUser extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		//dataSheetName = "MarketingUser";
		dataSheetName = "ManageMarketingUser";
		testCaseName  = "Create Marketing User";
		testDescription = "Add new marketing user and verify the newly created user.";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void addMarketingUser(String EmpCode, String Name , String UserRole, String EditedRole) throws Exception {	
		new createMarketingUserPage(driver,test)
		.clickUserMenu()
		.clickMarketingUserTab()
		.clickAddNewUserButton()
		.clickMarketingUserLink()
		.enterEmployeeCode(EmpCode)
		.clickSubmitEmployeeCode()
		.clickEmployeeRole(UserRole)
		.clickSubmitEmployee()
		.verifyMarketingEmployee(EmpCode, Name, UserRole);
		
	
	}
	
	
}

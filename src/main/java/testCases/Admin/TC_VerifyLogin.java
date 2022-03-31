package testCases.Admin;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.Admin.adminLoginPage;
import wrappers.CustomerPortalWrappers;


public class TC_VerifyLogin extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "LoginPage";
		testCaseName  = "Login & verify login";
		testDescription = "Login to Customer Portal application & verify login";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void verifyLogin(String UserName, String Password, String VerifyLogin) throws Exception {	
		invokeApp(browserName);
		new adminLoginPage(driver, test)
		.username(UserName)
		.password(Password)
		.clickLoginButton()
		.verifyLogin(VerifyLogin);
				
	}
	
	
}

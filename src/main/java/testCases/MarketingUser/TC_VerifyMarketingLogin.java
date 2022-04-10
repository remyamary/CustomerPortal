package testCases.MarketingUser;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.MarketingUser.marketingLoginPage;
import wrappers.CustomerPortalWrappers;


public class TC_VerifyMarketingLogin extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "MarketingLogin";
		testCaseName  = "Login & verify Marketing user login";
		testDescription = "Login to Customer Portal application & verify marketing user login";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void verifyMarketingLogin(String userName, String password, String verifyLogin) throws Exception {
		String isMarketingUser = "Yes";
		invokeApp(browserName,isMarketingUser);
		new marketingLoginPage(driver, test)
		.username(userName)
		.password(password)
		.clickLoginButton()
		.verifyLogin(verifyLogin);
				
	}
	
	
}

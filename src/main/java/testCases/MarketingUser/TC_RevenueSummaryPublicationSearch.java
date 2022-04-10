package testCases.MarketingUser;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.MarketingUser.revenueSummaryPage;
import wrappers.CustomerPortalWrappers;


public class TC_RevenueSummaryPublicationSearch extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "RevenueSummary";
		testCaseName  = "Search revenue summary page using PUBLICATION GROUP";
		testDescription = "Search revenue summary page using PUBLICATION GROUP & verify the search result";
		category = "Regression Test";
		authors = "Remya Mary Paul";
		browserName = "chrome";
		ExtentTest test = startTestCase(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
	}

	@Test(dataProvider = "fetchData")
	public void verifyRevenueSearchDateRange(String dateRange, String salesOffice, String publication) throws Exception {
		String fileName="./data/CustomerPortalTestData.xlsx";
		//String dataSheet="RevenueSummaryDateWise";
		String dataSheet="RevenueSummaryPublication";
		new revenueSummaryPage(driver, test)
		.clickResetSearchButton()
		.enterDateRange(dateRange)
		.selectPublicationGroup()
		.clickSubmitButton()		
		.verifyRevenueSummaryResults(fileName, dataSheet);
					
	}
	
	
}

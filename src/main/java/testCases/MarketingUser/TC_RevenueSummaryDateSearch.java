package testCases.MarketingUser;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pages.MarketingUser.revenueSummaryPage;
import wrappers.CustomerPortalWrappers;


public class TC_RevenueSummaryDateSearch extends CustomerPortalWrappers{
	String environment = "";

	@BeforeClass
	public void setValues() throws InterruptedException {
		dataSheetName = "RevenueSummary";
		testCaseName  = "Search revenue summary page using date range";
		testDescription = "Search revenue summary page using date range & verify the search result";
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
		String dataSheet="RevenueSummaryDate";
		new revenueSummaryPage(driver, test)
		.clickResetSearchButton()
		.enterDateRange(dateRange)
		.clickSubmitButton()
		.verifyRevenueSummaryResults(fileName, dataSheet);
				
	}
	
	
}

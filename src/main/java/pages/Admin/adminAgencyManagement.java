
package pages.Admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.relevantcodes.extentreports.ExtentTest;

import wrappers.CustomerPortalWrappers;

public class adminAgencyManagement extends CustomerPortalWrappers {

	public adminAgencyManagement(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		this.driver = driver;
		this.test = test;
	}

	public adminAgencyManagement clickAgencyMenu() throws InterruptedException {
		Thread.sleep(5000);
		clickByXpath(prop.getProperty("cp.agencyMenu.xpath"));
		return this;
	}

	public adminAgencyManagement enterAgencyCode(String agencyCode) {
		enterByXpath(prop.getProperty("cp.agencyCodeSearch.xpath"), agencyCode);
		return this;
	}

	/**
	 * This is a common method for verifying the agency user.
	 * 
	 * @author Remya Mary Paul
	 * @param agencyCode,name, region,email
	 * @throws InterruptedException
	 *
	 */
	public adminAgencyManagement verifyAgencyData(String agencyCode, String name, String region, String email,
			String mobile) throws InterruptedException {
		boolean result = false;
		String beforeAgencyUserXpath;
		// enterByXpath(prop.getProperty("cp.agencyUserSearch.xpath"), agencyCode);
		beforeAgencyUserXpath = prop.getProperty("cp.beforeAgencyUser.xpath");
		System.out.println("Start of printing table values");
		String[] actualUserValues = new String[5];
		actualUserValues[0] = agencyCode;
		actualUserValues[1] = name;
		actualUserValues[2] = region;
		actualUserValues[3] = email;
		actualUserValues[4] = mobile;
		for (int i = 1; i <= 5; i++) {
			String actualUserXpath = beforeAgencyUserXpath + i + "]";
			String tableValue = driver.findElement(By.xpath(actualUserXpath)).getText();
			System.out.println("Value " + i + ":" + tableValue);
			if (actualUserValues[i - 1].equals(tableValue)) {
				System.out.println("inside if stmt and i=" + i + ",Table Value = " + tableValue);
				result = true;
			}

		}
		if (result = true) {
			reportStep("Agency User table data completely matches with the excel data", "PASS");

		}

		else

		{
			reportStep("Table data and excel data mismatch", "FAIL");
		}
		System.out.println("Result=" + result);
		return this;
	}

	public adminAgencyManagement clickViewMore() throws InterruptedException {
		Thread.sleep(3000);
		clickByXpath(prop.getProperty("cp.agencyViewMore.xpath"));
		return this;
	}

	public adminAgencyManagement verifyNoBranchInfo(Boolean branchVal) throws InterruptedException {
		Thread.sleep(3000);
		String branchXpath = prop.getProperty("cp.agencyNoBranch.xpath");
		if(branchVal) {
		if (driver.findElement(By.xpath(branchXpath)).isDisplayed()) {

			System.out.println("No Branch verification Success");
			reportStep("Agency/Branch table data completely matches with the excel data", "PASS");

		}

		else {
			reportStep("Table data and excel data mismatch", "FAIL");
		}
		}
		
		
		return this;
	}
	
	public adminAgencyManagement verifyBranchData(String agencyCode, String branchCode, String name, String country,
			String region) throws InterruptedException {
		boolean result = false;
		String beforeBranchUserXpath;
		// enterByXpath(prop.getProperty("cp.agencyUserSearch.xpath"), agencyCode);
		///html/body/app-root/app-agency-details/main/div[1]/div/div[2]/table/tbody/tr[1]/td[1]
		beforeBranchUserXpath = prop.getProperty("cp.beforeBranchUser.xpath");
		System.out.println("Start of printing table values");
		String[] actualUserValues = new String[5];
		actualUserValues[0] = agencyCode;
		actualUserValues[1] = branchCode;
		actualUserValues[2] = name;
		actualUserValues[3] = country;
		actualUserValues[4] = region;
		for (int i = 1; i <= 5; i++) {
			String actualUserXpath = beforeBranchUserXpath + i + "]";
			String tableValue = driver.findElement(By.xpath(actualUserXpath)).getText();
			System.out.println("Value " + i + ":" + tableValue);
			if (actualUserValues[i - 1].equals(tableValue)) {
				System.out.println("inside if stmt and i=" + i + ",Table Value = " + tableValue);
				result = true;
			}

		}
		if (result = true) {
			reportStep("Branch table data completely matches with the excel data", "PASS");

		}

		else

		{
			reportStep("Table data and excel data mismatch", "FAIL");
		}
		System.out.println("Result=" + result);
		return this;
	}
}
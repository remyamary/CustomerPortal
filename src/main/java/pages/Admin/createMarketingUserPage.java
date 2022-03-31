
package pages.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.relevantcodes.extentreports.ExtentTest;

import wrappers.CustomerPortalWrappers;

public class createMarketingUserPage extends CustomerPortalWrappers {

	public createMarketingUserPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		this.driver = driver;
		this.test = test;
	}

	public createMarketingUserPage clickUserMenu() throws InterruptedException {
		Thread.sleep(5000);
		clickByXpath(prop.getProperty("cp.ManageUser.xpath"));
		return this;
	}

	public createMarketingUserPage clickMarketingUserTab() {
		clickByXpath(prop.getProperty("cp.MarketingUserTab.xpath"));
		return this;
	}

	public createMarketingUserPage clickAddNewUserButton() {
		clickByXpath(prop.getProperty("cp.AddNewUserButton.xpath"));
		return this;
	}

	public createMarketingUserPage clickMarketingUserLink() {
		clickByXpath(prop.getProperty("cp.AddMarketingUserLink.xpath"));
		return this;
	}

	public createMarketingUserPage enterEmployeeCode(String empcode) {
		enterById(prop.getProperty("cp.EmployeeCode.Id"), empcode);
		return this;
	}

	public createMarketingUserPage clickSubmitEmployeeCode() {
		clickByXpath(prop.getProperty("cp.MarketingUserSubmitButton.Xpath"));
		return this;
	}

	public createMarketingUserPage clickEmployeeRole(String UserRole) {
		// clickByXpath(prop.getProperty("cp.MarketingUserRole.Xpath"+UserRole+"]"));
		// driver.findElement(By.xpath("//label[contains(text(),'"+EditLocation+"')]")).click();
		driver.findElement(By.xpath("//label[text()='" + UserRole + "']")).click();
		return this;
	}

	public createMarketingUserPage clickSubmitEmployee() throws InterruptedException {
		// Thread.sleep(3000);
		clickByXpath(prop.getProperty("cp.MarketingRoleSubmit.Xpath"));
		return this;
	}

	public createMarketingUserPage verifyMarketingEmployee(String EmpCode, String EmpName, String EmpRole)
			throws InterruptedException {
		boolean result = false;
		enterByXpath(prop.getProperty("cp.MarketingUserSearchIn.xpath"), EmpCode);
		String beforeUserXpath = prop.getProperty("cp.BeforeUser.Xpath");
		System.out.println("Start of printing table values");
		String[] actualUserValues = new String[4];
		actualUserValues[0] = EmpCode;
		actualUserValues[1] = EmpName;
		actualUserValues[2] = EmpRole;
		for(int i=1;i<=3;i++) {
			String actualUserXpath = beforeUserXpath + i + "]";
			String tableValue = driver.findElement(By.xpath(actualUserXpath)).getText();
			System.out.println("Value " +i+ ":" + tableValue);
			if (actualUserValues[i-1].equals(tableValue)) {
				System.out.println("inside if stmt and i="+i+",Table Value = "+tableValue);
				result = true;
			}

		}
		if (result = true) {
			reportStep("Marketing User table data completely matches with the excel data", "PASS");

		}

		else

		{
			reportStep("Table data and excel data mismatch", "FAIL");
		}
		System.out.println("Result=" + result);
		return this;
	}

}

package pages.Admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		clickByXpath(prop.getProperty("cp.manageUser.xpath"));
		return this;
	}

	public createMarketingUserPage clickMarketingUserTab() throws InterruptedException {
		Thread.sleep(5000);
		clickByXpath(prop.getProperty("cp.marketingUserTab.xpath"));
		return this;
	}

	public createMarketingUserPage clickAddNewUserButton() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println("********** Add Marketing User ********");
		clickByXpath(prop.getProperty("cp.addNewUserButton.xpath"));
		return this;
	}

	public createMarketingUserPage clickMarketingUserLink() {
		clickByXpath(prop.getProperty("cp.addMarketingUserLink.xpath"));
		return this;
	}

	public createMarketingUserPage enterEmployeeCode(String empCode) {
		enterById(prop.getProperty("cp.employeeCode.id"), empCode);
		return this;
	}

	public createMarketingUserPage clickSubmitEmployeeCode()  throws InterruptedException{
		clickByXpath(prop.getProperty("cp.marketingUserSubmitButton.xpath"));
		 Thread.sleep(3000);
		return this;
	}

	public createMarketingUserPage clickEmployeeRole(String userRole)  throws InterruptedException{
		// clickByXpath(prop.getProperty("cp.MarketingUserRole.Xpath"+UserRole+"]"));
		// driver.findElement(By.xpath("//label[contains(text(),'"+EditLocation+"')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[text()='" +userRole+ "']")).click();
		return this;
	}

	public createMarketingUserPage clickSubmitEmployee() throws InterruptedException {
		// Thread.sleep(3000);
		clickByXpath(prop.getProperty("cp.marketingRoleSubmit.xpath"));
		return this;
	}

	/**
	 * This is a common method for verifying the newly added/ edited marketing user.
	 * 
	 * @author Remya Mary Paul
	 * @param empCode
	 * @param empName
	 * @param empRole
	 * @param editFlag - pass true if we need to verify edited marketing employee.
	 * @return
	 * @throws InterruptedException
	 *
	 */
	public createMarketingUserPage verifyMarketingEmployee(String empCode, String empName, String empRole,
			Boolean editFlag) throws InterruptedException {
		boolean result = false;
		String beforeUserXpath;
		enterByXpath(prop.getProperty("cp.marketingUserSearch.xpath"), empCode);
		if (editFlag) {
			beforeUserXpath = prop.getProperty("cp.editedBeforeUser.xpath");
		} else {
			beforeUserXpath = prop.getProperty("cp.beforeUser.xpath");
		}
		System.out.println("Start of printing table values");
		String[] actualUserValues = new String[4];
		actualUserValues[0] = empCode;
		actualUserValues[1] = empName;
		actualUserValues[2] = empRole;
		for (int i = 1; i <= 3; i++) {
			String actualUserXpath = beforeUserXpath + i + "]";
			String tableValue = driver.findElement(By.xpath(actualUserXpath)).getText();
			System.out.println("Value " + i + ":" + tableValue);
			if (actualUserValues[i - 1].equals(tableValue)) {
				System.out.println("inside if stmt and i=" + i + ",Table Value = " + tableValue);
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
	// clickEditMarketingUserIcon method will click on Edit icon against a Marketing User

	public createMarketingUserPage clickEditMarketingUserIcon() {
		System.out.println("********** Edit Marketing User ********");
		clickByXpath(prop.getProperty("cp.editMarketingUserLink.xpath"));
		return this;
	}

	// clickDeleteMarketingUserIcon method will click on Delete link against a Marketing User
	public createMarketingUserPage clickDeleteMarketingUserLink() {
		System.out.println("********** Delete Marketing User ********");
		clickByXpath(prop.getProperty("cp.deleteMarketingUserLink.xpath"));
		return this;
	}
	// clickdeleteConfirmButton method will click on Confirm button for deleting Marketing User

	public createMarketingUserPage clickdeleteConfirmButton() throws InterruptedException {
		Thread.sleep(3000);
		clickByXpath(prop.getProperty("cp.deleteConfirmButton.xpath"));
		System.out.println("confirm delete");
		return this;
	}
// verifyDeleteMarketingEmployee method accepts employee code as parameter and verify whether the employee is deleted or not.
	
	public void verifyDeleteMarketingEmployee(String empCode) throws InterruptedException {
		Thread.sleep(3000);
		// To locate table and assign its contents.
		List<WebElement> empTable = driver.findElements(By.xpath(prop.getProperty("cp.employeeTable.xpath")));
		
		System.out.println("table=" + empTable);
		// If the table has no rows(elements), then the user delete is verified.
		if (empTable.size() == 0) {
			System.out.println("Marketing  user deleted");
			reportStep("Marketing  user: "+empCode+" deleted", "PASS");
		} else {
			System.out.println("Failed to delete marketing user");
			reportStep("Failed to delete marketing user: "+empCode, "FAIL");
		}
		//driver.quit();
	}

}

package pages.MarketingUser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.relevantcodes.extentreports.ExtentTest;

import wrappers.CustomerPortalWrappers;



public class revenueSummaryPage extends CustomerPortalWrappers {

	public revenueSummaryPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException
	{
		this.driver = driver;
		this.test = test;
	}
	
	
	public revenueSummaryPage clickResetSearchButton() throws InterruptedException
	{
		Thread.sleep(3000);
		clickByXpath(prop.getProperty("cp.revenueSummaryResetButton.xpath"));
		return this;
	}
	
	public revenueSummaryPage enterDateRange(String date) throws InterruptedException
	{	
		enterById(prop.getProperty("cp.revenueSummarydateRange.id"), date);
		clickByXpath(prop.getProperty("cp.revenueSummaryHeading.xpath"));
		return this;
	}
	
	public revenueSummaryPage clickSubmitButton() throws InterruptedException
	{
		Thread.sleep(3000);
		clickByXpath(prop.getProperty("cp.revenueSummarySubmitButton.xpath"));
		Thread.sleep(5000);
		return this;
	}

public revenueSummaryPage verifyRevenueSummaryResults(String dataFileName,String dataSheetName) throws Exception
	{
	String [] Heading =new String[10];
	Heading[0]="Sales Office";
	Heading[1]="Publication Group";
	Heading[2]="Revenue Target(INR)";
	Heading[3]="Revenue Actual(INR)";
	Heading[4]="Variance(INR)";
	Heading[5]="% of Achievement";
	Heading[6]="LY-Revenue Actual(INR)";
	Heading[7]="% Gr";
	
	String [][] webTableData=new String[10][25];
	String [][] excelData=new String[10][25];
    Thread.sleep(8000);
	excelData=excelread(dataFileName,dataSheetName);
	webTableData=getWebTableData();
	compareTwoArrays(Heading, excelData, webTableData);
	return this;

	}
public revenueSummaryPage selectPublicationGroup() throws InterruptedException{
	Thread.sleep(2000);
	clickByXpath(prop.getProperty("cp.revenueSummaryPublicationGroup.xpath"));	
	Thread.sleep(3000);
	clickByXpath(prop.getProperty("cp.revenueSummaryPublicationSelect.xpath"));
	//clickByXpath(prop.getProperty("cp.loginVerify.xpath"));
	WebElement webElement = driver.findElement(By.xpath("//mat-select[@placeholder='Publication Group']"));
	webElement.sendKeys(Keys.TAB);
	//WebElement element = driver.findElement(By.xpath("//p[contains(@class, 'cust-name')]"));
	//element.click();
	//new Actions(driver).moveToElement(element).perform();
 
	//driver.switchTo().defaultContent();
	//clickById(prop.getProperty("cp.revenueSummarydateRange.id"));
	//clickByXpath(prop.getProperty("cp.revenueSummaryHeading.xpath"));
	//clickByXpath(prop.getProperty("cp.revenueSummarySearchOuter.xpath"));
	//selectVisibileTextByXPath((prop.getProperty("cp.revenueSummaryPublicationGroup.xpath")), " MANORAMA DAILY DISPLAY ");
	return this;
}
public revenueSummaryPage selectSalesOffice() throws InterruptedException{
	Thread.sleep(2000);
	clickByXpath(prop.getProperty("cp.revenueSummarySalesOfficePlace.xpath"));	
	Thread.sleep(3000);
	clickByXpath(prop.getProperty("cp.revenueSummarySalesOffice.xpath"));
	WebElement webElement = driver.findElement(By.xpath("//mat-select[@placeholder='Sales Office']"));
	webElement.sendKeys(Keys.TAB);
	return this;
}

}
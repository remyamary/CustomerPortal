package pages.Admin;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.relevantcodes.extentreports.ExtentTest;

import wrappers.CustomerPortalWrappers;



public class adminLoginPage extends CustomerPortalWrappers {

	public adminLoginPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException
	{
		this.driver = driver;
		this.test = test;
	}
	
	
	public adminLoginPage username(String userName) throws InterruptedException
	{
		enterById(prop.getProperty("cp.userName.id"), userName);
		return this;
	}
	
	public adminLoginPage password(String password) throws InterruptedException
	{	
		enterById(prop.getProperty("cp.password.id"), password);
		return this;
	}
	
	public adminLoginPage clickLoginButton()
	{
		clickByXpath(prop.getProperty("cp.loginButton.xpath"));
		return this;
	}

	public adminLoginPage verifyLogin(String verifyLogin) throws InterruptedException
	{
		Thread.sleep(1000);
		verifyTextByXpath(prop.getProperty("cp.loginVerify.xpath"), verifyLogin);
		return this;
		
	}
	

}
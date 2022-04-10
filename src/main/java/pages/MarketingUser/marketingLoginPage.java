package pages.MarketingUser;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.relevantcodes.extentreports.ExtentTest;

import wrappers.CustomerPortalWrappers;



public class marketingLoginPage extends CustomerPortalWrappers {

	public marketingLoginPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException
	{
		this.driver = driver;
		this.test = test;
	}
	
	
	public marketingLoginPage username(String userName) throws InterruptedException
	{
		enterById(prop.getProperty("cp.userName.id"), userName);
		return this;
	}
	
	public marketingLoginPage password(String password) throws InterruptedException
	{	
		enterById(prop.getProperty("cp.password.id"), password);
		return this;
	}
	
	public marketingLoginPage clickLoginButton()
	{
		clickByXpath(prop.getProperty("cp.loginButton.xpath"));
		return this;
	}

	public marketingLoginPage verifyLogin(String verifyLogin) throws InterruptedException
	{
		Thread.sleep(1000);
		verifyTextByXpath(prop.getProperty("cp.loginVerify.xpath"), verifyLogin);
		return this;
		
	}
	

}
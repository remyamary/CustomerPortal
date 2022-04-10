package wrappers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.relevantcodes.extentreports.ExtentTest;


import utils.Reporter;

public class GenericWrappers extends Reporter implements Wrappers 
{
	public int rowCount;
	public int columnCount;
	public int tablerowsize;
	public int tablecolumnsize;
	public int tablerowsize1;
	public int tablecolumnsize1;
	public int tablerowsize2;
	public int tablecolumnsize2;
	public int tablecolumnsizedup;
	public int tablerowsizedup;
	public int actualtablecolumnsize;
	public static RemoteWebDriver driver;
	String[][] uploadboq_table_array;
	String[][] webTableArray;
	String[][] uploadboq_excel_array;
	String[][] projectConfig_Grid_array;
	String[][] materialCostCalculation_Grid_Array;
	String[][] material_Grid_array;
	String[][] IndividualCostForInsulation_Grid_Array;
	String[][] IndividualCostForSiteEstablishment_Grid_Array;
	public static Robot robo;

	


	public int tableRowSizeWithoutHeading;
	String[][] uploaded_excel_array ;
	String[][] totalCost_Grid_Array;
	//	public SauceREST restAPI;

	public GenericWrappers(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test=test;
	}

	protected static Properties prop;
	public String username , password , siteEstablishmentDataFile,dataSheetName,dataFileName,insulationDataFile ,
	sUrl,primaryWindowHandle,sHubUrl,marketingUrl,sHubPort,environment="",jobStatus="",matSupplyDataFile,manpowerSupplyDataFile,paintingDataFile;

	public GenericWrappers() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
			sUrl = prop.getProperty("URL");
			marketingUrl = prop.getProperty("MARKETING_URL");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadObjects() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/object.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unloadObjects() {
		prop = null;
	}

	/**
	 * This method will launch the browser in local machine and maximize the browser and set the
	 * wait for 30 seconds and load the url
	 * 
	 * @param url - The url with http or https
	 * 
	 */
	public void invokeApp(String browser) {
		invokeApp(browser,false);
	}

	/**
	 * This method will launch the browser in grid node (if remote) and maximize the browser and set the
	 * wait for 30 seconds and load the url 
	 * 
	 * @param url - The url with http or https
	 * 
	 */
	public void invokeApp(String browser, boolean bRemote) {
		try {

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			//			dc.setPlatform(Platform.WINDOWS);

			// this is for grid run
			if(bRemote) {
				driver = new RemoteWebDriver(new URL("http://"+sHubUrl+":"+sHubPort+"/wd/hub"), dc);
			} else {
				
				
				// this is for local run
				if(browser.equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					//WebDriverManager.chromedriver().setup();
					//WebDriver driver = new ChromeDriver();
					driver = new ChromeDriver();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					
					//Runtime.getRuntime().exec("C:\\Users\\anfas.ansary\\workspace\\POC-BrowserTests_Amazon\\data\\manageproxy.exe");
					//


				}else if(browser.equalsIgnoreCase("firefox")){
					System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
					//WebDriverManager.firefoxdriver().setup();
					//WebDriver driver = new FirefoxDriver();
				    driver = new FirefoxDriver();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					Runtime.getRuntime().exec("C:\\Users\\anfas.ansary\\workspace\\POC-BrowserTests_Amazon\\data\\firefoxproxyhandler.exe");
				}		
			}
			driver.get(sUrl);
			Thread.sleep(3000);
			primaryWindowHandle = driver.getWindowHandle();		
			reportStep("The browser:" + browser + " launched successfully", "PASS");
		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The browser:" + browser + " could not be launched", "FAIL");
		}
	}
	
	/**
	 * This is an overloaded method will launch the browser and maximize the browser and set the
	 * wait for 30 seconds and load the marketing user - login url 
	 * @author Remya Mary Paul
	 * @param browser - Browser name
	 * @param isMarketing - Yes  for launching Customer portal marketing URL
	 */
	
	public void invokeApp(String browser, String isMarketing) {
		try {

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			if(isMarketing=="Yes") {				
				if(browser.equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					driver = new ChromeDriver();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					
				    }
			}
			driver.get(marketingUrl);
			Thread.sleep(3000);
			primaryWindowHandle = driver.getWindowHandle();		
			reportStep("The browser:" + browser + " launched successfully", "PASS");
		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The browser:" + browser + " could not be launched", "FAIL");
		}
	}

	/**
	 * This method will launch the browser in saucelabs and maximise the browser and set the
	 * wait for 30 seconds and load the url 
	 * 
	 * @param url - The url with http or https
	 * 
	 */
	public void invokeURLInSauceBrowser(String sauceUserID, String virtualAccessKey, String realDeviceAPIKey, String platformOS,
			String platformOSVersion, String deviceName, String browser, String browserVersion, String TCName) throws MalformedURLException, InterruptedException{
		String URL = "https://" + sauceUserID + ":" + virtualAccessKey + "@ondemand.saucelabs.com:443/wd/hub";

		DesiredCapabilities dc = null;
		if(browser.equalsIgnoreCase("chrome")){	/* Open in Chrome browser */
			dc=DesiredCapabilities.chrome();
			dc.setCapability("platform", platformOS+" "+platformOSVersion);
			dc.setCapability("version", "62.0");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("firefox")){	/* Open in Firefox browser */
			//			FirefoxProfile profile = new FirefoxProfile();
			//			profile.setPreference("security.insecure_password.ui.enabled",false);
			dc=DesiredCapabilities.firefox();
			//			dc.setCapability(FirefoxDriver.PROFILE, profile);
			dc.setCapability("platform", platformOS+" "+platformOSVersion);
			dc.setCapability("version", "latest");
			dc.setCapability("name", TCName);
			//			dc.setCapability("security.insecure_password.ui.enabled", true);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("ie")){	/* Open in IE browser */
			dc=DesiredCapabilities.internetExplorer();
			dc.setCapability("platform", platformOS+" "+platformOSVersion);
			dc.setCapability("version", "latest");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("edge")){
			dc = DesiredCapabilities.edge();
			dc.setCapability("platform", platformOS+" "+platformOSVersion);
			dc.setCapability("version", "latest");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("safari")){	/* Open in Safari browser */
			dc=DesiredCapabilities.safari();
			dc.setCapability("platform", platformOS+" "+platformOSVersion);
			//			dc.setCapability("platform", "macOS 10.13");
			dc.setCapability("version", "latest");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("androidreal")){	/* Open in iPhone browser */
			dc=DesiredCapabilities.android();
			dc.setCapability("testobjectApiKey", realDeviceAPIKey);
			dc.setCapability("testobject_app_id", sauceUserID);
			dc.setCapability("appiumVersion", "1.8.1");
			dc.setCapability("deviceName", deviceName);
			dc.setCapability("deviceOrientation", "portrait");
			dc.setCapability("platformVersion",platformOSVersion);
			dc.setCapability("platformName", platformOS);
			dc.setCapability("browserName", "Chrome");
			dc.setCapability("name", TCName);
			environment = "real";
		}else if(browser.equalsIgnoreCase("iphonereal")){	/* Open in iPhone browser */
			dc=DesiredCapabilities.iphone();
			dc.setCapability("testobjectApiKey", realDeviceAPIKey);
			dc.setCapability("testobject_app_id", sauceUserID);
			dc.setCapability("appiumVersion", "1.8.1");
			dc.setCapability("deviceName", deviceName);
			dc.setCapability("deviceOrientation", "portrait");
			dc.setCapability("platformVersion",platformOSVersion);
			dc.setCapability("platformName", platformOS);
			dc.setCapability("browserName", "Safari");
			dc.setCapability("name", TCName);
			environment = "real";
		}else if(browser.equalsIgnoreCase("iPhone")){	/* Open in iPhone Emulator browser */
			dc=DesiredCapabilities.iphone();
			dc.setCapability("appiumVersion", "1.8.1");
			dc.setCapability("deviceName","iPhone 8 Simulator");
			dc.setCapability("deviceOrientation", "portrait");
			dc.setCapability("platformVersion","11.3");
			dc.setCapability("platformName", "iOS");
			dc.setCapability("browserName", "Safari");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("iPad")){	/* Open in iPad Emulator browser */
			dc=DesiredCapabilities.iphone();
			dc.setCapability("appiumVersion", "1.8.1");
			dc.setCapability("deviceName","iPad Simulator");
			dc.setCapability("deviceOrientation", "portrait");
			dc.setCapability("platformVersion","11.3");
			dc.setCapability("platformName", "iOS");
			dc.setCapability("browserName", "Safari");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("Android Emulator")){	/* Open in Android Emulator browser */
			dc=DesiredCapabilities.android();
			dc.setCapability("appiumVersion", "1.8.1");
			dc.setCapability("deviceName","Android GoogleAPI Emulator");
			dc.setCapability("deviceOrientation", "portrait");
			dc.setCapability("browserName", "Chrome");
			dc.setCapability("platformVersion", "7.0");
			dc.setCapability("platformName","Android");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("Android Tab")){ /* Open in Android Tab Emulator browser */
			dc=DesiredCapabilities.android();
			dc.setCapability("appiumVersion", "1.8.1");
			dc.setCapability("deviceName","Samsung Galaxy Tab S3 GoogleAPI Emulator");
			dc.setCapability("deviceOrientation", "portrait");
			dc.setCapability("browserName", "Chrome");
			dc.setCapability("platformVersion", "7.0");
			dc.setCapability("platformName","Android");
			dc.setCapability("name", TCName);
			environment = "virtual";
		}else if(browser.equalsIgnoreCase("Ownlab–android-chrome")){ /* Script for running test in local Mobile Browser */
			dc = new DesiredCapabilities();
			dc.setCapability("deviceName", deviceName);
			dc.setCapability("platformVersion", platformOSVersion);
			dc.setCapability("platformName", platformOS);
			dc.setCapability("browserName", "Chrome");
			environment = "ownlab";
		}

		if(environment.equalsIgnoreCase("virtual")){
			driver = new RemoteWebDriver(new URL(URL), dc);
		}else if(environment.equalsIgnoreCase("real")){
			driver = new RemoteWebDriver(new URL("https://us1.appium.testobject.com/wd/hub"), dc);
			//			RemoteWebDriver driver = new RemoteWebDriver(new URL("https://eu1.appium.testobject.com/wd/hub"), dc);
		}else if(environment.equalsIgnoreCase("ownlab")){
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), dc);
		}
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		driver.get(sUrl);
		Thread.sleep(5000);
	}	
	public void invokeURLInLocalMobileBrowser(String platformName, String platformVersion, String deviceName, String browserName, String TCName) throws MalformedURLException, InterruptedException{

		try {			

			System.out.println("i am in invoke");
			System.out.println(platformName);
			System.out.println(platformVersion);
			System.out.println(deviceName);
			System.out.println(browserName);
			System.out.println(TCName);

			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName",platformName);
			caps.setCapability("platformVersion", platformVersion);
			caps.setCapability("deviceName", deviceName);
			caps.setCapability(CapabilityType.BROWSER_NAME, browserName);
			driver = new RemoteWebDriver (new URL("http://127.0.0.1:4723/wd/hub"),caps);


			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			driver.get(sUrl);

			Thread.sleep(5000);
			//	reportStep("The browser:" + browserName + " launched successfully", "PASS");

		}catch (Exception e) {
			//	e.printStackTrace();
			//	reportStep("The browser:" + browserName + " could not be launched", "FAIL");
		}	
	}	
	/**
	 * This method will enter the value to the text field using id attribute to locate
	 * 
	 * @param idValue - id of the webelement
	 * @param data - The data to be sent to the webelement
	 * 
	 * @throws IOException
	 * @throws COSVisitorException 
	 */
	public void enterById(String idValue, String data) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idValue)));
			driver.findElement(By.id(idValue)).clear();
			driver.findElement(By.id(idValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+idValue, "PASS");
		} catch (NoSuchElementException e) {
			reportStep("The data: "+data+" could not be entered in the field :"+idValue, "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+idValue, "FAIL");
		}
	}

	/**
	 * This method will enter the value to the text field using id attribute to locate
	 * 
	 * @param idValue - id of the webelement
	 * @param data - The data to be sent to the webelement
	 * 
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	public void enterByIdWithoutClear(String idValue,  String data) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idValue)));
			driver.findElement(By.id(idValue)).sendKeys(data);
			reportStep("The data: "+data+" entered successfully in field :"+idValue, "PASS");
		} catch (NoSuchElementException e) {
			reportStep("The data: "+data+" could not be entered in the field :"+idValue, "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+idValue, "FAIL");
		}
	}

	/**
	 * This method will enter the value to the text field using name attribute to locate
	 * 
	 * @param nameValue - name of the webelement
	 * @param data - The data to be sent to the webelement
	 * 
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public void enterByName(String nameValue, String data) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name(nameValue)));
			driver.findElement(By.name(nameValue)).clear();
			driver.findElement(By.name(nameValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+nameValue, "PASS");
		} catch (NoSuchElementException e) {
			reportStep("The data: "+data+" could not be entered in the field :"+nameValue, "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+nameValue, "FAIL");
		}
	}

	/**
	 * This method will enter the value to the text field using name attribute to locate
	 * 
	 * @param xpathValue - xpathValue of the webelement
	 * @param data - The data to be sent to the webelement
	 * 
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public void enterByXpath(String xpathValue, String data) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathValue)));
			System.out.println("GM:"+data);
			driver.findElement(By.xpath(xpathValue)).clear();
			//			Thread.sleep(3000);
			//System.out.println("2");
			driver.findElement(By.xpath(xpathValue)).sendKeys(data);
			//System.out.println("3");
			reportStep("The data: "+data+" entered successfully in field :"+xpathValue, "PASS");
		} catch (NoSuchElementException e) {
			reportStep("The data: "+data+" could not be entered in the field :"+xpathValue, "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+xpathValue, "FAIL");
		}
	}
	
	
	
	
	


	/**
	 * This method will verify the title of the browser 
	 * @param title - The expected title of the browser
	 * 
	 */
	public boolean verifyTitle(String title){
		boolean bReturn = false;
		try{
			if (driver.getTitle().equalsIgnoreCase(title)){
				reportStep("The title of the page: "+driver.getTitle()+" matches with the value :"+title, "PASS");
				bReturn = true;
			}else
				reportStep("The title of the page: "+driver.getTitle()+" did not match with the value :"+title, "FAIL");
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will verify the given text matches in the element value attribute
	 * @param xpath - The locator of the object in xpath
	 * @param text  - The text to be verified
	 * 
	 */
	public void verifyTextByXpathUsingValue(String xpath, String text){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			String sText = driver.findElementByXPath(xpath).getAttribute("value");
			//			System.out.println("sText:"+sText);
			//			System.out.println("text:"+text);
			if (sText.trim().equalsIgnoreCase(text.trim())){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the value", "FAIL");
		}
	}
	
	/**
	 * This method will verify the given text matches in the element any  attribute
	 * @param xpath - The locator of the object in xpath
	 * @param text  - The text to be verified
	 * Sarita Balakrishnan
	 */
	public void verifyTextByXpathUsingAttribute(String xpath,String attribute, String text){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			String sText = driver.findElementByXPath(xpath).getAttribute(attribute);
						System.out.println("sText:"+sText);
						System.out.println("text:"+text);
			if (sText.trim().equalsIgnoreCase(text.trim())){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the value", "FAIL");
		}
	}

	/**
	 * This method will verify the given text matches in the element text
	 * @param xpath - The locator of the object in xpath
	 * @param text  - The text to be verified
	 * 
	 */
	public void verifyTextByXpath(String xpath, String text){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			String sText = driver.findElementByXPath(xpath).getText();
						System.out.println("sText:"+sText);
						System.out.println("text:"+text);
			if (sText.trim().equalsIgnoreCase(text.trim())){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the value", "FAIL");
		}
	}

	
	
	public void verifySubstringTextByXpath(String xpath, String text){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			String sText = driver.findElementByXPath(xpath).getText();
			sText=sText.trim().replace(sText.substring(sText.length()-1), "");
						System.out.println("sText:"+sText);
						System.out.println("text:"+text);
			if (sText.trim().equalsIgnoreCase(text.trim())){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the value", "FAIL");
		}
	}
	
	public void verifySubstringTextarrayByXpath(String xpath1, String xpath2,String text){
		try {
			
//			WebDriverWait wait = new WebDriverWait(driver, 30);
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath1)));
					
						String []textArray =text.split(",");
						int len=textArray.length;
						for(int i=0; i<len; i++)
						{int j=i+1;
							
							System.out.println(xpath1+j+xpath2);
							String sText = driver.findElementByXPath(xpath1+j+xpath2).getText();
							sText=sText.trim().replace(sText.substring(sText.length()-1), "");
							System.out.println("sText:"+sText);
							System.out.println("text:"+textArray[i]);
			if (sText.trim().equalsIgnoreCase(textArray[i].trim())){
				reportStep("The text: "+sText+" matches with the value :"+textArray[i], "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+textArray[i], "FAIL");
			}
						}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the value", "FAIL");
		}
	}
	/**
	 * This method will verify the given text is available in the element text
	 * @param xpath - The locator of the object in xpath
	 * @param text  - The text to be verified
	 * 
	 */
	public void verifyTextContainsByXpath(String xpath, String text){
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			String sText = driver.findElementByXPath(xpath).getText();
			if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not contain the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in id
	 * @param text  - The text to be verified
	 * 
	 */
	public void ScrollPageUp() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, -500);");

	}
	public void ScrollPageDown() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,200)");

	}

	public void verifyTextById(String id, String text) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			String sText = driver.findElementById(id).getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in id
	 * @param text  - The text to be verified
	 * 
	 */
	public void verifyTextContainsById(String id, String text) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			String sText = driver.findElementById(id).getText();
			if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not contain the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in id
	 * @param text  - The text to be verified
	 * 
	 */
	public void verifyElementDisplayed(String id) {
		try{
			if (driver.findElementById(id).isDisplayed()){
				reportStep("Element: "+id+" is displayed", "PASS");
			}else{
				reportStep("Element: "+id+" is not displayed", "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}/**
	
	/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in id
	 * @param text  - The text to be verified
	 * 
	 */
	public void verifyElementDisplayedByXpath(String xpath,String element) {
		try{
			if (driver.findElementByXPath(xpath).isDisplayed()){
				reportStep("Element: "+element+" is displayed", "PASS");
			}else{
				reportStep("Element: "+element+" is not displayed", "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the element displayed", "FAIL");
		}
	}/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in id
	 * @param text  - The text to be verified
	 * 
	 */
	public void verifyDeletedElementDisplayed(String xpath,String element) {
		try{
			if (driver.findElements(By.xpath(xpath)).size()==0){
				reportStep("The "+element+" is deleted", "PASS");
			}else{
				reportStep("The "+element+" is not deleted", "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}
	
	

	/**
	 * This method will close all the browsers
	 * 
	 */
	public void quitBrowser() {
		try {
			driver.quit();
			reportStep("The browser:"+driver.getCapabilities().getBrowserName()+" is closed.", "PASS");
		} catch (Exception e) {
			reportStep("The browser:"+driver.getCapabilities().getBrowserName()+" could not be closed.", "FAIL");
		}

	}

	/**
	 * This method will click the element using id as locator
	 * @param id  The id (locator) of the element to be clicked
	 * 
	 */
	public void clickById(String id) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			driver.findElement(By.id(id)).click();
			reportStep("The element with id: "+id+" is clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with id: "+id+" could not be clicked.", "FAIL");
		}
	}

	/**
	 * This method will click the element using id as locator
	 * @param id  The id (locator) of the element to be clicked
	 * 
	 */
	public void clickByClassName(String classVal) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className(classVal)));
			driver.findElement(By.className(classVal)).click();
			reportStep("The element with class Name: "+classVal+" is clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with class Name: "+classVal+" could not be clicked.", "FAIL");
		}
	}

	/**
	 * This method will click the element using name as locator
	 * @param name  The name (locator) of the element to be clicked
	 * 
	 */
	public void clickByName(String name) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name(name)));
			driver.findElement(By.name(name)).click();
			reportStep("The element with name: "+name+" is clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with name: "+name+" could not be clicked.", "FAIL");
		}
	}

	/**
	 * This method will click the element using link name as locator
	 * @param name  The link name (locator) of the element to be clicked
	 * 
	 */
	public void clickByLink(String name) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(name)));
			driver.findElement(By.linkText(name)).click();
			reportStep("The element with link name: "+name+" is clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with link name: "+name+" could not be clicked.", "FAIL");
		}
	}

	/**
	 * This method will click the element using xpath as locator
	 * @param xpathVal  The xpath (locator) of the element to be clicked
	 * 
	 */
	public void clickByXpath(String xpathVal) {
		try{
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathVal)));
			driver.findElement(By.xpath(xpathVal)).click();
			reportStep("The element : "+xpathVal+" is clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with xpath: "+xpathVal+" could not be clicked.", "FAIL");
		}
	}

	/**
	 * This method hit enter against the element using xpath as locator
	 * @param xpath  The xpath (locator) of the element to be click
	 * 
	 */
	public void clickEnterByXpath(String xpath) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER);
			reportStep("The element with id: "+xpath+" is clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with id: "+xpath+" could not be clicked.", "FAIL");
		}
	}

	/**
	 * This method will mouse over on the element using xpath as locator
	 * @param xpathVal  The xpath (locator) of the element to be moused over
	 * 
	 */
	public void mouseOverByXpath(String xpathVal) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathVal)));
			new Actions(driver).moveToElement(driver.findElement(By.xpath(xpathVal))).build().perform();
			reportStep("The mouse over by xpath : "+xpathVal+" is performed.", "PASS");
		} catch (Exception e) {
			reportStep("The mouse over by xpath : "+xpathVal+" could not be performed.", "FAIL");
		}
	}

	/**
	 * This method will mouse over on the element and click using xpath as locator
	 * @param xpathVal  The xpath (locator) of the element to be mouse over
	 * 
	 */
	public void mouseOverByXpathAndClick(String xpathVal) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathVal)));
			new Actions(driver).moveToElement(driver.findElement(By.xpath(xpathVal))).click().build().perform();
			reportStep("The mouse over and click by xpath : "+xpathVal+" is performed.", "PASS");
		} catch (Exception e) {
			reportStep("The mouse over and click by xpath : "+xpathVal+" could not be performed.", "FAIL");
		}
	}

	/**
	 * This method will mouse over on the element using link name as locator
	 * @param xpathVal  The link name (locator) of the element to be moused over
	 * 
	 */
	public void mouseOverByLinkText(String linkName) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkName)));
			new Actions(driver).moveToElement(driver.findElement(By.linkText(linkName))).build().perform();
			reportStep("The mouse over by link : "+linkName+" is performed.", "PASS");
		} catch (Exception e) {
			reportStep("The mouse over by link : "+linkName+" could not be performed.", "FAIL");
		}
	}

	/**
	 * This method will return the text of the element using xpath as locator
	 * @param b  The xpath (locator) of the element
	 * 
	
	public String getTextByXpath(boolean b){
		String bReturn = "";
		try{
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(b)));
			return driver.findElement(By.xpath(b)).getText();
		} catch (Exception e) {
			reportStep("The element with xpath: "+b+" could not be found.", "FAIL");
		}
		return bReturn; 
	} */

	/**
	 * This method will return the text of the element using id as locator
	 * @param xpathVal  The id (locator) of the element
	 * 
	 */
	public String getTextById(String idVal) {
		String bReturn = "";
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idVal)));
			return driver.findElementById(idVal).getText();
		} catch (Exception e) {
			reportStep("The element with id: "+idVal+" could not be found.", "FAIL");
		}
		return bReturn; 
	}


	/**
	 * This method will select the drop down value using id as locator
	 * @param id The id (locator) of the drop down element
	 * @param value The value to be selected (visibletext) from the dropdown 
	 * 
	 */
	public void selectVisibileTextById(String id, String value) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			new Select(driver.findElement(By.id(id))).selectByVisibleText(value);;
			reportStep("The element with id: "+id+" is selected with value :"+value, "PASS");
		} catch (Exception e) {
			reportStep("The value: "+value+" could not be selected.", "FAIL");
		}
	}

	public void selectVisibileTextByXPath(String xpath, String value) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(value);;
			reportStep("The element with xpath: "+xpath+" is selected with value :"+value, "PASS");
		} catch (Exception e) {
			reportStep("The value: "+value+" could not be selected.", "FAIL");
		}
	}

	public void selectIndexById(String id, String value) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			new Select(driver.findElement(By.id(id))).selectByIndex(Integer.parseInt(value));;
			reportStep("The element with id: "+id+" is selected with index :"+value, "PASS");
		} catch (Exception e) {
			reportStep("The index: "+value+" could not be selected.", "FAIL");
		}
	}

	public void switchToParentWindow() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
				break;
			}
		} catch (Exception e) {
			reportStep("The window could not be switched to the first window.", "FAIL");
		}
	}

	public void switchToLastWindow() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
			}
		} catch (Exception e) {
			reportStep("The window could not be switched to the last window.", "FAIL");
		}
	}

	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
			reportStep("The alert accepted.", "PASS");
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be accepted.", "FAIL");
		}

	}


	public String getAlertText() {		
		String text = null;
		try {
			text = driver.switchTo().alert().getText();
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be accepted.", "FAIL");
		}
		return text;
	}

	public void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
			reportStep("The alert dismissed.", "PASS");
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be accepted.", "FAIL");
		}

	}

	@Override
	public long takeSnap(){
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			reportStep("The browser has been closed.", "FAIL");
		} catch (IOException e) {
			reportStep("The snapshot could not be taken", "WARN");
		}
		return number;
	}

	/**
	 * This method will click the dropdown using its name
	 * @param name  The name (locator) of the element to be clicked
	 * 
	 */
	public void selectDdByName(String name, String option) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name(name)));
			Select dropdown = new Select(driver.findElement(By.name(name)));
			//			dropdown.selectByValue(option);
			dropdown.selectByVisibleText(option);
			reportStep("The element with class Name: "+option+" is clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with class Name: "+option+" could not be clicked.", "FAIL");
		}
	}

	/**
	 * This method will click the dropdown using its name
	 * @param name  The name (locator) of the element to be clicked
	 * 
	 */
	public void verifyRadioButton(String xpath, String option) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			String status = driver.findElement(By.xpath(xpath)).getAttribute("checked");
			//			System.out.println(status);
			if(status.equalsIgnoreCase(option)){
				reportStep("The radio button of "+xpath+" is checked", "PASS");
			}
		} catch (Exception e) {
			reportStep("The radio button of "+xpath+" is not checked", "FAIL");
		}
	}


	/**
	 * This method will verify the data in the 1st row and 2 nd column
	 * @param Actualvalue (the expected value should be in the table)

	 */
	public void verifyTableElement(String xpath, String Actualvalue)
	{
		
		try 
		{	//String xpath="//table/tbody/tr[1]/td[2]";
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			Thread.sleep(2000);
			System.out.println(xpath);
			String Value= driver.findElement(By.xpath(xpath)).getText();
			System.out.println("val: "+Value);
			System.out.println("excelval "+Actualvalue);
			if(Value.equalsIgnoreCase(Actualvalue.trim()))
			{
				reportStep("Value checked successfully, the value entered by the user is "+Value, "PASS");
			}

			else
			{
				reportStep("Table value checking failed", "FAIL");
			}

		}
		catch (Exception e) 
		{
			reportStep("Table value checking failed", "FAIL");
		}
	}	

	/**This will check the updated table value
	 * param: xpath, updated value, old value
	 * 
	 * */

	public void tableValueUpdate(String xpath, String updatedValue, String oldValue) {
		try
		{
			Thread.sleep(2000);
			
			//clickByXpath(prop.getProperty("home.masters.paint.editpaintbtn"));
			//String xpath="//tr[1]//button[1]";
			//driver.findElement(By.xpath(xpath)).click();
			//Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("home.masters.paint.editpainttxtbox.xpath"))).clear();
			Thread.sleep(1000);
			enterByXpath(prop.getProperty("home.masters.paint.editpainttxtbox.xpath"), oldValue+updatedValue);
			Thread.sleep(1000);
			clickByXpath(prop.getProperty("home.masters.paint.editpaint.updatepaint.xpath"));
			Thread.sleep(1000);

			verifyTableElement(xpath, oldValue+updatedValue);


		}
		catch (Exception e)
		{
			reportStep("Table value update failed", "FAIL");
		}

	}

	/**This method is use to perform control+a clicking
	 * Parameters: xpath where you want to perform Control +a
	 * 
	 * */


	public void deselectAllvalues(String xpath)
	{
		try
		{
			driver.findElement(By.xpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			Thread.sleep(3000);
			driver.findElement(By.xpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			Thread.sleep(3000);

			reportStep("Deselected all values from drop down", "PASS");


		}
		catch (Exception e)
		{
			reportStep("Table value update failed", "FAIL");
		}
	}

	
	/*This method will get excel data and by row wise
	 * Parameter: sheet name which is passing form test case
	 * 
	 * */

	public String[][] excelread(String dataFileName,String dataSheetName) throws Exception 
	{		
		System.out.println("kjhgf"+dataSheetName);
		System.out.println("kiuytrs"+dataFileName);
		uploadboq_excel_array = null;
		FileInputStream fis=new FileInputStream(dataFileName);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(dataSheetName);
		rowCount = sheet.getLastRowNum();
		// get the number of columns
		columnCount = sheet.getRow(0).getLastCellNum();
		System.out.println("Column Count-:"+columnCount);
		System.out.println("Row Count-:"+rowCount);
		uploadboq_excel_array = new String[rowCount+1][columnCount];
		for(int i=1; i <rowCount+1; i++)
		{
			try 
			{
				XSSFRow row = sheet.getRow(i);
				for(int j=0; j<columnCount; j++)
				{ // loop through the columns
					try 
					{


						String cellValue = "";
						try
						{

							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							cellValue = row.getCell(j).getStringCellValue();
							//System.out.println(cellValue);
						}
						catch(NullPointerException e)
						{

						}

						uploadboq_excel_array[i-1][j]  = cellValue.trim(); // add to the data array
						System.out.println("excel cell values"+cellValue);
					} 
					catch (Exception e) {
						e.printStackTrace();
					}				
				}

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		fis.close();
		workbook.close();
		return uploadboq_excel_array;



	}

	/*This method will get web table data and store into an array by row wise
	 * 
	 * */

	public String[][] getWebTableData() throws Exception 
	{
		String beforexpath="//table[@class='table table-striped responsive-table']/tbody/tr[";
		String middlexpath="]/td[";
		String afterxpath="]";
		tablerowsize=driver.findElementsByXPath("//table[@class=\'table table-striped responsive-table\']/tbody/tr").size();
		tablecolumnsize=driver.findElementsByXPath("//table[@class=\'table table-striped responsive-table\']/tbody/tr[2]/td").size();
		//tablecolumnsizedup=tablecolumnsize-1;
		webTableArray= new String[tablerowsize][tablecolumnsize];
		for(int i=2;i<=tablerowsize;i++)
		{
			for(int j=1;j<=tablecolumnsize;j++)
			{
				String value="";
				value=driver.findElement(By.xpath(beforexpath+i+middlexpath+j+afterxpath)).getText();
				webTableArray[i-2][j-1]=value;
				System.out.println("Web data"+value);
			}
		}
		System.out.println("Web table Row count:"+tablerowsize);
		System.out.println("Web table Column count:"+tablecolumnsize);
		return webTableArray;		
	}

	
	

	
	
	
	/*This method will compare excel data and web table row wise
	 * 
	 * */

	public void comparearrays()
	{
		boolean result;
		if(tablerowsize==rowCount&&tablecolumnsizedup==columnCount)
		{
			for(int i=0;i<tablerowsize;i++)
			{
				for(int j=0;j<tablecolumnsize-1;j++)
				{	
					if(uploadboq_excel_array[i][j].equalsIgnoreCase(uploadboq_table_array[i][j]))
					{
						result=true;

					}

					else
					{
						reportStep("Table data and excel data mismatch for \t"+uploadboq_excel_array[0][j]+"\t uploadboq_table_array["+i+"]["+j+"]" +uploadboq_table_array[i][j] + "\t\t \t\t uploadboq_excel_array["+i+"]["+j+"]" + uploadboq_excel_array[i][j], "FAIL");
						//reportStep("Table data and excel data mismatch in value uploadboq_table_array["+i+"]["+j+"]" +uploadboq_table_array[i][j] + "\t\t \t\t uploadboq_excel_array["+i+"]["+j+"]" + uploadboq_excel_array[i][j], "FAIL");

					}

					//System.out.println("uploadboq_table_array["+i+"]["+j+"]" +uploadboq_table_array[i][j] + "\t\t \t\t uploadboq_excel_array["+i+"]["+j+"]" + uploadboq_excel_array[i][j]);

				}
			}

		}

		else
		{
			reportStep("Table row size and coulumn size are different to excel row size and column size", "FAIL");
		}

		if(result=true)
		{
			reportStep("Table data is completely matches with the excel data", "PASS");

		}

		else

		{
			reportStep("Table data and excel data mismatch", "FAIL");
		}



	}


	public  void defaultCostCalculationOwnVehicle(int colDep,int colQnty,int colCost,int colDur,int colTotCost) throws InterruptedException





	{

		System.out.println("--------------------------------ownVehicleGridDataCalculation-OwnVehicle--------------------------------------");
		int i,j;
		String xpathRow = "//*[@id='example']//tbody/tr";
		String ReportMsg;

		int rowLim = driver.findElements(By.xpath(xpathRow)).size();

		System.out.println("rowLim :" + rowLim);

		String vehicleType ;
		float Cost = 0 , Depreciation = 0 , Duration = 0 , Quantity = 0, calcTotalCost =0 , uiTotalCost = 0 ;

		for(i=1;i<=rowLim ;i++)
		{

			System.out.println("------------- Looping " + i + " ----------------");

			vehicleType = driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+2+"]")).getText();
			System.out.println("vehicleType :" +vehicleType);

			//driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+colDep+"]/input")).sendKeys("25");
			System.out.println("Xpath  : //*[@id='example']//tbody/tr["+i+"]/td["+colDep+"]");
			//Thread.sleep(1000);

			System.out.println("before java");

			Depreciation = Float.valueOf(driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+colDep+"]/input")).getAttribute("value"));

			//Depreciation = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+colDep+"]/input")).getAttribute("value"));
			System.out.println("Depreciation :" +Depreciation);

			Quantity = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+colQnty+"]/input")).getAttribute("value"));
			System.out.println("Quantity :" + Quantity);

			Cost = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+colCost+"]/input")).getAttribute("value"));
			System.out.println("Cost :" +Cost);

			Duration = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+colDur+"]/input")).getAttribute("value"));
			System.out.println("Duration :" +Duration);

			uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+i+"]/td["+colTotCost+"]")).getText());
			System.out.println("uiTotalCost :" +uiTotalCost);

			calcTotalCost = ((Cost*Depreciation) * (Duration * Quantity)/100) ;
			System.out.println("calcTotalCost :" +calcTotalCost);

			System.out.println("vehicleType :" + vehicleType + " Cost : " + Cost +" Depreciation  : " +Depreciation+"  Duration  : " + Duration  +"  Quantity  : " +Quantity+"  calcTotalCost  : " + calcTotalCost +"uiTotalCost : " + uiTotalCost);


			if(uiTotalCost==calcTotalCost)
			{
				reportStep("Cost Calculation For "+vehicleType + "  Is Correct. Expected:  "+calcTotalCost+"  Actual :"+uiTotalCost , "PASS");
			}
			else
			{
				reportStep("Cost Calculation For "+vehicleType + "  Is Not Correct. Expected:  "+calcTotalCost+"  Actual :"+uiTotalCost ,"FAIL");
			}

		}

	}

	public void EditAndCostCalculationOwnVehicle(String Dep,String Qnty,String Cost,String Dur) throws InterruptedException
	{

		System.out.println("--------------------------------EditAndCostCalculation-OwnVehicle---------------------------------------");
		String textvalue;
		String ReportMsg;

		String Xpath = "//*[@id='example']/tbody/tr[2]/td[3]/input";
		WebElement  Element=driver.findElement(By.xpath(Xpath));
		textvalue = Element.getAttribute("ng-reflect-model");
		System.out.println("getAttribute('name') : " + textvalue);
		textvalue = Element.getAttribute("value");
		System.out.println("getAttribute('value') : " + textvalue);



		System.out.println("enter to the function");
		String DepXpath = "//*[@id='example']/tbody/tr[1]/td[3]/input";
		String QntyXpath = "//*[@id='example']/tbody/tr[1]/td[5]/input";
		String CostXpath = "//*[@id='example']/tbody/tr[1]/td[7]/input";
		String DurXpath = "//*[@id='example']/tbody/tr[1]/td[8]/input";
		String TotcostXpath = "//*[@id='example']/tbody/tr[1]/td[10]";
		String vehicleType = driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+1+"]/td["+2+"]")).getText();

		System.out.println("assigned Xpath");

		//driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+1+"]/td["+3+"]/input")).sendKeys("25");

		Thread.sleep(2000);
		driver.findElement(By.xpath(DepXpath)).clear(); 
		driver.findElement(By.xpath(QntyXpath)).clear();
		driver.findElement(By.xpath(CostXpath)).clear();
		driver.findElement(By.xpath(DurXpath)).clear();

		driver.findElement(By.xpath(DepXpath)).sendKeys(Dep);
		driver.findElement(By.xpath(QntyXpath)).sendKeys(Qnty);
		driver.findElement(By.xpath(CostXpath)).sendKeys(Cost);
		driver.findElement(By.xpath(DurXpath)).sendKeys(Dur);
		String UiTotalCost = driver.findElement(By.xpath(TotcostXpath)).getText();

		float CalcTotCost = Float.parseFloat(Dep) * Integer.parseInt(Qnty) * Float.parseFloat(Cost)*Float.parseFloat(Dur);

		System.out.println("UiTotalCost : " + UiTotalCost + "  CalcTotCost :"+CalcTotCost);

		float uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example']//tbody/tr["+1+"]/td["+10+"]")).getText());


		if(uiTotalCost==CalcTotCost)
		{
			reportStep("Cost Calculation For "+vehicleType + "  Is Correct. Expected:  "+CalcTotCost+"  Actual :"+uiTotalCost , "PASS");
		}
		else
		{
			reportStep("Cost Calculation For "+vehicleType + "  Is Not Correct. Expected:  "+CalcTotCost+"  Actual :"+uiTotalCost ,"FAIL");
		}
	}


	public  void defaultCostCalculationHiredVehicle(int colQnty,int colCost,int colDur,int colTotCost) throws InterruptedException
	{
		System.out.println("--------------------------------defaultCostCalculationHiredVehicle--------------------------------------");
		int i,j;
		String xpathRow = "//*[@id='example1']//tbody/tr";
		String ReportMsg;

		int rowLim = driver.findElements(By.xpath(xpathRow)).size();

		System.out.println("rowLim :" + rowLim);

		String vehicleType ;
		float Cost = 0 , Depreciation = 0 , Duration = 0 , Quantity = 0, calcTotalCost =0 , uiTotalCost = 0 ;









		for(i=1;i<=rowLim ;i++)
		{

			System.out.println("------------- Looping " + i + " ----------------");

			vehicleType = driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+i+"]/td["+2+"]")).getText();
			System.out.println("vehicleType :" +vehicleType);




			System.out.println("before java");


			Quantity = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+i+"]/td["+colQnty+"]/input")).getAttribute("value"));
			System.out.println("Quantity :" + Quantity);

			Cost = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+i+"]/td["+colCost+"]/input")).getAttribute("value"));
			System.out.println("Cost :" +Cost);

			Duration = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+i+"]/td["+colDur+"]/input")).getAttribute("value"));
			System.out.println("Duration :" +Duration);

			uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+i+"]/td["+colTotCost+"]")).getText());
			System.out.println("uiTotalCost :" +uiTotalCost);

			calcTotalCost = (Cost * (Duration * Quantity)) ;
			System.out.println("calcTotalCost :" +calcTotalCost);

			//System.out.println("vehicleType :" + vehicleType + " Cost : " + Cost +" Depreciation  : " +Depreciation+"  Duration  : " + Duration  +"  Quantity  : " +Quantity+"  calcTotalCost  : " + calcTotalCost +"uiTotalCost :"+uiTotalCost);


			if(uiTotalCost==calcTotalCost)
			{
				reportStep("Cost Calculation For "+vehicleType + "  Is Correct. Expected:  "+calcTotalCost+"  Actual :"+uiTotalCost , "PASS");
			}
			else
			{
				reportStep("Cost Calculation For "+vehicleType + "  Is Not Correct. Expected:  "+calcTotalCost+"  Actual :"+uiTotalCost ,"FAIL");
			}

		}

	}

	public void EditAndCostCalculationHiredVehicle(String Qnty,String Cost,String Dur) throws InterruptedException
	{

		System.out.println("--------------------------------EditAndCostCalculationHiredVehicle---------------------------------------");
		String textvalue;
		String ReportMsg;

		System.out.println("enter to the function");






		String QntyXpath = "//*[@id='example1']/tbody/tr[1]/td[4]/input";
		String CostXpath = "//*[@id='example1']/tbody/tr[1]/td[6]/input";
		String DurXpath = "//*[@id='example1']/tbody/tr[1]/td[7]/input";
		String TotcostXpath = "//*[@id='example1']/tbody/tr[1]/td[9]";
		String vehicleType = driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+1+"]/td["+2+"]")).getText();

		System.out.println("assigned Xpath");

		//driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+1+"]/td["+3+"]/input")).sendKeys("25");

		Thread.sleep(2000);

		driver.findElement(By.xpath(QntyXpath)).clear();
		driver.findElement(By.xpath(CostXpath)).clear();












		driver.findElement(By.xpath(DurXpath)).clear();


		driver.findElement(By.xpath(QntyXpath)).sendKeys(Qnty);
		driver.findElement(By.xpath(CostXpath)).sendKeys(Cost);
		driver.findElement(By.xpath(DurXpath)).sendKeys(Dur);
		String UiTotalCost = driver.findElement(By.xpath(TotcostXpath)).getText();

		float CalcTotCost =  Integer.parseInt(Qnty) * Float.parseFloat(Cost)*Float.parseFloat(Dur);

		System.out.println("UiTotalCost : " + UiTotalCost + "  CalcTotCost :"+CalcTotCost);

		float uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='example1']//tbody/tr["+1+"]/td["+9+"]")).getText());

		if(uiTotalCost==(CalcTotCost))
		{
			reportStep("Cost Calculation For "+vehicleType + "Is Correct. Expected: "+ CalcTotCost + " Actual :"+uiTotalCost , "PASS");
		}
		else
		{
			reportStep("Cost Calculation For "+vehicleType + "Is Not Correct. Expected: "+ CalcTotCost +  "Actual :"+uiTotalCost ,"FAIL");
		}
	}






	public  void defaultCostCalculationForOwnSiteFacilities(int colQnty , int colDep,int colDur ,int colCost ,int colTotCost) throws InterruptedException
	{									

		System.out.println("--------------------------------defaultCostCalculationForOwnSiteFacilities--------------------------------------");
		int i,j;
		String xpathRow = "//*[@id='site-facilities-owned']/tbody/tr";
		String ReportMsg;

		int rowLim = driver.findElements(By.xpath(xpathRow)).size();

		System.out.println("rowLim :" + rowLim);

		String SiteFacilities ;
		float UnitRate = 0 , Depreciation = 0 , Duration = 0 , Quantity = 0, calcTotalCost =0 , uiTotalCost = 0 ;

		for(i=1;i<=rowLim ;i++)
		{


			System.out.println("------------- Looping " + i + " ----------------");






			SiteFacilities = driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+i+"]/td["+2+"]")).getText();






			System.out.println("Site Facility  :" +SiteFacilities);


			Quantity = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+i+"]/td["+colQnty+"]/input")).getAttribute("value"));
			System.out.println("Quantity :" + Quantity);


			Depreciation = Float.valueOf(driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+i+"]/td["+colDep+"]/input")).getAttribute("value"));
			//Depreciation = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+i+"]/td["+colDep+"]/input")).getAttribute("value"));
			System.out.println("Depreciation :" +Depreciation);

			Duration = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+i+"]/td["+colDur+"]/input")).getAttribute("value"));
			System.out.println("Duration :" +Duration);

			UnitRate = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+i+"]/td["+colCost+"]/input")).getAttribute("value"));
			System.out.println("Cost :" +UnitRate);

			uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+i+"]/td["+colTotCost+"]")).getText());
			System.out.println("uiTotalCost :" +uiTotalCost);

			calcTotalCost = ((UnitRate*Depreciation) * (Duration * Quantity)/100) ;

			System.out.println("calcTotalCost :" +calcTotalCost);

			System.out.println("vehicleType :" + SiteFacilities + " Cost : " + UnitRate +" Depreciation  : " +Depreciation+"  Duration  : " + Duration  +"  Quantity  : " +Quantity+"  calcTotalCost  : " + calcTotalCost +"uiTotalCost :" + calcTotalCost);

			DecimalFormat numberFormat = new DecimalFormat("#.00");
			calcTotalCost = Float.parseFloat(numberFormat.format(calcTotalCost));

			if(uiTotalCost==(calcTotalCost))
			{
				reportStep("Cost Calculation For "+SiteFacilities + "Is Correct. Expected:"+calcTotalCost+"Actual :"+uiTotalCost , "PASS");
			}
			else
			{
				reportStep("Cost Calculation For "+SiteFacilities + "Is Not Correct. Expected:"+calcTotalCost+"Actual :"+uiTotalCost ,"FAIL");
			}

		}

	}

	public void EditAndCostCalculationForOwnSiteFacilities(String Qnty,String Dep,String Dur,String Cost) throws InterruptedException
	{

		System.out.println("--------------------------------EditAndCostCalculationForOwnSiteFacilities---------------------------------------");
		String textvalue;
		String ReportMsg;


		System.out.println("enter to the function");
		String QntyXpath = "//*[@id='site-facilities-owned']/tbody/tr[1]/td[3]/input";
		String DepXpath = "//*[@id='site-facilities-owned']/tbody/tr[1]/td[4]/input";
		String DurXpath = "//*[@id='site-facilities-owned']/tbody/tr[1]/td[5]/input";
		String CostXpath = "//*[@id='site-facilities-owned']/tbody/tr[1]/td[6]/input";

		String TotcostXpath = "//*[@id='site-facilities-owned']/tbody/tr[1]/td[7]";
		String SiteFacilities = driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+1+"]/td["+2+"]")).getText();

		System.out.println("assigned Xpath");

		//driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+1+"]/td["+3+"]/input")).sendKeys("25");

		Thread.sleep(2000);
		driver.findElement(By.xpath(DepXpath)).clear(); 
		driver.findElement(By.xpath(QntyXpath)).clear();
		driver.findElement(By.xpath(CostXpath)).clear();
		driver.findElement(By.xpath(DurXpath)).clear();

		driver.findElement(By.xpath(DepXpath)).sendKeys(Dep);
		driver.findElement(By.xpath(QntyXpath)).sendKeys(Qnty);
		driver.findElement(By.xpath(CostXpath)).sendKeys(Cost);
		driver.findElement(By.xpath(DurXpath)).sendKeys(Dur);
		String UiTotalCost = driver.findElement(By.xpath(TotcostXpath)).getText();

		float CalcTotCost = (Float.parseFloat(Dep) * Integer.parseInt(Qnty) * Float.parseFloat(Cost)*Float.parseFloat(Dur)/100);

		System.out.println("UiTotalCost : " + UiTotalCost + "  CalcTotCost :"+CalcTotCost);

		float uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-owned']/tbody/tr["+1+"]/td["+7+"]")).getText());

		DecimalFormat numberFormat = new DecimalFormat("#.00");
		CalcTotCost = Float.parseFloat(numberFormat.format(CalcTotCost));

		if(uiTotalCost==(CalcTotCost))
		{
			reportStep("Cost Calculation For "+SiteFacilities + "Is Correct. Expected: "+ CalcTotCost + " Actual :"+uiTotalCost , "PASS");
		}
		else
		{
			reportStep("Cost Calculation For "+SiteFacilities + "Is Not Correct. Expected: "+ CalcTotCost +  "Actual :"+uiTotalCost ,"FAIL");
		}
	}

	public  void defaultCostCalculationForHiredSiteFacilities(int colQnty ,int colDur ,int colCost ,int colTotCost) throws InterruptedException
	{									

		System.out.println("--------------------------------defaultCostCalculationForHiredSiteFacilities--------------------------------------");
		int i,j;
		String xpathRow = "//*[@id='site-facilities-hired']/tbody/tr";
		String ReportMsg;

		int rowLim = driver.findElements(By.xpath(xpathRow)).size();

		System.out.println("rowLim :" + rowLim);

		String SiteFacilities ;
		float UnitRate = 0  , Duration = 0 , Quantity = 0, calcTotalCost =0 , uiTotalCost = 0 ;

		for(i=1;i<=rowLim ;i++)
		{


			System.out.println("------------- Looping " + i + " ----------------");

			SiteFacilities = driver.findElement(By.xpath("//*[@id='site-facilities-hired']/tbody/tr["+i+"]/td["+2+"]")).getText();

			System.out.println("Site Facility  :" +SiteFacilities);


			Quantity = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-hired']/tbody/tr["+i+"]/td["+colQnty+"]/input")).getAttribute("value"));
			System.out.println("Quantity :" + Quantity);


			Duration = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-hired']/tbody/tr["+i+"]/td["+colDur+"]/input")).getAttribute("value"));
			System.out.println("Duration :" +Duration);

			UnitRate = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-hired']/tbody/tr["+i+"]/td["+colCost+"]/input")).getAttribute("value"));
			System.out.println("Cost :" +UnitRate);

			uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-hired']/tbody/tr["+i+"]/td["+colTotCost+"]")).getText());
			System.out.println("uiTotalCost :" +uiTotalCost);

			calcTotalCost = (UnitRate * (Duration * Quantity)) ;

			System.out.println("calcTotalCost :" +calcTotalCost);

			System.out.println("vehicleType :" + SiteFacilities + " Cost : " + UnitRate +"  Duration  : " + Duration  +"  Quantity  : " +Quantity+"  calcTotalCost  : " + calcTotalCost +"uiTotalCost :" + calcTotalCost);

			DecimalFormat numberFormat = new DecimalFormat("#.00");
			calcTotalCost = Float.parseFloat(numberFormat.format(calcTotalCost));

			if(uiTotalCost==(calcTotalCost))
			{
				reportStep("Cost Calculation For "+SiteFacilities + "Is Correct. Expected:"+calcTotalCost+"Actual :"+uiTotalCost , "PASS");
			}
			else
			{
				reportStep("Cost Calculation For "+SiteFacilities + "Is Not Correct. Expected:"+calcTotalCost+"Actual :"+uiTotalCost ,"FAIL");
			}

		}

	}

	public void EditAndCostCalculationForHiredSiteFacilities(String Qnty,String Dur,String Cost) throws InterruptedException
	{

		System.out.println("--------------------------------EditAndCostCalculationForHiredSiteFacilities---------------------------------------");
		String textvalue;
		String ReportMsg;


		System.out.println("enter to the function");

		String QntyXpath = "//*[@id='site-facilities-hired']/tbody/tr[1]/td[3]/input";
		String CostXpath = "//*[@id='site-facilities-hired']/tbody/tr[1]/td[5]/input";
		String DurXpath = "//*[@id='site-facilities-hired']/tbody/tr[1]/td[4]/input";
		String TotcostXpath = "//*[@id='site-facilities-hired']/tbody/tr[1]/td[6]";
		String SiteFacilities = driver.findElement(By.xpath("//*[@id='site-facilities-hired']/tbody/tr["+1+"]/td["+2+"]")).getText();

		System.out.println("assigned Xpath");

		Thread.sleep(2000);

		driver.findElement(By.xpath(QntyXpath)).clear();
		driver.findElement(By.xpath(CostXpath)).clear();
		driver.findElement(By.xpath(DurXpath)).clear();

		driver.findElement(By.xpath(QntyXpath)).sendKeys(Qnty);
		driver.findElement(By.xpath(CostXpath)).sendKeys(Cost);
		driver.findElement(By.xpath(DurXpath)).sendKeys(Dur);
		String UiTotalCost = driver.findElement(By.xpath(TotcostXpath)).getText();

		float CalcTotCost =  Integer.parseInt(Qnty) * Float.parseFloat(Cost)*Float.parseFloat(Dur);

		System.out.println("UiTotalCost : " + UiTotalCost + "  CalcTotCost :"+CalcTotCost);

		float uiTotalCost  = Float.parseFloat(driver.findElement(By.xpath("//*[@id='site-facilities-hired']/tbody/tr["+1+"]/td["+6+"]")).getText());

		DecimalFormat numberFormat = new DecimalFormat("#.00");
		CalcTotCost = Float.parseFloat(numberFormat.format(CalcTotCost));

		if(uiTotalCost==(CalcTotCost))
		{
			reportStep("Cost Calculation For "+ SiteFacilities + "Is Correct. Expected: "+ CalcTotCost + " Actual :"+uiTotalCost , "PASS");
		}
		else

		{
			reportStep("Cost Calculation For "+ SiteFacilities + "Is Not Correct. Expected: "+ CalcTotCost +  "Actual :"+uiTotalCost ,"FAIL");
		}
	}

	public void NavigateToUrl(String Url) throws InterruptedException 
	{
		driver.get(Url);

		Thread.sleep(3000);		
	}


	public void invokeSaucelabsBrowser(String username, String accesskey, String browser, String windowsver,
			String TCName) throws InterruptedException, MalformedURLException {
		// TODO Auto-generated method stub

	}


	public void compareTwoArrays(String[] Heading , String[][] ExpectedResult,String[][] ActualResult) 
	{
		System.out.println("SARITA "+Heading[1]);
	
		int i , j ,k , result = 0;
		String  jlimit = "Start" , ilimit = "Start" ;

		for(i=0;ilimit != null;i++)
		{
			for(j=0;jlimit != null;j++)
			{

				System.out.println("Value  :" + Heading[j] + " , "+ExpectedResult[i][j] + " , " + ActualResult[i][j]);

				result =0;

				if(ExpectedResult[i][j].equalsIgnoreCase(ActualResult[i][j]))
				{
					System.out.println("Equal  ");
					reportStep("The value of "+Heading[j] +" is matching. Expected : " + ExpectedResult[i][j] + " ,  Actual :"+ ActualResult[i][j] , "PASS");
					result=1;
				}
				else
				{
					reportStep("The value of "+Heading[j] +" is not matching. Expected : " + ExpectedResult[i][j] + " ,  Actual :"+ ActualResult[i][j] , "FAIL");
				}

				try
				{
					System.out.println("Try j : " + i +" " + j );
					jlimit = ExpectedResult[i][j+1];
				}
				catch ( Exception e)
				{
					System.out.println("Exception j : " + i +" " + j );
					jlimit = null;
				}		
			}

			try
			{
				ilimit = ExpectedResult[i+1][0];
				jlimit="start";
			}
			catch ( Exception e)
			{
				System.out.println("Exception : " + i +" " + j );
				ilimit = null ;
			}
		}


	}
	
	public void copyvaluestoclipboard(String BOQFileLocation)
	{
		try
		{	
			
			StringSelection stringSelection = new StringSelection(BOQFileLocation);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);	

			reportStep("Path: "+BOQFileLocation+ "Copied to clip board successfully ", "PASS");

		}
		catch (Exception e)
		{
			reportStep("Unable to copy the value to the clipboard", "FAIL");
		}
	}
	
	public void robotclass(String BOQFileLocation)
	{
		try
		{	
			StringSelection stringSelection = new StringSelection(BOQFileLocation);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);	

			Robot robot = new Robot( );
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_ENTER);

			reportStep("Data uploaded successfully ", "PASS");

		}
		catch (Exception e)
		{
			reportStep("Unable to upload data", "FAIL");
		}
	}
	
	public static void dragAndDropByCoordinates(int SourceX,int SourceY,int TargetX,int TargetY) throws InterruptedException, AWTException
	{
	robo = new Robot();

	robo.mouseMove(SourceX, SourceY);
	Thread.sleep(1000);
	robo.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	Thread.sleep(1000);
	robo.mouseMove(TargetX, TargetY);
	Thread.sleep(1000);
	robo.mouseMove(SourceX, SourceY);
	Thread.sleep(1000);
	robo.mouseMove(TargetX, TargetY);
	Thread.sleep(1000);
	robo.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	public int getElementCoordinates(String LocatorType,String Locator,String Cordinate)
	{
	WebElement cordElement = null;
	int cord = 0;

	if(LocatorType.equalsIgnoreCase("id"))
	{
	System.out.println("Locator :" + Locator+"BB");
	cordElement = driver.findElement(By.id(Locator));
	}
	else if(LocatorType.equalsIgnoreCase("xpath"))
	{
	cordElement = driver.findElement(By.xpath(Locator));
	}

	if(Cordinate.equalsIgnoreCase("X"))
	{
	cord = cordElement.getLocation().getX();
	}
	else if(Cordinate.equalsIgnoreCase("Y"))
	{
	cord = cordElement.getLocation().getY();
	}

	return cord;
	}
	public void compareTwoSingleDimArrays(String[] Heading , String[] ExpectedResult,String[] ActualResult)
	{

	int i , j ,k , result = 0;
	String jlimit = "Start" , ilimit = "Start" ;

	for(j=0;j<ExpectedResult.length;j++)
	{

	System.out.println("Value :" + Heading[j] + " , "+ExpectedResult[j] + " , " + ActualResult[j]);

	result =0;

	if(ExpectedResult[j].equalsIgnoreCase(ActualResult[j]))
	{
	System.out.println("Equal ");
	reportStep("The value of "+Heading[j] +" is matching. Expected : " + ExpectedResult[j] + " , Actual :"+ ActualResult[j] , "PASS");
	result=1;
	}
	else
	{
	reportStep("The value of "+Heading[j] +" is not matching. Expected : " + ExpectedResult[j] + " , Actual :"+ ActualResult[j] , "FAIL");
	}

	}

	}

	@Override
	public String getTextByXpath(String xpathVal) {
		// TODO Auto-generated method stub
		return null;
	}


}


package org.Base1;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;

import org.ExtentReportListner1.ExtentRep;
import org.Utility1.ExcelDataConfig;
import org.Utility1.GenericFunction;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hpsf.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import zmq.ZError.IOException;


public class DriverInstance1 extends ExtentRep {
	public WebDriver driver;
	public static WebDriverWait wait;


	@BeforeClass(alwaysRun = true)
	public void InitiateDriver() throws InterruptedException, IOException, MalformedURLException, java.io.IOException {
	
		System.setProperty("webdriver.chrome.driver", "src/org/Driver/chromedriver.exe");
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		driver =new ChromeDriver();
		driver.get("https://web.whatsapp.com/");
		driver.manage().window().maximize();
		System.out.println("Title :"+driver.getTitle());
		Thread.sleep(60000);

		
		}
		
	

	public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException, java.io.IOException {
	//String dateName = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
	TakesScreenshot ts = (TakesScreenshot) driver;
	File source = ts.getScreenshotAs(OutputType.FILE);

	String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + ".png";
	File finalDestination = new File(destination);
	FileUtils.copyFile(source, finalDestination);
	return destination;
	}
	
	
//@AfterMethod
public void teardown(ITestResult result) throws IOException, InterruptedException, java.io.IOException
{
	Thread.sleep(10000);
	String screenshotPath = getScreenShot(driver, result.getName());
	if(result.getStatus()== ITestResult.FAILURE)
	{
	
		logger.fail("Test Case Failed Snapshot " + logger.addScreenCaptureFromPath(screenshotPath));
		logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test Case is FAILED", ExtentColor.RED));
	
		//	driver.quit();
		
	}
	else if(result.getStatus()==ITestResult.SKIP)
	{
	
		logger.skip("Test Case Skipped Snapshot" + logger.addScreenCaptureFromPath(screenshotPath));
		logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case is SKIPPED", ExtentColor.AMBER));

		
	}else if(result.getStatus()==ITestResult.SUCCESS)
	{
		
		logger.pass("Test Case Passed Snapshot  " + logger.addScreenCaptureFromPath(screenshotPath));
		logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case is PASSED", ExtentColor.GREEN));
		
	}
	else
	{
		//extentTest.log(Status.INFO, "Test is ignored"+result.getName());
	}
	}
	//@AfterClass	
	public void endTest()
	{
		//driver.close();
	}
	//@AfterSuite
	public void generateReport()
	{
		
	}

}

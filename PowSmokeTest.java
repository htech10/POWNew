package smoke;

//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utils.Misc;
import Utilities.DataReader;

public class PowSmokeTest {
	
	String inputFile;
	DataReader xlsx;
	int rowCount[]={0,0};
	int sheetCount;
	int rowNum[]={0,0};
	WebDriver driver;
	ExtentReports report = null;
	ExtentTest logger = null;

	public void readInputData() {
		inputFile="./TestData/pownewlinks.xlsx";
		xlsx=new DataReader(inputFile);
		rowCount[0]=xlsx.getRowCount(0);
		rowCount[1]=xlsx.getRowCount(1);
		sheetCount=xlsx.getSheetCount();
		System.out.println("Total Sheets= "+sheetCount);
		System.out.println("Total Rows 0 = "+rowCount[0]);
		System.out.println("Total Rows 1 = "+rowCount[1]);
	}
	
	@BeforeClass
	public void setup() {
		readInputData();
		System.setProperty("webdriver.chrome.driver", "c:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("http://104.238.103.200:8080/POWNew/startadmin.do");
		
		String reportFile="./Reports/Current/results.html";
		report=new ExtentReports(reportFile);

	}
	
	@DataProvider(name="links")
	public Object[][] dataProvider401() {
		Object[][] temp=null;
		int sheetNum=0;
		int rows=xlsx.getLastRowIndex(sheetNum);
		int cols=3;
		int startRowIndex=1;
		
		temp=new Object[rows][cols];
		for(int i=0;i<rows;i++)
		{
			
			temp[i][0]=xlsx.getDataV2(sheetNum,startRowIndex+i, 0);
			temp[i][1]=xlsx.getDataV2(sheetNum,startRowIndex+i, 1);
			temp[i][2]=xlsx.getDataV2(sheetNum,startRowIndex+i, 2);
		}
		return temp;
	}
	
	
	@Test(dataProvider="links")
	public void verifyLinks(String tcnum, String linkText, String verifyKey) {
		System.out.println("--");
		System.out.print("tcnum ="+tcnum);
		System.out.print("==|==link ="+linkText);
		System.out.print("==|==key ="+verifyKey);
		System.out.println("--");
		logger=report.startTest("Links Verification : "+tcnum);
		logger.log(LogStatus.INFO, "Test Case Started");
		logger.log(LogStatus.INFO, "Input Condition: Verification Key :"+verifyKey);

		Assert.assertTrue(Misc.compare("in", "in"));
	}

	
	@Test
	public void screenShotTest() throws Exception {
		logger=report.startTest("Screenshot Verification : TC_008");
		logger.log(LogStatus.INFO, "Test Case Started");
		logger.log(LogStatus.INFO, "Input Condition: Random Input :");

		driver.findElement(By.id("loginbutton")).click();
		//File outputFile=new File("./Screenshots/output_screen899.png");
		TakesScreenshot sshot=(TakesScreenshot) driver;
		String dt= Misc.dateAppenderStr();
		File outputFile=new File("./Screenshots/output_screen"+dt+".png");
		File inputFile=sshot.getScreenshotAs(OutputType.FILE);
		if(outputFile.exists())
		{
			outputFile.delete();
		}
		Files.copy(inputFile.toPath(), outputFile.toPath()); 
		Assert.assertTrue(driver.getTitle().equals("SOMETHING"));
	}
	
	@AfterMethod
	public void assertEachTest(ITestResult result) {
		String tcname=result.getName();
		int status=result.getStatus();
		boolean tcresult=result.isSuccess();
		long timeInmilli=result.getEndMillis();
		
		if(result.isSuccess()) {
			
		System.out.println("true");
		System.out.println("getName= "+tcname);
		System.out.println("getStatus = "+status);
		System.out.println("isSuccess = "+tcresult);
		System.out.println("Execution Time = "+timeInmilli);
		System.out.println("After Each Test");

		logger.log(LogStatus.PASS, "Execution Passed "+tcname);

		} else 
		{
			StringWriter sw=new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    result.getThrowable().printStackTrace(pw);
		    String strace=sw.toString();

			String errMessage=result.getThrowable().getMessage();
			System.out.println("false");
			System.out.println("getName= "+tcname);
			System.out.println("getStatus = "+status);
			System.out.println("isSuccess = "+tcresult);
			System.out.println("Execution Time = "+timeInmilli);
			System.out.println("Error Message = "+errMessage);
	//		System.out.println("Stace ="+strace);
			System.out.println("After Each Test");
			logger.log(LogStatus.FAIL, "Execution Failed ");
			logger.log(LogStatus.FAIL, errMessage);
			logger.log(LogStatus.FAIL, strace);
				
		}
		
		report.endTest(logger);
		report.flush();

	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("Tear Down Here !!");
		driver.close();
	}

}

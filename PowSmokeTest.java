package smoke;

//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.DataReader;

public class PowSmokeTest {
	
	String inputFile;
	DataReader xlsx;
	int rowCount[]={0,0};
	int sheetCount;
	int rowNum[]={0,0};
	
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
	}

	@AfterMethod
	public void assertEachTest() {
		System.out.println("After Each Test");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("Tear Down Here !!");
	}

}

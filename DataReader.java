package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader { 
XSSFWorkbook workbook;
XSSFSheet sheet1;
//FileOutputStream fout;
public DataReader (String xlpath) {
	try {
		// Read the file by giving the path
		File src = new File(xlpath);
		//all input output related operation is in java.io
		FileInputStream fis = new FileInputStream(src);
		//this will load the workbook i.e. full excel sheet 
		workbook = new XSSFWorkbook(fis);
	}  catch (Exception e) {
		System.out.println("Exception: "+e.getMessage());
	}					
}

public int getXType(int sheetno,int row, int col) {
	System.out.println("in getXType: reading sheet "+sheetno+" row="+row+"  col="+col);
		  return workbook.getSheetAt(sheetno).getRow(row).getCell(col).getCellType();
}

public String getData(int sheetNumber, int row, int col) {
	return workbook.getSheetAt(sheetNumber).getRow(row).getCell(col).getStringCellValue();
}

public int getNum(int sheetNumber, int row, int col) {
	return (int) workbook.getSheetAt(sheetNumber).getRow(row).getCell(col).getNumericCellValue();
}

public boolean getBool(int sheetNumber, int row, int col) {
	return workbook.getSheetAt(sheetNumber).getRow(row).getCell(col).getBooleanCellValue();
}

public Date getDate(int sheetNumber, int row, int col) {
	sheet1=workbook.getSheetAt(sheetNumber);
	System.out.println("Sheet ="+sheetNumber+" row ="+row+" Col ="+col);
	return sheet1.getRow(row).getCell(col).getDateCellValue();
}


public String getDataV2(int sheetNumber, int row, int col) {
	//return workbook.getSheetAt(sheetNumber).getRow(row).getCell(col).getStringCellValue();
	String retData="none";
	int retVal=getXType(sheetNumber,row,col);
	switch(retVal) {
	case 0:
		int intRet=getNum(sheetNumber,row,col);
		retData=Integer.toString(intRet);
		break;
	case 1:
		retData=getData(sheetNumber,row,col);
	    break;
	case 3:
		retData="null";
	    break;
	case 4:
		boolean bolVal=getBool(sheetNumber,row,col);
		retData=Boolean.toString(bolVal);
	    break;
	default:
		retData=getData(sheetNumber,row,col);
	}	
	return retData;
	
}


public int getRowCount(int sheetIndex) {
	return workbook.getSheetAt(sheetIndex).getLastRowNum()+1;
}

public int getSheetCount() {
	return workbook.getNumberOfSheets();
}

public int getActiveSheet() {
	return workbook.getActiveSheetIndex();
}

public int getLastRowIndex(int sheetNumber) {
	return workbook.getSheetAt(sheetNumber).getLastRowNum();
}

public int getLastColIndex(int sheetNumber, int rowNum) {
	return workbook.getSheetAt(sheetNumber).getRow(rowNum).getLastCellNum();
}



}

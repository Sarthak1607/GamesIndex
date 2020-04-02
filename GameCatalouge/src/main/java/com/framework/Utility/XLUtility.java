package com.framework.Utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.framework.base.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility extends TestBase {
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static Workbook wb;
	public static Sheet ws;
	public static Row row;
	public static Cell cell;

	public static int getRowCount(String xlFile, String xlSheet) throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		int rowCount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowCount;
	}

	public void setSatainexcel(String workBook,String sheetName ) throws IOException{
		File file = new File(workBook);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheet(sheetName);
		Row newRow = sheet.createRow(1);
	
	}
	public int getCellCount(String xlFile, String xlSheet, int rowNum) throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		int columnCount = row.getLastCellNum();
		wb.close();
		fi.close();
		return columnCount;
	}

	public  String getCellData(String xlFile, String xlSheet, int rowNum, int columnNum) throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.getCell(columnNum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public  void setCellData(String xlFile, String xlSheet, int rowNum, int columnNum, String data)
			throws IOException {
		fo = new FileOutputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.createCell(columnNum);
		cell.setCellValue(data);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}

	public void openExecel(String workbookPath, String sheetName) throws IOException {

		File file = new File(workbookPath);
		FileInputStream fip = new FileInputStream(file);
		wb = new XSSFWorkbook(fip);
		if (file.isFile() && file.exists()) {
			logger.info("WorkBook is accessable");
		} else {
			throw new FrameworkException("Workbook either not exist" + " or can't open");
		}
		wb.close();
	}
}

package com.framework.testScripts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.http.HttpResponse;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.framework.Utility.GlobalConfiguration;
import com.framework.base.SeleniumHelper;
import com.framework.base.TestBase;

public class CreatingGameCatalouge extends TestBase {

	// TODO Auto-generated method stub

	public static FileOutputStream file;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static SeleniumHelper helper = new SeleniumHelper();
	public static XSSFFont font;
	public static XSSFCellStyle style;
	@Test(priority = 1)
	public static void writeFirstRow() throws IOException {
		logger.info("--------Starting the excel-----------------");
		
		file = new FileOutputStream("D:\\JavaCrux\\GameCatalouge\\resources\\Gaming.xlsx");
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Catalouge");

		String dataToWrite[] = { "#", "Game Name", "Page URL", "Page Status", "Tournament Count" };
		font = (XSSFFont) workbook.createFont();
		style = (XSSFCellStyle) workbook.createCellStyle();
		font.setBold(true);
		style.setFont(font);
		Cell cell;
		row = sheet.createRow(0);
		for (int j = 0; j <= 4; j++) {
			
			row.createCell(j).setCellValue(dataToWrite[j]);
			cell=row.getCell(j);
			cell.setCellStyle(style);
		}
	}

	@Test(priority = 2)
	public static void gototheUrl() throws Exception {
		logger.info("--------Opening the URL-----------------");
		helper.openBrowser(
				GlobalConfiguration.getDynamicProperty("browserName"));
		helper.openUrl(
				GlobalConfiguration.getDynamicProperty("Url"));
		helper.explicitWaitForElementGetVisible("List of items", 
				"globalOR", "homePage.gameList", "60");
		List<WebElement> element = helper.getdriver().findElements((
				helper.getBy("globalOR", "homepage.allGames")));

		List<WebElement> urls = helper.getdriver().findElements((
				helper.getBy("globalOR", "homepage.allUrl")));
	
	
		for (int i = 0; i < element.size(); i++) {
			 row = sheet.createRow(i+1);
			for (int j = 0; j < 1; j++) {
				row.createCell(j).setCellValue(i+1);
				row.createCell(j+1).setCellValue(element.get(i).getText().toString());
				row.createCell(j+2).setCellValue(urls.get(i).getAttribute("href").toString());
				//for URL
				String url=urls.get(i).getAttribute("href");
				URL link=new URL(url);
				HttpsURLConnection httpcon=(HttpsURLConnection) link.openConnection();
				httpcon.connect();
				int responseCode=httpcon.getResponseCode();
			    logger.info("responseCode="+responseCode+"for"+element.get(i).getText());
				row.createCell(j+3).setCellValue(responseCode);
				
			}
		}
	}
	
	//FOR TOURNAMENT
	@Test(priority = 3)
			public static void NumberofTournamnets() throws Exception{
				List<WebElement> element = helper.getdriver().findElements((
						helper.getBy("globalOR", "homepage.allGames")));
				for (int i = 0; i < element.size(); i++) {
				row=sheet.getRow(i+1);
				helper.explicitWaitForElementGetVisible("Number of Games", "globalOR", 
							"homepage.allGames", "60");
			
				helper.clickExceutor("Page of the tournament", 
						GlobalConfiguration.getFormatedOR("homePage.games", i+1)[0],
					    GlobalConfiguration.getFormatedOR("homePage.games", i+1)[1]);
				
				helper.explicitWaitForElementGetVisible("Number of tournaments", "globalOR", 
						"tournamentPage.counts", "60");
				String numTournament=	helper.getdriver().findElement(
						helper.getBy("globalOR", "tournamentPage.counts")).getText().split(" ")[0];
				if (numTournament.equals("Tournaments")) {
				numTournament = "0";
			}
				row.createCell(4).setCellValue(numTournament);
				helper.getdriver().navigate().back();
			}
				workbook.write(file);
				file.close();
		}

	@AfterTest
	public void closeDriver() {
		logger.info("Driver is Closing");
		helper.getdriver().close();
	}
	}

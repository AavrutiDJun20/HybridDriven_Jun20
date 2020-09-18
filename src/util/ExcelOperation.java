package util;

import java.io.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperation {
	
	private static Sheet sheet;
	private static Workbook wb;
	
	static private void localExcelSheet(String filePath,String sheetName) {
		File file = new File(filePath);		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			wb = new XSSFWorkbook(inputStream);
			sheet = wb.getSheet(sheetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	static public Object[][] getAllRowsData(String filePath,String sheetName) throws IOException{
		localExcelSheet(filePath,sheetName);

		int totalRows = sheet.getLastRowNum();
		int totalColumns = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();
		Object[][] data = new Object[totalRows][totalColumns];
		DataFormatter dataFormatter = new DataFormatter();

		for(int rowIndex=1;rowIndex<=totalRows;rowIndex++) {
			for(int colIndex=0;colIndex<totalColumns;colIndex++) {
				Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
				try {
					if(cell.getCellType() == CellType.NUMERIC)
						data[rowIndex-1][colIndex] = dataFormatter.formatCellValue(cell);
					else if(cell.getCellType() == CellType.STRING)
						data[rowIndex-1][colIndex] = cell.getStringCellValue();
					else
						data[rowIndex-1][colIndex] = "";	
				}
				catch(NullPointerException ne) {
					data[rowIndex-1][colIndex] = "";
				}
			}
		}
		wb.close();
		return data;		
	}
}

package br.com.alessanderleite.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.alessanderleite.app.model.Customer;

public class Utils {

	public static ByteArrayInputStream customerToExcel(List<Customer> customers) throws IOException {
		String[] COLUMNs = {"Id", "Name", "Address", "Age"};
		try(
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		) {
			CreationHelper createHelper = workbook.getCreationHelper();
			
			Sheet sheet = workbook.createSheet("Customers");
			
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
			
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			
			// Row for Header
			Row headerRow = sheet.createRow(0);
			
			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}
			
			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
			
			int rowIdx = 1;
			for (Customer customer : customers) {
				Row row = sheet.createRow(rowIdx++);
				
				row.createCell(0).setCellValue(customer.getId());
				row.createCell(1).setCellValue(customer.getName());
				row.createCell(2).setCellValue(customer.getAddress());
				
				Cell ageCell = row.createCell(3);
				ageCell.setCellValue(customer.getAge());
				ageCell.setCellStyle(ageCellStyle);
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static List<Customer> parseExcelFile(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			
			Sheet sheet = workbook.getSheet("Customers");
			Iterator<Row> rows = sheet.iterator();
			
			List<Customer> listCustomers = new ArrayList<Customer>();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				Customer cust = new Customer();
				
				int cellIndex = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					
					if (cellIndex == 0) {
						cust.setId((long) currentCell.getNumericCellValue());
					} else if(cellIndex == 1) {
						cust.setName(currentCell.getStringCellValue());
					} else if(cellIndex == 2) {
						cust.setAddress(currentCell.getStringCellValue());
					} else if(cellIndex == 3) {
						cust.setAge((int) currentCell.getNumericCellValue());
					}
					cellIndex++;
				}
				listCustomers.add(cust);
			}
			// Close WorkBook
			workbook.close();
			return listCustomers;
			
		} catch (Exception e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}
}


package TestUtils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelReader {
	
	public static Map<String, String> readCustomerData(String excelFilePath) throws IOException {
	    FileInputStream file = new FileInputStream(new File(excelFilePath));
	    Workbook workbook = new XSSFWorkbook(file);
	    Sheet sheet = workbook.getSheetAt(0);

	    Iterator<Row> rowIterator = sheet.iterator();
	    Map<String, String> customerData = new HashMap<>();
	    String prefix = ""; // To hold the current prefix (header)

	    while (rowIterator.hasNext()) {
	        Row row = rowIterator.next();
	        if (row.getCell(0) != null) {
	            String key = getCellValueAsString(row.getCell(0)).trim();
	            String value = row.getCell(1) != null ? getCellValueAsString(row.getCell(1)).trim() : "";

	            // Check for headers to set the prefix
	            if (key.equalsIgnoreCase("CUSTOMER INFO")) {
	                prefix = "custinfo.";
	            } else if (key.equalsIgnoreCase("LEGAL REP ONE INFO")) {
	                prefix = "legalone.";
	            } else if (key.equalsIgnoreCase("LEGAL REP TWO INFO")) {
	                prefix = "legaltwo.";
	            } else if (!key.isEmpty() && !value.isEmpty()) {
	                // Insert key-value pairs with the current prefix
	                customerData.put(prefix + key, value);
	            }
	        }
	    }

	    workbook.close();
	    return customerData;
	}

	// Utility method to get cell value as String
	private static String getCellValueAsString(Cell cell) {
	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue();
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                return cell.getDateCellValue().toString();
	            } else {
	                return String.valueOf((long) cell.getNumericCellValue());
	            }
	        case BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        case FORMULA:
	            return cell.getCellFormula();
	        default:
	            return "";
	    }
	}
}

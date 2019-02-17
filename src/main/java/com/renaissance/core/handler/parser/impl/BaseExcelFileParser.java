package com.renaissance.core.handler.parser.impl;

import com.renaissance.core.CoreConst;
import com.renaissance.core.utils.excel.ExcelUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Super class for excel file parser
 * 
 * @author 908869
 */
public class BaseExcelFileParser {

	private static final Log logger = LogFactory.getLog(BaseExcelFileParser.class);
	
	/**
	 * @param sheet
	 * @param lines
	 * @param uploadFileColumnProperties
	 */
	protected void parse(Sheet sheet, List<Map<String, Object>> lines, List<String> uploadFileColumnProperties) {
		Iterator<Row> iter = sheet.iterator();
		// read head
		Row header = iter.next();
		if (header.getLastCellNum() < (uploadFileColumnProperties.size()-1)) {
			logger.error("The number of file columns is not valid");
			throw CoreConst.excelColumnsNotValidException();
		}

		// parse content
		while (iter.hasNext()) {
			Row row = iter.next();
			// 跳过空行
			if(ExcelUtils.isRowEmpty(row)) {
				continue;
			}

			Map<String, Object> lineOfData = new LinkedHashMap<String, Object>();
			for (int i = 0; i < uploadFileColumnProperties.size(); i++) {
				String value = null;
				System.out.println("--------------row.getCell(i)---------"+getCellString(row.getCell(i))+"---------"+i);
				if(row.getCell(i) != null && row.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(row.getCell(i))) {
					value = DateFormatUtils.format(row.getCell(i).getDateCellValue(), "yyyy-MM-dd");
				} else {
					value = getCellString(row.getCell(i));
				}
				lineOfData.put(uploadFileColumnProperties.get(i), value);
			}
			// 把第几行加入返回结果
			lineOfData.put("row", row.getRowNum());
			lines.add(lineOfData);
		}
	}
	
	/**
	 * Get String value from Cell
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellString(Cell cell) {
        String dataString = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    dataString = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    dataString = readNumericCell(cell);
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    dataString = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_ERROR:
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // not supported formula
                    break;
                default:
                    break;
            }
        }
        return dataString;
    }
	
	/**
	 * Read numeric cell as String
	 * 
	 * @param cell
	 * @return
	 */
	private String readNumericCell(Cell cell) {
        double value;
        String dataString = null;
        value = cell.getNumericCellValue();
        if (((int) value) == value) {
            dataString = Integer.toString((int) value);
        } else {
            dataString = new BigDecimal(value).toString();
        }
        return dataString;
    }
}

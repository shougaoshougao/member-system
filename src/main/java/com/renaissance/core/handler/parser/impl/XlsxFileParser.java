/**
 * 
 */
package com.renaissance.core.handler.parser.impl;

import com.renaissance.core.handler.parser.FileParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * XLSX formatted file parser
 * 
 * @author Wilson
 */

public class XlsxFileParser extends BaseExcelFileParser implements FileParser {

	private static final Log logger = LogFactory.getLog(XlsxFileParser.class);
	
	private static final String XLSX_SUFFIXES = "xlsx";
	
	public boolean isSupported(String extension) {
		return XLSX_SUFFIXES.equalsIgnoreCase(extension);
	}

	public List<Map<String, Object>> parse(InputStream inputStream, List<String> columnProperties) {
		
		logger.debug("Trying to parse " + XLSX_SUFFIXES + " file.");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			super.parse(sheet, result, columnProperties);
		} catch (IOException e) {
			logger.error("XlsxFileParser#parse(): ", e);
		}
		
		return result;
	}

}

/**
 * 
 */
package com.renaissance.core.handler.parser.impl;

import com.renaissance.core.handler.parser.FileParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * XLS formatted file parser
 * 
 * @author Wilson
 */
public class XlsFileParser extends BaseExcelFileParser implements FileParser {

	private static final Log logger = LogFactory.getLog(XlsFileParser.class);
	
	private static final String XLS_SUFFIXES = "xls";
	
	public boolean isSupported(String extension) {
		return XLS_SUFFIXES.equalsIgnoreCase(extension);
	}

	public List<Map<String, Object>> parse(InputStream inputStream, List<String> columnProperties) {
		logger.debug("Trying to parse " + XLS_SUFFIXES + " file.");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			Workbook workbook = new HSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			
			super.parse(sheet, result, columnProperties);

		} catch (IOException e) {
			logger.error("XlsxFileParser#parse(): ", e);
		}

		return result;
	}

}

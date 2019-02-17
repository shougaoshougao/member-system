package com.renaissance.core.utils.excel;

import com.renaissance.core.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Wilson
 */
public class ExcelUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

// TODO
//	protected void parseExcel(InputStream inputStream, String filename, Class<T> targetClass) throws IOException {
//		// 读取targetClass类的所有字段
//		Field[] fields = targetClass.getDeclaredFields();
//
//		Workbook workbook = createEmptyWorkbook(inputStream, filename);
//		Sheet sheet = workbook.getSheetAt(0);
//		Iterator<Row> iter = sheet.iterator();
//		// 读取表头行
//		Row header = iter.next();
//		if (header.getLastCellNum() < (uploadFileColumnProperties.size()-1)) {
//			logger.error("The number of file columns is not valid");
//			throw new RuntimeException("The number of file columns is not valid");
//		}
//
//		// parse content
//		while (iter.hasNext()) {
//			Row row = iter.next();
//
//			Map<String, Object> lineOfData = new LinkedHashMap<String, Object>();
//			for (int i = 0; i < uploadFileColumnProperties.size(); i++) {
//				String value = null;
//				System.out.println("--------------row.getCell(i)---------"+getCellString(row.getCell(i))+"---------"+i);
//				if(row.getCell(i) != null && row.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(row.getCell(i))) {
//					value = DateFormatUtils.format(row.getCell(i).getDateCellValue(), "yyyy-MM-dd");
//				} else {
//					value = getCellString(row.getCell(i));
//				}
//				lineOfData.put(uploadFileColumnProperties.get(i), value);
//			}
//			// 把第几行加入返回结果
//			lineOfData.put("row", row.getRowNum());
//			lines.add(lineOfData);
//		}
//	}

	/**
	 * 创建xssworkbook，即xlsx文件
	 *
	 * @param rowDataList
	 * @param clazz
	 * @param <E>
	 * @return
	 */
	public static <E> Workbook createXSSWorkbook(List<E> rowDataList, Class<E> clazz) {
		// 读取rowData类的所有字段
		Field[] rowDataFields = clazz.getDeclaredFields();

		XSSFWorkbook workbook = new XSSFWorkbook();
		// 读取sheetName，如果ExcelSheet注解，则用注解值做sheetName，否则用类名
		String sheetName;
        ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
        if(excelSheet != null) {
        	sheetName = excelSheet.value();
		} else {
        	sheetName = clazz.getSimpleName();
		}
		// 创建sheet
		Sheet sheet = workbook.createSheet(sheetName);
		// 创建标题行
		Row headerRow = sheet.createRow(0);
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14); // 14号字
		font.setColor(HSSFColor.BLACK.index); // 黑色
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
		font.setFontName("宋体");
		style.setFont(font);
		// 设置标题行
		IntStream.range(0, rowDataFields.length).forEach(columnIndex -> {
			Cell cell = headerRow.createCell(columnIndex);
			String header;
			//  如果有ExcelHeader注解，则用注解值做表头，否则用字段名做表头
			ExcelHeader excelHeader = rowDataFields[columnIndex].getAnnotation(ExcelHeader.class);
			if(excelHeader != null) {
				header = excelHeader.value();
			} else {
				header = rowDataFields[columnIndex].getName();
			}
			cell.setCellValue(header);
			cell.setCellStyle(style);
		});

		// 设置内容行
		if(CollectionUtils.isNotEmpty(rowDataList)) {
			IntStream.range(0, rowDataList.size()).forEach(rowIndex -> {
				Row contentRow = sheet.createRow(rowIndex + 1); // +1跳过表头行
				IntStream.range(0, rowDataFields.length).forEach(columnIndex -> {
					Cell cell = contentRow.createCell(columnIndex);
					Object object = BeanUtils.getProperty(rowDataList.get(rowIndex), rowDataFields[columnIndex].getName());
					cell.setCellValue(object == null ? "" : object.toString());
				});
			});
		}

		return workbook;
	}

	public static void writeExcelToResponse(Workbook workbook, String filename, HttpServletResponse response) {
	    // filename转编码
		try {
			filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("ExcelUtils#writeExcelToResponse(): ", e);
		}
		// 设置response参数
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		// 写数据
		try(OutputStream outputStream = response.getOutputStream()) {
			workbook.write(outputStream);
		} catch (IOException e) {
			logger.error("ExcelUtils#writeExcelToResponse(): ", e);
		}
	}

	/**
	 * 依据文件名后缀创建空的excel workbook
	 * @param filename
	 * @return
	 */
	private Workbook createEmptyWorkbook(InputStream inputStream, String filename) throws IOException {
		if("xls".equals(FilenameUtils.getExtension(filename))) {
			return new HSSFWorkbook(inputStream);
		} else if("xlsx".equals(FilenameUtils.getExtension(filename))) {
			return new XSSFWorkbook(inputStream);
		} else {
			throw new RuntimeException("excel文件名后缀不正确"+"["+filename+"]");
		}
	}

}
package com.aimilin.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aimilin.bean.ExcelResult;
import com.aimilin.bean.ExcelRow;
import com.aimilin.bean.ExcelSheet;
import com.aimilin.bean.ExcelType;
import com.aimilin.exception.ExcelWriteException;

/**
 * Excel写入工具类
 * 
 * @author LiuJunGuang
 */
public class ExcelWriteUtils {
	
	private ExcelWriteUtils() {
		super();
	}

	/**
	 * 根据文件类型获取文件名称
	 * 
	 * @author LiuJunGuang
	 * @param excelType Excel文件类型枚举
	 * @return 文件名已当前时间毫秒数作为文件名，后缀为指定的文件类型
	 */
	public static String getFileName(ExcelType excelType) {
		Long time = System.currentTimeMillis();
		String suffix = ".xlsx";
		if (excelType == ExcelType.XLS) {
			suffix = ".xls";
		}
		return time + suffix;
	}

	/**
	 * 根据Excel 类型生成不同的WorkBook
	 * 
	 * @author LiuJunGuang
	 * @param excelType ExcelType 枚举类型
	 * @return Workbook Excel工作簿
	 */
	public static Workbook getWorkbook(ExcelType excelType) {
		Objects.requireNonNull(excelType, "excelType must not be NULL !");
		if (ExcelType.XLS == excelType) {
			return new HSSFWorkbook();
		}
		return new XSSFWorkbook();
	}

	/**
	 * 将Excel结果写入到指定的输出流中
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel结果集
	 * @param os 输出流
	 * @param excelType Excel类型，如果格式为Excel2003格式，并且记录总行数超过了65535，则将结果集拆分多个Sheet，名称和原名称一样,多了索引
	 */
	public static void write(ExcelResult excelResult, OutputStream os, ExcelType excelType) {
		Objects.requireNonNull(excelResult);
		Objects.requireNonNull(os);
		List<ExcelSheet> sheets = excelResult.getSheetList();
		if (CollectionUtils.isEmpty(sheets)) {
			throw new ExcelWriteException("excelResult 结果对象中必须包含 ExcelSheet对象！");
		}

		Workbook wb = null;
		try {
			wb = getWorkbook(excelType);
			for (int i = 0; i < sheets.size(); i++) {
				ExcelSheet exlSheet = sheets.get(i);
				String sheetName = exlSheet.getName();
				if (sheetName == null || "".equals(sheetName)) {
					sheetName = "sheet" + i;
				}

				List<ExcelRow> exlRows = exlSheet.getRowList();
				// 将Excel2003格式数据拆分成多个Sheet
				if (ExcelType.XLS == excelType) {
					List<List<ExcelRow>> exlRowList = ListUtils.split(exlRows, 65535);
					for (int j = 0; j < exlRowList.size(); j++) {
						creatSheet(wb, sheetName + "_" + j, exlRowList.get(j), exlSheet.getHeadRow());
					}
				} else {
					creatSheet(wb, sheetName, exlRows, exlSheet.getHeadRow());
				}
			}
			wb.write(os);
		} catch (Exception e) {
			throw new ExcelWriteException(e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
			}
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private static void creatSheet(Workbook wb, String sheetName, List<ExcelRow> exlRows, ExcelRow headRow) {
		Sheet sheet = wb.createSheet(sheetName);
		// 设置表头
		int startRow = 0;
		if (headRow != null && headRow.getCellList() != null) {
			setValue(sheet.createRow(0), headRow);
			startRow = 1;
		}

		// 设置每行值
		for (int i = 0; i < exlRows.size(); i++) {
			Row row = sheet.createRow(i + startRow);
			setValue(row, exlRows.get(i));
		}
	}

	//设置Excel行数据
	private static void setValue(Row row, ExcelRow excelRow) {
		if (excelRow == null) {
			return;
		}
		List<String> cells = excelRow.getCellList();
		if (cells == null || cells.isEmpty()) {
			return;
		}
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(cells.get(i));
		}
	}

	/**
	 * 将Excel结果写入到字节输出流中
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel结果集
	 * @param excelType Excel类型
	 * @return Excel数据字节流
	 */
	public static byte[] write(ExcelResult excelResult, ExcelType excelType) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			write(excelResult, baos, excelType);
			return baos.toByteArray();
		} catch (Exception e) {
			throw new ExcelWriteException(e.getMessage());
		}
	}
}

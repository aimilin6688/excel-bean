package com.aimilin.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.aimilin.bean.ExcelResult;
import com.aimilin.bean.ExcelRow;
import com.aimilin.bean.ExcelSheet;
import com.aimilin.exception.ExcelReadException;

/**
 * Excel 读取工具类
 * 
 * @author LiuJunGuang
 */
public class ExcelReadUtils {

	/**
	 * 得到Excel表中的值
	 * 
	 * @param hssfCell Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	private static String getValue(Cell cell) {
		// 如果单元格为空的，则返回空字符串
		if (cell == null) {
			return "";
		}

		// 根据单元格类型，以不同的方式读取单元格的值
		String value = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
			} else {
				value = new BigDecimal(cell.getNumericCellValue()).toPlainString();
				if (value.endsWith(".0")) {
					value = value.substring(0, value.length() - ".0".length());
				}
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = Boolean.toString(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				// 先按照字符串格式获取
				value = String.valueOf(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					// 获取数字类型的结果
					value = String.valueOf(cell.getNumericCellValue());
				} catch (Exception e1) {
					// 获取公式
					value = cell.getCellFormula();
				}
			}
			break;
		default:
			value = cell.getStringCellValue();
		}
		return value;
	}

	/**
	 * 根据Excel文件输入流 ，获取Excel工作薄对象
	 * 
	 * @param is 输入流
	 * @return Workbook Excel工作博
	 * @throws ExcelReadException - 从Input中读取失败，则抛出该异常
	 */
	public static Workbook getWorkbook(InputStream is) {
		Objects.requireNonNull(is, "Excel InputStream must not be NULL !");
		try {
			return WorkbookFactory.create(is);
		} catch (Exception e) {
			throw new ExcelReadException(e.getMessage());
		}
	}

	/**
	 * 根据Excel文件地址 获取Excel工作薄对象
	 * 
	 * @param filePath excel文件地址
	 * @return Workbook Excel工作博
	 * @throws ExcelReadException - 从FilePath中读取失败，则抛出该异常，或者文件未找到等
	 */
	public static Workbook getWorkbook(String filePath) {
		Objects.requireNonNull(filePath, "Excel file path must not be NULL !");
		try {
			return WorkbookFactory.create(new File(filePath));
		} catch (Exception e) {
			throw new ExcelReadException(e.getMessage());
		}
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param bytes Excel 文件二进制数组
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return ExcelResult 数据结果集，如果为空则返回null
	 * @throws ExcelReadException 文件读取错误
	 */
	public static ExcelResult read(byte[] bytes, boolean includeHeader, boolean includeBlankLine) {
		try {
			Workbook wb = getWorkbook(new ByteArrayInputStream(bytes));
			return read(wb, includeHeader, includeBlankLine);
		} catch (Exception e) {
			throw new ExcelReadException(e.getMessage());
		}
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param is Excel 文件输入流
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return ExcelResult 数据结果集，如果为空则返回null
	 * @throws ExcelReadException 文件读取错误
	 */
	public static ExcelResult read(InputStream is, boolean includeHeader, boolean includeBlankLine) {
		try {
			Workbook wb = getWorkbook(is);
			return read(wb, includeHeader, includeBlankLine);
		} catch (Exception e) {
			throw new ExcelReadException(e.getMessage());
		}
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param filePath Excel 文件路径地址
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return ExcelResult 数据结果集，如果为空则返回null
	 * @throws ExcelReadException 文件读取错误
	 */
	public static ExcelResult read(String filePath, boolean includeHeader, boolean includeBlankLine) {
		try {
			Workbook wb = getWorkbook(filePath);
			return read(wb, includeHeader, includeBlankLine);
		} catch (Exception e) {
			throw new ExcelReadException(e.getMessage());
		}
	}

	/**
	 * 读取工作薄数据到List结果列表中.<br>
	 * <b>注意：</b>如果有多个工作薄，会自动合并到List中
	 * 
	 * @author LiuJunGuang
	 * @param wb Excel 工作薄
	 * @param includeHeader 是否忽略标题行（第一行），true - 包含，false - 忽略
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return ExcelResult Excel结果集
	 */
	public static ExcelResult read(Workbook wb, boolean includeHeader, boolean includeBlankLine) {
		Objects.requireNonNull(wb);
		try {
			ExcelResult result = new ExcelResult();
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
				Sheet xssfSheet = wb.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}

				ExcelSheet sheet = new ExcelSheet(xssfSheet.getSheetName(), numSheet);
				result.addSheet(sheet);
				// 循环行Row
				for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					if (rowNum == 0) {
						ExcelRow row = readRow(xssfSheet, rowNum, includeBlankLine);
						sheet.setHeadRow(row);
						if (!includeHeader) {
							continue;
						}
					}

					ExcelRow row = readRow(xssfSheet, rowNum, includeBlankLine);
					if (row != null) {
						sheet.addRow(row);
					}
				}
			}
			return result;
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				;// 忽略关闭异常
			}
		}
	}

	/**
	 * 读取Sheet中的一行数据
	 * 
	 * @author LiuJunGuang
	 * @param sheet Excel 中一个Sheet对象
	 * @param rowNum 行号
	 * @param includeBlankLine 是否包含空行，true 包含，false 不包含
	 * @return ExcelRow 行对象
	 */
	private static ExcelRow readRow(Sheet sheet, int rowNum, boolean includeBlankLine) {
		ExcelRow row = new ExcelRow(rowNum);
		Row hssfRow = sheet.getRow(rowNum);
		if (hssfRow == null) {
			return null;
		}

		for (int colNum = 0; colNum < hssfRow.getLastCellNum(); colNum++) {
			Cell hssfCell = hssfRow.getCell(colNum);
			row.addCell(getValue(hssfCell));
		}
		// 空行忽略
		if (includeBlankLine == false) {
			List<String> list = row.getCellList();
			if (list == null || list.size() == 0) {
				return null;
			}
			int i = 0;
			for (; i < list.size(); i++) {
				if (!"".equals(list.get(i))) {
					break;
				}
			}
			if (i == list.size()) {
				return null;
			}
		}
		return row;
	}

}

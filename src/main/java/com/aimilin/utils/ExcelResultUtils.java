package com.aimilin.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aimilin.bean.ExcelResult;
import com.aimilin.bean.ExcelRow;
import com.aimilin.bean.ExcelSheet;
import com.aimilin.bean.SellectSheet;
import com.aimilin.bean.SellectSheetAll;
import com.aimilin.bean.SellectSheetByIndex;
import com.aimilin.bean.SellectSheetByName;
import com.aimilin.converter.DictionaryConverter;

/**
 * Excel结果处理类
 * 
 * @author LiuJunGuang
 */
public class ExcelResultUtils {
	private static Logger log = LoggerFactory.getLogger(ExcelResultUtils.class);

	private ExcelResultUtils() {
		super();
	}

	/**
	 * 将Excel结果集转换成List
	 * 
	 * @param excelResult Excel结果集
	 * @param converters 可选，参数类型转换器
	 * @param startLine 开始行号，从0开始
	 * @return List 数据列表
	 */
	public static List<List<String>> toList(ExcelResult excelResult, int startLine, DictionaryConverter... converters) {
		return toList(excelResult, startLine, new SellectSheetAll(), converters);
	}

	/**
	 * 将 index 为 sheetIndex 的Sheet 转换成List
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel结果集
	 * @param startLine 开始行号，从0开始
	 * @param sheetIndex 开始Sheet，从0开始
	 * @param converters 可选，参数类型转换器
	 * @return List 数据列表
	 */
	public static List<List<String>> toList(final ExcelResult excelResult, int startLine, final int sheetIndex,
			DictionaryConverter... converters) {
		if (sheetIndex < 0) {
			throw new IllegalArgumentException("sheetName must not be larger 0!");
		}
		return toList(excelResult, startLine, new SellectSheetByIndex(sheetIndex), converters);
	}

	/**
	 * 将指定的Sheet转换成List
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel结果集
	 * @param startLine 开始行号，从0开始
	 * @param sellectSheet 选择的Sheet
	 * @param converters 可选，参数类型转换器
	 * @return List 数据列表
	 */
	public static List<List<String>> toList(ExcelResult excelResult, int startLine, SellectSheet sellectSheet,
			DictionaryConverter... converters) {
		if (excelResult == null) {
			throw new IllegalArgumentException("excelResult must not be null!");
		}
		if (startLine < 0) {
			throw new IllegalArgumentException("startLine must not larger or equals zero!");
		}

		List<ExcelSheet> sheetList = excelResult.getSheetList();
		if (sheetList == null || sheetList.size() == 0) {
			throw new IllegalArgumentException("sheetList must not be null!");
		}

		List<List<String>> list = new LinkedList<>();
		for (int i = 0; i < sheetList.size(); i++) {
			ExcelSheet sheet = sheetList.get(i);
			if (sellectSheet.isSellect(sheet)) {
				List<ExcelRow> rows = sheet.getRowList();
				if (rows == null || rows.isEmpty()) {
					continue;
				}

				List<String> heads = sheet.getHeads();
				for (int j = 0; j < rows.size(); j++) {
					if (j < startLine) {
						continue;
					}
					ExcelRow row = rows.get(j);
					List<String> cells = row.getCellList();
					List<String> cellList = new ArrayList<>();
					for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
						String value = ConverterUtils.converter(heads.get(cellIndex), cellIndex, cells.get(cellIndex),
								converters);
						cellList.add(value == null ? "" : value);
					}
					list.add(cellList);
				}
			}
		}
		return list;
	}

	/**
	 * 将 name 为 sheetName 的Sheet 转换成List
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel结果集
	 * @param startLine 开始行号，从0开始
	 * @param sheetName sheet名称，如果指定，则只有符合条件的话，才会转换
	 * @param converters 可选，参数类型转换器
	 * @return List 数据列表
	 */
	public static List<List<String>> toList(final ExcelResult excelResult, int startLine, final String sheetName,
			DictionaryConverter... converters) {
		if (sheetName == null || "".equals(sheetName)) {
			throw new IllegalArgumentException("sheetName must not be null!");
		}
		return toList(excelResult, startLine, new SellectSheetByName(sheetName), converters);
	}

	/**
	 * 将Excel结果集转换成Map
	 * 
	 * @param excelResult Excel结果集
	 * @param converters 可选，参数类型转换器
	 * @return List 数据列表 ，Map 中 key - Excel标题名称，value - Excel单元格值
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult, DictionaryConverter... converters) {
		return toMap(excelResult, new SellectSheetAll(), converters);
	}

	/**
	 * 将 index 为 sheetIndex 的Sheet 转换成Map
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel 结果集
	 * @param sheetIndex sheet 索引号，从0开始
	 * @param converters 可选，参数类型转换器
	 * @return List 数据列表 ，Map 中 key - Excel标题名称，value - Excel单元格值
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult, final int sheetIndex,
			DictionaryConverter... converters) {
		if (sheetIndex < 0) {
			throw new IllegalArgumentException("sheetName must not be larger 0!");
		}
		return toMap(excelResult, new SellectSheetByIndex(sheetIndex), converters);
	}

	/**
	 * 将Excel结果集转换成Map<br>
	 * <b>注意：</b>请确保 结果集中包含标题，转换的Map是以第一行为Key，请确保Excel表格第一行标题不重复
	 * 
	 * @param excelResult Excel 结果集
	 * @param sellectSheet 需要选择的ExcelSheet 接口
	 * @param converters 可选，参数类型转换器
	 * @return List 数据列表 ，Map 中 key - Excel标题名称，value - Excel单元格值
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult, SellectSheet sellectSheet,
			DictionaryConverter... converters) {
		if (excelResult == null) {
			throw new IllegalArgumentException("excelResult must not be null!");
		}

		List<ExcelSheet> sheetList = excelResult.getSheetList();
		if (sheetList == null || sheetList.isEmpty()) {
			throw new IllegalArgumentException("sheetList must not be null!");
		}

		List<Map<String, String>> listMap = new LinkedList<>();
		for (int i = 0; i < sheetList.size(); i++) {
			ExcelSheet sheet = sheetList.get(i);
			if (!sellectSheet.isSellect(sheet)) {
				continue;
			}

			List<ExcelRow> rows = sheet.getRowList();
			if (rows == null || rows.isEmpty()) {
				continue;
			}
			// 获取标题行
			List<String> titles = rows.get(0).getCellList();
			for (int j = 1; j < rows.size(); j++) {
				ExcelRow row = rows.get(j);
				List<String> cells = row.getCellList();
				if (cells == null || cells.isEmpty()) {
					continue;
				}
				Map<String, String> map = new LinkedHashMap<>();
				for (int k = 0; k < titles.size(); k++) {
					String value = "";
					try {
						value = ConverterUtils.converter(titles.get(k), k, cells.get(k), converters);
					} catch (Exception e) {
						log.warn("转换Sheet({})时，第{}行，第{}列出错.错误：{}", sheet.getName(), row.getIndex() + 1, k + 1,
								e.getMessage());
					}
					map.put(titles.get(k), value);
				}
				listMap.add(map);
			}
		}

		return listMap;
	}

	/**
	 * 将 name 为 sheetName 的Sheet 转换成Map
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel结果集
	 * @param sheetName Excel sheet名称，只有当名称等于该值，才会转换
	 * @param converters 可选，参数类型转换器
	 * @return List 数据列表 ，Map 中 key - Excel标题名称，value - Excel单元格值
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult, final String sheetName,
			DictionaryConverter... converters) {
		if (sheetName == null || "".equals(sheetName)) {
			throw new IllegalArgumentException("sheetName must not be null!");
		}
		return toMap(excelResult, new SellectSheetByName(sheetName), converters);
	}

	/**
	 * 将结果集转换成ExcelResult,默认sheet名称为sheet
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据集合
	 * @return Excel结果集
	 */
	public static ExcelResult toResult(List<List<String>> dataList) {
		return toResult(dataList, "sheet");
	}

	/**
	 * 将结果集转换成ExcelResult
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据集合
	 * @param sheetName sheet名称
	 * @param heads 标题头
	 * @return 结果集
	 */
	public static ExcelResult toResult(List<List<String>> dataList, String sheetName, String... heads) {
		if (dataList == null || dataList.isEmpty()) {
			return null;
		}
		if (sheetName == null || "".equals(sheetName)) {
			sheetName = "sheet";
		}

		ExcelSheet sheet = new ExcelSheet(sheetName, 0);
		sheet.addHeadRow(heads);
		for (int i = 0; i < dataList.size(); i++) {
			sheet.addRow(new ExcelRow(i + 1, dataList.get(i)));
		}
		return new ExcelResult(sheet);
	}

	/**
	 * 将结果集转换成ExcelResult
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据集合
	 * @param sheetName sheet名称
	 * @param heads 标题头
	 * @return 结果集
	 */
	public static ExcelResult toResult(List<List<String>> dataList, String sheetName, List<String> heads) {
		if (heads == null) {
			heads = new ArrayList<>();
		}
		return toResult(dataList, sheetName, heads.toArray(new String[0]));
	}

	/**
	 * 将Map列表转换成ExcelResult 对象，默认sheet名称为sheet,默认值为空字符串
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据
	 * @param heads Excel标题,Map中的key值与heads名称一一对应，默认使用heads名称从map中取值
	 * @param <T> 任意类型的对象
	 * @param converters 可选，参数类型转换器
	 * @return ExcelResult Excel 结果集
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads,
			DictionaryConverter... converters) {
		return toResult(listMap, heads, heads, "", "sheet", converters);
	}

	/**
	 * 将Map列表转换成ExcelResult 对象，默认sheet名称为sheet,默认值为空字符串
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据
	 * @param heads Excel标题
	 * @param props head 标题对应的map中的key值，注意Heads 和 props 列表属性需要一一对应
	 * @param <T> 任意类型的对象
	 * @param converters 可选，参数类型转换器
	 * @return ExcelResult 结果对象
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads, List<String> props,
			DictionaryConverter... converters) {
		return toResult(listMap, heads, props, "", "sheet", converters);
	}

	/**
	 * 将Map列表转换成ExcelResult 对象，默认sheet名称为sheet
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据
	 * @param heads Excel标题
	 * @param props head 标题对应的map中的key值，注意Heads 和 props 列表属性需要一一对应
	 * @param defaultValue 如果map中没有取到heads对应值，则使用默认值
	 * @param <T> 任意类型的对象
	 * @param converters 可选，参数类型转换器
	 * @return ExcelResult 结果对象
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads, List<String> props,
			String defaultValue, DictionaryConverter... converters) {
		return toResult(listMap, heads, props, defaultValue, "sheet", converters);
	}

	/**
	 * 将Map列表转换成ExcelResult 对象
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据列表
	 * @param headToProp 标题属性对应关系，key - Excel标题，value - map中属性名称
	 * @param defaultValue 默认值
	 * @param sheetName 默认sheet名称
	 * @param <T> 任意类型对象
	 * @param converters 可选，参数类型转换器
	 * @return ExcelResult Excel结果集
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, Map<String, String> headToProp,
			String defaultValue, String sheetName, DictionaryConverter... converters) {
		if (headToProp == null || headToProp.isEmpty()) {
			throw new IllegalArgumentException("headToProp must not be null!");
		}
		List<String> heads = new LinkedList<>();
		List<String> porps = new LinkedList<>();
		for (String key : headToProp.keySet()) {
			heads.add(key);
			porps.add(headToProp.get(key));
		}
		return toResult(listMap, heads, porps, defaultValue, sheetName, converters);
	}

	/**
	 * 将Map列表转换成ExcelResult 对象
	 * 
	 * @author LiuJunGuang
	 * @param listMap 结果集
	 * @param heads 标题头
	 * @param props head 标题对应的map中的key值，注意Heads 和 props 列表属性需要一一对应
	 * @param defaultValue 如果map中没有取到heads对应值，则使用默认值
	 * @param sheetName sheet 名称
	 * @param <T> 任意类型对象
	 * @param converters 可选，参数类型转换器
	 * @return ExcelResult对象
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads, List<String> props,
			String defaultValue, String sheetName, DictionaryConverter... converters) {
		if (listMap == null || listMap.isEmpty()) {
			return null;
		}
		if (heads == null || heads.isEmpty()) {
			throw new IllegalArgumentException("Excel heads must not be null!");
		}

		if (sheetName == null || "".equals(sheetName)) {
			sheetName = "sheet";
		}
		ExcelSheet sheet = new ExcelSheet(sheetName, 0);
		sheet.addHeadRow(heads);

		for (int i = 0; i < listMap.size(); i++) {
			Map<String, ?> map = listMap.get(i);
			ExcelRow row = new ExcelRow(i + 1);
			for (int j = 0; j < props.size(); j++) {
				String propName = props.get(j);
				String value = ConverterUtils.converter(propName, j, MapUtils.getString(map, propName), converters);
				row.addCell(value == null ? defaultValue : value);
			}
			sheet.addRow(row);
		}
		return new ExcelResult(sheet);
	}

}

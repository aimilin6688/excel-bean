package com.aimilin.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aimilin.bean.ExcelResult;
import com.aimilin.bean.ExcelRow;
import com.aimilin.bean.ExcelSheet;
import com.aimilin.bean.SellectSheet;
import com.aimilin.bean.SellectSheetAll;
import com.aimilin.bean.SellectSheetByIndex;
import com.aimilin.bean.SellectSheetByName;

/**
 * Excel结果处理类
 * 
 * @author LiuJunGuang
 * @date 2016年6月2日下午5:11:35
 */
public class ExcelResultUtils {
	private static Logger log = LoggerFactory.getLogger(ExcelResultUtils.class);

	/**
	 * 将Excel结果集转换成List
	 * 
	 * @return
	 * @date 2016年5月20日下午4:27:08
	 */
	public static List<List<String>> toList(ExcelResult excelResult) {
		return toList(excelResult, new SellectSheetAll());
	}

	/**
	 * 将 index 为 sheetIndex 的Sheet 转换成List
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel结果集
	 * @param sheetIndex sheet索引值，从0开始
	 * @return
	 * @date 2016年6月2日下午5:14:30
	 */
	public static List<List<String>> toList(final ExcelResult excelResult, final int sheetIndex) {
		if (sheetIndex < 0) {
			throw new IllegalArgumentException("sheetName must not be larger 0!");
		}
		return toList(excelResult, new SellectSheetByIndex(sheetIndex));
	}

	/**
	 * 将指定的Sheet转换成List
	 * 
	 * @author LiuJunGuang
	 * @param excelResult
	 * @param sellectSheet
	 * @return
	 * @date 2016年6月2日下午5:15:24
	 */
	public static List<List<String>> toList(ExcelResult excelResult, SellectSheet sellectSheet) {
		if (excelResult == null) {
			throw new IllegalArgumentException("excelResult must not be null!");
		}

		List<ExcelSheet> sheetList = excelResult.getSheetList();
		if (sheetList == null || sheetList.size() == 0) {
			throw new IllegalArgumentException("sheetList must not be null!");
		}

		List<List<String>> list = new LinkedList<List<String>>();
		for (int i = 0; i < sheetList.size(); i++) {
			ExcelSheet sheet = sheetList.get(i);
			if (sellectSheet.isSellect(sheet)) {
				List<ExcelRow> rows = sheet.getRowList();
				if (rows == null || rows.size() == 0) {
					continue;
				}
				for (ExcelRow row : sheet.getRowList()) {
					list.add(row.getCellList());
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
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:14:30
	 */
	public static List<List<String>> toList(final ExcelResult excelResult, final String sheetName) {
		if (sheetName == null || "".equals(sheetName)) {
			throw new IllegalArgumentException("sheetName must not be null!");
		}
		return toList(excelResult, new SellectSheetByName(sheetName));
	}

	/**
	 * 将Excel结果集转换成Map
	 * 
	 * @return
	 * @date 2016年5月20日下午4:27:08
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult) {
		return toMap(excelResult, new SellectSheetAll());
	}

	/**
	 * 将 index 为 sheetIndex 的Sheet 转换成Map
	 * 
	 * @author LiuJunGuang
	 * @param excelResult
	 * @param sheetIndex
	 * @return
	 * @date 2016年6月2日下午5:28:19
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult, final int sheetIndex) {
		if (sheetIndex < 0) {
			throw new IllegalArgumentException("sheetName must not be larger 0!");
		}
		return toMap(excelResult, new SellectSheetByIndex(sheetIndex));
	}

	/**
	 * 将Excel结果集转换成Map<br>
	 * <b>注意：</b>请确保结果集中包含标题，转换的Map是以第一行为Key，请确保Excel表格第一行标题不重复
	 * 
	 * @return
	 * @date 2016年5月20日下午4:27:30
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult, SellectSheet sellectSheet) {
		if (excelResult == null) {
			throw new IllegalArgumentException("excelResult must not be null!");
		}

		List<ExcelSheet> sheetList = excelResult.getSheetList();
		if (sheetList == null || sheetList.size() == 0) {
			throw new IllegalArgumentException("sheetList must not be null!");
		}

		List<Map<String, String>> listMap = new LinkedList<Map<String, String>>();
		for (int i = 0; i < sheetList.size(); i++) {
			ExcelSheet sheet = sheetList.get(i);
			if (!sellectSheet.isSellect(sheet)) {
				continue;
			}

			List<ExcelRow> rows = sheet.getRowList();
			if (rows == null || rows.size() == 0) {
				continue;
			}
			// 获取标题行
			List<String> titles = rows.get(0).getCellList();
			for (int j = 1; j < rows.size(); j++) {
				ExcelRow row = rows.get(j);
				List<String> cells = row.getCellList();
				if (cells == null || cells.size() == 0) {
					continue;
				}
				Map<String, String> map = new LinkedHashMap<String, String>();
				for (int k = 0; k < titles.size(); k++) {
					String value = "";
					try {
						value = cells.get(k);
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
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:27:58
	 */
	public static List<Map<String, String>> toMap(ExcelResult excelResult, final String sheetName) {
		if (sheetName == null || "".equals(sheetName)) {
			throw new IllegalArgumentException("sheetName must not be null!");
		}
		return toMap(excelResult, new SellectSheetByName(sheetName));
	}

	/**
	 * 将结果集转换成ExcelResult,默认sheet名称为sheet
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据集合
	 * @return
	 * @date 2016年6月10日下午9:27:12
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
	 * @date 2016年6月10日下午9:24:32
	 */
	public static ExcelResult toResult(List<List<String>> dataList, String sheetName, String... heads) {
		if (dataList == null || dataList.size() == 0) {
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
	 * @date 2016年6月10日下午9:24:32
	 */
	public static ExcelResult toResult(List<List<String>> dataList, String sheetName, List<String> heads) {
		if (heads == null) {
			heads = new ArrayList<String>();
		}
		return toResult(dataList, sheetName, heads.toArray(new String[0]));
	}

	/**
	 * 将Map列表转换成ExcelResult 对象，默认sheet名称为sheet,默认值为空字符串
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据
	 * @param heads Excel标题,Map中的key值与heads名称一一对应，默认使用heads名称从map中取值
	 * @return ExcelResult
	 * @date 2016年6月10日下午9:46:08
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads) {
		return toResult(listMap, heads, heads, "", "sheet");
	}

	/**
	 * 将Map列表转换成ExcelResult 对象，默认sheet名称为sheet,默认值为空字符串
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据
	 * @param heads Excel标题
	 * @param props head 标题对应的map中的key值，注意Heads 和 props 列表属性需要一一对应
	 * @return ExcelResult 结果对象
	 * @date 2016年6月10日下午9:45:37
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads, List<String> props) {
		return toResult(listMap, heads, props, "", "sheet");
	}

	/**
	 * 将Map列表转换成ExcelResult 对象，默认sheet名称为sheet
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据
	 * @param heads Excel标题
	 * @param props head 标题对应的map中的key值，注意Heads 和 props 列表属性需要一一对应
	 * @param defaultValue 如果map中没有取到heads对应值，则使用默认值
	 * @return ExcelResult 结果对象
	 * @date 2016年6月10日下午9:44:44
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads, List<String> props,
			String defaultValue) {
		return toResult(listMap, heads, props, defaultValue, "sheet");
	}

	/**
	 * 将Map列表转换成ExcelResult 对象
	 * 
	 * @author LiuJunGuang
	 * @param listMap 数据列表
	 * @param headToProp 标题属性对应关系，key - Excel标题，value - map中属性名称
	 * @param defaultValue 默认值
	 * @param sheetName 默认sheet名称
	 * @return
	 * @date 2016年6月11日下午4:35:24
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, Map<String, String> headToProp,
			String defaultValue, String sheetName) {
		if (headToProp == null || headToProp.isEmpty()) {
			throw new IllegalArgumentException("headToProp must not be null!");
		}
		List<String> heads = new LinkedList<String>();
		List<String> porps = new LinkedList<String>();
		for (String key : headToProp.keySet()) {
			heads.add(key);
			porps.add(headToProp.get(key));
		}
		return toResult(listMap, heads, porps, defaultValue, sheetName);
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
	 * @return ExcelResult对象
	 * @date 2016年6月10日下午9:30:41
	 */
	public static <T> ExcelResult toResult(List<Map<String, T>> listMap, List<String> heads, List<String> props,
			String defaultValue, String sheetName) {
		if (listMap == null || listMap.size() == 0) {
			return null;
		}
		if (heads == null || heads.size() == 0) {
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
				Object obj = map.get(props.get(j));
				row.addCell(obj == null ? defaultValue : obj.toString());
			}
			sheet.addRow(row);
		}
		return new ExcelResult(sheet);
	}

}

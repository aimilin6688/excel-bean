package com.aimilin.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.aimilin.converter.DictionaryConverter;
import com.aimilin.utils.BeanUtils;
import com.aimilin.utils.ExcelResultUtils;

/**
 * 存放Excel读取结果
 * 
 * @author LiuJunGuang
 */
public class ExcelResult {
	private List<ExcelSheet> sheetList;

	public ExcelResult() {
		super();
	}

	public ExcelResult(ExcelSheet sheet) {
		super();
		this.addSheet(sheet);
	}

	public ExcelResult(List<ExcelSheet> sheetList) {
		super();
		this.sheetList = sheetList;
	}

	/**
	 * 添加ExcelSheet
	 * 
	 * @param sheet ExcelSheet 对象
	 * @return ExcelSheet 结果集
	 */
	public List<ExcelSheet> addSheet(ExcelSheet sheet) {
		if (sheetList == null) {
			sheetList = new LinkedList<ExcelSheet>();
		}
		sheetList.add(sheet);
		return sheetList;
	}

	/**
	 * 根据索引号获取Sheet对象
	 * 
	 * @param index 索引号
	 * @return ExcelSheet结果
	 */
	public ExcelSheet getSheet(int index) {
		return sheetList.get(index);
	}

	/**
	 * 根据名称获取Sheet对象
	 * 
	 * @param sheetName 名称
	 * @return ExcelSheet结果
	 */
	public ExcelSheet getSheet(String sheetName) {
		if (sheetList == null) {
			return null;
		}
		for (ExcelSheet excelSheet : sheetList) {
			if (StringUtils.equals(sheetName, excelSheet.getName())) {
				return excelSheet;
			}
		}
		return null;
	}

	public List<ExcelSheet> getSheetList() {
		return sheetList;
	}

	public int getSheetCount() {
		return sheetList == null ? 0 : sheetList.size();
	}

	public void setSheetList(List<ExcelSheet> sheetList) {
		this.sheetList = sheetList;
	}

	public List<List<String>> toList() {
		return ExcelResultUtils.toList(this, 1);
	}

	public List<List<String>> toList(int sheetIndex) {
		return ExcelResultUtils.toList(this, 1, sheetIndex);
	}

	public List<List<String>> toList(String sheetName) {
		return ExcelResultUtils.toList(this, 1, sheetName);
	}

	public List<Map<String, String>> toMap(DictionaryConverter... converters) {
		return ExcelResultUtils.toMap(this, converters);
	}

	public List<Map<String, String>> toMap(int sheetIndex, DictionaryConverter... converters) {
		return ExcelResultUtils.toMap(this, sheetIndex, converters);
	}

	public List<Map<String, String>> toMap(String sheetName, DictionaryConverter... converters) {
		return ExcelResultUtils.toMap(this, sheetName, converters);
	}

	public <T> List<T> toBean(Class<T> clazz, String sheetName, DictionaryConverter... converters) {
		return BeanUtils.toBean(this, clazz, sheetName, converters);
	}

	public <T> List<T> toBean(Class<T> clazz, int sheetIndex, DictionaryConverter... converters) {
		return BeanUtils.toBean(this, clazz, sheetIndex, converters);
	}

	public <T> List<T> toBean(Class<T> clazz, DictionaryConverter... converters) {
		return BeanUtils.toBean(this, clazz, converters);
	}

	public List<String> getHeads(int sheetIndex) {
		return sheetList.get(sheetIndex).getHeads();
	}

	@Override
	public String toString() {
		return "ExcelResult [sheetList=" + sheetList + "]";
	}

}

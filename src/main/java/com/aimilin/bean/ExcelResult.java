package com.aimilin.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	public List<ExcelSheet> getSheetList() {
		return sheetList;
	}

	public void setSheetList(List<ExcelSheet> sheetList) {
		this.sheetList = sheetList;
	}

	public List<List<String>> toList() {
		return ExcelResultUtils.toList(this);
	}

	public List<List<String>> toList(int sheetIndex) {
		return ExcelResultUtils.toList(this, sheetIndex);
	}

	public List<List<String>> toList(String sheetName) {
		return ExcelResultUtils.toList(this, sheetName);
	}

	public List<Map<String, String>> toMap() {
		return ExcelResultUtils.toMap(this);
	}

	public List<Map<String, String>> toMap(int sheetIndex) {
		return ExcelResultUtils.toMap(this, sheetIndex);
	}

	public List<Map<String, String>> toMap(String sheetName) {
		return ExcelResultUtils.toMap(this, sheetName);
	}

	@Override
	public String toString() {
		return "ExcelResult [sheetList=" + sheetList + "]";
	}

}

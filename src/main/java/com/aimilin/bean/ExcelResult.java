package com.aimilin.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aimilin.utils.ExcelResultUtils;

/**
 * 存放Excel读取结果
 * 
 * @author LiuJunGuang
 * @date 2016年5月19日下午5:29:31
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
	 * @param sheet
	 * @return
	 * @date 2016年5月19日下午5:38:25
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

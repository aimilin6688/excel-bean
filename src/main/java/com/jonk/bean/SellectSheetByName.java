package com.jonk.bean;

/**
 * 
 * @author LiuJunGuang
 * @date 2016年6月2日下午5:21:55
 */
public class SellectSheetByName implements SellectSheet {
	private String sheetName;

	public SellectSheetByName(String sheetName) {
		super();
		this.sheetName = sheetName;
	}

	public boolean isSellect(ExcelSheet sheet) {
		if (sheetName.equalsIgnoreCase(sheet.getName())) {
			return true;
		}
		return false;
	}

}

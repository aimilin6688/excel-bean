package com.aimilin.bean;

/**
 * 
 * @author LiuJunGuang
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

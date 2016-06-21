package com.aimilin.bean;

/**
 * 
 * @author LiuJunGuang
 */
public class SellectSheetByIndex implements SellectSheet {
	private int sheetIndex;

	public SellectSheetByIndex(int sheetIndex) {
		super();
		this.sheetIndex = sheetIndex;
	}

	public boolean isSellect(ExcelSheet sheet) {
		if (sheet.getIndex() == sheetIndex) {
			return true;
		}
		return false;
	}

}

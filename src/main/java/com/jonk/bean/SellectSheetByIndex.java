package com.jonk.bean;

/**
 * 
 * @author LiuJunGuang
 * @date 2016年6月2日下午5:22:06
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

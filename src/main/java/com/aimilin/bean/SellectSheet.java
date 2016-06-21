package com.aimilin.bean;

/**
 * Excel中sheet选择条件接口
 * 
 * @author LiuJunGuang
 */
public interface SellectSheet {
	/**
	 * 指定的sheet是否选中
	 * 
	 * @author LiuJunGuang
	 * @param sheet ExcelSheet对象，
	 * @return true  选择该sheet对象，false 则忽略该对象
	 */
	public boolean isSellect(ExcelSheet sheet);
}

package com.jonk.bean;

/**
 * Excel中sheet选择条件接口
 * 
 * @author LiuJunGuang
 * @date 2016年6月2日下午5:07:39
 */
public interface SellectSheet {
	/**
	 * 指定的sheet是否选中
	 * 
	 * @author LiuJunGuang
	 * @param sheet
	 * @return
	 * @date 2016年6月3日下午1:56:55
	 */
	public boolean isSellect(ExcelSheet sheet);
}

package com.aimilin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试读取
 * 
 * @author LiuJunGuang
 */
public class ExcelUtilsTest {
	private Logger log = LoggerFactory.getLogger(ExcelUtilsTest.class);
	private String path = ExcelUtilsTest.class.getClassLoader().getResource(".").getPath();
	private String excel2003 = path + "student.xls";
	private String excel2007 = path + "student.xlsx";

}

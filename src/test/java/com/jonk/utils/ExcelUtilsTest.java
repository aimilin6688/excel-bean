package com.jonk.utils;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jonk.bean.Student1;

/**
 * 测试读取
 * 
 * @author LiuJunGuang
 * @date 2016年5月19日下午6:27:01
 */
public class ExcelUtilsTest {
	private Logger log = LoggerFactory.getLogger(ExcelUtilsTest.class);
	private String path = ExcelUtilsTest.class.getClassLoader().getResource(".").getPath();
	private String excel2003 = path + "student.xls";
	private String excel2007 = path + "student.xlsx";

	@Test
	public void testRead() {
		List<Student1> stu1List = ExcelUtils.read(excel2003, Student1.class);
		log.debug("学生信息列表：" + stu1List);
	}

}

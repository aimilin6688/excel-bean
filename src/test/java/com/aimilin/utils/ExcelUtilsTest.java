package com.aimilin.utils;

import java.util.List;

import org.junit.Test;

import com.aimilin.bean.Student;

/**
 * 测试读取
 * 
 * @author LiuJunGuang
 */
public class ExcelUtilsTest {
	private String path = ExcelUtilsTest.class.getClassLoader().getResource(".").getPath();
	private String excel2003 = path + "student.xls";
	private String excel2007 = path + "student.xlsx";

	@Test
	public void test01() {
		List<Student> students = ExcelUtils.read(excel2003, Student.class);
		System.out.println(students);

		String filePath = ExcelUtils.write(students, "C:\\software");
		System.out.println(filePath);

		ExcelUtils.read2List(excel2003);

		ExcelUtils.read2Map(excel2003);

	}

}

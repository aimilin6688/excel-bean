package com.jonk.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jonk.bean.ExcelResult;
import com.jonk.bean.Student;

/**
 * 
 * @author LiuJunGuang
 * @date 2016年6月2日下午4:21:09
 */
public class BeanUtilsTest {
	@Test
	public void test01() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("姓名", "张三");
		map.put("年龄", "18");
		map.put("生日", "2016-10-23 18:34:24");
		map.put("语文分数", "99.3");
		map.put("数学分数", "99.3");
		map.put("性别", "女");
		Student stu = BeanUtils.toBean(map, Student.class);
		System.out.println(stu);
	}

	@Test
	public void test02() {
		Student stu = new Student();
		stu.setAge(18);
		stu.setBirthday(new Date());
		stu.setChineseScore(99.9);
		stu.setGender(1);
		stu.setMathsScore(88.3);
		stu.set姓名("李四");

		Map<String, String> map = BeanUtils.toMap(stu);
		System.out.println(map);
	}

	@Test
	public void test03() {
		List<Student> stuList = new ArrayList<Student>();
		int index = 1;
		for (int i = 0; i < 10; i++) {
			Student stu = new Student();
			stu.setAge(18 + i);
			stu.setBirthday(new Date());
			stu.setChineseScore(99.9 - i);
			index *= -1;
			stu.setGender(1 + index);
			stu.setMathsScore(88.3 - i);
			stu.set姓名("李四" + i);
			stuList.add(stu);
		}
		ExcelResult result = BeanUtils.toResult(stuList);
		System.out.println(result);

		ExcelUtils.write(stuList, "c:/");
	}
}

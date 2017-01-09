package com.aimilin.utils;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;

import com.aimilin.bean.ExcelResult;
import com.aimilin.bean.Student;
import com.hbzx.converter.StateConverter;

/**
 * Bean工具类测试
 *
 * @author LiuJunGuang
 */
public class BeanUtilsTest {
	private static Logger log = LoggerFactory.getLogger(BeanUtilsTest.class);
	private static Profiler profiler = new Profiler("BeanUtilsTest");
	private String pattern = "yyyy.MM.dd/HH:mm:ss";

	@BeforeClass
	public static void testBefore() {
		profiler.setLogger(log);
	}

	@AfterClass
	public static void testAfter() {
		profiler.stop().log();
	}

	// 测试Map转换成JavaBean对象
	@Test
	public void testMap2Bean() {
		profiler.start("testMap2Bean");
		String date = "2016.10.23/12:29:56";
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("姓名", "张三");
		map.put("年龄", "18");
		map.put("生日", date);
		map.put("语文分数", "99.3");
		map.put("数学分数", "89.34");
		map.put("性别", "女");
		map.put("状态", "启用");
		ReflectionUtils.setPatterns(pattern);
		Student stu = BeanUtils.toBean(map, Student.class, new StateConverter());
		assertNotNull(stu);
		assertEquals("张三", stu.getName());
		assertEquals(Integer.valueOf(18), stu.getAge());
		assertEquals(Double.valueOf(99.3), stu.getChineseScore());
		assertEquals(Double.valueOf(89.34), stu.getMathsScore());
		assertEquals(Integer.valueOf(2), stu.getGender());
		assertEquals(Integer.valueOf(1), stu.getState());
		assertEquals(date, DateFormatUtils.format(stu.getBirthday(), pattern));
	}

	// bean转换成Map
	@Test
	public void testBean2Map() throws ParseException {
		profiler.start("testBean2Map");
		Date date = new Date();
		Student stu = new Student();
		stu.setAge(18);
		stu.setBirthday(date);
		stu.setChineseScore(99.9);
		stu.setGender(1);
		stu.setMathsScore(88.3);
		stu.setName("张三");
		stu.setState(2);

		ReflectionUtils.setPatterns(pattern);
		Map<String, String> map = BeanUtils.toMap(stu, new StateConverter());
		assertNotNull(stu);
		assertEquals("18", map.get("年龄"));
		assertEquals("张三", map.get("姓名"));
		assertEquals("88.3", map.get("数学分数"));
		assertEquals("99.9", map.get("语文分数"));
		assertEquals("男", map.get("性别"));
		assertEquals("注销", map.get("状态"));
		assertEquals(DateFormatUtils.format(date, pattern), map.get("生日"));
	}

	// bean转换成ExcelResult对象
	@Test
	public void testBean2ExcelResult() {
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
			stu.setState(2);
			stuList.add(stu);
		}
		ExcelResult result = BeanUtils.toResult(stuList, new StateConverter());
		System.out.println(result);

		ExcelUtils.write(result, "c:/software/");
	}
}

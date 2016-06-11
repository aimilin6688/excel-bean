package com.jonk.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jonk.bean.ExcelResult;
import com.jonk.bean.ExcelRow;
import com.jonk.bean.ExcelSheet;
import com.jonk.bean.ExcelType;
import com.jonk.bean.Student;

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

		ExcelResult result = ExcelUtils.read(excel2003, true);
		ExcelRow heads = result.getSheetList().get(0).getHeadRow();
		log.debug("Excel标题：" + heads);
		p(result.toList());

		List<List<String>> list = ExcelUtils.read2List(excel2007);
		p(list);
		ExcelUtils.write4List(list, heads.getCellList(), "c:/");

		log.debug(ExcelUtils.read2Map(excel2003).toString());

		List<Student> studentList = ExcelUtils.read2Bean(excel2003, Student.class);
		System.out.println(studentList);

	}

	@Test
	public void testWriteBean() {
		List<Student> studentList = ExcelUtils.read2Bean(excel2003, Student.class);
		System.out.println(studentList);
		ExcelUtils.write(studentList, "c:/aa.xls", ExcelType.XLS, false);
	}

	@Test
	public void testWriteMap() {
		List<Map<String, String>> list = ExcelUtils.read2Map(excel2003);
		System.out.println(list);
		List<String> heads = new ArrayList<String>(list.get(0).keySet());
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (String head : heads) {
			map.put(head, head);
		}

		ExcelUtils.write4Map(list, map, "学生", "c:/");
	}

	@Test
	public void testWrite() throws FileNotFoundException {
		ExcelResult er = new ExcelResult();

		ExcelSheet sheet = new ExcelSheet();
		sheet.setIndex(0);
		sheet.setName("学生");
		sheet.addHeadRow("姓名", "年龄");

		for (int i = 0; i < 100; i++) {
			ExcelRow row = new ExcelRow(i);
			row.addCell("张三" + i);
			row.addCell("18");
			sheet.addRow(row);
		}

		er.addSheet(sheet);
		System.out.println(ExcelUtils.write(er, "c:/"));
	}

	// 打印列表
	private void p(List<List<String>> list) {
		for (List<String> str : list) {
			log.debug(str.toString());
		}
	}

}

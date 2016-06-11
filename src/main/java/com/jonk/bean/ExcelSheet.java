package com.jonk.bean;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * ExcelSheet
 * 
 * @author LiuJunGuang
 * @date 2016年5月19日下午5:31:07
 */
public class ExcelSheet {
	private String name;
	private int index;
	private List<ExcelRow> rowList;
	private ExcelRow headRow;

	/**
	 * 添加Sheet行
	 * 
	 * @author LiuJunGuang
	 * @param row
	 * @return
	 * @date 2016年5月19日下午5:46:01
	 */
	public List<ExcelRow> addRow(ExcelRow... row) {
		if (rowList == null) {
			rowList = new LinkedList<ExcelRow>();
		}
		if (row == null || row.length == 0) {
			return rowList;
		}
		rowList.addAll(Arrays.asList(row));
		return this.rowList;
	}

	public void addHeadRow(String... headName) {
		if (headRow == null) {
			headRow = new ExcelRow();
		}
		if (headName != null && headName.length > 0) {
			headRow.addCell(headName);
		}
	}

	public void addHeadRow(List<String> heads) {
		if (heads == null || heads.size() == 0) {
			return;
		}
		if (headRow == null) {
			headRow = new ExcelRow();
		}
		for (String head : heads) {
			headRow.addCell(head);
		}
	}

	public ExcelSheet(String name, int index) {
		super();
		this.name = name;
		this.index = index;
	}

	public ExcelSheet(String name, int index, List<ExcelRow> rowList) {
		super();
		this.name = name;
		this.index = index;
		this.rowList = rowList;
	}

	public ExcelSheet() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<ExcelRow> getRowList() {
		return rowList;
	}

	public void setRowList(List<ExcelRow> rowList) {
		this.rowList = rowList;
	}

	public ExcelRow getHeadRow() {
		return headRow;
	}

	public void setHeadRow(ExcelRow headRow) {
		this.headRow = headRow;
	}

	@Override
	public String toString() {
		return "ExcelSheet [name=" + name + ", index=" + index + ", rowList=" + rowList + "]";
	}

}

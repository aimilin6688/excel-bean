package com.jonk.bean;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Excel行
 * 
 * @author LiuJunGuang
 * @date 2016年5月19日下午5:34:48
 */
public class ExcelRow {
	private int index;
	private List<String> cellList;

	public ExcelRow() {
		super();
	}

	public ExcelRow(int index) {
		super();
		this.index = index;
	}

	public ExcelRow(int index, List<String> cellList) {
		super();
		this.index = index;
		this.cellList = cellList;
	}

	public List<String> addCell(Collection<String> values) {
		if (values == null || values.size() == 0) {
			return cellList;
		}
		if (cellList == null) {
			cellList = new LinkedList<String>();
		}
		cellList.addAll(values);
		return cellList;
	}

	public List<String> addCell(String... values) {
		if (cellList == null) {
			cellList = new LinkedList<String>();
		}
		cellList.addAll(Arrays.asList(values));
		return cellList;
	}

	public List<String> getCellList() {
		return cellList;
	}

	public int getIndex() {
		return index;
	}

	public void setCellList(List<String> cellList) {
		this.cellList = cellList;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "ExcelRow [index=" + index + ", cellList=" + cellList + "]";
	}

}

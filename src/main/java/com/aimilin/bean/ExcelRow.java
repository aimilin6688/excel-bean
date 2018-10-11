package com.aimilin.bean;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Excel行
 * 
 * @author LiuJunGuang
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
		if (values == null || values.isEmpty()) {
			return cellList;
		}
		if (cellList == null) {
			cellList = new LinkedList<>();
		}
		cellList.addAll(values);
		return cellList;
	}

	public List<String> addCell(String... values) {
		if (cellList == null) {
			cellList = new LinkedList<>();
		}
		cellList.addAll(Arrays.asList(values));
		return cellList;
	}

	public int getCellCount() {
		return cellList == null ? 0 : cellList.size();
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

package com.aimilin.utils;

import java.lang.reflect.Field;
import java.util.Comparator;

import com.aimilin.annotation.Column;

/**
 * 属性排序器
 * 
 * @author LiuJunGuang
 */
public class FieldComparator implements Comparator<Field> {

	public int compare(Field o1, Field o2) {
		Column f1 = o1.getAnnotation(Column.class);
		Column f2 = o2.getAnnotation(Column.class);
		if (f1 == null && f2 == null) {
			return o1.getName().compareTo(o2.getName());
		}

		if (f1 == null && f2 != null) {
			return 1;
		}

		if (f1 != null && f2 == null) {
			return -1;
		}

		if (f1.index() == f2.index()) {
			return o1.getName().compareTo(o2.getName());
		}

		return f1.index() - f2.index();
	}

}

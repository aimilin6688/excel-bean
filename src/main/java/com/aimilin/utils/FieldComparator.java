package com.aimilin.utils;

import java.lang.reflect.Field;
import java.util.Comparator;

import com.aimilin.annotation.Row;

/**
 * 属性排序器
 * 
 * @author LiuJunGuang
 */
public class FieldComparator implements Comparator<Field> {

	public int compare(Field o1, Field o2) {
		Row f1 = o1.getAnnotation(Row.class);
		Row f2 = o2.getAnnotation(Row.class);
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

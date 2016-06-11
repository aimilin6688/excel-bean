package com.hbzx.converter;

import java.lang.reflect.Array;
import java.util.Calendar;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

public class ExtConvertUtilsBean extends ConvertUtilsBean {

	@Override
	public String convert(Object value) {
		if (value == null) {
			return null;
		} else if (value.getClass().isArray()) {
			if (Array.getLength(value) < 1) {
				return (null);
			}
			value = Array.get(value, 0);
			if (value == null) {
				return null;
			} else {
				Converter converter = lookup(String.class);
				return (converter.convert(String.class, value));
			}
		}

		Converter converter = null;
		if (value instanceof java.util.Date) {
			converter = lookup(java.util.Date.class);
		} else if (value instanceof java.sql.Date) {
			converter = lookup(java.sql.Date.class);
		} else if (value instanceof java.sql.Time) {
			converter = lookup(java.sql.Time.class);
		} else if (value instanceof Calendar) {
			converter = lookup(Calendar.class);
		}
		if (converter == null) {
			converter = lookup(String.class);
		}
		return (converter.convert(String.class, value));
	}
}
package com.aimilin.utils;

import static java.util.Locale.ENGLISH;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.CalendarConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;

import com.aimilin.converter.ExtConvertUtilsBean;

public class ReflectionUtils {
	private static CalendarConverter calendarConverter = new CalendarConverter(null);
	private static DateConverter dateConverter = new DateConverter(null);
	private static SqlDateConverter sqlDateConverter = new SqlDateConverter(null);
	private static SqlTimeConverter sqlTimeConverter = new SqlTimeConverter(null);
	private static ExtConvertUtilsBean extConvertUtilsBean = new ExtConvertUtilsBean();
	private static String[] patterns = { "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yy-MM-dd HH:mm:ss", "yyyy-MM-dd", "MM/dd/yyyy", "HH:mm:ss" };
	static {
		ReflectionUtils.registerDateConverter(patterns);
	}
	
	private ReflectionUtils() {
		super();
	}

	/**
	 * 重新注册日期转换器
	 * @param patterns
	 */
	private static void registerDateConverter(String ... patterns) {
		setPatterns(patterns);
		BeanUtilsBean.setInstance(new BeanUtilsBean(extConvertUtilsBean));
		ConvertUtils.register(calendarConverter, Calendar.class);
		ConvertUtils.register(dateConverter, java.util.Date.class);
		ConvertUtils.register(sqlDateConverter, java.sql.Date.class);
		ConvertUtils.register(sqlTimeConverter, java.sql.Time.class);
	}
	
	
	/**
     * Register the provided converters with the specified defaults.
     *
     * @param throwException <code>true</code> if the converters should
     * throw an exception when a conversion error occurs, otherwise
     * <code>false</code> if a default value should be used.
     * @param defaultNull <code>true</code>if the <i>standard</i> converters
     * (see {@link ConvertUtilsBean#registerStandard(boolean, boolean)})
     * should use a default value of <code>null</code>, otherwise <code>false</code>.
     * N.B. This values is ignored if <code>throwException</code> is <code>true</code>
     * @param defaultArraySize The size of the default array value for array converters
     * (N.B. This values is ignored if <code>throwException</code> is <code>true</code>).
     * Specifying a value less than zero causes a <code>null</code> value to be used for
     * the default.
     */
    public static void register(boolean throwException, boolean defaultNull, int defaultArraySize) {
    	extConvertUtilsBean.register(throwException, defaultNull, defaultArraySize);
    	registerDateConverter(patterns);
    }
	

	/**
	 * @param patterns 日期转换时，日期的格式
	 */
	public static void setPatterns(String... patterns) {
		calendarConverter.setPatterns(patterns);
		dateConverter.setPatterns(patterns);
		sqlDateConverter.setPatterns(patterns);
		sqlTimeConverter.setPatterns(patterns);
	}

	/**
	 * <p>
	 * Set the specified property value, performing type conversions as required to conform to the type of the
	 * destination property.
	 * </p>
	 *
	 * <p>
	 * For more details see <code>BeanUtilsBean</code>.
	 * </p>
	 *
	 * @param bean Bean on which setting is to be performed
	 * @param name Property name (can be nested/indexed/mapped/combo)
	 * @param value Value to be set
	 *
	 * @exception IllegalAccessException if the caller does not have access to the property accessor method
	 * @exception InvocationTargetException if the property accessor method throws an exception
	 * @see BeanUtilsBean#setProperty
	 */
	public static void setProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {
		org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
	}

	/**
	 * <p>
	 * Return the value of the specified property of the specified bean, no matter which property reference format is
	 * used, as a String.
	 * </p>
	 *
	 * <p>
	 * For more details see <code>BeanUtilsBean</code>.
	 * </p>
	 *
	 * @param bean Bean whose property is to be extracted
	 * @param name Possibly indexed and/or nested name of the property to be extracted
	 * @return The property's value, converted to a String
	 *
	 * @exception IllegalAccessException if the caller does not have access to the property accessor method
	 * @exception InvocationTargetException if the property accessor method throws an exception
	 * @exception NoSuchMethodException if an accessor method for this property cannot be found
	 * @see BeanUtilsBean#getProperty
	 */
	public static String getProperty(Object bean, String name)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
	}

	/**
	 * 获取指定类的所有属性包括继承父类的属性
	 *
	 * @param clazz 类
	 * @return 所有属性信息
	 */
	public static Field[] getFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields();
				fieldList.addAll(Arrays.asList(fields));
			} catch (Exception e) {
			}
		}
		return fieldList.toArray(new Field[0]);
	}

	/**
	 * 判断指定类的指定属性是否有读方法，有返回true，否则返回 false
	 *
	 * @param clazz 类
	 * @param fieldName 属性名称
	 * @return true - 有读方法， false - 无读方法
	 */
	public static boolean hasReadMethod(Class<?> clazz, String fieldName) {
		try {
			new PropertyDescriptor(fieldName, clazz, "is" + capitalize(fieldName), null);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断指定类的指定属性是否有写方法，有返回true，否则返回 false
	 *
	 * @param clazz 类
	 * @param fieldName 属性名称
	 * @return true - 有写方法， false - 无写方法
	 */
	public static boolean hasWriteMethod(Class<?> clazz, String fieldName) {
		try {
			new PropertyDescriptor(fieldName, clazz, null, "set" + capitalize(fieldName));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断制定的类属性是否有读写方法
	 *
	 * @param clazz 类
	 * @param fieldName 属性名称
	 * @return true - 有读写方法， false - 无读写方法
	 */
	public static boolean hasReadAndWriteMethos(Class<?> clazz, String fieldName) {
		try {
			new PropertyDescriptor(fieldName, clazz);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a String which capitalizes the first letter of the string.
	 */
	private static String capitalize(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
	}
}

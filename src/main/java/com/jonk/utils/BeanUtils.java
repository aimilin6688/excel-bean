package com.jonk.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.CalendarConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hbzx.converter.ExtConvertUtilsBean;
import com.jonk.annotation.Dictionary;
import com.jonk.annotation.Row;
import com.jonk.annotation.Sheet;
import com.jonk.bean.ExcelResult;
import com.jonk.bean.ExcelRow;
import com.jonk.bean.ExcelSheet;

/**
 * Bean工具类
 * 
 * @author LiuJunGuang
 * @date 2016年6月2日下午3:40:06
 */
public class BeanUtils {
	private static Logger log = LoggerFactory.getLogger(BeanUtils.class);
	private static FieldComparator fieldComparator = new FieldComparator();
	static {
		String[] patterns = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
		CalendarConverter calendarConverter = new CalendarConverter(null);
		calendarConverter.setPatterns(patterns);

		DateConverter dateConverter = new DateConverter(null);
		dateConverter.setPatterns(patterns);

		SqlDateConverter sqlDateConverter = new SqlDateConverter(null);
		sqlDateConverter.setPatterns(patterns);

		SqlTimeConverter sqlTimeConverter = new SqlTimeConverter(null);
		sqlTimeConverter.setPatterns(patterns);

		BeanUtilsBean.setInstance(new BeanUtilsBean(new ExtConvertUtilsBean()));
		ConvertUtils.register(calendarConverter, Calendar.class);
		ConvertUtils.register(dateConverter, java.util.Date.class);
		ConvertUtils.register(sqlDateConverter, java.sql.Date.class);
		ConvertUtils.register(sqlTimeConverter, java.sql.Time.class);
	}

	/**
	 * 获取字典中指定的类型值,如果字典中没有指定的值，则返回value
	 * 
	 * @author LiuJunGuang
	 * @param rowField
	 * @param value
	 * @param dic2value 字典值转换成对象值，true - 根据Dictionary 的value 查找，返回name，false - 根据Dictionary 的name查找，返回 value
	 * @return
	 * @date 2016年6月10日下午2:57:00
	 */
	private static String getDictionaryValue(Row rowField, String value, boolean dic2value) {
		if (rowField == null) {
			return value;
		}
		Dictionary[] dictionaries = rowField.dictionaries();
		if (dictionaries == null || dictionaries.length == 0 || value == null || "".equals(value)) {
			return value;
		}

		for (Dictionary dictionary : dictionaries) {
			if (dic2value) {
				if (value.equals(dictionary.value())) {
					return dictionary.name();
				}
			} else {
				if (value.equals(dictionary.name())) {
					return dictionary.value();
				}
			}
		}

		return value;
	}

	/**
	 * 将excelResult 转换成javaBean
	 * 
	 * @author LiuJunGuang
	 * @param excelResult
	 * @param clazz clazz 需要生成的JavaBean对象
	 * @return 如果列表为空，或者为null，则直接返回null,如果发生异常则直接返回null
	 * @date 2016年6月2日下午3:43:15
	 */
	public static <T> List<T> toBean(ExcelResult excelResult, Class<T> clazz) {
		Sheet sheet = clazz.getAnnotation(Sheet.class);

		if (sheet != null) {
			String sheetName = sheet.value();
			if (sheetName != null && !"".equals(sheetName)) {
				return toBean(excelResult.toMap(sheetName), clazz, true);
			}

			int sheetIndex = sheet.index();
			if (sheetIndex >= 0) {
				return toBean(excelResult.toMap(sheetIndex), clazz, true);
			}
		}
		return toBean(excelResult.toMap(), clazz, true);
	}

	/**
	 * 将excelResult 转换成javaBean
	 * 
	 * @author LiuJunGuang
	 * @param excelResult
	 * @param clazz clazz 需要生成的JavaBean对象
	 * @param sheetIndex
	 * @return 如果列表为空，或者为null，则直接返回null,如果发生异常则直接返回null
	 * @date 2016年6月2日下午3:43:15
	 */
	public static <T> List<T> toBean(ExcelResult excelResult, Class<T> clazz, int sheetIndex) {
		return toBean(excelResult.toMap(sheetIndex), clazz, true);
	}

	/**
	 * 将excelResult 转换成javaBean
	 * 
	 * @author LiuJunGuang
	 * @param excelResult
	 * @param clazz clazz 需要生成的JavaBean对象
	 * @param sheetName
	 * @return 如果列表为空，或者为null，则直接返回null,如果发生异常则直接返回null
	 * @date 2016年6月2日下午3:43:15
	 */
	public static <T> List<T> toBean(ExcelResult excelResult, Class<T> clazz, String sheetName) {
		return toBean(excelResult.toMap(sheetName), clazz, true);
	}

	/**
	 * 将ListMap 转换成javaBean
	 * 
	 * @author LiuJunGuang
	 * @param list
	 * @param clazz clazz 需要生成的JavaBean对象
	 * @return 如果列表为空，或者为null，则直接返回null,如果发生异常则直接返回null
	 * @date 2016年6月2日下午3:43:15
	 */
	public static <T> List<T> toBean(List<Map<String, String>> list, Class<T> clazz) {
		return toBean(list, clazz, true);
	}

	/**
	 * 将ListMap 转换成javaBean
	 * 
	 * @author LiuJunGuang
	 * @param list
	 * @param clazz
	 * @return 如果列表为空，或者为null，则直接返回null
	 * @date 2016年6月2日下午3:43:15
	 */
	public static <T> List<T> toBean(List<Map<String, String>> list, Class<T> clazz, boolean ignoreException) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<T> result = new ArrayList<T>();
		for (Map<String, String> map : list) {
			T bean = toBean(map, clazz, ignoreException);
			if (bean != null) {
				result.add(bean);
			}
		}
		return result;
	}

	/**
	 * 将结果对象转换成Excel结果集对象,并忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @return
	 * @date 2016年6月10日下午4:43:14
	 */
	public static <T> ExcelResult toResult(List<T> list) {
		return toResult(list, true);
	}

	/**
	 * 将结果对象转换成Excel结果集对象
	 * 
	 * @author LiuJunGuang
	 * @param list
	 * @param ignoreException
	 * @return
	 * @date 2016年6月10日下午4:40:29
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ExcelResult toResult(List<T> list, boolean ignoreException) {
		if (list == null || list.size() == 0) {
			return null;
		}
		Class clazz = list.get(0).getClass();
		String sheetName = clazz.getSimpleName();
		int sheetIndex = 0;
		Sheet sheet = (Sheet) clazz.getAnnotation(Sheet.class);
		// 设置sheet名称
		if (sheet != null) {
			sheetName = sheet.value();
			if (sheetName == null || "".equals(sheetName)) {
				sheetName = clazz.getSimpleName();
			}
			sheetIndex = sheet.index();
			if (sheetIndex < 0) {
				sheetIndex = 0;
			}
		}

		// 设置结果信息
		ExcelSheet exlSheet = new ExcelSheet(sheetName, sheetIndex);
		exlSheet.addHeadRow(getHeads(clazz));
		for (int i = 0; i < list.size(); i++) {
			T obj = list.get(i);
			Map<String, String> map = toMap(obj, ignoreException);
			if (map == null) {
				continue;
			}

			ExcelRow row = new ExcelRow(i + 1);
			row.addCell(map.values());
			exlSheet.addRow(row);
		}
		return new ExcelResult(exlSheet);
	}

	// 获取Excel标题信息
	protected static List<String> getHeads(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();// 根据Class对象获得属性 私有的也可以获得
		Arrays.sort(fields, fieldComparator);// 属性按照index 排序
		List<String> heads = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Row rowField = field.getAnnotation(Row.class);
			String name = field.getName();
			if (rowField != null && !"".equals(rowField.value())) {
				name = rowField.value();
			}
			heads.add(name);
		}
		return heads;
	}

	/**
	 * 根据Map设置Class属性，使用{@link Row} 注解}
	 * 
	 * @author LiuJunGuang
	 * @param map 属性值Map
	 * @param clazz 需要生成的JavaBean对象
	 * @return 如果Map为空或者null，则返回 null,如果转换失败则返回null
	 * @date 2016年6月2日下午4:51:05
	 */
	public static <T> T toBean(Map<String, String> map, Class<T> clazz) {
		return toBean(map, clazz, true);
	}

	/**
	 * 根据Map设置Class属性，使用{@link Row} 注解}
	 * 
	 * @author LiuJunGuang
	 * @param map 属性值Map
	 * @param clazz 需要生成的JavaBean对象
	 * @param ignoreException 是否忽略转换异常，true 忽略，异常之后返回null，否则抛出异常
	 * @return 如果Map为空或者null，则返回 null
	 * @date 2016年6月2日下午4:51:05
	 */
	public static <T> T toBean(Map<String, String> map, Class<T> clazz, boolean ignoreException) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		try {
			Field[] fields = clazz.getDeclaredFields();// 根据Class对象获得属性 私有的也可以获得
			T obj = clazz.getConstructor().newInstance();
			String[] keys = map.keySet().toArray(new String[0]);
			for (Field field : fields) {
				Row rowField = field.getAnnotation(Row.class);
				String name = field.getName();
				if (rowField != null) {
					name = rowField.value();
					if (name == null || "".equals(name)) {
						int index = rowField.index();
						if (index >= 0) {
							name = keys[index];
						}
					}
				}
				if (name == null || "".equals(name)) {
					name = field.getName();
				}
				String value = MapUtils.getString(map, name);
				value = getDictionaryValue(rowField, value, true);
				org.apache.commons.beanutils.BeanUtils.setProperty(obj, field.getName(), value);
			}
			return obj;
		} catch (Exception e) {
			if (ignoreException) {
				log.warn(e.getMessage(), e);
				return null;
			} else {
				log.error(e.getMessage(), e);
				throw new ConversionException("生成javaBean失败！", e);
			}
		}
	}

	/**
	 * 将对象列表转换成Map列表，并忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @return
	 * @date 2016年6月10日下午3:57:18
	 */
	public static <T> List<Map<String, String>> toMap(List<T> list) {
		return toMap(list, true);
	}

	/**
	 * 将对象列表转换成Map列表
	 * 
	 * @author LiuJunGuang
	 * @param list
	 * @param ignoreException
	 * @return
	 * @date 2016年6月10日下午3:53:30
	 */
	public static <T> List<Map<String, String>> toMap(List<T> list, boolean ignoreException) {
		if (list == null || list.size() == 0) {
			return null;
		}

		List<Map<String, String>> result = new LinkedList<Map<String, String>>();
		for (T t : list) {
			Map<String, String> map = toMap(t, ignoreException);
			if (map != null) {
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 将Java对象转换成Map对象,并忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param obj
	 * @return
	 * @date 2016年6月10日下午3:58:08
	 */
	public static <T> Map<String, String> toMap(T obj) {
		return toMap(obj, true);
	}

	/**
	 * 将Java对象转换成Map对象
	 * 
	 * @author LiuJunGuang
	 * @param obj
	 * @return
	 * @date 2016年6月10日下午3:22:53
	 */
	public static <T> Map<String, String> toMap(T obj, boolean ignoreException) {
		if (obj == null) {
			if (ignoreException) {
				return null;
			} else {
				throw new IllegalArgumentException("obj must not be null!");
			}
		}

		try {
			Field[] fields = obj.getClass().getDeclaredFields();// 根据Class对象获得属性 私有的也可以获得
			Map<String, String> map = new LinkedHashMap<String, String>();
			Arrays.sort(fields, fieldComparator);// 属性按照index 排序
			for (Field field : fields) {
				Row rowField = field.getAnnotation(Row.class);
				String name = field.getName();
				if (rowField != null && !"".equals(rowField.value())) {
					name = rowField.value();
				}
				String value = org.apache.commons.beanutils.BeanUtils.getProperty(obj, field.getName());
				value = getDictionaryValue(rowField, value, false);
				map.put(name, value);
			}
			return map;
		} catch (Exception e) {
			if (ignoreException) {
				log.warn(e.getMessage(), e);
				return null;
			} else {
				log.error(e.getMessage(), e);
				throw new ConversionException("生成Map失败！", e);
			}
		}
	}
}

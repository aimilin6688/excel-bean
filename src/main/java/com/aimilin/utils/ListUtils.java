package com.aimilin.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * List操作工具类
 * 
 * @author LiuJunGuang
 */
public class ListUtils {
	
	private ListUtils() {
		super();
	}

	/**
	 * 根据指定的大小拆分列表
	 * 
	 * @param list 需要拆分的列表
	 * @param size 每个子列表最大元素个数
	 * @param <T> 任意类型数据
	 * @return 拆分之后的列表
	 */
	public static <T> List<List<T>> split(List<T> list, int size) {
		if (list == null || list.isEmpty())
			return null;
		List<List<T>> splitList = new LinkedList<>();

		int listSize = list.size();
		for (int i = 0; i < Math.ceil(listSize / Double.valueOf(size)); i++) {
			int end = (i + 1) * size > listSize ? listSize : (i + 1) * size;
			List<T> subList = list.subList(i * size, end);
			splitList.add(subList);
		}
		return splitList;
	}

	/**
	 * 将给定的字符串数据构建一个列表类型对象
	 * 
	 * @param params 字符串数组
	 * @return List 结果列表
	 */
	public static List<String> createList(String... params) {
		return array2List(params);
	}

	/**
	 * 将给定的数据对象构建一个列表类型对象
	 * 
	 * @param params 字符串数组
	 * @param <S> 任意类型
	 * @return List 结果列表
	 */
	@SuppressWarnings("unchecked")
	public static <S> List<S> createList(S... params) {
		if (params == null) {
			return null;
		}
		List<S> targets = new ArrayList<>();
		for (S param : params) {
			targets.add(param);
		}
		return targets;
	}

	/**
	 * 合并给定两个列表数据到一个新列表中，源列表始终会排在前面,不会影响源和目标数据列表
	 * 
	 * @param sources 源列表
	 * @param targets 目标列表
	 * @param <T> 任意类型
	 * @return List 结果列表
	 */
	public static <T> List<T> mergerList(List<T> sources, List<T> targets) {
		List<T> results = new ArrayList<>();
		if (sources != null && !sources.isEmpty()) {
			results.addAll(sources);
		}
		if (targets != null && !targets.isEmpty()) {
			results.addAll(targets);
		}
		return results;
	}

	/**
	 * 将给定的列表合并成一个列表
	 * 
	 * @param sources 源列表
	 * @param <T> 任意类型
	 * @return List 结果列表
	 */
	public static <T> List<T> mergerList(List<List<T>> sources) {
		List<T> results = new ArrayList<>();
		if (sources == null || sources.isEmpty()) {
			return results;
		}
		for (List<T> source : sources) {
			if (source == null || source.isEmpty()) {
				continue;
			}
			results.addAll(source);
		}
		return results;
	}

	/**
	 * 将字符串(以逗号分隔，如:A,B,C)解析成List
	 * 
	 * @param str 参数
	 * @return 若未指定字符串则返回空列表
	 */
	public static List<String> str2List(String str) {
		return str2List(str, ",");
	}

	/**
	 * 将字符串(以指定分隔符分隔，如:A,B,C)解析成List
	 * 
	 * @param str 字符串参数
	 * @param separator 分隔符，若未给定则返回只包含指定字符串的列表
	 * @return 若未指定字符串则返回空列表
	 */
	public static List<String> str2List(String str, String separator) {
		String[] array = StringUtils.split(str, separator);
		if (ArrayUtils.isEmpty(array)) {
			return new ArrayList<>();
		}
		return array2List(array);
	}

	/**
	 * 将字符串列表对象转换成字符串数组对象，如果list为null或空列表，则返回null
	 * 
	 * @param list 要转换的字符串列表对象
	 * @return 字符串数组
	 */
	public static String[] list2Array(List<String> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		String[] strArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strArray[i] = list.get(i);
		}
		return strArray;
	}

	/**
	 * 将字符串数组转换成以逗号分隔的字符串
	 * 
	 * @param array 字符串数组
	 * @return 字符串
	 */
	public static String array2Str(String[] array) {
		return array2Str(array, null);
	}

	/**
	 * 以指定分隔符构建字符串
	 * 
	 * @param words 字符串数组
	 * @param separator 分隔符
	 * @return 拼接之后的字符串
	 */
	public static String array2Str(String[] words, String separator) {
		if (ArrayUtils.isEmpty(words)) {
			return "";
		}
		if (StringUtils.isEmpty(separator)) {
			separator = ",";
		}
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			if (StringUtils.isEmpty(word)) {
				continue;
			}
			sb.append(word).append(separator);
		}
		return StringUtils.removeEnd(sb.toString(), separator);
	}

	/**
	 * 将给定列表List转换成以逗号分隔的字符串
	 * 
	 * @param sources 列表
	 * @return 若未指定列表则返回空字符串，若返回null则表示格式化失败
	 */
	public static String list2Str(List<String> sources) {
		return list2Str(sources, ",");
	}

	/**
	 * 将给定列表List转换成以指定分隔符分隔的字符串
	 * 
	 * @param sources 字符串参数列表
	 * @param separator 分隔符，若未指定默认以逗号代替
	 * @return 字符串
	 */
	public static String list2Str(List<String> sources, String separator) {
		return list2Str(sources, null, separator);
	}

	/**
	 * List转换成列表
	 * 
	 * @param sources 列表
	 * @param prefix 前缀
	 * @param separator 分隔符
	 * @return 字符串
	 */
	public static String list2Str(List<String> sources, String prefix, String separator) {
		if (sources == null || sources.isEmpty())
			return null;
		StringBuilder sb = new StringBuilder();
		for (String string : sources) {
			if (prefix != null) {
				sb.append(prefix);
			}
			sb.append(string).append(separator);
		}
		return StringUtils.removeEnd(sb.toString(), separator);
	}

	/**
	 * 将给定类型的对象列表转换成列表
	 * 
	 * @param array 数组
	 * @param <S> 任意类型数据
	 * @return List 结果集
	 */
	@SuppressWarnings("unchecked")
	public static <S> List<S> array2List(S... array) {
		List<S> strList = new LinkedList<>();
		if (array != null && array.length != 0) {
			for (S string : array) {
				strList.add(string);
			}
		}
		return strList;
	}

	/**
	 * 字符串列表去重
	 * 
	 * @param sources 源列表
	 * @return List
	 */
	public static List<String> unique(List<String> sources) {
		if (sources == null || sources.isEmpty()) {
			return sources;
		}
		List<String> targets = new ArrayList<>();
		for (String source : sources) {
			if (StringUtils.isEmpty(source)) {
				continue;
			}
			if (targets.contains(source)) {
				continue;
			}
			targets.add(source);
		}
		return targets;
	}

	/**
	 * 将字符串列表转换成大写字符串列表，同时过滤掉null及空字符串
	 * 
	 * @param strList 字符串列表
	 * @return List
	 */
	public static List<String> toUpper(List<String> strList) {
		List<String> targets = new ArrayList<>();
		for (String string : strList) {
			if (StringUtils.isNotBlank(string)) {
				targets.add(string.toUpperCase());
			}
		}
		return targets;
	}

	/**
	 * 将字符串列表转换成小写字符串列表，同时过滤掉null及空字符串
	 * 
	 * @param strList 字符串列表
	 * @return List
	 */
	public static List<String> toLower(List<String> strList) {
		List<String> targets = new ArrayList<>();
		for (String string : strList) {
			if (StringUtils.isNotBlank(string)) {
				targets.add(string.toLowerCase());
			}
		}
		return targets;
	}

	/**
	 * 计算指定列表的长度
	 * 
	 * @param lists 字符串集合
	 * @return 大小
	 */
	public static int size(Collection<?> lists) {
		return lists == null ? 0 : lists.size();
	}

	/**
	 * 判定集合为空
	 * 
	 * @param entityList List列表
	 * @return 是否为空，true 空
	 */
	public static boolean isEmpty(Collection<?> entityList) {
		return entityList == null || entityList.isEmpty();
	}

	/**
	 * 判定集合非空
	 * 
	 * @param entityList List列表
	 * @return 是否为空，true 不空
	 */
	public static boolean isNotEmpty(Collection<?> entityList) {
		return entityList != null && !entityList.isEmpty();
	}

	/**
	 * 列表排序,本排序方法是null值安全的
	 * 
	 * @param sources 源列表
	 * @param <T> 任意类型
	 * @param comparer 比较器
	 */
	public static <T> void sortList(List<T> sources, Comparator<? super T> comparer) {
		if (isEmpty(sources) || comparer == null) {
			return;
		}
		Collections.sort(sources, comparer);
	}

	/**
	 * 列表排序
	 * 
	 * @param <T> 任意类型
	 * @param sources 源列表
	 */
	public static <T extends Comparable<? super T>> void sortList(List<T> sources) {
		if (isEmpty(sources)) {
			return;
		}
		Collections.sort(sources);
	}

	/**
	 * 列表求和
	 * 
	 * @param paramList 参数列表
	 * @return 长度
	 */
	public static Integer sumList(int[] paramList) {
		if (paramList == null || paramList.length == 0)
			return 0;
		Integer sum = 0;
		for (Integer integer : paramList) {
			sum += integer;
		}
		return sum;
	}

	/**
	 * 根据指定的大小拆分列表
	 * 
	 * @param list 需要拆分的列表
	 * @param size 每个子列表最大元素个数
	 * @param <T> 任意类型数据
	 * @return 拆分之后的列表
	 */
	public static <T> List<List<T>> splitList(List<T> list, int size) {
		if (list == null || list.isEmpty())
			return null;
		List<List<T>> splitList = new LinkedList<>();

		int listSize = list.size();
		for (int i = 0; i < Math.ceil(listSize / Double.valueOf(size)); i++) {
			int end = (i + 1) * size > listSize ? listSize : (i + 1) * size;
			List<T> subList = list.subList(i * size, end);
			splitList.add(subList);
		}
		return splitList;
	}

	/**
	 * 过滤Map中指定的字段
	 * 
	 * @param list 列表
	 * @param key 待过滤的属性名称
	 * @param <T> 任意类型
	 * @param <V> 任意类型
	 * @return 过滤之后的属性值列表
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> List<V> filter(List<Map<String, T>> list, String key) {
		if (list == null || list.isEmpty())
			return null;
		List<V> result = new ArrayList<>();
		for (Map<String, ?> map : list) {
			Object value = MapUtils.getObject(map, key);
			if (value != null) {
				result.add((V) value);
			}
		}
		return result;
	}

	/**
	 * 将列表转换成Key - Map 形式，方便取值，如果key值不唯一，则将会覆盖原值，谨慎使用
	 * 
	 * @param list 数据源
	 * @param key 作为Key的属性名称
	 * @param <T> 任意类型
	 * @return Map
	 */
	public static <T> Map<String, Map<String, T>> keyMap(List<Map<String, T>> list, String key) {
		Map<String, Map<String, T>> result = new HashMap<>();
		if (list == null || list.isEmpty())
			return result;
		for (Map<String, T> map : list) {
			String value = MapUtils.getString(map, key);
			if (StringUtils.isNotBlank(value)) {
				result.put(value, map);
			}
		}
		return result;
	}

	/**
	 * 将列表转换成Key - List&lt;Map&lt;String,Object&gt;&gt; 形式，方便取值,如果key值重复则在列表中添加
	 * 
	 * @param list 数据源
	 * @param key 作为Key的属性名称
	 * @param <T> 任意类型
	 * @return Map
	 */
	public static <T> Map<String, List<Map<String, T>>> keyList(List<Map<String, T>> list, String key) {
		return keyList(list, key, null);
	}

	/**
	 * 将列表转换成Key - List&lt;Map&lt;String,Object&gt;&gt; 形式，方便取值,如果key值重复则在列表中添加,如果Key值取不到或者为空则使用默认值
	 * 
	 * @param list 列表
	 * @param key 作为key 的属性名称
	 * @param defaultKey Key 的默认值
	 * @param <T> 任意类型
	 * @return Map
	 */
	public static <T> Map<String, List<Map<String, T>>> keyList(List<Map<String, T>> list, String key,
			String defaultKey) {
		Map<String, List<Map<String, T>>> result = new LinkedHashMap<>();
		if (list == null || list.isEmpty())
			return result;
		for (Map<String, T> map : list) {
			String keyValue = MapUtils.getString(map, key, defaultKey);
			if (result.containsKey(keyValue)) {
				result.get(keyValue).add(map);
			} else {
				List<Map<String, T>> tempList = new ArrayList<>();
				tempList.add(map);
				result.put(keyValue, tempList);
			}
		}
		return result;
	}
}

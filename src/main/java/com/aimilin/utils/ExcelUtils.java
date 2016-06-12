package com.aimilin.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Workbook;

import com.aimilin.bean.ExcelResult;
import com.aimilin.bean.ExcelType;
import com.aimilin.exception.ExcelWriteException;

/**
 * Excel操作工具类
 * 
 * @author LiuJunGuang
 * @date 2016年5月19日下午4:47:22
 */
public class ExcelUtils {

	/**
	 * 根据Excel文件输入流 ，获取Excel工作薄对象
	 * 
	 * @param is 输入流
	 * @return Workbook
	 * @date 2016年3月30日下午5:18:47
	 */
	public static Workbook getWorkbook(InputStream is) {
		return ExcelReadUtils.getWorkbook(is);
	}

	/**
	 * 根据Excel文件地址 获取Excel工作薄对象
	 * 
	 * @param filePath excel文件地址
	 * @return Workbook
	 * @date 2016年3月30日下午12:07:08
	 */
	public static Workbook getWorkbook(String filePath) {
		return ExcelReadUtils.getWorkbook(filePath);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * <b>注意:</b>包含标题，不包含空行
	 * 
	 * @param is Excel文件直接数组
	 * @return ExcelResult
	 * @date 2016年5月19日下午6:58:51
	 */
	public static ExcelResult read(byte[] bytes) {
		return read(bytes, true);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * <b>注意:</b>不包含空行
	 * 
	 * @author LiuJunGuang
	 * @param is Excel文件直接数组
	 * @param includeHeader 是否包含标题，true 包含，false 忽略标题
	 * @return ExcelResult
	 * @date 2016年5月19日下午6:58:51
	 */
	public static ExcelResult read(byte[] bytes, boolean includeHeader) {
		return read(bytes, includeHeader, false);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * 
	 * @author LiuJunGuang
	 * @param bytes Excel文件字节数组
	 * @param includeHeader 是否包含标题，true 包含，false 忽略标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return ExcelResult
	 * @date 2016年5月19日下午7:00:47
	 */
	public static ExcelResult read(byte[] bytes, boolean includeHeader, boolean includeBlankLine) {
		return ExcelReadUtils.read(bytes, includeHeader, includeBlankLine);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param bytes Excel 文件二进制数组
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(byte[] bytes, Class<T> clazz) {
		return read2Bean(bytes, clazz);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param bytes Excel 文件二进制数组
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetIndex 开始为0
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(byte[] bytes, Class<T> clazz, int sheetIndex) {
		return read2Bean(bytes, clazz, sheetIndex);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param bytes Excel 文件二进制数组
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(byte[] bytes, Class<T> clazz, String sheetName) {
		return read2Bean(bytes, clazz, sheetName);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * <b>注意:</b>包含标题，不包含空行
	 * 
	 * @param is Excel文件输入流
	 * @return ExcelResult
	 * @date 2016年5月19日下午6:58:51
	 */
	public static ExcelResult read(InputStream is) {
		return read(is, true);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * <b>注意:</b>不包含空行
	 * 
	 * @author LiuJunGuang
	 * @param is Excel文件输入流
	 * @param includeHeader 是否包含标题，true 包含，false 忽略标题
	 * @return ExcelResult
	 * @date 2016年5月19日下午6:58:51
	 */
	public static ExcelResult read(InputStream is, boolean includeHeader) {
		return read(is, includeHeader, false);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * 
	 * @author LiuJunGuang
	 * @param is Excel文件输入流
	 * @param includeHeader 是否包含标题，true 包含，false 忽略标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return ExcelResult
	 * @date 2016年5月19日下午7:00:47
	 */
	public static ExcelResult read(InputStream is, boolean includeHeader, boolean includeBlankLine) {
		return ExcelReadUtils.read(is, includeHeader, includeBlankLine);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param is Excel 文件输入流
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(InputStream is, Class<T> clazz) {
		return read2Bean(is, clazz);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param is Excel 文件输入流
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetIndex 开始为0
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(InputStream is, Class<T> clazz, int sheetIndex) {
		return read2Bean(is, clazz, sheetIndex);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param is Excel 文件输入流
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(InputStream is, Class<T> clazz, String sheetName) {
		return read2Bean(is, clazz, sheetName);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * <b>注意:</b>包含标题，不包含空行
	 * 
	 * @author LiuJunGuang
	 * @param filePath Excel文件路径
	 * @return ExcelResult
	 * @date 2016年5月19日下午6:58:51
	 */
	public static ExcelResult read(String filePath) {
		return read(filePath, true);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * <b>注意:</b>不包含空行
	 * 
	 * @author LiuJunGuang
	 * @param filePath Excel文件路径
	 * @param includeHeader 是否包含标题，true 包含，false 忽略标题
	 * @return ExcelResult
	 * @date 2016年5月19日下午6:58:51
	 */
	public static ExcelResult read(String filePath, boolean includeHeader) {
		return read(filePath, includeHeader, false);
	}

	/**
	 * 读取Excel数据到结果列表中，支持2003和2007格式读取<br>
	 * 
	 * @author LiuJunGuang
	 * @param filePath Excel文件路径
	 * @param includeHeader 是否包含标题，true 包含，false 忽略标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return ExcelResult
	 * @date 2016年5月19日下午7:00:47
	 */
	public static ExcelResult read(String filePath, boolean includeHeader, boolean includeBlankLine) {
		return ExcelReadUtils.read(filePath, includeHeader, includeBlankLine);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param filePath 文件路径
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(String filePath, Class<T> clazz) {
		return read2Bean(filePath, clazz);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param filePath 文件路径
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetIndex 开始为0
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(String filePath, Class<T> clazz, int sheetIndex) {
		return read2Bean(filePath, clazz, sheetIndex);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param filePath 文件路径
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read(String filePath, Class<T> clazz, String sheetName) {
		return read2Bean(filePath, clazz, sheetName);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param bytes Excel 文件二进制数组
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(byte[] bytes, Class<T> clazz) {
		ExcelResult result = read(bytes);
		return BeanUtils.toBean(result, clazz);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param bytes Excel 文件二进制数组
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetIndex 开始为0
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(byte[] bytes, Class<T> clazz, int sheetIndex) {
		ExcelResult result = read(bytes);
		return BeanUtils.toBean(result, clazz, sheetIndex);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param bytes Excel 文件二进制数组
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(byte[] bytes, Class<T> clazz, String sheetName) {
		ExcelResult result = read(bytes);
		return BeanUtils.toBean(result, clazz, sheetName);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param is Excel 文件输入流
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(InputStream is, Class<T> clazz) {
		ExcelResult result = read(is);
		return BeanUtils.toBean(result, clazz);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param is Excel 文件输入流
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetIndex 开始为0
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(InputStream is, Class<T> clazz, int sheetIndex) {
		ExcelResult result = read(is);
		return BeanUtils.toBean(result, clazz, sheetIndex);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param is Excel 文件输入流
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(InputStream is, Class<T> clazz, String sheetName) {
		ExcelResult result = read(is);
		return BeanUtils.toBean(result, clazz, sheetName);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param filePath 文件路径
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(String filePath, Class<T> clazz) {
		ExcelResult result = read(filePath);
		return BeanUtils.toBean(result, clazz);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param filePath 文件路径
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetIndex 开始为0
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(String filePath, Class<T> clazz, int sheetIndex) {
		ExcelResult result = read(filePath);
		return BeanUtils.toBean(result, clazz, sheetIndex);
	}

	/**
	 * 读取Excel数据到JavaBean对象中
	 * 
	 * @author LiuJunGuang
	 * @param filePath 文件路径
	 * @param clazz JavaBean对象，注意该对象可以使用
	 * @param sheetName
	 * @return
	 * @date 2016年6月2日下午5:00:15
	 */
	public static <T> List<T> read2Bean(String filePath, Class<T> clazz, String sheetName) {
		ExcelResult result = read(filePath);
		return BeanUtils.toBean(result, clazz, sheetName);
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取，默认第一行为标题行，返回数据不包含第一行.<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param bytes Excel 文件二进制数组
	 * @return 去除第一行之后的数据列表，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:50:41
	 */
	public static List<List<String>> read2List(byte[] bytes) {
		return read2List(bytes, false);
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param bytes Excel 文件二进制数组
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @return 数据结果集，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:57:47
	 */
	public static List<List<String>> read2List(byte[] bytes, boolean includeHeader) {
		return read2List(bytes, includeHeader, false);
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param bytes Excel 文件二进制数组
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return 数据结果集，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:57:47
	 */
	public static List<List<String>> read2List(byte[] bytes, boolean includeHeader, boolean includeBlankLine) {
		ExcelResult result = read(bytes, includeHeader, includeBlankLine);
		return result == null ? null : result.toList();
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取，默认第一行为标题行，返回数据不包含第一行.<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param is Excel 文件输入流
	 * @return 去除第一行之后的数据列表，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:50:41
	 */
	public static List<List<String>> read2List(InputStream is) {
		return read2List(is, false);
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param is Excel 文件输入流
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @return 数据结果集，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:57:47
	 */
	public static List<List<String>> read2List(InputStream is, boolean includeHeader) {
		return read2List(is, includeHeader, false);
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param is Excel 文件输入流
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return 数据结果集，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:57:47
	 */
	public static List<List<String>> read2List(InputStream is, boolean includeHeader, boolean includeBlankLine) {
		ExcelResult result = read(is, includeHeader, includeBlankLine);
		return result == null ? null : result.toList();
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取，默认第一行为标题行，返回数据不包含第一行.<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param filePath Excel 文件路径
	 * @return 去除第一行之后的数据列表，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:50:41
	 */
	public static List<List<String>> read2List(String filePath) {
		return read2List(filePath, false);
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param filePath Excel 文件路径地址
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @return 数据结果集，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:57:47
	 */
	public static List<List<String>> read2List(String filePath, boolean includeHeader) {
		return read2List(filePath, includeHeader, false);
	}

	/**
	 * 读取Excel数据到list列表中，支持2003和2007格式读取<br>
	 * <b>注意：</b>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * 
	 * @param filePath Excel 文件路径地址
	 * @param includeHeader 是否包含第一行标题头，true - 包含第一行标题，false - 不包含第一行标题
	 * @param includeBlankLine 是否包含空行 true - 包含，false - 忽略空行
	 * @return 数据结果集，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:57:47
	 */
	public static List<List<String>> read2List(String filePath, boolean includeHeader, boolean includeBlankLine) {
		ExcelResult result = read(filePath, includeHeader, includeBlankLine);
		return result == null ? null : result.toList();
	}

	/**
	 * 读取Excel数据到Map列表中，支持2003和2007格式读取，默认第一行为标题行，返回数据不包含第一行.<br>
	 * <b>注意：</b>
	 * <ul>
	 * <li>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * <li>
	 * <li>Map key-标题，value 值，默认第一行为标题</li>
	 * </ul>
	 * 
	 * @param bytes Excel 文件二进制数组
	 * @return 去除第一行之后的数据列表，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:50:41
	 */
	public static List<Map<String, String>> read2Map(byte[] bytes) {
		ExcelResult result = read(bytes, true, false);
		return result == null ? null : result.toMap();
	}

	/**
	 * 读取Excel数据到Map列表中，支持2003和2007格式读取，默认第一行为标题行，返回数据不包含第一行.<br>
	 * <b>注意：</b>
	 * <ul>
	 * <li>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * <li>
	 * <li>Map key-标题，value 值，默认第一行为标题</li>
	 * </ul>
	 * 
	 * @param is Excel 文件输入流
	 * @return 去除第一行之后的数据列表，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:50:41
	 */
	public static List<Map<String, String>> read2Map(InputStream is) {
		ExcelResult result = read(is, true, false);
		return result == null ? null : result.toMap();
	}

	/**
	 * 读取Excel数据到Map列表中，支持2003和2007格式读取，默认第一行为标题行，返回数据不包含第一行.<br>
	 * <b>注意：</b>
	 * <ul>
	 * <li>如果Excel单元格无内容，或者读取单元格失败则默认为空字符串
	 * <li>
	 * <li>Map key-标题，value 值，默认第一行为标题</li>
	 * </ul>
	 * 
	 * @param filePath Excel 文件路径
	 * @return 去除第一行之后的数据列表，如果为空则返回null
	 * @throws IOException 文件操作错误异常将抛出该异常
	 * @date 2016年5月19日下午4:50:41
	 */
	public static List<Map<String, String>> read2Map(String filePath) {
		ExcelResult result = read(filePath, true, false);
		return result == null ? null : result.toMap();
	}

	/**
	 * 将ExcelResult 数据集合Excel 写入到字节数组中，Excel 默认使用2007格式
	 * 
	 * @author LiuJunGuang
	 * @param excelResult 数据集
	 * @return
	 * @date 2016年6月8日下午2:47:06
	 */
	public static byte[] write(ExcelResult excelResult) {
		return ExcelWriteUtils.write(excelResult, ExcelType.XLSX);
	}

	/**
	 * 将ExcelResult 数据集合Excel 写入到字节数组中
	 * 
	 * @author LiuJunGuang
	 * @param excelResult 数据集
	 * @param excelType Excel 枚举类型
	 * @return Excel 文件字节数组
	 * @date 2016年6月8日下午2:46:11
	 */
	public static byte[] write(ExcelResult excelResult, ExcelType excelType) {
		return ExcelWriteUtils.write(excelResult, excelType);
	}

	/**
	 * 将ExcelResult 数据集合写入到输出流中，默认使用Excel 2007 格式
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel数据结果集
	 * @param os 输出流
	 * @date 2016年6月8日下午2:45:45
	 */
	public static void write(ExcelResult excelResult, OutputStream os) {
		ExcelWriteUtils.write(excelResult, os, ExcelType.XLSX);
	}

	/**
	 * 将ExcelResult 数据集合写入到输出流中
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel数据结果集
	 * @param os 输出流
	 * @param excelType Excel 枚举类型
	 * @date 2016年6月8日下午2:43:51
	 */
	public static void write(ExcelResult excelResult, OutputStream os, ExcelType excelType) {
		ExcelWriteUtils.write(excelResult, os, excelType);
	}

	/**
	 * 将ExcelResult 数据集合写入到指定文件路径中，自动创建文件名称,默认为Excel2007 格式
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel数据结果集
	 * @param filePath 指定的Excel文件存放路径，路径中不要包含文件名称
	 * @return 文件保存路径
	 * @date 2016年6月8日下午2:43:51
	 */
	public static String write(ExcelResult excelResult, String filePath) {
		return write(excelResult, filePath, ExcelType.XLSX, true);
	}

	/**
	 * 将ExcelResult 数据集合写入到指定文件路径中，自动创建文件名称
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel数据结果集
	 * @param filePath 指定的Excel文件存放路径，路径中不要包含文件名称
	 * @param excelType 生成的Excel文件类型
	 * @return 文件保存路径
	 * @date 2016年6月8日下午2:43:51
	 */
	public static String write(ExcelResult excelResult, String filePath, ExcelType excelType) {
		return write(excelResult, filePath, excelType, true);
	}

	/**
	 * 将ExcelResult 数据集合写入到指定文件路径中，如果不创建文件名称则路径中必须包含文件名称
	 * 
	 * @author LiuJunGuang
	 * @param excelResult Excel数据结果集
	 * @param filePath 指定的Excel文件存放路径
	 * @param excelType 生成的Excel文件类型
	 * @param createFileName 是否自动创建文件名称，文件名为当前时间毫秒加文件类型后缀，true 自动创建，否则不自动创建
	 * @return 文件保存路径
	 * @date 2016年6月8日下午2:43:51
	 */
	public static String write(ExcelResult excelResult, String filePath, ExcelType excelType, boolean createFileName) {
		Objects.requireNonNull(filePath);
		try {
			String fileAbstractPath = filePath;
			if (createFileName) {
				fileAbstractPath = filePath + File.separator + ExcelWriteUtils.getFileName(excelType);
			}
			FileOutputStream fos = new FileOutputStream(new File(fileAbstractPath));
			ExcelWriteUtils.write(excelResult, fos, excelType);
			return fileAbstractPath;
		} catch (FileNotFoundException e) {
			throw new ExcelWriteException(e.getMessage());
		}
	}

	/**
	 * 将对象列表写入到Excel字节流中，默认使用Excel2007格式。直接忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @return Excel 字节流
	 * @date 2016年6月10日下午5:03:02
	 */
	public static <T> byte[] write(List<T> list) {
		return write(list, ExcelType.XLSX, true);
	}

	/**
	 * 将对象列表写入到指定Excel类型字节流中，直接忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param excelType Excel文件类型
	 * @return Excel 字节流
	 * @date 2016年6月10日下午5:03:40
	 */
	public static <T> byte[] write(List<T> list, ExcelType excelType) {
		return write(list, excelType, true);
	}

	/**
	 * 将对象列表写入到指定Excel类型字节流中，并设置是否忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param excelType Excel文件类型
	 * @param ignoreException true 忽略转换异常，false 抛出异常
	 * @return Excel 字节流
	 * @date 2016年6月10日下午5:04:15
	 */
	public static <T> byte[] write(List<T> list, ExcelType excelType, boolean ignoreException) {
		ExcelResult excelResult = BeanUtils.toResult(list, ignoreException);
		return write(excelResult, excelType);
	}

	/**
	 * 将对象列表写入到输出流中,默认使用Excel2007格式，直接忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param os 输出流
	 * @date 2016年6月10日下午5:05:22
	 */
	public static <T> void write(List<T> list, OutputStream os) {
		write(list, os, ExcelType.XLSX, true);
	}

	/**
	 * 将对象列表写入到输出流中,使用指定Excel格式，直接忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param os 输出流
	 * @param excelType 指定输出的Excel格式
	 * @date 2016年6月10日下午5:05:48
	 */
	public static <T> void write(List<T> list, OutputStream os, ExcelType excelType) {
		write(list, os, excelType, true);
	}

	/**
	 * 将对象列表写入到输出流中,使用指定Excel格式，并可指定是否忽略转换异常
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param os 输出流
	 * @param excelType Excel文件类型
	 * @param ignoreException true 忽略转换异常，false 抛出转换异常
	 * @date 2016年6月10日下午5:06:36
	 */
	public static <T> void write(List<T> list, OutputStream os, ExcelType excelType, boolean ignoreException) {
		ExcelResult excelResult = BeanUtils.toResult(list, ignoreException);
		write(excelResult, os, excelType);
	}

	/**
	 * 将对象列表输出到指定文件夹中，文件名称自动生成，文件格式为Excel2007格式，忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param filePath 输出文件路径
	 * @return 文件全名
	 * @date 2016年6月10日下午5:08:10
	 */
	public static <T> String write(List<T> list, String filePath) {
		return write(list, filePath, ExcelType.XLSX, true, true);
	}

	/**
	 * 将对象列表输出到指定文件夹中，文件名称自动生成，忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param filePath 输出文件路径
	 * @param excelType Excel文件格式
	 * @return 文件全名
	 * @date 2016年6月10日下午5:09:07
	 */
	public static <T> String write(List<T> list, String filePath, ExcelType excelType) {
		return write(list, filePath, excelType, true, true);
	}

	/**
	 * 将对象列表输出到指定文件夹中，忽略异常信息
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param filePath 输出文件路径
	 * @param excelType Excel文件格式
	 * @param createFileName true 自动创建文件名称，false - 路径中指定文件名称和后缀
	 * @return 文件全名称
	 * @date 2016年6月10日下午5:09:35
	 */
	public static <T> String write(List<T> list, String filePath, ExcelType excelType, boolean createFileName) {
		return write(list, filePath, excelType, createFileName, true);
	}

	/**
	 * 将对象列表输出到指定文件夹中
	 * 
	 * @author LiuJunGuang
	 * @param list 对象列表
	 * @param filePath 文件路径
	 * @param excelType Excel文件格式
	 * @param createFileName 是否创建文件名称，true - 自动创建文件名称，false 路径中指定文件名称
	 * @param ignoreException true - 忽略转换异常信息，false - 抛出转换异常信息
	 * @return 文件全名称
	 * @date 2016年6月10日下午5:10:30
	 */
	public static <T> String write(List<T> list, String filePath, ExcelType excelType, boolean createFileName,
			boolean ignoreException) {
		ExcelResult excelResult = BeanUtils.toResult(list, ignoreException);
		return write(excelResult, filePath, excelType, createFileName);
	}

	/**
	 * 将List对象写入到Excel 字节数组中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param dataList Excel数据
	 * @return
	 * @date 2016年6月10日下午11:13:28
	 */
	public static byte[] write4List(List<List<String>> dataList) {
		ExcelResult excelResult = ExcelResultUtils.toResult(dataList, "sheet");
		return write(excelResult);
	}

	/**
	 * 将List对象写入到Excel 字节数组中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param dataList Excel数据
	 * @param heads Excel标题
	 * @return
	 * @date 2016年6月10日下午11:14:05
	 */
	public static byte[] write4List(List<List<String>> dataList, List<String> heads) {
		ExcelResult excelResult = ExcelResultUtils.toResult(dataList, "sheet", heads);
		return write(excelResult);
	}

	/**
	 * 将数据列表写入到输出六中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param os 输出流
	 * @date 2016年6月10日下午11:14:25
	 */
	public static void write4List(List<List<String>> dataList, List<String> heads, OutputStream os) {
		write4List(dataList, heads, "sheet", os, ExcelType.XLSX);
	}

	/**
	 * 将数据列表写入到文件中，文件制定路径，文件名自动生成，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:15:04
	 */
	public static String write4List(List<List<String>> dataList, List<String> heads, String filePath) {
		return write4List(dataList, heads, "sheet", filePath);
	}

	/**
	 * 将数据列表写入到Excel字节数组中
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param sheetName ExcelSheet名称
	 * @param excelType Excel类型
	 * @return 字节数组
	 * @date 2016年6月10日下午11:16:09
	 */
	public static byte[] write4List(List<List<String>> dataList, List<String> heads, String sheetName,
			ExcelType excelType) {
		ExcelResult excelResult = ExcelResultUtils.toResult(dataList, sheetName, heads);
		return write(excelResult, excelType);
	}

	/**
	 * 将数据列表写入到输出流中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param sheetName sheet名称
	 * @param os 输出流
	 * @date 2016年6月10日下午11:16:55
	 */
	public static void write4List(List<List<String>> dataList, List<String> heads, String sheetName, OutputStream os) {
		write4List(dataList, heads, sheetName, os, ExcelType.XLSX);
	}

	/**
	 * 将数据列表写入到输出流中
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param sheetName sheet名称
	 * @param os 输出流
	 * @param excelType Excel格式
	 * @date 2016年6月10日下午11:17:43
	 */
	public static void write4List(List<List<String>> dataList, List<String> heads, String sheetName, OutputStream os,
			ExcelType excelType) {
		ExcelResult excelResult = ExcelResultUtils.toResult(dataList, sheetName, heads);
		write(excelResult, os, excelType);
	}

	/**
	 * 将数据列表写入到文件路径中，默认为Excel2007格式，文件名称自动创建
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param sheetName Sheet名称
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:18:19
	 */
	public static String write4List(List<List<String>> dataList, List<String> heads, String sheetName,
			String filePath) {
		return write4List(dataList, heads, sheetName, filePath, ExcelType.XLSX);
	}

	/**
	 * 将数据列表写入到文件路径中，文件名称自动创建
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param sheetName Sheet名称
	 * @param filePath 文件路径
	 * @param excelType Excel 文件格式
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:19:22
	 */
	public static String write4List(List<List<String>> dataList, List<String> heads, String sheetName, String filePath,
			ExcelType excelType) {
		return write4List(dataList, heads, sheetName, filePath, excelType, true);
	}

	/**
	 * 将数据列表写入到文件路径中
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param heads Excel标题
	 * @param sheetName Sheet名称
	 * @param filePath 文件路径
	 * @param excelType Excel 文件格式
	 * @param createFileName true 自动创建文件名称，false - 路径中指定文件名称
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:19:59
	 */
	public static String write4List(List<List<String>> dataList, List<String> heads, String sheetName, String filePath,
			ExcelType excelType, boolean createFileName) {
		ExcelResult excelResult = ExcelResultUtils.toResult(dataList, sheetName, heads);
		return write(excelResult, filePath, excelType, createFileName);
	}

	/**
	 * 将数据列表写入到输出流中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param os 输出流
	 * @date 2016年6月10日下午11:20:55
	 */
	public static void write4List(List<List<String>> dataList, OutputStream os) {
		write4List(dataList, null, "sheet", os, ExcelType.XLSX);
	}

	/**
	 * 将数据列表写入到指定文件路径，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param dataList 数据列表
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:21:26
	 */
	public static String write4List(List<List<String>> dataList, String filePath) {
		return write4List(dataList, null, filePath);
	}

	/**
	 * 将数据列表转换成Excel字节数组，默认为Excel2007格式，并且Map中的属性名称和heads一一对应
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题，Map中的Key和Heads一一对应
	 * @return Excel字节数组
	 * @date 2016年6月10日下午11:49:52
	 */
	public static <T> byte[] write4Map(List<Map<String, T>> mapList, List<String> heads) {
		return write4Map(mapList, heads, heads);
	}

	/**
	 * 将数据列表转换成Excel字节数组，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @return Excel字节数组
	 * @date 2016年6月10日下午11:49:55
	 */
	public static <T> byte[] write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props) {
		return write4Map(mapList, heads, props, "sheet", "", ExcelType.XLSX);
	}

	/**
	 * 将数据列表转换成Excel输出流，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param os Excel输出流
	 * @date 2016年6月10日下午11:49:57
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			OutputStream os) {
		write4Map(mapList, heads, props, "sheet", os);
	}

	/**
	 * 将数据列表转换成Excel文件，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param filePath Excel文件路径，文件名称和格式会自动生成
	 * @return 文件全名称
	 * @date 2016年6月10日下午11:49:59
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String filePath) {
		return write4Map(mapList, heads, props, "sheet", filePath);
	}

	/**
	 * 将数据列表转换成Excel文件输出流，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName Sheet名称
	 * @param os Excel文件输出流
	 * @date 2016年6月10日下午11:50:01
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, OutputStream os) {
		write4Map(mapList, heads, props, sheetName, "", os);
	}

	/**
	 * 将数据列表转换成Excel文件中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName sheet名称
	 * @param filePath 文件路径，文件名称和格式会自动生成
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:50:04
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, String filePath) {
		return write4Map(mapList, heads, props, sheetName, "", filePath);
	}

	/**
	 * 将数据列表转换成Excel字节数组，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName sheet名称
	 * @param defaultValue 默认值，如果Map中没有取到值则使用默认值
	 * @param excelType Excel类型
	 * @return Excel字节数组
	 * @date 2016年6月10日下午11:50:06
	 */
	public static <T> byte[] write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, String defaultValue, ExcelType excelType) {
		ExcelResult excelResult = ExcelResultUtils.toResult(mapList, heads, props, defaultValue, sheetName);
		return write(excelResult, excelType);
	}

	/**
	 * 将数据列表转换成Excel输出流，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName sheet名称
	 * @param defaultValue 默认值，如果Map中没有取到值则使用默认值
	 * @param os Excel输出流
	 * @date 2016年6月10日下午11:50:09
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, String defaultValue, OutputStream os) {
		write4Map(mapList, heads, props, sheetName, defaultValue, os, ExcelType.XLSX);
	}

	/**
	 * 将数据列表转换成Excel输出流
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName sheet名称
	 * @param defaultValue 默认值，如果Map中没有取到值则使用默认值
	 * @param os Excel输出流
	 * @param excelType Excel格式
	 * @date 2016年6月10日下午11:50:11
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, String defaultValue, OutputStream os, ExcelType excelType) {
		ExcelResult excelResult = ExcelResultUtils.toResult(mapList, heads, props, defaultValue, sheetName);
		write(excelResult, os, excelType);
	}

	/**
	 * 将数据列表转换成Excel文件，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName sheet名称
	 * @param defaultValue 默认值，如果Map中没有取到值则使用默认值
	 * @param filePath 文件路径，不包含文件名称
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:50:13
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, String defaultValue, String filePath) {
		return write4Map(mapList, heads, props, sheetName, defaultValue, filePath, ExcelType.XLSX);
	}

	/**
	 * 将数据列表转换成Excel文件
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName sheet名称
	 * @param defaultValue 默认值，如果Map中没有取到值则使用默认值
	 * @param filePath 文件路径，不能包含文件名称
	 * @param excelType Excel文件格式
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:50:15
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, String defaultValue, String filePath, ExcelType excelType) {
		return write4Map(mapList, heads, props, sheetName, defaultValue, filePath, excelType, true);
	}

	/**
	 * 将数据列表转换成Excel文件
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param props Map中属性名称和Heads中的对应关系，要求和heads中的属性顺序对应
	 * @param sheetName sheet名称
	 * @param defaultValue 默认值，如果Map中没有取到值则使用默认值
	 * @param filePath 文件路径
	 * @param excelType Excel文件路径
	 * @param createFileName 是否创建文件名称 true - 自动创建文件名称，false - 路径中需要指定文件名称和格式
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:50:17
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, List<String> heads, List<String> props,
			String sheetName, String defaultValue, String filePath, ExcelType excelType, boolean createFileName) {
		ExcelResult excelResult = ExcelResultUtils.toResult(mapList, heads, props, defaultValue, sheetName);
		return write(excelResult, filePath, excelType, createFileName);
	}

	/**
	 * 将数据列表写入到Excel 输出流中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param os Excel输出流
	 * @date 2016年6月10日下午11:50:20
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, List<String> heads, OutputStream os) {
		write4Map(mapList, heads, heads, "sheet", os);
	}

	/**
	 * 将数据列表写入到Excel 文件中，默认为Excel2007格式，文件名称和格式自动创建
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:50:22
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, List<String> heads, String filePath) {
		return write4Map(mapList, heads, heads, filePath);
	}

	/**
	 * 将数据列表写入到Excel 文件中，默认为Excel2007格式，文件名称和格式自动创建
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param heads Excel标题
	 * @param sheetName sheet名称
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月10日下午11:50:22
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, List<String> heads, String sheetName,
			String filePath) {
		return write4Map(mapList, heads, heads, sheetName, filePath);
	}

	/**
	 * 将数据列表写入到Excel 文件中
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param defaultValue 取不到值时默认值
	 * @param sheetName sheet名称
	 * @param filePath 文件路径
	 * @param excelType Excel文件类型
	 * @param createFileName true- 自动创建文件名称，false 路径中指定文件名称
	 * @return 文件全路径
	 * @date 2016年6月11日下午4:45:36
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String defaultValue,
			String sheetName, String filePath, ExcelType excelType, boolean createFileName) {
		ExcelResult excelResult = ExcelResultUtils.toResult(mapList, head2Prop, defaultValue, sheetName);
		return write(excelResult, filePath, excelType, createFileName);
	}

	/**
	 * 将数据列表写入到Excel 文件中，自动创建文件名称
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param defaultValue 取不到值时默认值
	 * @param sheetName sheet名称
	 * @param filePath 文件路径
	 * @param excelType Excel文件类型
	 * @return 文件全路径
	 * @date 2016年6月11日下午4:45:39
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String defaultValue,
			String sheetName, String filePath, ExcelType excelType) {
		return write4Map(mapList, head2Prop, defaultValue, sheetName, filePath, excelType, true);
	}

	/**
	 * 将数据列表写入到Excel 文件中，默认为Excel2007格式，文件名称和格式自动创建
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param defaultValue 取不到值时默认值
	 * @param sheetName sheet名称
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月11日下午4:45:41
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String defaultValue,
			String sheetName, String filePath) {
		return write4Map(mapList, head2Prop, defaultValue, sheetName, filePath, ExcelType.XLSX, true);
	}

	/**
	 * 将数据列表写入到Excel 文件中，默认为Excel2007格式，文件名称和格式自动创建
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param sheetName sheet名称
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月11日下午4:45:43
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String sheetName,
			String filePath) {
		return write4Map(mapList, head2Prop, "", sheetName, filePath);
	}

	/**
	 * 将数据列表写入到Excel 文件中，默认为Excel2007格式，文件名称和格式自动创建
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param filePath 文件路径
	 * @return 文件全路径
	 * @date 2016年6月11日下午4:45:45
	 */
	public static <T> String write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String filePath) {
		return write4Map(mapList, head2Prop, "", "sheet", filePath);
	}

	/**
	 * 将数据列表写入到输出流中
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param defaultValue 取不到值时默认值
	 * @param sheetName sheet名称
	 * @param os 输出流
	 * @param excelType Excel文件格式
	 * @date 2016年6月11日下午4:45:47
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String defaultValue,
			String sheetName, OutputStream os, ExcelType excelType) {
		ExcelResult excelResult = ExcelResultUtils.toResult(mapList, head2Prop, defaultValue, sheetName);
		write(excelResult, os, excelType);
	}

	/**
	 * 将数据列表写入到Excel 输出流中
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param defaultValue 取不到值时默认值
	 * @param sheetName sheet名称
	 * @param os 输出流
	 * @date 2016年6月11日下午4:45:49
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String defaultValue,
			String sheetName, OutputStream os) {
		write4Map(mapList, head2Prop, defaultValue, sheetName, os, ExcelType.XLSX);
	}

	/**
	 * 将数据列表写入到Excel 文件中，默认为Excel2007格式
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param sheetName sheet名称
	 * @param os 输出流
	 * @date 2016年6月11日下午4:45:51
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String sheetName,
			OutputStream os) {
		write4Map(mapList, head2Prop, "", sheetName, os);
	}

	/**
	 * 将数据列表写入到Excel 文件中，默认为Excel2007格式，默认值为空字符串
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param os 输出流
	 * @date 2016年6月11日下午4:45:54
	 */
	public static <T> void write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, OutputStream os) {
		write4Map(mapList, head2Prop, "", os);
	}

	/**
	 * 将数据列表写入到字节数组中
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @param defaultValue 取不到值时默认值
	 * @param sheetName sheet名称
	 * @param excelType Excel格式
	 * @return
	 * @date 2016年6月11日下午4:45:56
	 */
	public static <T> byte[] write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop, String defaultValue,
			String sheetName, ExcelType excelType) {
		ExcelResult excelResult = ExcelResultUtils.toResult(mapList, head2Prop, defaultValue, sheetName);
		return write(excelResult, excelType);
	}

	/**
	 * 将列表数据写入到输出流中
	 * 
	 * @author LiuJunGuang
	 * @param mapList 数据列表
	 * @param head2Prop Excel标题和Map中属性对应关系，key - Excel标题，value - Map属性名称
	 * @return 字节数组
	 * @date 2016年6月11日下午4:45:58
	 */
	public static <T> byte[] write4Map(List<Map<String, T>> mapList, Map<String, String> head2Prop) {
		return write4Map(mapList, head2Prop, "", "sheet", ExcelType.XLSX);
	}

}

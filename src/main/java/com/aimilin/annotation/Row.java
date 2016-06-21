package com.aimilin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对应Excel 一列
 * 
 * @author LiuJunGuang
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Row {
	/**
	 * 列名称
	 * 
	 * <pre>
	 * 	注意:
	 *    1. 读取Excel时，value 属性对应列标题，以value属性有值，忽略index属性，value无值index 有值，则使用index索引，如果没有设置则使用属性名称
	 *    2. 写Excel数据时，value 属性对应列标题，index 对应列的索引位置
	 * </pre>
	 * 
	 * @author LiuJunGuang
	 * @return 字符串，默认为空字符串
	 */
	public String value() default "";

	/**
	 * 列索引，优先级次于列名称
	 * 
	 * <pre>
	 * 	注意：
	 *     1. 读取时，如果指定value则使用value，否则index指定就使用index，没有设置使用属性名称
	 *     2. 写入时，和value一起指定列名称和列排序位置，如果不指定使用value自然顺序排
	 * </pre>
	 * 
	 * @author LiuJunGuang
	 * @return int 索引号，默认为-1
	 */
	public int index() default -1;

	/**
	 * 类型转换字典数据
	 * 
	 * @author LiuJunGuang
	 * @return 字典数组
	 */
	public Dictionary[] dictionaries() default {};

}

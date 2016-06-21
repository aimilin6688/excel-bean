package com.aimilin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对应Excel Sheet
 * 
 * @author LiuJunGuang
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {
	/**
	 * Excel 中Sheet的名称，在读取时，如果value 有值，则只读取value指定的sheet，如果value无值index有值，则只读取index索引指定的sheet。<br>
	 * 如果都没有设定则默认读取所有sheet
	 * 
	 * 
	 * @author LiuJunGuang
	 * @return 字符串，默认为空字符串
	 */
	public String value() default "";

	/**
	 * excel 中sheet的索引值，读取时优先级次与value，写入时和value一起指定sheet的索引和名称
	 * 
	 * @author LiuJunGuang
	 * @return int 索引号，默认为-1
	 */
	public int index() default -1;

}

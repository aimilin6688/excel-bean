package com.aimilin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 * @author LiuJunGuang
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dictionary {
	/**
	 * 字典名称
	 * 
	 * @author LiuJunGuang
	 * @return 字符串
	 */
	public String name();

	/**
	 * 字典值
	 * 
	 * @author LiuJunGuang
	 * @return 字符串，字典值
	 */
	public String value();

}

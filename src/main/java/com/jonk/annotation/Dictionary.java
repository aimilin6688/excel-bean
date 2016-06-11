package com.jonk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author LiuJunGuang
 * @date 2016年6月10日下午2:47:15
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dictionary {
	/**
	 * 字典名称
	 * 
	 * @author LiuJunGuang
	 * @return
	 * @date 2016年6月10日下午2:52:42
	 */
	public String name();

	/**
	 * 字典值
	 * 
	 * @author LiuJunGuang
	 * @return
	 * @date 2016年6月10日下午2:52:49
	 */
	public String value();

}

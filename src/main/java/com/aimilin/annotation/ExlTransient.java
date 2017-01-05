package com.aimilin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否将字段写入到Excel中，默认不写入
 *
 * @author LiuJunGuang
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExlTransient {
	public boolean value() default true;
}

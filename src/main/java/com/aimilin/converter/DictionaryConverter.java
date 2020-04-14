package com.aimilin.converter;

/**
 * 字典转换器接口
 * 
 * @author aimil
 *
 */
public interface DictionaryConverter {

	/**
	 * 将字典名称转换成字典的值，一般在导入Excel时解析字段的名称为JavaBean对应的值，例如将 男 -》 1 <br>
	 * 注意：<b>如果属性不需要转换请返回原值</b>
	 * 
	 * @param propName 属性名称
	 * @param index 属性索引
	 * @param propValue 传入参数值
	 * @return String
	 */
	public String convert(String propName, int index, String propValue);
}

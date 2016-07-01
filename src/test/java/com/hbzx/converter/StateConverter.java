package com.hbzx.converter;

import com.aimilin.converter.DictionaryConverter;

public class StateConverter implements DictionaryConverter {

	@Override
	public String convert(String propName, int index, String propValue) {
		if ("state".equals(propName)) {
			switch (propValue) {
			case "1":
				return "启用";

			case "2":
				return "注销";

			case "启用":
				return "1";

			case "注销":
				return "2";
			}
		}
		return propValue;
	}

}

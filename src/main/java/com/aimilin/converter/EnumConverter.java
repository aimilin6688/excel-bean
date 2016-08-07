package com.aimilin.converter;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EnumConverter extends AbstractConverter {
	private static final Logger log = LoggerFactory.getLogger(EnumConverter.class);

	@Override
	protected String convertToString(final Object pValue) throws Throwable {
		return ((Enum) pValue).name();
	}

	@Override
	protected Object convertToType(final Class pType, final Object pValue) throws Throwable {
		final Class<? extends Enum> type = pType;
		try {
			return Enum.valueOf(type, pValue.toString());
		} catch (final IllegalArgumentException e) {
			log.warn("No enum value \"" + pValue + "\" for " + type.getName());
		}

		return null;
	}

	@Override
	protected Class getDefaultType() {
		return null;
	}

}
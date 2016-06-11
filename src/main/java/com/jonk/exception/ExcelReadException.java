package com.jonk.exception;

/**
 * Excel解析异常
 * 
 * @author LiuJunGuang
 * @date 2016年5月19日下午6:25:36
 */
public class ExcelReadException extends RuntimeException {

	/**
	 * @fields serialVersionUID
	 */
	private static final long serialVersionUID = 5088452947240555955L;

	public ExcelReadException() {
		super();
	}

	public ExcelReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExcelReadException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelReadException(String message) {
		super(message);
	}

	public ExcelReadException(Throwable cause) {
		super(cause);
	}

}

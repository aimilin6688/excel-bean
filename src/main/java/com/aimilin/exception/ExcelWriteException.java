package com.aimilin.exception;

/**
 * Excel写入异常
 * 
 * @author LiuJunGuang
 */
public class ExcelWriteException extends RuntimeException {
	/**
	 * @fields serialVersionUID
	 */
	private static final long serialVersionUID = -1534846305064569377L;

	public ExcelWriteException() {
		super();
	}

	public ExcelWriteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExcelWriteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelWriteException(String message) {
		super(message);
	}

	public ExcelWriteException(Throwable cause) {
		super(cause);
	}
}

package com.gc.exception;

public class CommonRuntimeException extends NestableRuntimeException {

	/**
	 * Constructs a new <code>NestableRuntimeException</code> without specified
	 * detail message.
	 */
	public CommonRuntimeException() {
		super();
	}

	/**
	 * Constructs a new <code>NestableRuntimeException</code> with specified
	 * detail message.
	 *
	 * @param msg the error message
	 */
	public CommonRuntimeException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a new <code>NestableRuntimeException</code> with specified
	 * nested <code>Throwable</code>.
	 *
	 * @param cause the exception or error that caused this exception to be
	 *              thrown
	 */
	public CommonRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new <code>NestableRuntimeException</code> with specified
	 * detail message and nested <code>Throwable</code>.
	 *
	 * @param msg   the error message
	 * @param cause the exception or error that caused this exception to be
	 *              thrown
	 */
	public CommonRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

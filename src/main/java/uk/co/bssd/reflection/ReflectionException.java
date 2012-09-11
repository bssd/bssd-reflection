package uk.co.bssd.reflection;

public class ReflectionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReflectionException(String message) {
		super(message);
	}
	
	public ReflectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
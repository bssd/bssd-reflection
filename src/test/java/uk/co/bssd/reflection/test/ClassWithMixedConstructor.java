package uk.co.bssd.reflection.test;

public class ClassWithMixedConstructor<T> {

	private final String stringValue;
	private final T genericValue;
	private final Long longValue;
	
	public ClassWithMixedConstructor(String stringValue, T genericValue, Long longValue) {
		this.stringValue = stringValue;
		this.genericValue = genericValue;
		this.longValue = longValue;
	}
	
	public String stringValue() {
		return this.stringValue;
	}
	
	public T genericValue() {
		return this.genericValue;
	}
	
	public Long longValue() {
		return this.longValue;
	}
}
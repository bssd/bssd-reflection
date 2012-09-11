package uk.co.bssd.reflection.test;

public class ClassWithStringConstructor {

	private final String value;
	
	public ClassWithStringConstructor(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
package uk.co.bssd.reflection.test;

public class ClassWithIntegerConstructor {

	private final Integer value;
	
	public ClassWithIntegerConstructor(Integer value) {
		this.value = value;
	}
	
	public Integer value() {
		return this.value;
	}
}
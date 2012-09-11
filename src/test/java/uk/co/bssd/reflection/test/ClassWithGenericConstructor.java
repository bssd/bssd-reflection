package uk.co.bssd.reflection.test;

public class ClassWithGenericConstructor<T> {

	private final T value;
	
	public ClassWithGenericConstructor(T value) {
		this.value = value;
	}
	
	public T value() {
		return this.value;
	}
}
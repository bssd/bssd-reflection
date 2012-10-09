package uk.co.bssd.reflection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ClassWrapperTest {

	@Test
	public void testForNameWithValidClassName() {
		assertThat(classWrapper(), is(notNullValue()));
	}

	@Test(expected = ReflectionException.class)
	public void testForNameWithInvalidClassName() {
		ClassWrapper.forName("wibblywoo");
	}
	
	@Test
	public void testWrapperReturnsCorrectNumberOfConstructors() {
		assertThat(constructors().size(), is(0));
	}
	
	private ConstructorWrappers constructors() {
		return classWrapper().constructors();
	}

	private ClassWrapper classWrapper() {
		return ClassWrapper.forName(ClassWrapper.class.getName());
	}
}
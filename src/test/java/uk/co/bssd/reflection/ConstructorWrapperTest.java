package uk.co.bssd.reflection;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import uk.co.bssd.reflection.test.ClassWithStringConstructor;

public class ConstructorWrapperTest {

	private static final String VALUE_STRING = "helloWorld";
	
	@Test
	public void testParametersForStringConstructor() {
		List<ParameterWrapper> parameters = parameters(ClassWithStringConstructor.class);
		assertThat(parameters.size(), is(1));
		
		ParameterWrapper parameter = parameters.get(0);
		assertThat(parameter.name(), is("value"));
		assertThat(parameter.classname(), is(String.class.getName()));
	}
	
	@Test
	public void testInstantiateClassViaConstructorWithStringParameter() {
		ConstructorWrapper constructor = constructor(ClassWithStringConstructor.class);
		Object newInstance = constructor.instantiate(VALUE_STRING);
		assertThat(newInstance, is(instanceOf(ClassWithStringConstructor.class)));
		assertThat(((ClassWithStringConstructor)newInstance).value(), is(VALUE_STRING));
	}
	
	private List<ParameterWrapper> parameters(Class<?> clazz) {
		return constructor(clazz).parameters();
	}
	
	private ConstructorWrapper constructor(Class<?> clazz) {
		return ClassWrapper.forName(clazz.getName()).constructors().iterator().next();
	}
}
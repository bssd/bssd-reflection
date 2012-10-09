package uk.co.bssd.reflection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ConstructorWrappersTest {

	private ClassWrapper classWrapper;
	
	@Before 
	public void before() {
		this.classWrapper = ClassWrapper.forClass(Double.class);
	}
	
	@Test
	public void testFindByConstructorArgumentTypes() {
		assertThat(constructorWrappers().findByArgumentTypes(Double.TYPE), is(notNullValue()));
	}
	
	private ConstructorWrappers constructorWrappers() {
		return this.classWrapper.constructors();
	}
}
package uk.co.bssd.reflection;

import org.apache.commons.lang3.StringUtils;

public class ParameterWrapper {

	private final String name;
	private final String classname;
	private final String genericTypeName;
	
	public ParameterWrapper(String name, String classname) {
		this(name, classname, null);
	}
	
	public ParameterWrapper(String name, String classname, String genericTypeName) {
		this.name = name;
		this.classname = classname;
		this.genericTypeName = genericTypeName;
	}
	
	public String name() {
		return this.name;
	}
	
	public String classname() {
		return this.classname;
	}
	
	public boolean isGenericType() {
		return StringUtils.isNotBlank(this.genericTypeName);
	}
	
	public String genericTypeName() {
		return this.genericTypeName;
	}

	public Class<?> getType() {
		if (this.classname == Double.TYPE.getName()) {
			return Double.TYPE;
		}
		return ClassWrapper.forName(this.classname).clazz();
	}
}
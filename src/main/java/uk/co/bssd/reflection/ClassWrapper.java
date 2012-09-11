package uk.co.bssd.reflection;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class ClassWrapper {

	public static ClassWrapper forName(String name) {
		try {
			Class<?> c = Class.forName(name);
			return new ClassWrapper(c);
		} catch (ClassNotFoundException e) {
			throw new ReflectionException(
					"Unable to create ClassWrapper for class '" + name + "'", e);
		}
	}

	private final Class<?> clazz;

	public ClassWrapper(Class<?> clazz) {
		this.clazz = clazz;
	}

	public List<ConstructorWrapper> constructors() {
		List<ConstructorWrapper> constructorWrappers = new ArrayList<ConstructorWrapper>();
		for (Constructor<?> constructor : this.clazz.getConstructors()) {
			constructorWrappers.add(new ConstructorWrapper(this, constructor));
		}
		return constructorWrappers;
	}
	
	public String className() {
		return this.clazz.getName();
	}

	/* package */ClassNode classNode() {
		InputStream inputStream = inputStream();
		try {
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(inputStream);
			classReader.accept(classNode, 0);
			return classNode;
		} catch (IOException e) {
			throw new ReflectionException(
					"Unable to load ClassNode for class '"
							+ this.clazz.getName() + "'", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

	private InputStream inputStream() {
		ClassLoader declaringClassLoader = this.clazz.getClassLoader();
		Type declaringType = Type.getType(clazz);
		String url = declaringType.getInternalName() + ".class";

		InputStream classFileInputStream = declaringClassLoader
				.getResourceAsStream(url);
		if (classFileInputStream == null) {
			throw new IllegalArgumentException(
					"The class loader cannot find the bytecode that defined the class (URL: "
							+ url + ")");
		}
		return classFileInputStream;
	}
}
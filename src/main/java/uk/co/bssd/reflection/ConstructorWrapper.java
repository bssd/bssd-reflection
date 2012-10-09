package uk.co.bssd.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public class ConstructorWrapper {

	private final ClassWrapper classWrapper;
	private final Constructor<?> constructor;

	public ConstructorWrapper(ClassWrapper classWrapper,
			Constructor<?> constructor) {
		this.classWrapper = classWrapper;
		this.constructor = constructor;
	}
	
	public Object instantiate(List<Object> arguments) {
		return instantiate(arguments.toArray());
	}
	
	public Object instantiate(Object... arguments) {
		try {
			return this.constructor.newInstance(arguments);
		} catch (Exception e) {
			String message = String
					.format("Unable to instantiate class '%s' with constructor '%s' and arguments '%s'",
							this.classWrapper.className(), this.constructor,
							Arrays.toString(arguments));
			throw new ReflectionException(message, e);
		}
	}	

	@SuppressWarnings("unchecked")
	public List<ParameterWrapper> parameters() {
		MethodNode method = methodNode();
		Type[] argumentTypes = Type.getArgumentTypes(method.desc);
		List<ParameterWrapper> parameterNames = new ArrayList<ParameterWrapper>(
				argumentTypes.length);

		java.lang.reflect.Type[] genericParameterTypes = constructor
				.getGenericParameterTypes();

		List<LocalVariableNode> localVariables = method.localVariables;
		
		if (localVariables.size() > 0) {
			// The first local variable actually represents "this" object
			for (int i = 1; i < localVariables.size(); i++) {
				String name = localVariables.get(i).name;
				Type type = argumentTypes[i-1];
				String parameterClass = type.getClassName();
				
				ParameterWrapper parameterDescriptor;
				
				java.lang.reflect.Type genericParameterType = genericParameterTypes[i-1];
				if (genericParameterType instanceof TypeVariable) {
					TypeVariable<?> typeVariable = (TypeVariable<?>) genericParameterType;
					parameterDescriptor = new ParameterWrapper(name,
							parameterClass, typeVariable.getName());
				} else {
					parameterDescriptor = new ParameterWrapper(name, parameterClass);
				}
				
				parameterNames.add(parameterDescriptor);
			}
		} else {
			int i = 0;
			for (Type argumentType : argumentTypes) {
				parameterNames.add(new ParameterWrapper("arg" + i++, argumentType.getClassName()));
			}
		}

		return parameterNames;
	}

	@SuppressWarnings("unchecked")
	private MethodNode methodNode() {
		List<MethodNode> methods = classNode().methods;
		String constructorDescriptor = Type
				.getConstructorDescriptor(this.constructor);
		for (MethodNode method : methods) {
			if (method.name.equals("<init>")
					&& method.desc.equals(constructorDescriptor)) {
				return method;
			}
		}
		throw new IllegalStateException(
				"Unable to find method node for constructor");
	}

	private ClassNode classNode() {
		return this.classWrapper.classNode();
	}
}
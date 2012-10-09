package uk.co.bssd.reflection;

import java.util.Iterator;
import java.util.List;

public class ConstructorWrappers implements Iterable<ConstructorWrapper> {

	private final List<ConstructorWrapper> constructorWrappers;
	
	ConstructorWrappers(List<ConstructorWrapper> wrappers) {
		this.constructorWrappers = wrappers;
	}
	
	@Override
	public Iterator<ConstructorWrapper> iterator() {
		return this.constructorWrappers.iterator();
	}
	
	public ConstructorWrapper findByArgumentTypes(Class<?>... types) {
		for (ConstructorWrapper wrapper : this.constructorWrappers) {
			List<ParameterWrapper> parameters = wrapper.parameters();
			if (parameters.size() == types.length) {
				boolean valid = true;
				for (int i = 0, max = parameters.size(); i < max && valid; i++) {
					if (parameters.get(i).getType() != types[i]) {
						valid = false;;
					}
				}
				if (valid) {
					return wrapper;
				}
			}
		}
		return null;
	}
	
	public int size() {
		return this.constructorWrappers.size();
	}

}
package proteomics.feature;

import java.util.Set;
import java.util.TreeSet;

public class CategoricalStatistics<T> extends Statistics<T> {

	private Set<T> value_set;
	
	public CategoricalStatistics() {
		value_set = new TreeSet<>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		
		sb.append("\n");
		sb.append(String.format("%32s:\t%s", "value set", value_set));
		
		return sb.toString();
	}
	
	public void acc(T value) {
		super.acc(value);
		
		if (value == null)
			return;
		
		value_set.add(value);
	}
	
	public Set<T> getValueSet() {
		return value_set;
	}
}

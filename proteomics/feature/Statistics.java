package proteomics.feature;

import java.util.Set;

public class Statistics<T> {

	protected int count;
	protected int null_count;
	
	public Statistics() {
		count = 0;
		null_count = 0;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%32s:\t%d\n", "count", count));
		sb.append(String.format("%32s:\t%d", "null count", null_count));
		
		return sb.toString();
	}
	
	public void acc(T value) {
		if (value == null)
			null_count++;
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getNullCount() {
		return null_count;
	}
	
	public T min() {
		return null;
	}
	
	public T max() {
		return null;
	}
	
	public T median() {
		return null;
	}
	
	public double mean() {
		return 0;
	}
	
	public double std() {
		return 0;
	}
	
	public Set<T> getValueSet() {
		return null;
	}
}

package proteomics.feature;

import java.util.ArrayList;
import java.util.List;

public class NumericalStatistics extends Statistics<Double> {

	private double sum;
	private double sum2;
	
	private List<Double> value_list;
	private boolean is_sorted;
	
	public NumericalStatistics() {
		sum = sum2 = 0.0;
		
		value_list = new ArrayList<>();
		is_sorted = false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		
		sb.append("\n");
		sb.append(String.format("%32s:\t%.4f\n", "min", min()));
		sb.append(String.format("%32s:\t%.4f\n", "max", max()));
		sb.append(String.format("%32s:\t%.4f\n", "median", median()));
		sb.append(String.format("%32s:\t%.4f\n", "mean", Double.isNaN(mean()) ? null : mean()));
		sb.append(String.format("%32s:\t%.4f", "std", Double.isNaN(std()) ? null : std()));
		
		return sb.toString();
	}
	
	public void acc(Double value) {
		super.acc(value);
		
		if (value == null)
			return;
		
		sum += value;
		sum2 += Math.pow(value, 2);
		
		value_list.add(value);
		is_sorted = false;
	}
	
	public Double min() {
		if (value_list.isEmpty())
			return null;
		
		if (is_sorted == false)
			sort();
		return value_list.get(0);
	}
	
	public Double max() {
		if (value_list.isEmpty())
			return null;
		
		if (is_sorted == false)
			sort();
		return value_list.get(value_list.size() - 1);
	}
	
	public Double median() {
		if (value_list.isEmpty())
			return null;
		
		if (is_sorted == false)
			sort();
		return value_list.get(value_list.size() / 2);
	}
	
	private void sort() {
		MaxHeap<Double> heap = new MaxHeap<>();
		
		for (double value : value_list)
			heap.insert(value);
		
		int index = value_list.size() - 1;
		while (!heap.isEmpty())
			value_list.set(index--, heap.delete());

		is_sorted = true;
	}

	public double mean() {
		return sum / count;
	}
	
	public double std() {
		return Math.sqrt((sum2 / count) - Math.pow(mean(), 2));
	}
}

class MaxHeap<T extends Comparable<T>> {
	
	List<T> list;
	int size;
	
	MaxHeap() {
		list = new ArrayList<>();
		list.add(null);
		size = 0;
	}
	
	void insert(T t) {
		list.add(++size, t);
		
		for (int i = size; i > 1; i /= 2) {
			T child = list.get(i);
			T parent = list.get(i/2);
			
			if (child.compareTo(parent) > 0) {
				T temp = child;
				list.set(i, parent);
				list.set(i/2, temp);
			}
			else {
				break;
			}
		}
	}
	
	T delete() {
		if (size == 0)
			return null;
		
		T item = list.get(1);
		list.set(1, list.get(size--));
		
		for (int i = 1; i*2 <= size;) {
			T parent = list.get(i);
			T left_child = list.get(i*2);
			T right_child = list.get(i*2 + 1);
			
			if (parent.compareTo(left_child) > 0 && parent.compareTo(right_child) > 0)
				break;
			else if (left_child.compareTo(right_child) > 0) {
				T temp = parent;
				list.set(i, left_child);
				list.set(i*2, temp);
				
				i = i*2;
			}
			else {
				T temp = parent;
				list.set(i, right_child);
				list.set(i*2 + 1, temp);
				
				i = i*2 + 1;
			}
		}
		list.remove(size+1);
		
		return item;
	}
	
	boolean isEmpty() {
		return (size == 0);
	}
}
package proteomics.data;

public class SearchScore {

	public final String name;
	public final double value;
	
	private final boolean is_descending;
	
	public SearchScore(String name, double value) {
		this.name = name;
		this.value = value;
		
		if (name.equals("expect"))
			is_descending = false;
		else
			is_descending = true;
	}
	
	public String toString() {
		return String.format("%s %.4f", name, value);
	}
	
	public boolean isDescending() {
		return is_descending;
	}
}

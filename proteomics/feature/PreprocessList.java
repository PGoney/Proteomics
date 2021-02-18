package proteomics.feature;

import java.util.LinkedList;

import proteomics.preprocess.PreprocessData;

public class PreprocessList extends LinkedList<PreprocessData> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int bit;	// checking this feature can preprocess.
	
	public PreprocessList(int bit) {
		this.bit = bit;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (PreprocessData data : this)
			sb.append(data + "\t");
//		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	public boolean canPreprocess(int bit) {
		return ((this.bit & bit) != 0)? true : false;
	}
	
	public boolean offer(PreprocessData data) {
		if (!canPreprocess(data.getMethod().bit()))
			return false;
		return super.offer(data);
	}
}

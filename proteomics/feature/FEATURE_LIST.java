package proteomics.feature;

import java.util.ArrayList;
import java.util.List;

import proteomics.preprocess.PREPROCESS_METHOD;

public interface FEATURE_LIST<T> {

	public String name();
	public String type();
	
	public boolean isActive();
	public void active();
	public void passive();

	public Statistics<?> getStatistics(T t);
	public Statistics<?> getStatistics();
	
	public PreprocessList getPreprocessList();
	
	public void addPreprocess(PREPROCESS_METHOD method, String[] args);
	public void clearPreprocess();
	
	public static FEATURE_LIST<?>[] values() {
		List<FEATURE_LIST<?>> feature_list = new ArrayList<>();
		
		for (SPECTRUM_FEATURE feature : SPECTRUM_FEATURE.values())
			feature_list.add(feature);
		for (PSM_FEATURE feature : PSM_FEATURE.values())
			feature_list.add(feature);
		
		return feature_list.toArray(new FEATURE_LIST<?>[feature_list.size()]);
	}
}
package proteomics.feature;

import proteomics.data.PSM;
import proteomics.data.PSMset;
import proteomics.preprocess.PREPROCESS_METHOD;
import proteomics.preprocess.PreprocessData;

public enum PSM_FEATURE implements FEATURE_LIST<PSMset> {

	CALCULATED_NEUTRAL_PEP_MASS("Double"),
	DELTA_MASS("Double"),
	NUM_OF_MISSED_CLEAVAGE("Integer"),
	PEPTIDE_SEQUENCE("String"),
	PROTEIN_SEQUENCE("String"),
	SEARCH_SCORE("List<SearchScore>"),
	
	MATCHED_MASS_ERROR_MEAN("Double"),
	MATCHED_MASS_ERROR_STD("Double"),
	NTT("Integer"),
	NUM_OF_MODIFICATION("Integer"),
	PEPTIDE_SEQUENCE_LENGTH("Integer"),
	PROTEIN_SEQUENCE_LENGTH("Integer"),
	TARGET_DECOY_LABEL("String"),
	
	ANNOTATED_PEAK_LIST("List<AnnoPeak>")
	;
	
	private boolean is_active;
	private final String type;
	
	private Statistics statistics;
	private PreprocessList preprocess_queue;
	
	private PSM_FEATURE(String type) {
		is_active = true;
		this.type = type;
		
		preprocess_queue = new PreprocessList(0xFFFF);
	}
	
	private PSM_FEATURE(String type, int preprocess_bit) {
		is_active = false;
		this.type = type;
		
		preprocess_queue = new PreprocessList(preprocess_bit);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("%32s:\t%s\n", "name", name()));
		sb.append(String.format("%32s:\t%s\n", "isActive", is_active));
		sb.append(String.format("%32s:\t%s", "preprocess", preprocess_queue));
		
		return sb.toString();
	}

	@Override
	public String type() {
		return type;
	}

	@Override
	public boolean isActive() {
		return is_active;
	}

	@Override
	public void active() {
		is_active = true;
	}

	@Override
	public void passive() {
		is_active = false;
	}

	@Override
	public PreprocessList getPreprocessList() {
		return preprocess_queue;
	}
	
	@Override
	public Statistics<?> getStatistics() {
		return statistics;
	}

	@Override
	public Statistics<?> getStatistics(PSMset psm_set) {
		if (psm_set == null)
			return statistics;
		
		switch (type) {
		case "Double":
			{
				NumericalStatistics statistics = new NumericalStatistics();
				for (int key : psm_set.keySet()) {
					PSM psm = psm_set.get(key);
					Object value = psm.get(this);
					statistics.acc((Double) value);
				}
				this.statistics = statistics;
			}
			break;
		case "List<AnnoPeak>":
		case "List<SearchScore>":
		case "String":
			{
				Statistics<Object> statistics = new Statistics<>();
				for (int key : psm_set.keySet()) {
					PSM psm = psm_set.get(key);
					Object value = psm.get(this);
					statistics.acc(value);
				}
				this.statistics = statistics;
			}
			break;
		case "Integer":
			{
				CategoricalStatistics<Integer> statistics = new CategoricalStatistics<>();
				for (int key : psm_set.keySet()) {
					PSM psm = psm_set.get(key);
					Object value = psm.get(this);
					statistics.acc((Integer) value);
				}
				this.statistics = statistics;
			}
			break;
		default:
			statistics = null;
		}
		
		return statistics;
	}

	@Override
	public void addPreprocess(PREPROCESS_METHOD method, String[] args) {
		preprocess_queue.offer(new PreprocessData(method, args));
	}

	@Override
	public void clearPreprocess() {
		preprocess_queue.clear();
	}
}

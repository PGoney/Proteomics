package proteomics.feature;

import proteomics.data.Spectra;
import proteomics.data.Spectrum;
import proteomics.preprocess.PREPROCESS_METHOD;
import proteomics.preprocess.PreprocessData;

public enum SPECTRUM_FEATURE implements FEATURE_LIST<Spectra> {

	BASE_PEAK_INTENSITY("Double"),
	BASE_PEAK_MASS("Double"),
	CHARGE("Integer"),
	MAX_SEQUENCE_TAG_LENGTH("Integer", false),
	PEAK_COUNT("Integer"),
	PEAK_LIST("List<Peak>"),
	PRECURSOR_INTENSITY("Double"),
	PRECURSOR_MASS("Double"),
	TIC("Double");
	
	private boolean is_active;
	private final String type;
	
	private Statistics statistics;
	private PreprocessList preprocess_queue;
	
	private SPECTRUM_FEATURE(String type) {
		this(type, true);
	}
	
	private SPECTRUM_FEATURE(String type, boolean is_active) {
		this(type, is_active, 0xFFFF);
	}
	
	private SPECTRUM_FEATURE(String type, boolean is_active, int preprocess_bit) {
		this.is_active = is_active;
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
	public Statistics<?> getStatistics(Spectra spectra) {
		if (spectra == null)
			return statistics;
		
		switch (type) {
		case "Double":
			{
				NumericalStatistics statistics = new NumericalStatistics();
				for (int key : spectra.keySet()) {
					Spectrum spec = spectra.get(key);
					Object value = spec.get(this);
					statistics.acc((Double) value);
				}
				this.statistics = statistics;
			}
			break;
		case "List<Peak>":
			{
				Statistics<Object> statistics = new Statistics<>();
				for (int key : spectra.keySet()) {
					Spectrum spec = spectra.get(key);
					Object value = spec.get(this);
					statistics.acc(value);
				}
				this.statistics = statistics;
			}
			break;
		case "Integer":
			{
				CategoricalStatistics<Integer> statistics = new CategoricalStatistics<>();
				for (int key : spectra.keySet()) {
					Spectrum spec = spectra.get(key);
					Object value = spec.get(this);
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

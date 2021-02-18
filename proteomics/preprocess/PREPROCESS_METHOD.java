package proteomics.preprocess;

public enum PREPROCESS_METHOD {

	// about peak
	FILTER_PEAKS(0x0001,
			"filter_peaks --TopN --N [number] --tolerance [number] --type [\"ppm\" or \"da\"]\n"
			+ "filter_peaks --intensity_thresholding --base_peak --proportion [number]\n"
			+ "filter_peaks --intensity_thresholding --TIC --proportion [number]\n"
			+ "filter_peaks --intensity_thresholding --intensity [number]"),
	PEAK_SCALING(0x0002,
			"peak_scaling --min_max --min [number] --max [number]\n"
			+ "peak_scaling --normalize --gaussian"),
	PEAK_PERMUTATION(0x0004,
			null),
	ZERO_PADDING(0x0008,
			"zero_padding --count [number]"),
	
	// about feature except peaks
	IMPUTATION(0x0010,
			"imputation --remove\n"
			+ "imputation --min\n"
			+ "imputation --max\n"
			+ "imputation --mean\n"
			+ "imputation --median"),
	SCALING(0x0020,
			"scaling --min_max --min [number] --max [number]\n"
			+ "scaling --normalize --gaussian")
	;
	
	private int bit;
	private String help;
	
	private PREPROCESS_METHOD(int bit, String help) {
		this.bit = bit;
		this.help = help;
	}
	
	public int bit() {
		return bit;
	}
	
	public String help() {
		return help;
	}
}

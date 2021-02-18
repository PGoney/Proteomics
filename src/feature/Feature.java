package proteomics.feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proteomics.data.PSM;
import proteomics.data.PSMset;
import proteomics.data.Peak;
import proteomics.data.Spectra;
import proteomics.data.Spectrum;
import proteomics.preprocess.PREPROCESS_METHOD;
import proteomics.preprocess.Preprocess;
import proteomics.preprocess.PreprocessData;

/**
 * Extract the feature mapping at certain spectrum and psm.
 * 
 * @author pjw23
 *
 */
public class Feature {

	private Spectrum spec;
	private PSM psm;
	
	private Map<FEATURE_LIST<?>, Object> value_map;
	
	public Feature(Spectrum spec, PSM psm) {
		this.spec = spec;
		this.psm = psm;
		
		value_map = new HashMap<>();
		
		for (SPECTRUM_FEATURE feature : SPECTRUM_FEATURE.values()) {
			if (feature.isActive() && spec.get(feature) == null)
				spec.calculate(feature);
			
			Object value = spec.get(feature);
			value_map.put(feature, value);
		}
		
		for (PSM_FEATURE feature : PSM_FEATURE.values()) {
			if (feature.isActive() && psm.get(feature) == null) {
				psm.calculate(feature);
				psm.calculate(feature, spec);
			}
			
			Object value = psm.get(feature);
			value_map.put(feature, value);
		}
	}
	
	/**
	 * load statistics.
	 * 
	 * @param spectra
	 * @param psm_set
	 * @param feature
	 */
	public static void load(Spectra spectra, PSMset psm_set, FEATURE_LIST<?> feature) {
		if (spectra != null && feature instanceof SPECTRUM_FEATURE) {
			for (int key : spectra.keySet()) {
					Spectrum spec = spectra.get(key);
					if (feature.isActive())
						spec.calculate((SPECTRUM_FEATURE) feature);
			}
			((SPECTRUM_FEATURE) feature).getStatistics(spectra);
		}
		
		if (psm_set != null && feature instanceof PSM_FEATURE) {
			for (int key : psm_set.keySet()) {
				Spectrum spec = spectra.get(key);
				PSM psm = psm_set.get(key);
				if (feature.isActive()) {
					psm.calculate((PSM_FEATURE) feature);
					psm.calculate((PSM_FEATURE) feature, spec);
				}
			}
			
			((PSM_FEATURE) feature).getStatistics(psm_set);
		}
	}

	/**
	 * load the statistics.
	 * 
	 * @param spectra
	 * @param psm_set
	 */
	public static void load(Spectra spectra, PSMset psm_set) {
		if (spectra != null) {
			System.out.println("Spectrum Feature Loading...");
			int cnt = 0;
			double percentage;
			int base = 0;
			for (int key : spectra.keySet()) {
				percentage = ((double) cnt) / spectra.keySet().size() * 100;
				if (percentage >= base) {
					System.out.print(base + "...");
					base += 10;
				}
				
				for (SPECTRUM_FEATURE feature : SPECTRUM_FEATURE.values()) {
					Spectrum spec = spectra.get(key);
					if (feature.isActive())
						spec.calculate(feature);
				}
				cnt++;
			}
			System.out.println(100);
			
			for (SPECTRUM_FEATURE feature : SPECTRUM_FEATURE.values()) {
				feature.getStatistics(spectra);
			}
		}
		
		if (psm_set != null) {
			System.out.println("PSM Feature Loading...");
			int cnt = 0;
			double percentage;
			int base = 0;
			for (int key : psm_set.keySet()) {
				percentage = ((double) cnt) / psm_set.keySet().size() * 100;
				if (percentage >= base) {
					System.out.print(base + "...");
					base += 10;
				}
				
				for (PSM_FEATURE feature : PSM_FEATURE.values()) {
					Spectrum spec = spectra.get(key);
					PSM psm = psm_set.get(key);
					if (feature.isActive()) {
						psm.calculate(feature);
						psm.calculate(feature, spec);
					}
				}
				cnt++;
			}
			System.out.println(100);
			
			for (PSM_FEATURE feature : PSM_FEATURE.values()) {
				feature.getStatistics(psm_set);
			}
		}
	}
	
	public String toString() {
		if (value_map.isEmpty())
			return "";
		
		StringBuilder sb = new StringBuilder();

		for (SPECTRUM_FEATURE feature : SPECTRUM_FEATURE.values()) {
			if (!feature.isActive())
				continue;
			else if (feature == SPECTRUM_FEATURE.PEAK_LIST)
				continue;
			
			Object value = value_map.get(feature);
			sb.append(parse(value));
			sb.append("\t");
		}

		for (PSM_FEATURE feature : PSM_FEATURE.values()) {
			if (!feature.isActive())
				continue;
			else if (feature == PSM_FEATURE.ANNOTATED_PEAK_LIST)
				continue;
			else if (feature == PSM_FEATURE.SEARCH_SCORE)
				continue;
			
			Object value = value_map.get(feature);
			sb.append(parse(value));
			sb.append("\t");
		}

		if (PSM_FEATURE.SEARCH_SCORE.isActive()) {
			Object value = value_map.get(PSM_FEATURE.SEARCH_SCORE);
			sb.append(parse(value));
			sb.append("\t");
		}
		if (SPECTRUM_FEATURE.PEAK_LIST.isActive()) {
			Object value = value_map.get(SPECTRUM_FEATURE.PEAK_LIST);
			sb.append(parse(value));
			sb.append("\t");
		}
		if (PSM_FEATURE.ANNOTATED_PEAK_LIST.isActive()) {
			Object value = value_map.get(PSM_FEATURE.ANNOTATED_PEAK_LIST);
			sb.append(parse(value));
			sb.append("\t");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}

	/**
	 * For unifying displaying format.
	 * @param value
	 * @return
	 */
	private String parse(Object value) {
		if (value instanceof Double)
			return String.format("%.4f", value);
		else if (value instanceof Peak)
			return value.toString();
		else if (value instanceof List<?>)
			return value.toString();
		return String.valueOf(value);
	}

	/**
	 * Preprocess feature with its preprocess list.
	 */
	public void preprocess() {
		for (FEATURE_LIST<?> feature : value_map.keySet()) {
			PreprocessList queue = feature.getPreprocessList();
			if (feature.isActive() && queue.size() == 0) {
				String[] args = { "--remove" };
				queue.add(new PreprocessData(PREPROCESS_METHOD.IMPUTATION, args));
			}
			
			for (PreprocessData data : queue.toArray(new PreprocessData[queue.size()])) {
				PREPROCESS_METHOD method = data.getMethod();
				String[] args = data.getArgument();
				Map<String, String> arg_map = parseArgument(args);
				
				if (feature == SPECTRUM_FEATURE.PEAK_LIST) {
					List<Peak> peak_list = (List<Peak>) get(feature);
					
					if (method == PREPROCESS_METHOD.FILTER_PEAKS) {
						switch (args[0]) {
						case "--TopN":
							int N = Integer.parseInt(arg_map.get("--N"));
							double tolerance = Double.parseDouble(arg_map.get("--tolerance"));
							String type = arg_map.get("--type");
							
							peak_list = Preprocess.TopN(peak_list, N, tolerance, type);
							break;
						case "--intensity_thresholding":
							double intensity;
							double proportion;
							
							switch (args[1]) {
							case "--base_peak":
								intensity = (double) get(SPECTRUM_FEATURE.BASE_PEAK_INTENSITY);
								proportion = Double.parseDouble(arg_map.get("--proportion"));
								
								intensity *= proportion;
								break;
							case "--TIC":
								intensity = (double) get(SPECTRUM_FEATURE.TIC);
								proportion = Double.parseDouble(arg_map.get("--proportion"));
								
								intensity *= proportion;
								break;
							default:
								intensity = Double.parseDouble(arg_map.get("--intensity"));
							}
							
							peak_list = Preprocess.intensityThresholding(peak_list, intensity);
							break;
						}
					}
					else if (method == PREPROCESS_METHOD.PEAK_SCALING) {
						switch (args[0]) {
						case "--min_max":
							double min = Double.parseDouble(arg_map.get("--min"));
							double max = Double.parseDouble(arg_map.get("--max"));
							
							peak_list = Preprocess.scalingPeak(peak_list, min, max);
							break;
						case "--normalize":
							switch (args[1]) {
							case "--gaussian":								
								peak_list = Preprocess.scalingPeakNormalize(peak_list);
								break;
							case "--function":
								break;
							}
							break;
						}
					}
					else if (method == PREPROCESS_METHOD.PEAK_PERMUTATION) {
						
					}
					else if (method == PREPROCESS_METHOD.ZERO_PADDING) {
						int cnt = Integer.parseInt(arg_map.get("--count"));
						
						peak_list = Preprocess.zeroPadding(peak_list, cnt);
					}
					
					set(feature, peak_list);
				}
				else {
					if (method == PREPROCESS_METHOD.IMPUTATION) {
						if (value_map.get(feature) != null)
							continue;
						else if (args[0].equals("--remove")) {
							value_map.clear();
							return;
						}

						if (!feature.type().equals("Double"))
							continue;
						
						Statistics<?> statistics = feature.getStatistics(null);
						System.out.println(statistics);
						Object value = null;
						
						switch (args[0]) {
						case "--min":
							value = statistics.min();
							break;
						case "--max":
							value = statistics.max();
							break;
						case "--mean":
							value = statistics.mean();
							break;
						case "--median":
							value = statistics.median();
							break;
						}
						
						if (value != null)
							statistics.null_count--;
						
						set(feature, value);
					}
					else if (method == PREPROCESS_METHOD.SCALING) {
						if (!feature.type().equals("Double"))
							continue;

						Statistics<?> statistics = feature.getStatistics(null);
						double value = (double) get(feature);
						
						switch (args[0]) {
						case "--min_max":
							double min = Double.parseDouble(arg_map.get("--min"));
							double max = Double.parseDouble(arg_map.get("--max"));
							
							value = Preprocess.scaling(value, (double) statistics.min(), (double) statistics.max(), min, max);
							break;
						case "--normalize":
							switch (args[1]) {
							case "--gaussian":
								value = Preprocess.scalingNormalize(value, statistics.mean(), statistics.std());
								break;
							case "--function":
								break;
							}
							break;
						}
						
						set(feature, value);
					}
				}
			}
		}
	}

	private Map<String, String> parseArgument(String[] args) {
		Map<String, String> arg_map = new HashMap<>();
		
		arg_map.put("--N", "150");
		arg_map.put("--tolerance", "20");
		arg_map.put("--type", "ppm");
		
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
			case "--N":
			case "--tolerance":
			case "--type":
			case "--proportion":
			case "--intensity":
			case "--min":
			case "--max":
			case "--count":
			case "--mean":
			case "--std":
				if (args.length > i+1 && !(args[i+1].startsWith("--")))
					arg_map.put(arg, args[++i]);
				break;
			}
		}
		
		return arg_map;
	}

	private void set(FEATURE_LIST<?> feature, Object value) {
		value_map.replace(feature, value);
	}

	private Object get(FEATURE_LIST<?> feature) {
		if (value_map.get(feature) == null) {
			if (feature instanceof SPECTRUM_FEATURE) {
				spec.calculate((SPECTRUM_FEATURE) feature);
				set(feature, spec.get((SPECTRUM_FEATURE) feature));
			}
			else if (feature instanceof PSM_FEATURE) {
				psm.calculate((PSM_FEATURE) feature);
				psm.calculate((PSM_FEATURE) feature, spec);
				set(feature, psm.get((PSM_FEATURE) feature));
			}
		}
		return value_map.get(feature);
	}
}

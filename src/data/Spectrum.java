package proteomics.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proteomics.feature.FEATURE_LIST;
import proteomics.feature.SPECTRUM_FEATURE;

/**
 * Storing spectrum feature such as charge or peak list.
 * 
 * @author pjw23
 *
 */
public class Spectrum {
	
	private Map<SPECTRUM_FEATURE, Object> value_map;
	
	public Spectrum() {
		value_map = new HashMap<>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (FEATURE_LIST<Spectra> feature : SPECTRUM_FEATURE.values()) {
			String name = feature.name();
			Object value = value_map.get(feature);
			
			sb.append(String.format("%32s:\t%s\n", name, parse(value)));
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	/**
	 * For unifying displaying format.
	 * 
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

	public void set(SPECTRUM_FEATURE feature, Object value) {
		value_map.put(feature, value);
	}

	public Object get(SPECTRUM_FEATURE feature) {
		return value_map.get(feature);
	}

	/**
	 * For values needed calculation.
	 * @param feature
	 */
	@SuppressWarnings("unchecked")
	public void calculate(SPECTRUM_FEATURE feature) {
		if (feature == SPECTRUM_FEATURE.BASE_PEAK_MASS) {
			List<Peak> peak_list = (List<Peak>) get(SPECTRUM_FEATURE.PEAK_LIST);
			Peak base_peak = calculateBasePeak(peak_list);
			set(feature, base_peak.getMass());
		}
		else if (feature == SPECTRUM_FEATURE.BASE_PEAK_INTENSITY) {
			List<Peak> peak_list = (List<Peak>) get(SPECTRUM_FEATURE.PEAK_LIST);
			Peak base_peak = calculateBasePeak(peak_list);
			set(feature, base_peak.getIntensity());
		}
		else if (feature == SPECTRUM_FEATURE.MAX_SEQUENCE_TAG_LENGTH) {
			List<Peak> peak_list = (List<Peak>) get(SPECTRUM_FEATURE.PEAK_LIST);
			int value = calculateMaxSequenceTagLength(peak_list);
			set(feature, value);
		}
		else if (feature == SPECTRUM_FEATURE.PEAK_COUNT) {
			List<Peak> peak_list = (List<Peak>) get(SPECTRUM_FEATURE.PEAK_LIST);
			set(feature, peak_list.size());
		}
		else if (feature == SPECTRUM_FEATURE.TIC) {
			List<Peak> peak_list = (List<Peak>) get(SPECTRUM_FEATURE.PEAK_LIST);
			double value = calculateTIC(peak_list);
			set(feature, value);
		}
	}

	private double calculateTIC(List<Peak> peak_list) {
		double TIC = 0.0;
		
		for (Peak peak : peak_list)
			TIC += peak.getIntensity();
		
		return TIC;
	}

	private int calculateMaxSequenceTagLength(List<Peak> peak_list) {
		double[] aaMasses = Parameters.aminoacid_masss;
		double tolerance = 0.025; // TODO: it must be declared in other class.
		// Pre: find max and min diff (abs).
		
		double maxAAMass = 0;
		double minAAMass = 1000;
		for(int i=0; i<aaMasses.length; i++) {
			if(aaMasses[i] == 0) continue;
			maxAAMass = maxAAMass < aaMasses[i] ? aaMasses[i] : maxAAMass; 
			minAAMass = minAAMass > aaMasses[i] ? aaMasses[i] : minAAMass;
		}
		
		// Step
		// 1. Recursive way to obtain the max tag.
		Peak[] arrayPeak = peak_list.toArray(new Peak[peak_list.size()]);
		int sizeOfPeakList = arrayPeak.length;

		Arrays.sort(arrayPeak, new Comparator<Peak>() {
			public int compare(Peak p1, Peak p2) {
				if (p1.getMass() < p2.getMass())
					return -1;
				if (p1.getMass() > p2.getMass())
					return 1;
				return 0;
			}
		});
		
		List<Tag> totalConsecutiveTags = new ArrayList<Tag>();
		
		// Supposed that peaks are sorted by ascending order of m/z.
		for(int i=0; i<sizeOfPeakList; i++) {
			Peak pivotPeak = arrayPeak[i];
			
			for(int j=i+1; j<sizeOfPeakList; j++) {
				double massDiff = arrayPeak[j].getMass() - pivotPeak.getMass();
				if(massDiff < minAAMass - tolerance) continue;
				if(massDiff > maxAAMass + tolerance) break;
				char[] pAAs = Tag.explainableAAfromTwoPeaks(pivotPeak.getMass(), arrayPeak[j].getMass(), tolerance, 1);
				if(pAAs != null) {
					List<Peak> consecutivePeaks = new ArrayList<Peak>();
					consecutivePeaks.add(pivotPeak);
					calculateMaxSequenceTagLength(arrayPeak, j, consecutivePeaks, totalConsecutiveTags, minAAMass, maxAAMass, tolerance);
				}
			}
		}
		
		int maxLength = 0;
		for(Tag tag : totalConsecutiveTags) maxLength = maxLength < tag.getTagLength() ? tag.getTagLength() : maxLength;
		
		return maxLength;
	}
	
	private void calculateMaxSequenceTagLength (Peak[] arrayPeak, int index, List<Peak> consecutivePeaks, List<Tag> totalConsecutiveTags, double minAAMass, double maxAAMass, double tolerance) {
		Peak pivotPeak = arrayPeak[index];
		int sizeOfPeaks = arrayPeak.length;
		boolean moreTag = false;
		int pivotIndex = consecutivePeaks.size();
		consecutivePeaks.add(pivotPeak);
		
		for(int i=index+1; i<sizeOfPeaks; i++) {
			double massDiff = arrayPeak[i].getMass() - pivotPeak.getMass();
			if(massDiff < minAAMass - tolerance) continue;
			if(massDiff > maxAAMass + tolerance) break;
			char[] pAAs = Tag.explainableAAfromTwoPeaks(pivotPeak.getMass(), arrayPeak[i].getMass(), tolerance, 1);
			if(pAAs != null) {
				calculateMaxSequenceTagLength(arrayPeak, i, consecutivePeaks, totalConsecutiveTags, minAAMass, maxAAMass, tolerance);
				while(pivotIndex < consecutivePeaks.size()) {
					consecutivePeaks.remove(consecutivePeaks.size()-1);
				}
				moreTag = true;
			}
		}
		
		if(!moreTag) {
			ArrayList<Peak> tagPeaks = new ArrayList<Peak>(consecutivePeaks);
			Tag tag = new Tag(tagPeaks, tolerance);
			totalConsecutiveTags.add(tag);
		}
	}

	private Peak calculateBasePeak(List<Peak> peak_list) {
		Peak base_peak = peak_list.get(0);
		for (Peak peak : peak_list)
			if (base_peak.getIntensity() < peak.getIntensity())
				base_peak = peak;
		return base_peak;
	}

}

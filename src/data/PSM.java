package proteomics.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proteomics.feature.FEATURE_LIST;
import proteomics.feature.PSM_FEATURE;
import proteomics.feature.SPECTRUM_FEATURE;

/**
 * 
 * @author pjw23
 *
 * PSM has feature associated identifying spectrum.
 * It is derived from external program such as comet-ms or moda.
 */
public class PSM {
	
	private Map<PSM_FEATURE, Object> value_map;
	
	private class Constants {
		public static final double UNIT_MASS = 1.;

		public static final double Electron = 0.000549;
		public static final double Hydrogen = 1.007825035;
		public static final double Oxygen = 15.99491463;
		public static final double Nitrogen = 14.003074;
		public static final double Proton = Hydrogen - Electron;
		public static final double HO = Hydrogen + Oxygen;
		public static final double H2O = Hydrogen * 2 + Oxygen;
		public static final double NH3 = Hydrogen * 3 + Nitrogen;
		public static final double IsotopeSpace = 1.00235;

		public static final double NTERM_FIX_MOD = 0;
		public static final double CTERM_FIX_MOD = 0;
		public static final double B_ION_OFFSET = Proton;
		public static final double Y_ION_OFFSET = H2O + Proton;
		public static final double A_ION_OFFSET = Oxygen + 12.;
		/*
		 * Used for xcorr calculation, 0.4 is the default value with low resolution MS2
		 * with high resolution, 0.0 should be fine.
		 * https://groups.google.com/forum/#!topic/crux-users/xv7kx75zp1s
		 */
		public static final double binOffset = 0.4;
		/*
		 * In tide search, for low resolution, the default is 1.0005079. for
		 * high-resolution data, 0.02 is recommended.
		 */
		public static final double binWidth = 1.0005079;
		public static final int weightScaling = 20;

	}

	/**
	 * private class peakwithInfo with ions, charge
	 * 
	 * @author kyoon3
	 * 
	 */
	private class PeakWithInfo extends Peak {

		String ion_type;
		double charge;
		
		PeakWithInfo() {
			super(0, 0);
			// TODO Auto-generated constructor stub
		}
		
		void setMass(double mass) {
			this.mass = mass;
		}
	}
	
	public PSM() {
		value_map = new HashMap<>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (FEATURE_LIST<PSMset> feature : PSM_FEATURE.values()) {
			String name = feature.name();
			Object value = value_map.get(feature);
			
			sb.append(String.format("%32s:\t%s\n", name, parse(value)));
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	/**
	 * Unifying displaying format.
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

	public void set(PSM_FEATURE feature, Object value) {
		value_map.put(feature, value);
	}

	public Object get(PSM_FEATURE feature) {
		return value_map.get(feature);
	}

	/**
	 * This is for value needing calculation without spectrum.
	 * 
	 * @param feature
	 */
	public void calculate(PSM_FEATURE feature) {
		Object value = null;
		
		if (feature == PSM_FEATURE.NTT) {
			String peptide = (String) get(PSM_FEATURE.PEPTIDE_SEQUENCE);
			int NTT = calculateNTT(peptide, "RK");
			
			value = NTT;
		}
		else if (feature == PSM_FEATURE.NUM_OF_MODIFICATION) {
			String peptide = (String) get(PSM_FEATURE.PEPTIDE_SEQUENCE);
			
			value = getModList(peptide).size();
		}
		else if (feature == PSM_FEATURE.PEPTIDE_SEQUENCE_LENGTH) {
			String peptide = (String) get(PSM_FEATURE.PEPTIDE_SEQUENCE);
			String pep_strip_seq = getPeptideStripSeq(peptide);
			
			value = pep_strip_seq.length();
		}
		else if (feature == PSM_FEATURE.PROTEIN_SEQUENCE_LENGTH) {
			String protein = (String) get(PSM_FEATURE.PROTEIN_SEQUENCE);
			
			value = protein.length();
		}
		else if (feature == PSM_FEATURE.TARGET_DECOY_LABEL) {
			String protein = (String) get(PSM_FEATURE.PROTEIN_SEQUENCE);
			String is_target = isTarget(protein, "decoy");
			
			value = is_target;
		}
		else {
			return;
		}
		
		set(feature, value);
	}

	/**
	 * This is for value needing calculation with spectrum.
	 * 
	 * @param feature
	 * @param spec
	 */
	public void calculate(PSM_FEATURE feature, Spectrum spec) {
		Object value = null;

		if (feature == PSM_FEATURE.MATCHED_MASS_ERROR_MEAN) {
			List<AnnoPeak> anno_peak_list = (List<AnnoPeak>) get(PSM_FEATURE.ANNOTATED_PEAK_LIST);
			
			if (anno_peak_list == null) {
				anno_peak_list = extractAnnotatedPeaks(spec, 20, "ppm", 2);
				set(PSM_FEATURE.ANNOTATED_PEAK_LIST, anno_peak_list);
			}
			
			value = calMatchedMassErrorMean(anno_peak_list);
		}
		else if (feature == PSM_FEATURE.MATCHED_MASS_ERROR_STD) {
			List<AnnoPeak> anno_peak_list = (List<AnnoPeak>) get(PSM_FEATURE.ANNOTATED_PEAK_LIST);
			
			if (anno_peak_list == null) {
				anno_peak_list = extractAnnotatedPeaks(spec, 20, "ppm", 2);
				set(PSM_FEATURE.ANNOTATED_PEAK_LIST, anno_peak_list);
			}
			
			value = calMatchedMassErrorStd(anno_peak_list);
		}
		else if (feature == PSM_FEATURE.ANNOTATED_PEAK_LIST) {
			value = extractAnnotatedPeaks(spec, 20, "ppm", 2);
		}
		else {
			return;
		}
		
		set(feature, value);
	}
	
	private Double calMatchedMassErrorStd(List<AnnoPeak> anno_peak_list) {
		double sum = 0.0;
		double sum2 = 0.0;
		int count = anno_peak_list.size();
		
		for (AnnoPeak peak : anno_peak_list) {
			sum += peak.getMass_error();
			sum2 += Math.pow(peak.getMass_error(), 2);
		}
		
		if (anno_peak_list.size() == 0)
			return null;
		
		return Math.sqrt((sum2 / count) - Math.pow(sum / count, 2));
	}

	private Double calMatchedMassErrorMean(List<AnnoPeak> anno_peak_list) {
		double sum = 0.0;
		
		for (AnnoPeak peak : anno_peak_list) {
			sum += peak.getMass_error();
		}
		
		if (anno_peak_list.size() == 0)
			return null;
		
		return sum / anno_peak_list.size();
	}

	/**
	 * @param spec  Spectrum to get Annotated Peaks
	 * @param tol   size of the tolerance
	 * @param unit  tolerance unit to consider. if "ppm" then consider ppm
	 *              tolerance, if "da" then consider da tolerance
	 * @param maxCS maximum fragment charge state to consider. Current maximum
	 *              possible fragment charge is 2.
	 * @return List of AnnoPeak object
	 */
	private List<AnnoPeak> extractAnnotatedPeaks(Spectrum spec, double tol, String unit, int maxCS) {
		// TODO Auto-generated method stub
		List<Peak> CurrentPeakInfo = (List<Peak>) spec.get(SPECTRUM_FEATURE.PEAK_LIST);
		String seq = this.getPeptideStripSeq((String) get(PSM_FEATURE.PEPTIDE_SEQUENCE));
		List<PeakWithInfo> TheoPeak = new ArrayList<PeakWithInfo>();// calculate the Theopeak using peptide interface
		List<AnnoPeak> AnnotatedPeakList = new ArrayList<AnnoPeak>();
		TheoPeak = TheoPeakCalculator(seq, maxCS);
		AnnotatedPeakList = GetAnnotatedPeakList(TheoPeak, CurrentPeakInfo, tol, unit);
		return AnnotatedPeakList;
	}

	/**
	 * private theoretical peak builder
	 * 
	 * @param seq   sequence to make theoretical peak list
	 * @param maxCS currently up to 2
	 * 
	 * @return List peak of theoretical peak.
	 */
	private List<PeakWithInfo> TheoPeakCalculator(String seq, int maxCS) {
		List<PeakWithInfo> TheoPeak = new ArrayList<PeakWithInfo>();
		if (maxCS >= 1) {
			double TotalMass = 0;
			for (int i = 0; i < seq.length(); i++) {// add b-ion
				double CurrentMass = Parameters.aminoacid_masss[seq.charAt(i) - 'A'];
				TotalMass += CurrentMass;
				PeakWithInfo peak = new PeakWithInfo();
				peak.setMass(TotalMass + (1 * Constants.Proton));// PROTON mass
				peak.ion_type = "b";
				peak.charge = 1;
				TheoPeak.add(peak);
			}
			TotalMass = 0;
			for (int i = seq.length() - 1; i >= 0; i--) {// add y-ion
				double CurrentMass = Parameters.aminoacid_masss[seq.charAt(i) - 'A'];
				PeakWithInfo peak = new PeakWithInfo();
				TotalMass += CurrentMass;
				peak.setMass(TotalMass + Constants.Proton + Constants.H2O);// add Hydrogen + H2O mass
				peak.ion_type = "y";
				peak.charge = 1;
				TheoPeak.add(peak);
			}
			if (maxCS >= 2) {
				TotalMass = 0;
				for (int i = 0; i < seq.length(); i++) {// add b-iom
					double CurrentMass = Parameters.aminoacid_masss[seq.charAt(i) - 'A'];
					TotalMass += CurrentMass;
					PeakWithInfo peak = new PeakWithInfo();
					peak.setMass((TotalMass + Constants.Proton * 2) / 2);
					peak.ion_type = "b";
					peak.charge = 2;
					TheoPeak.add(peak);
				}
				TotalMass = 0;
				for (int i = seq.length() - 1; i >= 0; i--) {// add y-ion
					double CurrentMass = Parameters.aminoacid_masss[seq.charAt(i) - 'A'];
					TotalMass += CurrentMass;
					PeakWithInfo peak = new PeakWithInfo();
					peak.setMass((TotalMass + Constants.Proton * 2 + Constants.H2O) / 2);// add Hydrogen + H2O
					peak.ion_type = "y";
					peak.charge = 2;
					TheoPeak.add(peak);
				}
			}
		} else {
			System.out.println("Wrong charge state. Current Fragment Charge maximum is 2");
			return null;
		}
		return TheoPeak;
	}

	/**
	 * GetAnnotatedPeak from theoretical peak.
	 * 
	 * @param TheoPeaks   List PeakWithInfo of theoretical peak
	 * @param CurrentPeak List peak of current spm peak.
	 * @param tol         tolerance amount
	 * @param unit        "ppm" or "da" in String format
	 * @return List of annotated Peak
	 */
	private List<AnnoPeak> GetAnnotatedPeakList(List<PeakWithInfo> TheoPeakList, List<Peak> CurrentPeakInfo, double tol,
			String unit) {
		List<AnnoPeak> AnnotatedPeakList = new ArrayList<AnnoPeak>();
		for (PeakWithInfo TheoPeak : TheoPeakList) {
			for (Peak CurrentPeak : CurrentPeakInfo) {
				double mass_diff = 0; // mass diff between theopeak and currentpeak.
				if (unit.equals("ppm")) {
					mass_diff = 1e6 * ((TheoPeak.getMass() - CurrentPeak.getMass()) / TheoPeak.getMass());
				} else if (unit.equals("da")) {
					mass_diff = TheoPeak.getMass() - CurrentPeak.getMass();
				} else {// if unit is not ppm or da
					System.out.println("Wrong unit. Use ppm or da");
					return null;
				}
				if (Math.abs(mass_diff) <= tol) {// if peak matches Within tolerance Created Annotated Peak
					AnnoPeak AnnotatedPeak = new AnnoPeak(CurrentPeak.getMass(), CurrentPeak.getIntensity());
					AnnotatedPeak.setCharge(String.valueOf(TheoPeak.charge));
					AnnotatedPeak.setMass_error(mass_diff);
					AnnotatedPeak.setIon_type(TheoPeak.ion_type);
//					AnnotatedPeak.setAnnotation(TheoPeak.ion_type + charge);
					AnnotatedPeak.setAnnotation(TheoPeak.ion_type + "2");
					AnnotatedPeakList.add(AnnotatedPeak);
				}
			}
		}
		return AnnotatedPeakList;

	}
	
	private List<Modification> getModList(String peptide) {
		/**
		 * 
		 * @param seq -> peptide sequence with modification delta mass, for example, +144.102IYAES+79.966DEEDFK+144.102EQTR
		 * @return Modification List of input sequence
		 */
		List<Modification> output = new ArrayList<Modification>();
		String seq = peptide;
		seq = seq+"X"; // X means end of sequence
		String AA = "";
		
		double tempDouble;
		
		try {
			for (char c : seq.toCharArray()) {
				if (c < 'A' || c > 'Z') {
					AA += String.valueOf(c);
				}
				else {
					if (AA.length() >1) {
						if (AA.charAt(0) == '+' ) {
							tempDouble = Double.parseDouble(AA.substring(1));
							tempDouble = Math.round(tempDouble*1000)/1000.0;
							AA = String.valueOf(tempDouble) + "_" +  String.valueOf(c) +  "_Nterm";
							if (Modification.getMod(AA) == null) {
								AA = String.valueOf(tempDouble) + "_" +  "X" +  "_Nterm";
							}
							output.add(Modification.getMod(AA));
						}
						else {
							tempDouble = Double.parseDouble(AA.substring(2));
							tempDouble = Math.round(tempDouble*1000)/1000.0;
							AA = String.valueOf(tempDouble) + "_" +  AA.substring(0, 1) + "_any";
							if (Modification.getMod(AA) == null) {
								AA = String.valueOf(tempDouble) + "_" +  "X" +  "_Nterm";
							}
							output.add(Modification.getMod(AA)); // ModificationHash �� �׳� ���Ƿ� �ϴ� ��а���. ���߿� ��¥ �Լ��̸����� �ٲ㼭 �ϱ�.
						}					
					}
					AA = String.valueOf(c);
				}
			}
		} catch (Exception e) {
			
		}

		
		return output;
	}

	/**
	 * NTT
	 * 
	 * @param peptide
	 * @param termini
	 * @return
	 */
	private int calculateNTT(String peptide, String termini) {
		int num = 0;
		
		String str = "";
		
		for(char c : peptide.toCharArray()) {
			if (c == '.' || (c >= 'A' && c <= 'Z'))
				str += c;
		}

		char prev = str.charAt(0);
		char cur = str.charAt(str.lastIndexOf('.')-1);

		for (char c : termini.toCharArray()) {
			if (c == prev)
				num++;
			if (c == cur)
				num++;
		}

		return num;
	}

	private String isTarget(String protein, String prefix) {
		if (protein.startsWith(prefix))
			return "decoy";
		return "target";
	}

	private String getPeptideStripSeq(String peptide) {
		String seq = peptide.substring(2, peptide.length() - 2);
		
		String str = "";
		
		for(char c : seq.toCharArray()) {
			if (c >= 'A' && c <= 'Z')
				str += c;
		}
		
		return str;
	}

}

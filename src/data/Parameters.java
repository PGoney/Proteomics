package proteomics.data;

import java.io.IOException;

public class Parameters {
	public static String isobaric_label;
	public static double[] isobaric_tags;
	public static double[][] isobaric_matrix;
//	private static Map<String, Modification> modMap;
	
	private static int ROUNDING = 3;
	
	public static int getROUNDING() {
		return ROUNDING;
	}
	public static void setROUNDING(int rOUNDING) {
		ROUNDING = rOUNDING;
	}

	/**
	 * The array for residue mass of amino acid. It can be used by "array['#Some amino acid character'-'A']"
	 */
	public static final double aminoacid_masss[]={
			71.03711,	0,			103.00919, 115.02694,	129.04259, 
			147.06841,	57.02146,	137.05891, 113.08406,	0, 
			128.09496,	113.08406,	131.04049, 114.04293,	0, 
			97.05276,	128.05858,	156.10111, 87.03203,	101.04768,
			0,			99.06841,	186.07931, 0,			163.06333,	0};
	/**
	 * 
	 * If you want to get modification object, you should enter the proper modification expression.
	 * Examples:  	When the value of "ROUNDING" is 3,
	 * 				getModification("S-18.010565", 3)
	 * 				-> return Modification "Dehydrated(S3)"
	 * 				getModification("Nterm+42.010565", 0)
	 * 				-> return Modification "Acetyl/Nterm(*0)"	
	 * @param	modification expression, the position of this modified amino acid in the peptide
	 * @return	the instance of Modification.
	 * @throws IOException
	 */
	public static Modification getModification(String site, String position, String deltaMass) {
		return Modification.getMod(site, position, deltaMass);
	}
	
	
//	public static void main(String[] args) {
//		Modification mod =getModification("Nterm+42.010565", 0);
//		System.out.println(mod);
//		System.out.println(Character.isAlphabetic('C'));
//	}
}

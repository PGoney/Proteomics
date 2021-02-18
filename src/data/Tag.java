package proteomics.data;

import java.util.ArrayList;

public class Tag {

	private ArrayList<Peak> peakList = null;
	private double tolerance = 0;
	public Tag (ArrayList<Peak> peakList, double tolerance) {this.peakList = peakList; this.tolerance = tolerance;}
	
	public ArrayList<String> getPossibleSequences () {
		ArrayList<String> possibleSequences = new ArrayList<String>();
		getPossibleSequences(possibleSequences, 0, new StringBuilder());		
		return possibleSequences;
	}
	
	private void getPossibleSequences(ArrayList<String> sequences, int index, StringBuilder sb) {
		int nextIndex = index+1;
		if(nextIndex >= this.getSizeOfPeaks()) {
			sequences.add(sb.toString());
			return;
		}
		
		char[] pAAs = explainableAAfromTwoPeaks(peakList.get(index).getMass(), peakList.get(nextIndex).getMass(), tolerance, 1);
		
		for(char aa : pAAs) {
			sb.setLength(nextIndex);
			sb.append(aa);
			getPossibleSequences(sequences, nextIndex, sb);
		}
	}
	
	public int getSizeOfPeaks () {
		return this.peakList.size();
	}
	
	public int getTagLength () {
		return this.peakList.size() - 1;
	}
	
	public static char[] explainableAAfromTwoPeaks (double peak1, double peak2, double tolerance, int charge) {
		int magicNumber = 26;
		char[] pAAs = new char[magicNumber];
		double[] aaMasses = Parameters.aminoacid_masss;
		int index = 0;
		
		double diff = Math.abs(peak1 - peak2);
		for(int i=0; i<aaMasses.length; i++) {
			if(aaMasses[i] == 0) continue;
			// within a tolerance.
			if(diff > (aaMasses[i] - tolerance)/charge && diff < (aaMasses[i] + tolerance)/charge) {
				// find possible aa.
				pAAs[index++] = (char) (i+'A');
			}
		}
		
		char[] returnAAs = null;
		if(index != 0) returnAAs = new char[index];
		for(int i=0; i<index; i++) returnAAs[i] = pAAs[i];
		
		return returnAAs;
		
	}
}

package proteomics.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Modification {
	
	private static HashMap<String, Modification> map = new HashMap<String, Modification>();
	
	/**
	 * Modification key: deltaMass_site_position.
	 * 
	 * @param site
	 * @param position
	 * @param deltaMass
	 * @return
	 */
	public static Modification getMod (String site, String position, String deltaMass) {
		String key = deltaMass +"_" + site + "_" + position;
		if(map.size() == 0)
			try {
				initMap();
			} catch (IOException e) {
				e.printStackTrace();
			}
		Modification modification = map.get(key);
		
		if(modification == null) {
			System.err.println("There is no matched modification: " + key);
		}
		
		return modification;
	}
	
	public static Modification getMod (String key) {
		
		if(map.size() == 0)
			try {
				initMap();
			} catch (IOException e) {
				e.printStackTrace();
			}
		Modification modification = map.get(key);
		
		if(modification == null) {
			System.err.println("There is no matched modification: " + key);
		}
		
		return modification;
	}
	
	public static void initMap() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res//unimod.xml"));	
		String key;
		String line;
		String[] lineSplit;
		String title;
		String site;
		String position;
		String deltaM = "";
		String chemicalComposition = "";
		String temp;
		ArrayList<String> ar = new ArrayList<String>();

		
		while ((line=br.readLine()) != null) {
			if (line.contains("umod:mod ")) {
				title = line.split("=")[1].split(" ")[0].split("\"")[1];
				
				line=br.readLine();
				while(!line.contains("/umod:mod")) {
					line=br.readLine();
					if (line.contains("umod:specificity ")) {
						lineSplit =line.split("=");
						site = lineSplit[2].split("\"")[1];
						if (site.length()>1) {
							site = "X";
						}
						position = lineSplit[3].split("\"")[1];
						if (position.contains("N-term")) {
							position = "Nterm";
						}
						else if (position.contains("C-term")) {
							position = "Cterm";
						}
						else {
							position = "any";
						}
						ar.add(site + "_" + position);
					}
					if (line.contains("umod:delta ")) {
						lineSplit =line.split("=");
						deltaM = lineSplit[1].split(" ")[0].split("\"")[1];
						deltaM = String.valueOf(Math.round(Double.parseDouble(deltaM)*1000)/1000.0);
						if (line.contains("composition")) {
							chemicalComposition = lineSplit[3].split(">")[0];
						}
						else {
							line = br.readLine();
							temp = line.substring(line.indexOf("comp"));
							chemicalComposition = temp.split("=")[1].split(">")[0];
						}
						
					}

				}
				for (int i=0 ; i<ar.size();i++) {
					site = ar.get(i).split("_")[0];
					position = ar.get(i).split("_")[1];
					key = deltaM + "_" + ar.get(i);
					map.put(key, new Modification(title, Double.parseDouble(deltaM), site.charAt(0), position, chemicalComposition  ));
				}
				ar.clear();

				
			}

		}
		
		br.close();
	}
	
	private String name;
	private double deltaMass;
	private String chemicalComposition;
	private String position;	//	Nterm, Cterm, Side-chain
	private char site;
	


	public Modification(String name, double deltaMass, char site, String position, String chemicalComposition) {
		this.name = name;
		this.deltaMass = deltaMass;
		this.site = site;
		this.position = position;
		this.chemicalComposition = chemicalComposition;


	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public double getDeltaMass() {
		return deltaMass;
	}



	public void setDeltaMass(double deltaMass) {
		this.deltaMass = deltaMass;
	}



	public String getChemicalComposition() {
		return chemicalComposition;
	}



	public void setChemicalComposition(String chemicalComposition) {
		this.chemicalComposition = chemicalComposition;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public char getSite() {
		return site;
	}



	public void setSite(char site) {
		this.site = site;
	}


	

	
//	public String toString() {
//		String modString = "";
//		if( name.startsWith(aminoAcid+"") )
//			modString =  "" + (aminoAcid=='*'?positionConstraint:aminoAcid) + (deltaMass>0?"+"+deltaMass:deltaMass);
//		else
//			modString =  name;
//		if( positionConstraint.endsWith("term") )
//			modString += "/" + positionConstraint;
//		return modString + "(" + aminoAcid + position + ")";
//	}

}

package proteomics.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import proteomics.feature.SPECTRUM_FEATURE;

/**
 * 
 * This is for storing set of PSM.
 * 
 * @author pjw23
 *
 */
public class Spectra {

	private Set<String> file_name_set;
	private String file_ext;
	
	private Map<Integer, Spectrum> spec_map;
	
	public Spectra() {
		file_name_set = new TreeSet<>();
		file_ext = null;
		
		spec_map = new HashMap<>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (String file_name : file_name_set)
			sb.append("Input: " + file_name + "\n");
		sb.append("# of Spectrum: " + size());
		
		return sb.toString();
	}
	
	public void read(String file_path) throws FileExtensionException {
		if (file_ext == null) {
			if (file_path.endsWith(".mgf"))
				file_ext = ".mgf";
			else
				throw new FileExtensionException("E: " + file_path + " is not supported");
		}
		
		if (!file_path.endsWith(file_ext))
			throw new FileExtensionException("E: " + file_path + " does not match *" + file_ext);
		
		try {
			File file = new File(file_path);
			
			if (file_name_set.contains(file.getName()))
				return;
			
			if (file_ext.equals(".mgf")) {
				readMGF(file);
			}
			
			file_name_set.add(file.getName());
		} catch (IOException e) {
			
		}
	}

	private void readMGF(File file) throws IOException {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			
			String file_name_without_ext = extractFileNameWithoutExt(file.getName());
			
			Spectrum spectrum = null;
			int scan_id = -1;
			List<Peak> peak_list = null;
			
			String line;
			while ((line = br.readLine()) != null) {
//				if (size() == 10)
//					break;
				
				if (line.equals("BEGIN IONS")) {
					spectrum = new Spectrum();
					
					scan_id = -1;
					peak_list = new ArrayList<>();
					
					continue;
				}
				else if (line.equals("END IONS")) {
					if (scan_id == -1)
						continue;
					
//					spectrum.setMSLevel(2);
//					spectrum.setParentInfo(-1);
					
					spectrum.set(SPECTRUM_FEATURE.PEAK_LIST, peak_list);
					
					int hash_key = extractHashKey(file_name_without_ext, scan_id);
					
					spec_map.put(hash_key, spectrum);
					
					continue;
				}
				
				if (line.contains("=")) {
					String[] tok = line.split("=");
					String key = tok[0];
					String value = tok[1];
					
					switch (key) {
					case "TITLE":
						scan_id = findScanNum(value);
						break;
					case "SCANS":
						scan_id = Integer.parseInt(value);
						break;
					case "PEPMASS":
						if (value.contains(" ")) {
							String[] tok2 = value.split(" ");
							
							double precursor_mass = Double.parseDouble(tok2[0]);
							double precursor_intensity = Double.parseDouble(tok2[1]);

							spectrum.set(SPECTRUM_FEATURE.PRECURSOR_MASS, precursor_mass);
							spectrum.set(SPECTRUM_FEATURE.PRECURSOR_INTENSITY, precursor_intensity);
						}
						else {
							double precursor_mass = Double.parseDouble(value);

							spectrum.set(SPECTRUM_FEATURE.PRECURSOR_MASS, precursor_mass);
						}
						break;
					case "CHARGE":
						int charge = Integer.parseInt(value.substring(0, value.lastIndexOf('+')));
						spectrum.set(SPECTRUM_FEATURE.CHARGE, charge);
						break;
					case "RTINSECONDS":
//						double RT = Double.parseDouble(value);
						break;
					}
				}
				else {
					String[] tok = line.split(" ");
					
					double mass = Double.parseDouble(tok[0]);
					double intensity = Double.parseDouble(tok[1]);
					
					peak_list.add(new Peak(mass, intensity));
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			br.close();
		}
	}
	
	/** 
	 * How the scan number are read on file by file basis. 
	 * @param spectrum, String line - file in line 
	 * @param boolean scanCheck - describe scan number within spectrum
	 * @return void
	 */
	private int findScanNum(String line) {
		String[] tok = line.split(" ");
		for (int i = 0; i < tok.length; i++) {
			if (tok[i].contains("scans:")) { // TITLE=File66 Spectrum74 scans: 2166
				return Integer.parseInt(tok[i + 1]);
			} else if (tok[i].contains("scan=")) { // TITLE=170628_H1299_HPH_F01.4.4.1
													// File:"170628_H1299_HPH_F01.raw", NativeID:"controllerType=0
													// controllerNumber=1 scan=4"
				String[] str = tok[i].split("=");
				return Integer.parseInt(str[1].substring(0, str[1].length() - 1));
			}
		}
		
		tok = line.split("\\.");
		return Integer.parseInt(tok[2]);	// TITLE=170628_H1299_HPH_F01.4.4.1
	}

	private int extractHashKey(String file_name_without_ext, int scan_id) {
		String str = file_name_without_ext + String.valueOf(scan_id);
		return str.hashCode();
	}

	private String extractFileNameWithoutExt(String file_name) {
		String result;

		int ext_length = file_ext.length();
		
		result = file_name.substring(0, file_name.length() - ext_length);
		
		return result;
	}
	
	public Set<String> getFileNameList() {
		return file_name_set;
	}
	
	public int size() {
		return spec_map.size();
	}
	
	public Set<Integer> keySet() {
		return spec_map.keySet();
	}
	
	public Spectrum get(int key) {
		return spec_map.get(key);
	}
}

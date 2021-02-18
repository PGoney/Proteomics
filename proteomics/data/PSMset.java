package proteomics.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import proteomics.feature.PSM_FEATURE;

/**
 * This is for storing set of PSM.
 * 
 * @author pjw23
 *
 */
public class PSMset {

	private Set<String> file_name_set;
	private String file_ext;
	
	private Map<Integer, PSM> psm_map;
	
	public PSMset() {
		file_name_set = new TreeSet<>();
		file_ext = null;
		
		psm_map = new HashMap<>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (String file_name : file_name_set)
			sb.append("Input: " + file_name + "\n");
		sb.append("# of PSM: " + size());
		
		return sb.toString();
	}

	/**
	 * Read the PSM file.
	 * 
	 * @param file_path
	 * @throws FileExtensionException
	 */
	public void read(String file_path) throws FileExtensionException {
		if (file_ext == null) {
			if (file_path.endsWith(".pep.xml"))
				file_ext = ".pep.xml";
			else
				throw new FileExtensionException("E: " + file_path + " is not supported");
		}
		
		if (!file_path.endsWith(file_ext))
			throw new FileExtensionException("E: " + file_path + " does not match *" + file_ext);

		try {
			File file = new File(file_path);
			
			if (file_name_set.contains(file.getName()))
				return;
			
			if (file_ext.equals(".pep.xml")) {
				readPepXml(file_path);
			}
			
			file_name_set.add(file.getName());
		} catch (IOException e) {
			
		} catch (ParserConfigurationException e) {

		} catch (SAXException e) {

		}
	}

	private void readPepXml(String file_name) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;

		documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(file_name);

		Element msms_run_summary = null;

		Element sample_enzyme = null;
		Element search_summary = null;
		Element spectrum_query = null;
		
		Element search_result = null;
		Element search_hit = null;
		
		Element search_score = null;
		
		String base_name;
		int scan_id = -1;
		int assumed_charge = -1;
		
		String peptide_seq = null;
		String protein_seq = null;
		double calculated_mass = -1;
		double delta_mass = -1;
		int num_of_missed_cleavage = -1;
		
		List<SearchScore> score_list;

		{
			Element msms_pipeline_analysis = document.getDocumentElement();

			NodeList msms_pipeline_analysis_children = msms_pipeline_analysis.getChildNodes();
			for (int i = 0; i < msms_pipeline_analysis_children.getLength(); i++) {
				Node node = msms_pipeline_analysis_children.item(i);

				if (node.getNodeType() != Node.ELEMENT_NODE)
					continue;

				Element ele = (Element) node;

				String nodeName = ele.getNodeName();
				if (nodeName.equals("msms_run_summary"))
					msms_run_summary = ele;
			}
			
			base_name = new File(msms_run_summary.getAttribute("base_name")).getName();
		}
		
		{
			NodeList msms_run_summary_children = msms_run_summary.getChildNodes();
			for (int i = 0; i < msms_run_summary_children.getLength(); i++) {
				Node node = msms_run_summary_children.item(i);
				
				if (node.getNodeType() != Node.ELEMENT_NODE)
					continue;
				
				Element ele = (Element) node;
				
				String nodeName = ele.getNodeName();
				if (nodeName.equals("samle_enzyme"))
					sample_enzyme = ele;
				else if (nodeName.equals("search_summary"))
					search_summary = ele;
				else if (nodeName.equals("spectrum_query")) {
					spectrum_query = ele;
					
					String title = spectrum_query.getAttribute("spectrumNativeID");
					scan_id = findScanNum(title);
					assumed_charge = Integer.parseInt(spectrum_query.getAttribute("assumed_charge"));
					
					NodeList spectrum_query_children = spectrum_query.getChildNodes();
					for (int j = 0; j < spectrum_query_children.getLength(); j++) {
						Node node2 = spectrum_query_children.item(j);
						
						if (node2.getNodeType() != Node.ELEMENT_NODE)
							continue;
						
						Element ele2 = (Element) node2;
						
						String nodeName2 = ele2.getNodeName();
						if (nodeName2.equals("search_result"))
							search_result = ele2;
					}
					
					NodeList search_result_children = search_result.getChildNodes();
					for (int j = 0; j < search_result_children.getLength(); j++) {
						Node node2 = search_result_children.item(j);
						
						if (node2.getNodeType() != Node.ELEMENT_NODE)
							continue;
						
						Element ele2 = (Element) node2;
						
						String nodeName2 = ele2.getNodeName();
						if (nodeName2.equals("search_hit") && ele2.getAttribute("hit_rank").equals("1")) {
							search_hit = ele2;
							break;
						}
					}
					
					String prev_peptide = search_hit.getAttribute("peptide_prev_aa");
					String next_peptide = search_hit.getAttribute("peptide_next_aa");
					
					peptide_seq = prev_peptide + "." + search_hit.getAttribute("peptide") + "." + next_peptide;
					protein_seq = search_hit.getAttribute("protein");
					calculated_mass = Double.parseDouble(search_hit.getAttribute("calc_neutral_pep_mass"));
					delta_mass = Double.parseDouble(search_hit.getAttribute("massdiff"));
					num_of_missed_cleavage = Integer.parseInt(search_hit.getAttribute("num_missed_cleavages"));

					score_list = new ArrayList<>();
					
					NodeList search_hit_children = search_hit.getChildNodes();
					for (int j = 0; j < search_hit_children.getLength(); j++) {
						Node node2 = search_hit_children.item(j);
						
						if (node2.getNodeType() != Node.ELEMENT_NODE)
							continue;
						
						Element ele2 = (Element) node2;
						
						String nodeName2 = ele2.getNodeName();
						if (nodeName2.equals("search_score")) {
							search_score = ele2;
							String score_name = search_score.getAttribute("name");
							double score_value = Double.parseDouble(search_score.getAttribute("value"));
							score_list.add(new SearchScore(score_name, score_value));
						}
					}

					int hash_key = extractHashKey(base_name, scan_id);
					
					PSM psm = new PSM();
					
					psm.set(PSM_FEATURE.CALCULATED_NEUTRAL_PEP_MASS, calculated_mass);
					psm.set(PSM_FEATURE.DELTA_MASS, delta_mass);
					psm.set(PSM_FEATURE.NUM_OF_MISSED_CLEAVAGE, num_of_missed_cleavage);
					psm.set(PSM_FEATURE.PEPTIDE_SEQUENCE, peptide_seq);
					psm.set(PSM_FEATURE.PROTEIN_SEQUENCE, protein_seq);
					psm.set(PSM_FEATURE.SEARCH_SCORE, score_list);
					
					psm_map.put(hash_key, psm);
				}
			}
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
		return Integer.parseInt(tok[1]);	// TITLE=170628_H1299_HPH_F01.4.4.1
	}

	private int extractHashKey(String file_name_without_ext, int scan_id) {
		String str = file_name_without_ext + String.valueOf(scan_id);
		return str.hashCode();
	}

	private String extractFileNameWithoutExt(String file_name) {
		String result;
		
		File file = new File(file_name);
		String file_base_name = file.getName();
		int ext_length = file_ext.length();
		
		result = file_base_name.substring(0, file_base_name.length() - ext_length);
		
		return result;
	}
	
	public Set<String> getFileNameList() {
		return file_name_set;
	}

	public int size() {
		return psm_map.size();
	}

	public Set<Integer> keySet() {
		return psm_map.keySet();
	}

	public PSM get(int key) {
		return psm_map.get(key);
	}

}

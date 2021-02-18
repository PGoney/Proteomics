package proteomics.ui.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import proteomics.data.FileExtensionException;
import proteomics.data.PSMset;
import proteomics.data.Spectra;
import proteomics.feature.FEATURE_LIST;
import proteomics.feature.Feature;
import proteomics.feature.PSM_FEATURE;
import proteomics.feature.SPECTRUM_FEATURE;
import proteomics.preprocess.PREPROCESS_METHOD;
import proteomics.preprocess.Preprocess;;

public class FrameFeatureList extends JFrame {

	private JPanel contentPane;
	private JPanel panel_Read;
	private JPanel panel_Preprocess;
	private JPanel panel_FeatureList;
	private JLayeredPane layeredPane;
	private JLabel lblFeaturelist_PSM;
	private JPanel panel_Write;
	private JLabel lblWrite;
	private JButton btnPrev;
	private JButton btnRead;
	private JTabbedPane tabbedPane;
	private JPanel panel_Zeropadding;
	private JPanel panel_INFO4;
	private JPanel panel_PARAMETER4;
	private JTextField textField_PeakDimension;
	private JLabel label_1;
	private JButton button_zeroPadding;
	private JLabel label_2;
	private JPanel panel_TOPN;
	private JButton button;
	private JLabel lblToleranceType;
	private JPanel panel_THRESHOLD;
	private JButton button_2;
	private JLabel lblThreshold_1;
	private JPanel panel_Scaling;
	private JPanel panel_PARAMETER1;
	private JButton button_Scaling;
	private JPanel panel_INFO1;
	private JTextField txtThisIsScaling;
	private JComboBox comboBox;
	private JLabel lblInputN;
	private JTextField textField_2;
	private JLabel lblThreshold;
	private JTextField textField_3;
	private JTextField txtThisIsFiltering;
	private JTextField txtThisIsImputation;
	private JTextField txtThisIsZeropadding;
	private JButton btnLoad;
	private JScrollPane scrollPane_SPFeatures;
	private JScrollPane scrollPane_PSMFeatures;
	private JScrollPane scrollPane_SCAN;
	private JScrollPane scrollPane_MSMS;
	private JScrollPane scrollPane_SearchResult;
	private JScrollPane scrollPane_SearchParameter;
	private JTextArea textArea_LOG;

	
	private JTextArea textArea_MSMS;
	private JTextArea textArea_Search;
	private JTextArea textArea_Param;
	private JRadioButton rdbtnNewRadioButton;
	private JPanel panel_Write_Features;
	private JLabel lblPanelfeatureafterWritePage;
	private JTable table_SP;
	private JTable table_PSM;
	private JTextField textField_Min;
	private JTextField textField_8;
	private JLabel lblMax;
	private JTextField textField_Max;
	private JRadioButton rdbtnTic;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JTextPane txtpnMSMS;
	private JTextPane txtpnSearchResult;
	private JTextPane textPane;
	private JTextPane txtpnSearchParameter;

	//File list
	private Filelist MSMS_List = new Filelist();
	private Filelist SearchResult_List = new Filelist();
	private Filelist SearchParameter_List = new Filelist();
	//Spectra
	Spectra sp = new Spectra();
	PSMset psm = new PSMset();
	//Feature List- Spectrum
	
	SPECTRUM_FEATURE[] sp_features = SPECTRUM_FEATURE.values();
	FEATURE_LIST<?>[] Spectrum_list = {
			 
			 SPECTRUM_FEATURE.CHARGE,
			 SPECTRUM_FEATURE.PEAK_LIST,
			 SPECTRUM_FEATURE.PEAK_COUNT,
			 SPECTRUM_FEATURE.BASE_PEAK_MASS,
			 SPECTRUM_FEATURE.BASE_PEAK_INTENSITY,
			 SPECTRUM_FEATURE.TIC,
			 SPECTRUM_FEATURE.PRECURSOR_MASS,
			 SPECTRUM_FEATURE.PRECURSOR_INTENSITY,
//			 SPECTRUM_FEATURE.MAX_SEQUENCE_TAG_LENGTH,	 
	};
	//Feature List- Psm
	PSM_FEATURE[] psm_features = PSM_FEATURE.values();
	FEATURE_LIST<?>[] PSM_list = {
		
			PSM_FEATURE.ANNOTATED_PEAK_LIST,
			PSM_FEATURE.CALCULATED_NEUTRAL_PEP_MASS,
			PSM_FEATURE.DELTA_MASS,
			PSM_FEATURE.MATCHED_MASS_ERROR_MEAN,
			PSM_FEATURE.MATCHED_MASS_ERROR_STD,
			PSM_FEATURE.NTT,
			PSM_FEATURE.NUM_OF_MISSED_CLEAVAGE,
			PSM_FEATURE.NUM_OF_MODIFICATION,
			PSM_FEATURE.PEPTIDE_SEQUENCE,
			PSM_FEATURE.PEPTIDE_SEQUENCE_LENGTH,
			PSM_FEATURE.PROTEIN_SEQUENCE,
			PSM_FEATURE.PROTEIN_SEQUENCE_LENGTH,
			PSM_FEATURE.SEARCH_SCORE,
			PSM_FEATURE.TARGET_DECOY_LABEL,
			
	};
	
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JTextArea textArea_Peaklist;
	private JTextArea textArea_2spfeatures;
	private JTextArea textArea_2psmfeatures;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	
	/**
	 * Launch the application.
	 */


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameFeatureList frame = new FrameFeatureList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 */
	public FrameFeatureList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmRead = new JMenuItem("Read");
		mntmRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_Read);
			}
		});
		mnMenu.add(mntmRead);
		
		JMenuItem mntmPrerprocess = new JMenuItem("Prerprocess");
		mntmPrerprocess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_Preprocess);
			}
		});
		mnMenu.add(mntmPrerprocess);
		
		JMenuItem mntmWrite = new JMenuItem("Write");
		mntmWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_Write);
			}
		});
		mnMenu.add(mntmWrite);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		layeredPane = new JLayeredPane();
		
		JScrollPane scrollPane_LOG = new JScrollPane();
		scrollPane_LOG.setViewportBorder(new TitledBorder(null, "LOG", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 884, Short.MAX_VALUE))
						.addComponent(scrollPane_LOG, GroupLayout.PREFERRED_SIZE, 890, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(scrollPane_LOG, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
		);
		
		
		textArea_LOG = new JTextArea();
		textArea_LOG.setEditable(false);
		scrollPane_LOG.setViewportView(textArea_LOG);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		panel_Read = new JPanel();
		layeredPane.add(panel_Read, "name_204537727083439");
		
		JButton btnNext = new JButton("NEXT");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_FeatureList);
			}
		});
		Feature.load(sp, psm);
		
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_LOG.append("Loading..\n");
				FileDialog dialog = new FileDialog(new JFrame(), "Load", FileDialog.LOAD);
				dialog.setMultipleMode(true);
				dialog.setVisible(true);
				
				
				
				File[] file_list = dialog.getFiles();				
				
				for (File file : file_list) {
					//String filename = file.getAbsolutePath(); //get file path..
					String filename = file.getName();
					if (filename.endsWith(".mgf")) {
						//MSMS_list.insert(file);
						MSMS_List.add(file);
						textArea_MSMS.append(filename+"\n");
						

					}else if (filename.endsWith(".xml")) {
						//search_result_list.insert(file);
						SearchResult_List.add(file);
						textArea_Search.append(filename+"\n");	
						
						
					}else if (filename.endsWith(".params")) {
						//search_parameter_list.insert(file);
						SearchParameter_List.add(file);
						textArea_Param.append(filename+"\n");
						
					}
								
					//scrollPane_1.setViewportView(filename);
				}
				
				try {
					for (File f : file_list) {
						if (f.getName().endsWith(".mgf"))
							sp.read(f.getAbsolutePath());
						if (f.getName().endsWith(".pep.xml"))
							psm.read(f.getAbsolutePath());
					}
				} catch (Exception e1) {
					
				}
				
				
				
				textArea_LOG.append(sp.toString() + "\n");
				textArea_LOG.append(psm.toString() + "\n");
				
				
//				textArea_LOG.append("# of specrum: " + sp.size() + "\n");
//				textArea_LOG.append("Load Files\n");
//				for (String f_name : sp.getFileNameList())
//					textArea_LOG.append(f_name + "\n");
				
				/*//test code...//
				for (int key : sp.keySet()) {
					textArea_LOG.append(sp.get(key).toString() + "\n");
					break;
				}
				
				textArea_LOG.append(psm.toString()+"\n");
				for (int key : psm.keySet()) {
					textArea_LOG.append(psm.get(key).toString() + "\n");
					break;
				}
				*/
				
				//System.out.println(MSMS_List);
				//System.out.println(SearchResult_List);
				
				/*
				for (FEATURE_LIST<?> features : Spectrum_list) {
					textArea.append(features.toString()+"\n");
					if (features.name() == "PEAK_LIST") {
						textArea_Peaklist.append(features.getStatistics(sp));
					}
				}*/
				
				for (SPECTRUM_FEATURE feature : sp_features) {
					textArea.append(feature.toString()+"\n");//print spectrum feature list page
					if (feature.isActive() == true) {
						textArea_2spfeatures.append(feature.name()+"\n");
					}
					
					if (feature.name() == "PEAK_LIST") {
						textArea_Peaklist.append(feature.getStatistics(sp).toString());
					}
				}
				
				for (PSM_FEATURE feature : psm_features) {
					textArea_1.append(feature.toString()+"\n");//print psm feature list page
					
					if (feature.isActive() == true) {
						textArea_2psmfeatures.append(feature.name()+"\n");
					}
					
				}
				
				
				
				
				/*
				for (FEATURE_LIST<?> features : PSM_list) 
					textArea_1.append(features.toString() + "\n");
					
				}
				*/
				
				
				
				textArea_LOG.append("Read complete..\n");
				
			}
		});
			
		
		scrollPane_MSMS = new JScrollPane();
		scrollPane_MSMS.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MS/MS File", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		scrollPane_SearchResult = new JScrollPane();
		scrollPane_SearchResult.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Search Result File", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		scrollPane_SearchParameter = new JScrollPane();
		scrollPane_SearchParameter.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Search Parameter File", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_MSMS.setText(null);
				textArea_Search.setText(null);
				textArea_Param.setText(null);
				textArea_LOG.append("Reset complete...\n");
				
			}
		});
		
		txtpnMSMS = new JTextPane();
		txtpnMSMS.setEditable(false);
		txtpnMSMS.setText("This space is for upload MS/MS file. \nPlease upload only *.mgf files.");
		
		textPane = new JTextPane();
		textPane.setText("This space is for upload MS/MS file. \nPlease upload only *.mgf files.");
		
		txtpnSearchResult = new JTextPane();
		txtpnSearchResult.setEditable(false);
		txtpnSearchResult.setText("This space is for uplaod search result file. \nPlease upload *.pep.xml files.");
		
		txtpnSearchParameter = new JTextPane();
		txtpnSearchParameter.setEditable(false);
		txtpnSearchParameter.setText("This space is for uplaod search result file.\nPlease upload only Search Parameter files.");
		
		GroupLayout gl_panel_Read = new GroupLayout(panel_Read);
		gl_panel_Read.setHorizontalGroup(
			gl_panel_Read.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Read.createSequentialGroup()
					.addGroup(gl_panel_Read.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_Read.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 570, Short.MAX_VALUE)
							.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_panel_Read.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_panel_Read.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane_MSMS, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
								.addComponent(txtpnMSMS, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
							.addGap(40)
							.addGroup(gl_panel_Read.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(scrollPane_SearchResult)
								.addComponent(txtpnSearchResult, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addGroup(gl_panel_Read.createParallelGroup(Alignment.LEADING)
								.addComponent(txtpnSearchParameter, 0, 0, Short.MAX_VALUE)
								.addComponent(scrollPane_SearchParameter, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))
							.addGap(12)))
					.addContainerGap())
		);
		gl_panel_Read.setVerticalGroup(
			gl_panel_Read.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Read.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Read.createParallelGroup(Alignment.LEADING)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtpnSearchResult, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
						.addComponent(txtpnMSMS)
						.addComponent(txtpnSearchParameter, GroupLayout.PREFERRED_SIZE, 61, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 12, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel_Read.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_MSMS, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
						.addGroup(gl_panel_Read.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane_SearchResult, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
							.addComponent(scrollPane_SearchParameter, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)))
					.addGap(17)
					.addGroup(gl_panel_Read.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNext)
						.addComponent(btnLoad)
						.addComponent(btnReset))
					.addContainerGap())
		);
		///
		textArea_Param = new JTextArea();
		scrollPane_SearchParameter.setViewportView(textArea_Param);
		
		textArea_Search = new JTextArea();
		scrollPane_SearchResult.setViewportView(textArea_Search);
		
		textArea_MSMS = new JTextArea();
		scrollPane_MSMS.setViewportView(textArea_MSMS);
		///
		
		panel_Read.setLayout(gl_panel_Read);
		
		//list items...
		String[] demo_SP={"charge", "precursor mass", "precursor intensity", "base peak m/z", "base peak intensity"};
		String[] demo_PSM={"calculated neutral pep mass", "delta mass", "peptide sequence length", "matched mass error mean", "matched mass error standard deviation"};
		String[] demo_PeakList= {sp.toString()};
		
		panel_FeatureList = new JPanel();
		layeredPane.add(panel_FeatureList, "name_204760945006261");
		
		lblFeaturelist_PSM = new JLabel("PSM Feature List");
		
		btnPrev = new JButton("Prev");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_Read);
			}
		});
		
		btnRead = new JButton("Read");
		
		JScrollPane scrollPane_Spectrum = new JScrollPane();
		scrollPane_Spectrum.setViewportBorder(null);
		
		JScrollPane scrollPane_PSM = new JScrollPane();
		scrollPane_PSM.setViewportBorder(null);
		
		JLabel lblFeaturelist_SP = new JLabel("Sepctrum Feature List");
		GroupLayout gl_panel_FeatureList = new GroupLayout(panel_FeatureList);
		gl_panel_FeatureList.setHorizontalGroup(
			gl_panel_FeatureList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_FeatureList.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_FeatureList.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_Spectrum, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPrev)
						.addComponent(lblFeaturelist_SP))
					.addGroup(gl_panel_FeatureList.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_FeatureList.createSequentialGroup()
							.addGap(839)
							.addComponent(btnRead))
						.addGroup(gl_panel_FeatureList.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_FeatureList.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_PSM, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFeaturelist_PSM))
							.addGap(481))))
		);
		gl_panel_FeatureList.setVerticalGroup(
			gl_panel_FeatureList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_FeatureList.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panel_FeatureList.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFeaturelist_PSM)
						.addComponent(lblFeaturelist_SP))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_FeatureList.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane_Spectrum, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_PSM, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_FeatureList.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRead)
						.addComponent(btnPrev)))
		);
		
		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		scrollPane_PSM.setViewportView(textArea_1);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane_Spectrum.setViewportView(textArea);
		
		//table Feature List..
		JCheckBox checkBox = new JCheckBox();
		//String[] ColumnNames = {"index", "Feature Name", "Feature Info", "select Info"};
		String[] ColumnNames = {"features", "info"};	
		/*
		Vector data = new Vector<>();
        for (int r = 0; r < feature_list.length; r++) {
            Vector row = new Vector<>();
            for (int c = 0; c < ColumnNames.length; c++) {
 
                row.add("값" + c);
            }
            data.add(row);
        }
		 */


		
		
		Object[][] SP_tableData =

            {{ "1", "the charge state of MS/MS spectrum"},

             {"2", "precursor mass",  "mass value of MS spectrum", true},

             {"3", "precursor intensity", "intensity value of MS spectrum", new Boolean(true)},

             {"4", "TIC(Total Intensity Count)", "the value of Total Intensity Count", new Boolean(true)},
             
             {"5", "base peak m/z", "m/z value of base peak(abundant ion)", new Boolean(true)},
            
             {"6", "base peak intensity", "intensity value of base peak(abundant ion)", new Boolean(false)},
             
             {"7", "# of peaks", "the number of peaks in mgf file", new Boolean(false) },
             
             {"8", "max sequence tag", "the full tag of max sequence ", new Boolean(false) },
             
             {"9", "peak list", "The list to contains peaks of mgf file", new Boolean(false) }
             
             
            };
		
		Object[][] PSM_tableData =

            {{"9", "calculated neutral pep mass", "the peptide mass of neutral charge state", true},

             {"10", "delta mass",  "the difference of precursor neutral mass and calculated neutral pep mass", true},

             {"11", "peptide sequence length", "the length of peptide sequence", new Boolean(true)},

             {"12", "matched mass error mean", "The mean  value of m/z between  theoretical peak and annotated peak", new Boolean(true)},
             
             {"13", "matched mass error standard deviation", "The standard deviation value of m/z between  theoretical peak and annotated peak", new Boolean(true)},
           
             {"14", "search score","the degree of similarity between the experimental MS/MS spectrum and the theoretical spectrum", new Boolean(false)},
             
             {"15", "NTT","the Number of peptide Termini consistent with the Type of enzymatic cleavage used", new Boolean(false)},
             
             {"16", "# of missed cleavage", "the number of miss cleavage", new Boolean(false)},
             
             {"17", "# of modification", "the number of modification", new Boolean(false)},
             
             {"18", "target/decoy label", "the label of target and decoy database", new Boolean(false) }
             
            };
		
		
		DefaultTableModel model_SP = new DefaultTableModel(SP_tableData, ColumnNames);
	
		DefaultTableModel model_PSM = new DefaultTableModel(PSM_tableData, ColumnNames);
		/*
		table_PSM = new JTable(model_PSM);
		scrollPane_PSM.setViewportView(table_PSM);
	
		table_SP = new JTable(model_SP);
		scrollPane_Spectrum.setViewportView(table_SP);
		
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		*/
		
		panel_FeatureList.setLayout(gl_panel_FeatureList);
		
		panel_Preprocess = new JPanel();
		layeredPane.add(panel_Preprocess, "name_204756139041608");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		scrollPane_SPFeatures = new JScrollPane();
		
		scrollPane_PSMFeatures = new JScrollPane();
		
		scrollPane_SCAN = new JScrollPane();
		
		GroupLayout gl_panel_Preprocess = new GroupLayout(panel_Preprocess);
		gl_panel_Preprocess.setHorizontalGroup(
			gl_panel_Preprocess.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Preprocess.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 646, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_Preprocess.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_SPFeatures, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addComponent(scrollPane_PSMFeatures, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_SCAN, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_Preprocess.setVerticalGroup(
			gl_panel_Preprocess.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Preprocess.createSequentialGroup()
					.addGroup(gl_panel_Preprocess.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_Preprocess.createSequentialGroup()
							.addContainerGap()
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 412, Short.MAX_VALUE))
						.addGroup(gl_panel_Preprocess.createSequentialGroup()
							.addGap(22)
							.addComponent(scrollPane_SPFeatures, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_PSMFeatures, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_SCAN, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
		textArea_2psmfeatures = new JTextArea();
		textArea_2psmfeatures.setEditable(false);
		scrollPane_PSMFeatures.setViewportView(textArea_2psmfeatures);
		
		lblNewLabel_2 = new JLabel("PSM Features");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_PSMFeatures.setColumnHeaderView(lblNewLabel_2);
		
		textArea_2spfeatures = new JTextArea();
		textArea_2spfeatures.setEditable(false);
		scrollPane_SPFeatures.setViewportView(textArea_2spfeatures);
		
		lblNewLabel_1 = new JLabel("Spectrum Features");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_SPFeatures.setColumnHeaderView(lblNewLabel_1);
		
		textArea_Peaklist = new JTextArea();
		textArea_Peaklist.setEditable(false);
		scrollPane_SCAN.setViewportView(textArea_Peaklist);
		
		JLabel lblPeakCounts = new JLabel("Peak Counts");
		lblPeakCounts.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_SCAN.setColumnHeaderView(lblPeakCounts);
		
		panel_Scaling = new JPanel();
		tabbedPane.addTab("Peak Scaling", null, panel_Scaling, null);
		
		panel_PARAMETER1 = new JPanel();
		panel_PARAMETER1.setBorder(new TitledBorder(null, "Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				
		
		//Peak Scaling panel - String to Double complete , add spectra mgf file
		button_Scaling = new JButton("Submit");
		button_Scaling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String Max_num = textField_Max.getText();
				String Min_num = textField_Min.getText();
				
				double MaxValue = Double.parseDouble(Max_num);
				double MinValue = Double.parseDouble(Min_num);
			
				textArea_LOG.append("Max: "+ MaxValue+"\n");
				textArea_LOG.append("Min: "+ MinValue+"\n");
				
				System.out.println(MaxValue + MinValue);
				
				String[] array = {"This", "is", "Just", "Array"};
				Preprocess preprocess = new Preprocess(); 
				
				
				/*
				for (int key : sp.keySet()) {
					Spectrum spec = sp.get(key);
					System.out.println(spec);
				
				}*/
				
				System.out.println(sp);
				System.out.println(psm);
				
//				System.out.println(SPECTRUM_FEATURE.TIC);
				String[] args_scaling= {
						"--min_max","--min",Min_num,"--max",Max_num,
				};
				SPECTRUM_FEATURE.PEAK_LIST.addPreprocess(PREPROCESS_METHOD.PEAK_SCALING, args_scaling);
				
				for (SPECTRUM_FEATURE feature : sp_features) {
					if (feature.name() == "PEAK_LIST") {
						textArea_LOG.append(feature.toString()+"\n");
						System.out.println(feature.getStatistics(sp)+"\n");
					}
					
					/*
					for ( PREPROCESS_METHOD method : PREPROCESS_METHOD.values()){
						System.out.println(method.name());
						System.out.println(feature.getPreprocessList().canPreprocess(method.bit()));
					}
					*/
				}
				
				
			}
		});
		
		
		
		textField_Min = new JTextField();
		textField_Min.setColumns(10);
		
		JLabel lblMin = new JLabel("Min:");
		
		lblMax = new JLabel("Max:");
		
		textField_Max = new JTextField();
		textField_Max.setColumns(10);
			
		
		GroupLayout gl_panel_PARAMETER1 = new GroupLayout(panel_PARAMETER1);
		gl_panel_PARAMETER1.setHorizontalGroup(
			gl_panel_PARAMETER1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_PARAMETER1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_PARAMETER1.createParallelGroup(Alignment.LEADING)
						.addComponent(button_Scaling, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_PARAMETER1.createSequentialGroup()
							.addComponent(lblMin, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_Min, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel_PARAMETER1.createSequentialGroup()
							.addComponent(lblMax, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_Max, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(363, Short.MAX_VALUE))))
		);
		gl_panel_PARAMETER1.setVerticalGroup(
			gl_panel_PARAMETER1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_PARAMETER1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_PARAMETER1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMin)
						.addComponent(textField_Min, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_PARAMETER1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_PARAMETER1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
							.addComponent(button_Scaling))
						.addGroup(gl_panel_PARAMETER1.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_panel_PARAMETER1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMax)
								.addComponent(textField_Max, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
		);
		panel_PARAMETER1.setLayout(gl_panel_PARAMETER1);
		
		panel_INFO1 = new JPanel();
		panel_INFO1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		txtThisIsScaling = new JTextField();
		txtThisIsScaling.setText("Please insert Min, Max value, Log Base Value or Specific value(TIC or Base Peak Intensity).");
		txtThisIsScaling.setColumns(10);
		GroupLayout gl_panel_Scaling = new GroupLayout(panel_Scaling);
		gl_panel_Scaling.setHorizontalGroup(
			gl_panel_Scaling.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Scaling.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Scaling.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_INFO1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
						.addComponent(panel_PARAMETER1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_Scaling.setVerticalGroup(
			gl_panel_Scaling.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Scaling.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_INFO1, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_PARAMETER1, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_INFO1.setLayout(new BoxLayout(panel_INFO1, BoxLayout.X_AXIS));
		panel_INFO1.add(txtThisIsScaling);
		panel_Scaling.setLayout(gl_panel_Scaling);
		
		JPanel panel_Filtering = new JPanel();
		tabbedPane.addTab("Peak Filtering", null, panel_Filtering, null);
		
		JTabbedPane tabbedPane_Filtering = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel panel_INFO2 = new JPanel();
		panel_INFO2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		txtThisIsFiltering = new JTextField();
		txtThisIsFiltering.setHorizontalAlignment(SwingConstants.LEFT);
		txtThisIsFiltering.setText("This is Filtering Panel. Filtering ");
		txtThisIsFiltering.setColumns(10);
		GroupLayout gl_panel_Filtering = new GroupLayout(panel_Filtering);
		gl_panel_Filtering.setHorizontalGroup(
			gl_panel_Filtering.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Filtering.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Filtering.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane_Filtering, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
						.addComponent(panel_INFO2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_Filtering.setVerticalGroup(
			gl_panel_Filtering.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Filtering.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_INFO2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane_Filtering, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_INFO2.setLayout(new BoxLayout(panel_INFO2, BoxLayout.X_AXIS));
		panel_INFO2.add(txtThisIsFiltering);
		
		panel_TOPN = new JPanel();
		tabbedPane_Filtering.addTab("TopN", null, panel_TOPN, null);
		
		button = new JButton("Submit");
		
		lblToleranceType = new JLabel("Tolerance Type:");
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"PPM", "DA"}));
		
		lblInputN = new JLabel("Input N:");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		lblThreshold = new JLabel("Tolerance:");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		GroupLayout gl_panel_TOPN = new GroupLayout(panel_TOPN);
		gl_panel_TOPN.setHorizontalGroup(
			gl_panel_TOPN.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_TOPN.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_panel_TOPN.createParallelGroup(Alignment.LEADING)
						.addComponent(button, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_TOPN.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_TOPN.createParallelGroup(Alignment.LEADING)
								.addComponent(lblThreshold)
								.addComponent(lblInputN, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_TOPN.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_TOPN.createSequentialGroup()
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
									.addComponent(lblToleranceType, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
									.addGap(67))
								.addGroup(gl_panel_TOPN.createSequentialGroup()
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addContainerGap(346, Short.MAX_VALUE))))))
		);
		gl_panel_TOPN.setVerticalGroup(
			gl_panel_TOPN.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_TOPN.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel_TOPN.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInputN)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_panel_TOPN.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblThreshold)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblToleranceType))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(button))
		);
		panel_TOPN.setLayout(gl_panel_TOPN);
		
		panel_THRESHOLD = new JPanel();
		tabbedPane_Filtering.addTab("Threshold", null, panel_THRESHOLD, null);
		
		button_2 = new JButton("Submit");
		
		lblThreshold_1 = new JLabel("Threshold:");
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		
		rdbtnTic = new JRadioButton("TIC(Total Intensity Count)");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		lblNewLabel = new JLabel("Default Value:");
		GroupLayout gl_panel_THRESHOLD = new GroupLayout(panel_THRESHOLD);
		gl_panel_THRESHOLD.setHorizontalGroup(
			gl_panel_THRESHOLD.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_THRESHOLD.createSequentialGroup()
					.addContainerGap(504, Short.MAX_VALUE)
					.addComponent(button_2))
				.addGroup(gl_panel_THRESHOLD.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_panel_THRESHOLD.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_THRESHOLD.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_THRESHOLD.createSequentialGroup()
							.addComponent(lblThreshold_1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addGap(16)
							.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(88)
							.addComponent(rdbtnTic)))
					.addContainerGap(197, Short.MAX_VALUE))
		);
		gl_panel_THRESHOLD.setVerticalGroup(
			gl_panel_THRESHOLD.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_THRESHOLD.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panel_THRESHOLD.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblThreshold_1)
						.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnTic))
					.addGap(15)
					.addGroup(gl_panel_THRESHOLD.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
					.addComponent(button_2))
		);
		panel_THRESHOLD.setLayout(gl_panel_THRESHOLD);
		panel_Filtering.setLayout(gl_panel_Filtering);
		
		JPanel panel_Imputation = new JPanel();
		tabbedPane.addTab("Imputation", null, panel_Imputation, null);
		
		JPanel panel_PARAMETER3 = new JPanel();
		panel_PARAMETER3.setBorder(new TitledBorder(null, "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_INFO3 = new JPanel();
		panel_INFO3.setBorder(new TitledBorder(null, "Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton button_imputation = new JButton("Submit");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Remove", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel_INFO3 = new GroupLayout(panel_INFO3);
		gl_panel_INFO3.setHorizontalGroup(
			gl_panel_INFO3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_INFO3.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button_imputation)
					.addContainerGap())
		);
		gl_panel_INFO3.setVerticalGroup(
			gl_panel_INFO3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_INFO3.createSequentialGroup()
					.addGroup(gl_panel_INFO3.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel_INFO3.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(button_imputation)
						.addGroup(gl_panel_INFO3.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		JRadioButton rdbtnRemove = new JRadioButton("Remove:Delete Row");
		panel_1.add(rdbtnRemove);
		
		JRadioButton rdbtnMax = new JRadioButton("Max");
		panel.add(rdbtnMax);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Min");
		panel.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnAvergae = new JRadioButton("Average");
		panel.add(rdbtnAvergae);
		
		JRadioButton rdbtnMdian = new JRadioButton("Median");
		panel.add(rdbtnMdian);
		panel_INFO3.setLayout(gl_panel_INFO3);
		GroupLayout gl_panel_Imputation = new GroupLayout(panel_Imputation);
		gl_panel_Imputation.setHorizontalGroup(
			gl_panel_Imputation.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_Imputation.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Imputation.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_INFO3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
						.addComponent(panel_PARAMETER3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 602, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_Imputation.setVerticalGroup(
			gl_panel_Imputation.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Imputation.createSequentialGroup()
					.addGap(16)
					.addComponent(panel_PARAMETER3, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_INFO3, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_PARAMETER3.setLayout(new BoxLayout(panel_PARAMETER3, BoxLayout.X_AXIS));
		
		txtThisIsImputation = new JTextField();
		txtThisIsImputation.setText("This is imputation Panel");
		txtThisIsImputation.setColumns(10);
		panel_PARAMETER3.add(txtThisIsImputation);
		panel_Imputation.setLayout(gl_panel_Imputation);
		
		panel_Zeropadding = new JPanel();
		tabbedPane.addTab("ZeroPadding", null, panel_Zeropadding, null);
		
		panel_INFO4 = new JPanel();
		panel_INFO4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		txtThisIsZeropadding = new JTextField();
		txtThisIsZeropadding.setText("This is ZeroPadding Panel. Fill the zero values as much as peak dimensions.");
		txtThisIsZeropadding.setColumns(10);
		GroupLayout gl_panel_INFO4 = new GroupLayout(panel_INFO4);
		gl_panel_INFO4.setHorizontalGroup(
			gl_panel_INFO4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_INFO4.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtThisIsZeropadding, GroupLayout.PREFERRED_SIZE, 589, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_INFO4.setVerticalGroup(
			gl_panel_INFO4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_INFO4.createSequentialGroup()
					.addComponent(txtThisIsZeropadding, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_INFO4.setLayout(gl_panel_INFO4);
		
		panel_PARAMETER4 = new JPanel();
		panel_PARAMETER4.setBorder(new TitledBorder(null, "Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		textField_PeakDimension = new JTextField();
		textField_PeakDimension.setColumns(10);
		
		label_1 = new JLabel("");
		
		button_zeroPadding = new JButton("Submit");
		
		label_2 = new JLabel("Peak Dimension:");
		GroupLayout gl_panel_PARAMETER4 = new GroupLayout(panel_PARAMETER4);
		gl_panel_PARAMETER4.setHorizontalGroup(
			gl_panel_PARAMETER4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_PARAMETER4.createSequentialGroup()
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(183))
				.addGroup(gl_panel_PARAMETER4.createSequentialGroup()
					.addGap(18)
					.addComponent(label_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_PeakDimension, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(342, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel_PARAMETER4.createSequentialGroup()
					.addContainerGap(484, Short.MAX_VALUE)
					.addComponent(button_zeroPadding, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_PARAMETER4.setVerticalGroup(
			gl_panel_PARAMETER4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_PARAMETER4.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_PARAMETER4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_PARAMETER4.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField_PeakDimension, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_2))
						.addComponent(label_1))
					.addPreferredGap(ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
					.addComponent(button_zeroPadding))
		);
		panel_PARAMETER4.setLayout(gl_panel_PARAMETER4);
		GroupLayout gl_panel_Zeropadding = new GroupLayout(panel_Zeropadding);
		gl_panel_Zeropadding.setHorizontalGroup(
			gl_panel_Zeropadding.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Zeropadding.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Zeropadding.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_INFO4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_PARAMETER4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_Zeropadding.setVerticalGroup(
			gl_panel_Zeropadding.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Zeropadding.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_INFO4, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_PARAMETER4, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_Zeropadding.setLayout(gl_panel_Zeropadding);
		
		panel_Preprocess.setLayout(gl_panel_Preprocess);
		
		panel_Write_Features = new JPanel();
		layeredPane.add(panel_Write_Features, "name_238229922976798");
		
		lblPanelfeatureafterWritePage = new JLabel("panel_Feature(After Write page)");
		
		JButton btnNewButton = new JButton("Write");
		GroupLayout gl_panel_Write_Features = new GroupLayout(panel_Write_Features);
		gl_panel_Write_Features.setHorizontalGroup(
			gl_panel_Write_Features.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Write_Features.createSequentialGroup()
					.addContainerGap(566, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(11))
				.addGroup(Alignment.LEADING, gl_panel_Write_Features.createSequentialGroup()
					.addGap(325)
					.addComponent(lblPanelfeatureafterWritePage)
					.addContainerGap(363, Short.MAX_VALUE))
		);
		gl_panel_Write_Features.setVerticalGroup(
			gl_panel_Write_Features.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_Write_Features.createSequentialGroup()
					.addGap(15)
					.addComponent(lblPanelfeatureafterWritePage)
					.addPreferredGap(ComponentPlacement.RELATED, 352, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		panel_Write_Features.setLayout(gl_panel_Write_Features);
		
		panel_Write = new JPanel();
		layeredPane.add(panel_Write, "name_205954822323284");
		
		lblWrite = new JLabel("Write");
		
		rdbtnNewRadioButton = new JRadioButton("Delimited - Characters such as commas or tabs separate each field.");
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Fixed width -Fields are aligned in columns with spaces between each field.");
		
		JButton btnFeatureList = new JButton("NEXT");
		btnFeatureList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_Write_Features);
			}
		});
		GroupLayout gl_panel_Write = new GroupLayout(panel_Write);
		gl_panel_Write.setHorizontalGroup(
			gl_panel_Write.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Write.createSequentialGroup()
					.addGap(356)
					.addComponent(lblWrite)
					.addContainerGap(496, Short.MAX_VALUE))
				.addGroup(gl_panel_Write.createSequentialGroup()
					.addContainerGap(766, Short.MAX_VALUE)
					.addComponent(btnFeatureList, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panel_Write.createSequentialGroup()
					.addGap(42)
					.addGroup(gl_panel_Write.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton)
						.addComponent(rdbtnNewRadioButton_1))
					.addContainerGap(701, Short.MAX_VALUE))
		);
		gl_panel_Write.setVerticalGroup(
			gl_panel_Write.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Write.createSequentialGroup()
					.addGap(8)
					.addComponent(lblWrite)
					.addGap(74)
					.addComponent(rdbtnNewRadioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNewRadioButton_1)
					.addPreferredGap(ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
					.addComponent(btnFeatureList)
					.addContainerGap())
		);
		panel_Write.setLayout(gl_panel_Write);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void switchPanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	public void Readmgf(String filename) {
		Spectra spV1 = new Spectra();
		
		try {
			spV1.read(filename);
		} catch (FileExtensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void ReadPepXml(String filename) {
		PSMset psmset_v1 = new PSMset();
		
		try {
			psmset_v1.read(filename);
		} catch (FileExtensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Filelist extends java.awt.List {
	private java.util.List<File> MyList;

	public Filelist() throws HeadlessException {
		super();
		MyList = new ArrayList<>();
	}
	
	public void add(File file) {
		this.add(file.getName());
		MyList.add(file);
	}
	
	public void remove(String Filename) {
		for (File file : MyList) {
			if (file.getName().equals(Filename))
				MyList.remove(file);
		}
		super.remove(Filename);
	}
	
	public File[] getFileList() {
		return MyList.toArray(new File[MyList.size()]);
	}
	
}



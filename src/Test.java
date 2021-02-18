package proteomics;

import java.awt.EventQueue;
import java.io.IOException;

import proteomics.data.FileExtensionException;
import proteomics.ui.cli.CLI;
import proteomics.ui.gui.Mainframe;

public class Test {

	public static void main(String[] args) throws FileExtensionException, IOException {
		
		if (args[0].equals("--cli")) {
			String[] input_args = new String[args.length - 1];
			for (int i = 1; i < args.length; i++)
				input_args[i-1] = args[i];
			CLI.init(input_args);
			CLI.shell();
		}
		else if (args[0].equals("--gui")) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Mainframe frame = new Mainframe();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

//		SPECTRUM_FEATURE[] feature_list = SPECTRUM_FEATURE.values();
//		for (SPECTRUM_FEATURE feature : feature_list) {
//			System.out.println(feature.name());
//			System.out.println(feature.isActive());
//			System.out.println("--------");
//			for (PREPROCESS_METHOD method : PREPROCESS_METHOD.values()) {
//				System.out.println(method.name());
//				System.out.println(feature.getPreprocessList().canPreprocess(method.bit()));
//			}
//			System.out.println();
//		}
		
//		String[] test_args = {
//				"data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_1.mgf",
//				"data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_1.pep.xml"
//		};
//		CLI.init(test_args);
//		CLI.shell();
//		System.out.println(SPECTRUM_FEATURE.valueOf("TIC"));
//		System.out.println(SPECTRUM_FEATURE.valueOf("TIC_"));
		
//		Spectra spectra = new Spectra_v1();
//		PSMset psm_set = new PSMset_v1();
//
//		spectra.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_1.mgf");
//		spectra.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_2.mgf");
//		spectra.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_3.mgf");
//		spectra.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_4.mgf");
//		spectra.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_5.mgf");
//		spectra.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_6.mgf");
//
//		psm_set.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_1.pep.xml");
//		psm_set.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_2.pep.xml");
//		psm_set.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_3.pep.xml");
//		psm_set.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_4.pep.xml");
//		psm_set.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_5.pep.xml");
//		psm_set.read("data/20100609_Velos1_TaGe_SA_293/20100609_Velos1_TaGe_SA_293_6.pep.xml");
//		
//		System.out.println(spectra);
//		System.out.println();
//		System.out.println(psm_set);
//		System.out.println();
//		
//		{
//			System.out.println("----Spectrum Example----");
//			
//			int cnt = 0;
//			
//			for (int key : spectra.keySet()) {
//				if (cnt == 2)
//					break;
//				
//				Spectrum spec = spectra.get(key);
//				
//				System.out.println(spec);
//				System.out.println();
//				
//				cnt++;
//			}
//		}
//		
//		{
//			System.out.println("----PSM Example----");
//			
//			int cnt = 0;
//			
//			for (int key : psm_set.keySet()) {
//				if (cnt == 2)
//					break;
//				
//				Spectrum spec = spectra.get(key);
//				PSM psm = psm_set.get(key);
//				
//				if (spec == null)
//					continue;
//				
//				System.out.println(psm);
//				System.out.println();
//				
//				cnt++;
//			}
//		}
//		
//		{
//			System.out.println("----Feature----");
//			
//			int cnt = 0;
//			
//			FEATURE_LIST<?>[] feature_list = {
//					SPECTRUM_FEATURE.CHARGE,
//					SPECTRUM_FEATURE.TIC,
//					
//					PSM_FEATURE.DELTA_MASS,
//					PSM_FEATURE.NUM_OF_MISSED_CLEAVAGE,
//					PSM_FEATURE.NTT,
//					
//					SPECTRUM_FEATURE.PEAK_LIST
//			};
//
//			for (FEATURE_LIST<?> feature : feature_list) {
//				feature.active();
//			}
//			
//			for (SPECTRUM_FEATURE feature : SPECTRUM_FEATURE.values())
//				feature.active();
//			SPECTRUM_FEATURE.MAX_SEQUENCE_TAG_LENGTH.passive();
//			
//			String[] args_1_1 = {
//					"--TopN", "--N", "3", "--tolerance", "20", "--type", "ppm"
//			};
//			String[] args_1_2 = {
//					"--intensity_thresholding", "--base_peak", "--proportion", "0.1"
//			};
//			String[] args_1_3 = {
//					"--intensity_thresholding", "--TIC", "--proportion", "0.01"
//			};
//			String[] args_1_4 = {
//					"--intensity_thresholding", "--intensity", "6000"
//			};
//			String[] args_2_1 = {
//					"--min_max", "--min", "0", "--max", "100"
//			};
//			String[] args_2_2 = {
//					"--normalize", "--gaussian"
//			};
//			String[] args_3 = {
//					"--count", "4"
//			};
//			String[] args_4_1 = {
//					"--remove"
//			};
//			String[] args_4_2 = {
//					"--min"
//			};
//			String[] args_4_3 = {
//					"--max"
//			};
//			String[] args_4_4 = {
//					"--mean"
//			};
//			String[] args_4_5 = {
//					"--median"
//			};
//			String[] args_5_1 = {
//					"--min_max", "--min", "0", "--max", "100"
//			};
//			String[] args_5_2 = {
//					"--normalize", "--gaussian", "--mean", "--std"
//			};
//
//			SPECTRUM_FEATURE.PEAK_LIST.addPreprocess(PREPROCESS_METHOD.FILTER_PEAKS, args_1_1);
//			SPECTRUM_FEATURE.PEAK_LIST.addPreprocess(PREPROCESS_METHOD.PEAK_SCALING, args_2_2);
//			SPECTRUM_FEATURE.PEAK_LIST.addPreprocess(PREPROCESS_METHOD.ZERO_PADDING, args_3);
//
//			SPECTRUM_FEATURE.TIC.addPreprocess(PREPROCESS_METHOD.IMPUTATION, args_4_4);
//			SPECTRUM_FEATURE.TIC.addPreprocess(PREPROCESS_METHOD.SCALING, args_5_2);
//
//			PSM_FEATURE.MATCHED_MASS_ERROR_MEAN.addPreprocess(PREPROCESS_METHOD.IMPUTATION, args_4_4);
//			PSM_FEATURE.MATCHED_MASS_ERROR_STD.addPreprocess(PREPROCESS_METHOD.IMPUTATION, args_4_4);
//			
//			Feature.load(spectra, psm_set);
//
//			for (FEATURE_LIST<?> feature : feature_list) {
//				System.out.print(feature.name() + "\t");
//			}
//			System.out.println();
//			
//			for (int key : psm_set.keySet()) {
//				if (cnt == 2)
//					break;
//
//				Spectrum spec = spectra.get(key);
//				PSM psm = psm_set.get(key);
//				
//				if (spec == null)
//					continue;
//				
//				Feature feature = new Feature_v1(spec, psm);
//				feature.preprocess();
//				
//				System.out.println(feature);
//				
//				cnt++;
//			}
//
//			for (FEATURE_LIST<?> feature : feature_list) {
//				feature.clearPreprocess();
//			}
//		}
//		
//		{
//			System.out.println("----SPECTRUM FEATURE----");
//			
//			for (SPECTRUM_FEATURE feature : SPECTRUM_FEATURE.values()) {
//				if (!feature.isActive())
//					continue;
//				System.out.println(feature);
//				System.out.println(feature.getStatistics(null));
//				System.out.println();
//			}
//		}
//		
//		{
//			System.out.println("----PSM FEATURE----");
//			
//			for (PSM_FEATURE feature : PSM_FEATURE.values()) {
//				if (!feature.isActive())
//					continue;
//				System.out.println(feature);
//				System.out.println(feature.getStatistics(null));
//				System.out.println();
//			}
//		}
	}
}

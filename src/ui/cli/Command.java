package proteomics.ui.cli;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proteomics.data.PSM;
import proteomics.data.Spectrum;
import proteomics.feature.FEATURE_LIST;
import proteomics.feature.Feature;
import proteomics.feature.Feature;
import proteomics.feature.PSM_FEATURE;
import proteomics.feature.SPECTRUM_FEATURE;
import proteomics.preprocess.PREPROCESS_METHOD;

public abstract class Command {
	public static final int QUIT = 0;
	public static final int NORMAL = 1;
	public static final int ERROR = -1;
	
	public static final String CRLF = "\r\n";
	
	public static final Command HELP = new Help();
	public static final Command FEATURE = new FeatureCommand();
	public static final Command WRITE = new Write();
	
	protected String[] usage;
	protected String[][] options;
	
	public String getShortHelp() {
		return String.format("%-16s\t%s", usage[0], usage[1]);
	}
	
	public void printHelp() {
		String line = "";
		
		line += getShortHelp() + CRLF + CRLF;
		line += String.format("%-16s\t%s", "[USAGE]", "[EXPLAIN]") + CRLF;
		for (String[] option : options)
			line += String.format("%-16s\t%s", option[0], option[1]) + CRLF;

		System.out.println(line);
	}
	
	public abstract int excute(String[] args);
}

class Help extends Command {
	
	Help() {
		String[] temp_usage = { "help", "It offers information of each instruction" };
		String[][] temp_options = {};
		
		usage = temp_usage;
		options = temp_options;
	}

	@Override
	public int excute(String[] args) {
		// TODO Auto-generated method stub

		String line = "";
		
		line += String.format("%-16s\t%s", "[USAGE]", "[EXPLAIN]") + CRLF;
		line += Command.HELP.getShortHelp() + CRLF;
		line += Command.FEATURE.getShortHelp() + CRLF;
		line += Command.WRITE.getShortHelp() + CRLF;
		
		System.out.print(line);
		
		return NORMAL;
	}
}

class FeatureCommand extends Command {
	
	FeatureCommand() {
		String[] temp_usage = { "feature", "Print feature list" };
		String[][] temp_options = {
				{ "--help", "It offers information of command" },
				{ "--active [feature]", "Active feature" },
				{ "--disactive [feature]", "Disactive feature" },
				{ "--preprocess [feature] [method] [arguments]", "Register preprocess" }
		};
		
		usage = temp_usage;
		options = temp_options;
	}

	@Override
	public int excute(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length == 1) {
			printFeature();
			return NORMAL;
		}
		
		switch (args[1]) {
		case "--help":
			printHelp();
			return NORMAL;
		case "--active":
			activeFeature(args);
			return NORMAL;
		case "--disactive":
			disactiveFeature(args);
			return NORMAL;
		case "--preprocess":
			preprocessFeature(args);
			return NORMAL;
		}
		
		String feature_name = args[1].toUpperCase();
		FEATURE_LIST<?>[] feature_list = FEATURE_LIST.values();
		for (FEATURE_LIST<?> feature : feature_list) {
			if (feature.name().equals(feature_name)) {
				Feature.load(CLI.spectra, CLI.psm_set, feature);
				System.out.println(feature);
				System.out.println(feature.getStatistics(null));
				break;
			}
		}
		
		return ERROR;
	}

	private void preprocessFeature(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length == 2 || args[2].equals("--help")) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("[feature] [method] [arguments]");
			sb.append("\n\n");
			for (PREPROCESS_METHOD method : PREPROCESS_METHOD.values()) {
				if (method.help() == null)
					continue;
				sb.append(method.help() + '\n');
			}
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
		}
		else {
			String feature_name = args[2].toUpperCase();
			String preprocess_method = args[3].toUpperCase();
			List<String> arg_list = new ArrayList<>();
			for (int i = 4; i < args.length; i++)
				arg_list.add(args[i]);
			
			FEATURE_LIST<?>[] feature_list = FEATURE_LIST.values();
			
			for (FEATURE_LIST<?> feature : feature_list) {
				if (feature.name().equals(feature_name)) {
					PREPROCESS_METHOD method = PREPROCESS_METHOD.valueOf(preprocess_method);
					feature.addPreprocess(method, arg_list.toArray(new String[arg_list.size()]));
					System.out.println(feature);
					break;
				}
			}
		}
	}

	private void disactiveFeature(String[] args) {
		for (int i = 2; i < args.length; i++) {
			String feature_name = args[i].toUpperCase();
			
			try {
				FEATURE_LIST<?> feature = SPECTRUM_FEATURE.valueOf(feature_name);
				feature.passive();
			} catch (Exception e) {
				try {
					FEATURE_LIST<?> feature = PSM_FEATURE.valueOf(feature_name);
					feature.passive();
				} catch (Exception e2) {
					
				}
			}
		}
	}

	private void activeFeature(String[] args) {
		for (int i = 2; i < args.length; i++) {
			String feature_name = args[i].toUpperCase();
			
			try {
				FEATURE_LIST<?> feature = SPECTRUM_FEATURE.valueOf(feature_name);
				feature.active();
			} catch (Exception e) {
				try {
					FEATURE_LIST<?> feature = PSM_FEATURE.valueOf(feature_name);
					feature.active();
				} catch (Exception e2) {
					
				}
			}
		}
	}

	private void printFeature() {
		printFeature(SPECTRUM_FEATURE.values(), PSM_FEATURE.values());
	}
	
	private void printFeature(SPECTRUM_FEATURE[] sp_feature_list, PSM_FEATURE[] psm_feature_list) {
		String line = "";
		
		line += String.format("%-24s\t%-8s\t%-24s\t%-8s", "[SPECTRUM]", "[ACTIVE]", "[PSM]", "[ACTIVE]") + CRLF;
				
		int index = 0;
		while (true) {
			if (index >= sp_feature_list.length && index >= psm_feature_list.length)
				break;
			
			String sp_feature = "";
			String psm_feature = "";
			
			String sp_isActive = "";
			String psm_isActive = "";
			
			if (index < sp_feature_list.length) {
				sp_feature = sp_feature_list[index].name();
				sp_isActive = String.valueOf(sp_feature_list[index].isActive());
			}
			if (index < psm_feature_list.length) {
				psm_feature = psm_feature_list[index].name();
				psm_isActive = String.valueOf(psm_feature_list[index].isActive());
			}
			
			line += String.format("%-24s\t%-8s\t%-24s\t%-8s", sp_feature, sp_isActive, psm_feature, psm_isActive) + CRLF;
			
			index++;
		}
		
		System.out.print(line);
	}
}

class Write extends Command {

	Write() {
		String[] temp_usage = { "write", "Write features at file" };
		String[][] temp_options = {
				{ "--help", "It offers information of command" }
		};
		
		usage = temp_usage;
		options = temp_options;
	}
	
	@Override
	public int excute(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length == 1)
			return NORMAL;
		
		switch(args[1]) {
		case "--help":
			printHelp();
			return NORMAL;
		}
		
		BufferedWriter out = null;
		
		try {
			out = new BufferedWriter(new FileWriter(args[1]));
			
			Feature.load(CLI.spectra, CLI.psm_set);
			
			if (CLI.spectra.size() == 0) {
				for (int key : CLI.psm_set.keySet()) {
					PSM psm = CLI.psm_set.get(key);
					
					Feature feature = new Feature(null, psm);
					feature.preprocess();
					
					out.write(feature.toString());
					out.write("\n");
				}
			}
			else if (CLI.psm_set.size() == 0) {
				for (int key : CLI.spectra.keySet()) {
					Spectrum spec = CLI.spectra.get(key);
					
					Feature feature = new Feature(spec, null);
					feature.preprocess();
					
					out.write(feature.toString());
					out.write("\n");
				}
			}
			else {
				for (int key : CLI.psm_set.keySet()) {
					Spectrum spec = CLI.spectra.get(key);
					PSM psm = CLI.psm_set.get(key);
					
					if (spec == null)
						continue;
					
					Feature feature = new Feature(spec, psm);
					feature.preprocess();
					
					out.write(feature.toString());
					out.write("\n");
				}
			}
			
			for (FEATURE_LIST<?> feature : FEATURE_LIST.values())
				feature.clearPreprocess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return NORMAL;
	}
	
}
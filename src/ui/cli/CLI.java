package proteomics.ui.cli;

import java.util.Scanner;

import proteomics.data.FileExtensionException;
import proteomics.data.PSMset;
import proteomics.data.PSMset;
import proteomics.data.Spectra;
import proteomics.data.Spectra;

public class CLI {
	
	public static final String CRLF = "\r\n";
	
	protected static final Spectra spectra = new Spectra();
	protected static final PSMset psm_set = new PSMset();
	
	public static void init(String[] args) {
		System.out.println("Reading...");

		try {
			for (String arg : args) {
				if (arg.endsWith(".mgf"))
					spectra.read(arg);
				else if (arg.endsWith(".pep.xml"))
					psm_set.read(arg);
			}
			
			System.out.println(spectra);
			System.out.println(psm_set);
		} catch (FileExtensionException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void shell() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		String input = null;
		
		try {
			while (true) {
				System.out.print("prompt> ");
				input = sc.nextLine();
				
				if (excuteCommand(input.split(" ")) == 0)
					break;
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int excuteCommand(String[] args) {
		if (args.length == 0 || args[0].toUpperCase().equals("QUIT"))
			return Command.QUIT;

		switch (args[0]) {
		case "help":
			return Command.HELP.excute(args);
		case "feature":
			return Command.FEATURE.excute(args);
		case "write":
			return Command.WRITE.excute(args);
		}

		return Command.ERROR;
	}
}
package proteomics.data;

/**
 * 
 * @author pjw23
 *
 * When program reads the files, it always stores files that has same extension.
 * ex)
 * 	Spectra class - *.mgf
 * 	PSM class - *.pep.xml
 */
public class FileExtensionException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileExtensionException(String message) {
		super(message);
	}
}

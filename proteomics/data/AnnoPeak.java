package proteomics.data;

/**
 * 
 * @author pjw23
 *
 */
public class AnnoPeak extends Peak {
	
	private String ion_type;
	private String charge;
	private double mass_error;
	private String annotation;	//	y1, b1, y1-H2O, ...
	
	public AnnoPeak(double mass, double intensity) {
		super(mass, intensity);
		// TODO Auto-generated constructor stub
	}
	
	public String getIon_type() {
		return ion_type;
	}
	
	public void setIon_type(String ion_type) {
		this.ion_type = ion_type;
	}
	
	public String getCharge() {
		return charge;
	}
	
	public void setCharge(String charge) {
		this.charge = charge;
	}
	
	public double getMass_error() {
		return mass_error;
	}
	
	public void setMass_error(double mass_error) {
		this.mass_error = mass_error;
	}
	
	public String getAnnotation() {
		return annotation;
	}
	
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public String toString() {
		String ret = super.toString();
		
		ret += " " + annotation;
		
		return ret;
	}
}

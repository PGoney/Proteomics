package proteomics.data;

public class Peak {
	
	protected double mass;
	protected double intensity;

	public Peak(double mass, double intensity) {

		this.mass = mass;
		this.intensity = intensity;
	}
	
	public String toString() {
		return String.format("%.4f %.4f", mass, intensity);
	}

	public double getMass() {
		return mass;
	}
	
	public double getIntensity() {
		return intensity;
	}
}

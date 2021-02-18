package proteomics.preprocess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import proteomics.data.Peak;
import proteomics.data.Spectrum;

public class Preprocess {

	public static List<Peak> TopN(List<Peak> peak_list, int N, double tolerance, String type) {
		List<Peak> result = new ArrayList<>();
		
		int cnt = 0;
		while (true) {
			if (cnt >= N || peak_list.size() == 0)
				break;
			
			double mass = getBasePeak(peak_list).getMass();
			
			if (type.equals("da"));
			else if (type.equals("ppm"))
				tolerance = (tolerance * mass * Math.pow(10, -6));
			
			Peak peak = binning(peak_list, mass, tolerance);
			result.add(peak);
			
			cnt++;
		}

		Collections.sort(result, new Comparator<Peak>() {

			@Override
			public int compare(Peak o1, Peak o2) {
				if (o1.getMass() < o2.getMass())
					return -1;
				else if (o1.getMass() > o2.getMass())
					return 1;
				return 0;
			}
			
		});
		
		return result;
	}
	
	private static Peak getBasePeak(List<Peak> peak_list) {
		Peak base_peak = peak_list.get(0);
		
		for (Peak p : peak_list) {
			if (p.getIntensity() > base_peak.getIntensity())
				base_peak = p;
		}
		
		return base_peak;
	}
	
	private static Peak binning(List<Peak> peak_list, double mz, double tolerance) {
		ArrayList<Peak> satisfied_peak_list = new ArrayList<>();
		
		for (Peak p : peak_list) {
			if (Math.abs(p.getMass() - mz) <= tolerance) {
				satisfied_peak_list.add(p);
			}
		}
		
		double weighed_mz = 0;
		double total_intensity = 0;
		for (Peak p : satisfied_peak_list) {
			weighed_mz += p.getMass() * p.getIntensity();
			total_intensity += p.getIntensity();
			peak_list.remove(p);
		}
		weighed_mz /= total_intensity;
		
		return new Peak(weighed_mz, total_intensity);
	}

	public static List<Peak> intensityThresholding(List<Peak> peak_list, double intensity) {
		List<Peak> result = new ArrayList<>();
		
		for (Peak peak : peak_list)
			if (peak.getIntensity() >= intensity)
				result.add(peak);
		
		return result;
	}

	public static List<Peak> scalingPeak(List<Peak> peak_list, double min, double max) {
		List<Peak> result = new ArrayList<>();
		
		Peak min_peak = getMinPeak(peak_list);
		Peak max_peak = getMaxPeak(peak_list);
		
		for (Peak peak : peak_list) {
			double mass = peak.getMass();
			double intensity = peak.getIntensity();
			
			intensity = (intensity - min_peak.getIntensity()) / (max_peak.getIntensity() - min_peak.getIntensity())
					* (max - min) + min;
			
			result.add(new Peak(mass, intensity));
		}
		
		return result;
	}

	private static Peak getMaxPeak(List<Peak> peak_list) {
		Peak peak = peak_list.get(0);
		for (Peak p : peak_list)
			if (peak.getIntensity() < p.getIntensity())
				peak = p;
		return peak;
	}

	private static Peak getMinPeak(List<Peak> peak_list) {
		Peak peak = peak_list.get(0);
		for (Peak p : peak_list)
			if (p.getIntensity() != 0 && peak.getIntensity() > p.getIntensity())
				peak = p;
		return peak;
	}

	public static List<Peak> scalingPeakNormalize(List<Peak> peak_list) {
		List<Peak> result = new ArrayList<>();

		double sum = 0.0;
		double sum2 = 0.0;
		int count = peak_list.size();
		for (Peak peak : peak_list) {
			sum += peak.getIntensity();
			sum2 += Math.pow(peak.getIntensity(), 2);
		}

		double mean = sum / count;
		double std = Math.sqrt((sum2 / count) - Math.pow(mean, 2));
		
		for (Peak peak : peak_list) {
			double intensity = peak.getIntensity();
			intensity = Gaussian.cdf(intensity, mean, std);
			result.add(new Peak(peak.getMass(), intensity));
		}
		
		return result;
	}

	public static List<Peak> zeroPadding(List<Peak> peak_list, int cnt) {
		List<Peak> result = new ArrayList<>(peak_list);
		
		while (result.size() < cnt) {
			result.add(new Peak(0, 0));
		}
		
		return result;
	}

	public static double scaling(double value, double ori_min, double ori_max, double min, double max) {
		return (value - ori_min) / (ori_max - ori_min) * (max - min) + min;
	}

	public static double scalingNormalize(double value, double mean, double std) {
		return Gaussian.cdf(value, mean, std);
	}
}

class Gaussian {

    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    public static double pdf(double x, double mu, double sigma) {
        return pdf((x - mu) / sigma) / sigma;
    }

    public static double cdf(double z) {
        if (z < -8.0) return 0.0;
        if (z >  8.0) return 1.0;
        double sum = 0.0, term = z;
        for (int i = 3; sum + term != sum; i += 2) {
            sum  = sum + term;
            term = term * z * z / i;
        }
        return 0.5 + sum * pdf(z);
    }

    public static double cdf(double z, double mu, double sigma) {
        return cdf((z - mu) / sigma);
    } 

    public static double inverseCDF(double y) {
        return inverseCDF(y, 0.00000001, -8, 8);
    } 

    private static double inverseCDF(double y, double delta, double lo, double hi) {
        double mid = lo + (hi - lo) / 2;
        if (hi - lo < delta) return mid;
        if (cdf(mid) > y) return inverseCDF(y, delta, lo, mid);
        else              return inverseCDF(y, delta, mid, hi);
    }
}
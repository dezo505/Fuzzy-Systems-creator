package FuzzySystem;

import FuzzySystem.FuzzySystemCore.FuzzySets.*;

public class FuzzyCreator {
	public static FuzzySet trapezeFuzzySet(double min, double max, double x1, double x2, double xPeak1, double xPeak2, boolean inverse, String setTitle, String varTitle) {
		FuzzySet newSet = new TrapezeSet(min, max, varTitle, setTitle,
				x1, x2, xPeak1, xPeak2, inverse);
		return newSet;
	}
	
	
	public static FuzzySet triangleFuzzySet(double min, double max, double x1, double x2, double x3, boolean inverse ,String setTitle, String varTitle) {
		FuzzySet newSet = new TriangleSet(min, max, varTitle, setTitle,
				x1, x2, x3, inverse);
		return newSet;
	}
	
	
	public static FuzzySet gaussFuzzySet(double min, double max, double width, double center, boolean inverse, String setTitle, String varTitle) {
		FuzzySet newSet = new GaussSet(min, max, varTitle, setTitle,
				center, width, inverse);
		return newSet;
	}
	
	public static FuzzySet linearFuzzySet(double min, double max, double x1, double x2, String setTitle, String varTitle) {
		FuzzySet newSet = new LinearFuzzySet(min, max, varTitle, setTitle,
				x1, x2);
		return newSet;
	}
	
	public static FuzzySet monotoneGaussFuzzySet(double min, double max, double width, double center, boolean leftSided, boolean inverse, String setTitle, String varTitle){
		FuzzySet newSet = new MonotoneGaussSet(min, max, varTitle, setTitle,
				center, width, leftSided, inverse);
		return newSet;
	}
	
	public static FuzzySet thresholdFuzzySet(double min, double max, double threshholdValue, boolean inverse, String setTitle, String varTitle){
		FuzzySet newSet = new ThresholdSet(min, max, varTitle, setTitle,
				threshholdValue, inverse);
		return newSet;
	}
}

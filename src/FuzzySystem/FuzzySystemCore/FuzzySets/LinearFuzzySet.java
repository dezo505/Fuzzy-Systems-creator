package FuzzySystem.FuzzySystemCore.FuzzySets;

public class LinearFuzzySet extends FuzzySet{
	private static final long serialVersionUID = 1L;
	
	private double a;
	private double b;
	
	//TODO: co jak a == inifinity?
	
	public LinearFuzzySet(double min, double max, String variableTitle, String setTitle,
			double x0, double x1) {
		super(min, max, variableTitle, setTitle);
		
		a = 1 / (x1 - x0);
		b = -a*x0;
	}
	
	@Override
	public double calculateValue(double x) {
		return Math.max(Math.min(a*x + b, 1.0), 0.0);
	}
}

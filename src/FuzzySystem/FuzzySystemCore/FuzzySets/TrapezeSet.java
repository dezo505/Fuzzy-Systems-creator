package FuzzySystem.FuzzySystemCore.FuzzySets;

public class TrapezeSet extends FuzzySet{
	private static final long serialVersionUID = -6470005097197876508L;
	private double a1;
	private double b1;
	private double a2;
	private double b2;
	private double center;
	
	public TrapezeSet(double min, double max, String variableTitle, String setTitle,
			double x1, double x2, double x3, double x4, boolean inverse) {
		super(min, max, variableTitle, setTitle);
		if(!inverse) {
			a1 = 1/(x2 - x1);
			b1 = -x1*a1;
			
			a2 = 1/(x3 - x4);
			b2 = -x4*a2;
					
		}else {
			a1 = 1/(x1 - x2);
			b1 = -x2*a1;
			
			a2 = 1/(x4 - x3);
			b2 = -x3*a2;
		}
		center = (x2 + x3)/2;
		
	}

	@Override
	public double calculateValue(double x) {
		return Math.max(Math.min((center <= x)?(a2*x + b2):(a1*x + b1), 1), 0);
	}
}

package FuzzySystem.FuzzySystemCore.FuzzySets;

public class GaussSet extends FuzzySet{
	private static final long serialVersionUID = 4073169726223954683L;
	
	private double center;
	private double width;
	
	private boolean inverse;
	
	public GaussSet(double min, double max, String variableTitle, String setTitle, 
			double center, double width, boolean inverse) {
		super(min, max, variableTitle, setTitle);
		this.center = center;
		this.width = width;
		this.inverse = inverse;
	}

	@Override
	public double calculateValue(double x) {
		double result = Math.exp(-Math.pow((center - x)/width, 2));
		if(inverse)return 1 - result;
		return result;
	}
}

package FuzzySystem.FuzzySystemCore.FuzzySets;

public class MonotoneGaussSet extends FuzzySet{
	private static final long serialVersionUID = 4073169726223954683L;
	
	private double center;
	private double width;
	private boolean isLeft;
	private boolean inverse;
	
	public MonotoneGaussSet(double min, double max, String variableTitle, String setTitle,
			double center, double width, boolean leftSided, boolean inverse){
		super(min, max, variableTitle, setTitle);
		this.center = center;
		this.width = width;
		this.isLeft = leftSided;
		this.inverse = inverse;
	}

	@Override
	public double calculateValue(double x) {
		double result;
		if(isLeft)result =  (center <= x)?(Math.exp(-Math.pow((center - x)/width, 2))) : 1;
		else result = (center >= x)?(Math.exp(-Math.pow((center - x)/width, 2))) : 1;
		if(inverse)return 1 - result;
		return result;
	}
}

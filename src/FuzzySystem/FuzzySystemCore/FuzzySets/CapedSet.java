package FuzzySystem.FuzzySystemCore.FuzzySets;

public class CapedSet extends FuzzySet{
	private static final long serialVersionUID = 7674500407992371606L;
	private FuzzySet set;
	private double cap;
	
	public CapedSet(double min, double max, String variableTitle, String setTitle,
			FuzzySet set, double cap) {
		super(min, max, variableTitle, setTitle);
		this.set = set;
		this.cap = cap;
	}

	@Override
	public double calculateValue(double x) {
		return Math.min(cap, set.calculateValue(x));
	}
	
	
}

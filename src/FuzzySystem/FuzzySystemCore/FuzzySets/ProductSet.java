package FuzzySystem.FuzzySystemCore.FuzzySets;

import java.util.ArrayList;

public class ProductSet extends FuzzySet{
	private static final long serialVersionUID = -3160735984849287813L;
	private ArrayList<FuzzySet> sets;
	
	public ProductSet(double min, double max, String variableTitle, String setTitle,
			ArrayList<FuzzySet> sets) {
		super(min, max, variableTitle, setTitle);
		this.sets = sets;
	}
	
	public ProductSet(double min, double max, String variableTitle, String setTitle) {
		super(min, max, variableTitle, setTitle);
		this.sets = new ArrayList<FuzzySet>();
	}
	
	public void addSet(FuzzySet set){
		sets.add(set);
	}

	@Override
	public double calculateValue(double x) {
		double result = sets.get(0).calculateValue(x);
		for(FuzzySet set: sets) {
			result = Math.min(result, set.calculateValue(x));
		}
		return result;
	}
}

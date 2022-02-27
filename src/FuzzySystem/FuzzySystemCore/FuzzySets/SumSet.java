package FuzzySystem.FuzzySystemCore.FuzzySets;

import java.util.ArrayList;

public class SumSet extends FuzzySet{
	private static final long serialVersionUID = -3160735984849287813L;
	private ArrayList<FuzzySet> sets;
	
	public SumSet(double min, double max, String variableTitle, String setTitle,
			ArrayList<FuzzySet> sets) {
		super(min, max, variableTitle, setTitle);
		this.sets = sets;
	}
	
	public SumSet(double min, double max, String variableTitle, String setTitle) {
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
			result = Math.max(result, set.calculateValue(x));
		}
		return result;
	}
}

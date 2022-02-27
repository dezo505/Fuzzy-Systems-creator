package FuzzySystem.FuzzySystemCore.FuzzySystemOutput;

import java.io.Serializable;
import java.util.ArrayList;

import FuzzySystem.Rules.Rule;

public class TsukamotoSO implements FuzzySystemOutput, Serializable{
	private static final long serialVersionUID = -8793176570000883714L;
	private ArrayList<Rule> rules;
	private double minX;
	private double maxX;
	
	public TsukamotoSO(double min, double max){
		rules = new ArrayList<Rule>();
		setMinX(min);
		setMaxX(max);
	}
	
	@Override
	public void setRules(ArrayList<Rule> ruleSet) {
		rules = ruleSet;		
	}

	@Override
	public ArrayList<Rule> getRules() {
		return rules;
	}

	@Override
	public double ruleSetValue(double[] givenValues) {
		double sum1 = 0;
		double sum2 = 0;
		
		for(Rule r: rules) {
			sum1 += r.fulfillment(givenValues);
			sum2 += (double)r.ruleValue(givenValues) * r.fulfillment(givenValues);
		}
		
		if(sum1 == 0) {
			System.out.println("None of rules were fulfilled");
			return 0;
		}
		
		return sum2 / sum1;
	}

	
	public String getTitle() {
		return "Tsukamoto system";
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}
}

package FuzzySystem.Rules;

import java.io.Serializable;

import FuzzySystem.FuzzySystemCore.FuzzySets.*;
import FuzzySystem.Rules.RuleOutput.RuleOutput;

public class Rule implements Serializable{
	private static final long serialVersionUID = 7872772768537585995L;
	private FuzzySet[] ruleSets;
	private RuleOutput ruleOutput;
	
	private boolean[] andOr;
	
	public Rule(int setsNumber, RuleOutput ruleOutput){
		this(setsNumber);
		
		ruleOutput.setRule(this);
		this.ruleOutput = ruleOutput;
	}
	
	public Rule(int setsNumber){
		ruleSets = new FuzzySet[setsNumber];
		andOr = new boolean[setsNumber - 1];
	}
	
	public void setSet(int index, FuzzySet newSet) {
		ruleSets[index] = newSet;
	}
	
	public FuzzySet[] getSets() {
		return ruleSets;
	}
	
	public void setRuleOutput(RuleOutput ro) {
		ruleOutput = ro;
	}
	
	public RuleOutput getRuleOutput() {
		return ruleOutput;
	}
	
	public void setAndOr(boolean value, int index) {
		andOr[index] = value;
	}
	
	public void setAndOr(int index, boolean value) {
		andOr[index] = value;
	}
	
	public void setAndOr(boolean value) {
		for(int i = 0; i < andOr.length; i++)andOr[i] = value;
	}
	
	public double fulfillment(double[] values) {
		double result = ruleSets[0].calculateValue(values[0]);
		
		for(int i = 0; i < ruleSets.length - 1; i++) {
			if(andOr[i]){	//AND
				result = Math.min(result, ruleSets[i+1].calculateValue(values[i+1]));
			}else {		//OR
				result = Math.max(result, ruleSets[i+1].calculateValue(values[i+1]));
			}
		}
		return result;
	}
	
	public Object ruleValue(double[] givenValues) {
		return ruleOutput.ruleValue(givenValues);
	}
	
	public String toReadableString() {
		String result = "";
		
		result += ruleSets[0].getVariableTitle() + " IS " + ruleSets[0].getSetTitle();
		
		for(int i = 1; i < ruleSets.length; i++) {
			result += (andOr[i-1])? " AND ": " OR ";
			result += ruleSets[i].getVariableTitle() + " IS " + ruleSets[i].getSetTitle();
		}
		
		result += " " + ruleOutput.toReadableString();
		
		return result;
	}
}

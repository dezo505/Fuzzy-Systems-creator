package FuzzySystem.FuzzySystemCore.FuzzySystemOutput;

import java.io.Serializable;
import java.util.ArrayList;

import FuzzySystem.FuzzySystemCore.VarFuzzification;
import FuzzySystem.FuzzySystemCore.FuzzySets.*;
import FuzzySystem.Rules.Rule;

public class MamdaniSO implements FuzzySystemOutput, Serializable{
	private static final long serialVersionUID = -1654116059849911145L;
	private ArrayList<Rule> rules;
	private VarFuzzification outputFuzzification;
	
	public MamdaniSO(VarFuzzification o){
		rules = new ArrayList<Rule>();
		outputFuzzification  = o;
	}
	
	public VarFuzzification getOutFuzz() {
		return outputFuzzification;
	}
	
	public ArrayList<Rule> getRules() {
		return rules;
	}
	
	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	public double ruleSetValue(double[] values){
		if(rules.size() == 0)return 0;
		SumSet temporarySet = new SumSet(outputFuzzification.getMinX(), outputFuzzification.getMaxX(), "-", "-");	
		
		for(Rule r: rules) {	
			FuzzySet t2 = (FuzzySet)r.ruleValue(values);
			temporarySet.addSet(t2);
		}
		double result = temporarySet.centerOfGravity();
		
		if(Double.isNaN(result)) {	//f(x) = 0 for every x
			System.out.println("None of rules were fulfilled");
			return 0;
		}
		
		return result;
	}
	
	public String getTitle() {
		return "Mamdani system";
	}
}


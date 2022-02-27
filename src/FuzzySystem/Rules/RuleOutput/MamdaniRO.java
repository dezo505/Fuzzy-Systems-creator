package FuzzySystem.Rules.RuleOutput;

import java.io.Serializable;

import FuzzySystem.FuzzySystemCore.FuzzySets.*;
import FuzzySystem.Rules.Rule;

public class MamdaniRO implements RuleOutput, Serializable{
	private static final long serialVersionUID = 4197272557670277739L;
	
	private Rule outputRule;
	private FuzzySet outputSet;
	
	public MamdaniRO(FuzzySet outputSet){
		this.outputSet = outputSet;
	}
	
	public Object ruleValue(double[] givenValues) {
		FuzzySet outClone = (FuzzySet)outputSet.clone();
		FuzzySet result = new CapedSet(outClone.getMinX(), outClone.getMaxX(), "-", "-",
				outClone, outputRule.fulfillment(givenValues));
		return result;
	}
	
	public void setRule(Rule rule) {
		outputRule = rule;
	}
	
	public Rule getRule() {
		return outputRule;
	}
	
	public String toReadableString() {
		return "THEN " + outputSet.getVariableTitle() + " IS " + outputSet.getSetTitle(); 
	}
}

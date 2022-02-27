package FuzzySystem.Rules.RuleOutput;

import java.io.Serializable;

import FuzzySystem.FuzzySystemCore.FuzzySets.*;
import FuzzySystem.Rules.Rule;

public class TsukamotoRO implements RuleOutput, Serializable{
	private static final long serialVersionUID = 1293484810279871922L;
	private Rule outputRule;
	
	private FuzzySet outputSet;
	
	public TsukamotoRO(FuzzySet f) {
		outputSet = f;
	}
	
	@Override
	public void setRule(Rule r) {
		outputRule = r;
	}

	@Override
	public Object ruleValue(double[] givenValues) {
		boolean ascending;
		if(outputSet.calculateValue(outputSet.getMaxX()) > outputSet.calculateValue(outputSet.getMinX())) {
			ascending = true;
		}else {
			ascending = false;
		}
		
		double full = outputRule.fulfillment(givenValues);
		
		double minEdge = outputSet.getMinX();
		double maxEdge = outputSet.getMaxX();
		
		int accuracy = 40;	//~2^40 ~ 1 000 000 000 000
		
		if(ascending) {
			for(int i = 0; i < accuracy; i++){
				if(full > outputSet.calculateValue((maxEdge + minEdge)/2)) {
					minEdge = (maxEdge + minEdge)/2;
				}else {
					maxEdge = (maxEdge + minEdge)/2;
				}
			}
		}else {
			for(int i = 0; i < accuracy; i++){
				if(full < outputSet.calculateValue((maxEdge + minEdge)/2)) {
					minEdge = (maxEdge + minEdge)/2;
				}else {
					maxEdge = (maxEdge + minEdge)/2;
				}
			}
		}
		return (maxEdge + minEdge)/2;
	}
	
	public String toReadableString() {
		return "THEN " + outputSet.getVariableTitle() + " IS " + outputSet.getSetTitle(); 
	}
}

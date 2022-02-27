package FuzzySystem.FuzzySystemCore.FuzzySystemOutput;



import java.io.Serializable;
import java.util.ArrayList;

import FuzzySystem.Rules.Rule;

public class TksSO implements FuzzySystemOutput, Serializable{
	private static final long serialVersionUID = -3451013034226961290L;
	ArrayList<Rule> rules;
	
	@Override
	public double ruleSetValue(double[] givenValues) {
		double sum1 = 0;
		double sum2 = 0;
		
		for(Rule r: rules) {
			sum1 += r.fulfillment(givenValues);
			sum2 += (double)r.ruleValue(givenValues);
		}
		
		if(sum1 == 0) {
			System.out.println("None of rules were fulfilled");
			return 0;
		}
		
		return sum2 / sum1;
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}
	
	@Override
	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	public String getTitle() {
		return "Takagi Sugeno system";
	}
	
}
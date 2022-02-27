package FuzzySystem.Rules.RuleOutput;

import java.io.Serializable;

import FuzzySystem.Rules.Rule;

public class TksRO implements RuleOutput, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4909085442178701123L;
	private Rule outputRule;
	private LinearFunction[] functions;

	public TksRO(double[][] givenFunctions){
		functions = new LinearFunction[givenFunctions.length];
		for(int i = 0; i < givenFunctions.length; i++) {
			functions[i] = new LinearFunction(givenFunctions[i]);
		}
	}
	
	public void setRule(Rule rule) {
		outputRule = rule;
	}
	
	public Rule getRule() {
		return outputRule;
	}
	
	public Object ruleValue(double[] givenValues){
		double functionSum = 0;
		for(int i = 0; i < functions.length; i++) {
			functionSum += functions[i].value(givenValues[i]);
		}
		
		return functionSum * outputRule.fulfillment(givenValues);		//double
	}
	
	public void setFunction(int index, double a, double b) {
		functions[index].setA(a);
		functions[index].setB(b);
	}
	
	class LinearFunction implements Serializable{		//y = ax + b
		private static final long serialVersionUID = 5361718956866422773L;
		private double a;
		private double b;
		
		LinearFunction(double a, double b){
			this.a = a;
			this.b = b;
		}
		
		LinearFunction(double[] a_b){
			this.a = a_b[0];
			this.b = a_b[1];
		}
		
		double value(double x) {
			return a*x + b;
		}
		
		void setB(double b) {
			this.b = b;
		}
		
		void setA(double a) {
			this.a = a;
		}
	}

	@Override
	public String toReadableString() {
		String result = "THEN OUTPUT IS ";
		int sumB = 0;
		for(int i  = 0; i < functions.length; i++) {
			sumB += functions[i].value(0);
			result += "(" + (functions[i].value(1) - functions[i].value(0))+ ")*X" + String.valueOf(i + 1) + " ";
			result += "+ ";
			
		}
		result += String.valueOf(sumB);
		
		return result;
	}
}

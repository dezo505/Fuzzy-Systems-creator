package FuzzySystem.Rules.RuleOutput;


import FuzzySystem.Rules.Rule;

public interface RuleOutput{
	void setRule(Rule r);

	Object ruleValue(double[] givenValues);
	String toReadableString();
}

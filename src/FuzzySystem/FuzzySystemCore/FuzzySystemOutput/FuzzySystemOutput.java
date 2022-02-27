package FuzzySystem.FuzzySystemCore.FuzzySystemOutput;

import java.util.ArrayList;

import FuzzySystem.Rules.Rule;

public interface FuzzySystemOutput{
	void setRules(ArrayList<Rule> ruleSet);
	public ArrayList<Rule> getRules();
	double ruleSetValue(double[] values);
	String getTitle();
}

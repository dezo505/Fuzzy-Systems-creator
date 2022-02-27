package FuzzySystem;

import FuzzySystem.FuzzySystemCore.*;
import FuzzySystem.FuzzySystemCore.FuzzySets.FuzzySet;
import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.*;
import FuzzySystem.Rules.RuleOutput.*;
import FuzzySystem.Rules.RuleOutput.TksRO;
import FuzzySystem.Rules.Rule;

public class RuleCreator {

	public static Rule ruleBase(FuzzyControlSystem system, int[] setInd, boolean[] andOr) {
		Rule ruleBase = new Rule(setInd.length);
		for(int i = 0; i < system.getInputSize(); i++) {
			ruleBase.setSet(i, system.getInput(i).getSet(setInd[i]).clone());
		}
		
		for(int i = 0; i < system.getInputSize() - 1; i++) {
			ruleBase.setAndOr(andOr[i], i);
		}
		
		return ruleBase;
	}
	
	public static Rule mamdaniRule(FuzzyControlSystem system, int[] setInd, int setOutInd, boolean[] andOr) {
		
		Rule resultRule = ruleBase(system, setInd, andOr);
		
		if(setOutInd < 0)setOutInd = 0;
		if(setOutInd > ((MamdaniSO)system.getSystemOutput()).getOutFuzz().getSetsNumber())
			setOutInd  = ((MamdaniSO)system.getSystemOutput()).getOutFuzz().getSetsNumber() - 1;
		
		RuleOutput newMamdaniOutput = new MamdaniRO(((MamdaniSO)system.getSystemOutput()).getOutFuzz().getSet(setOutInd));
		newMamdaniOutput.setRule(resultRule);
		
		resultRule.setRuleOutput(newMamdaniOutput);
	
		return resultRule;
	}
	
	public static Rule tksRule(FuzzyControlSystem system, int[] setInd, boolean[] andOr, double[][] functions){
		Rule resultRule = ruleBase(system, setInd, andOr);
		
		RuleOutput newTksOutput = new TksRO(functions);
		newTksOutput.setRule(resultRule);
		
		resultRule.setRuleOutput(newTksOutput);
		
		return resultRule;
	}
	
	public static Rule tsukamotoRule(FuzzyControlSystem system, int[] setInd, boolean[] andOr, FuzzySet outputSet){
Rule resultRule = ruleBase(system, setInd, andOr);
		
		RuleOutput newTksOutput = new TsukamotoRO(outputSet);
		newTksOutput.setRule(resultRule);
		
		resultRule.setRuleOutput(newTksOutput);
		
		return resultRule;
	}
}

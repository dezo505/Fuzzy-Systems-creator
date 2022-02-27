package FuzzySystem.FuzzySystemCore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.FuzzySystemOutput;
import FuzzySystem.Rules.Rule;

public class FuzzyControlSystem implements Serializable{
	private static final long serialVersionUID = 8720241393924989526L;

	private VarFuzzification[] inputFuzz;
	
	private ArrayList<Rule> ruleSet;
	private FuzzySystemOutput systemOutput;
	
	public FuzzyControlSystem(int inputVariableNumber, FuzzySystemOutput systemOutput){
		inputFuzz = new VarFuzzification[inputVariableNumber];
		ruleSet = new ArrayList<Rule>();
		this.systemOutput = systemOutput;
		this.systemOutput.setRules(ruleSet); 
	}
	
	
	public VarFuzzification getInput(int index) {
		return inputFuzz[index];
	}
	
	public void setInput(VarFuzzification vf, int index) {
		inputFuzz[index] = vf;
	}
	
	public Rule getRule(int index) {
		return ruleSet.get(index);
	}
	
	public ArrayList<Rule> getRules(){
		return ruleSet;
	}
	
	public void addRule(Rule rule) {
		ruleSet.add(rule);
	}
	
	public void removeRule(int index) {
		ruleSet.remove(index);
	}
	
	public int rulesNumber() {
		return ruleSet.size();
	}
	
	public void setSystemOutput(FuzzySystemOutput systemOutput) {
		this.systemOutput = systemOutput;
	}
	
	public FuzzySystemOutput getSystemOutput() {
		return systemOutput;
	}
	
	public double outputValue(double[] values) {
		systemOutput.setRules(ruleSet);
		for(Rule r: ruleSet) {
			r.getRuleOutput().setRule(r);
		}
		return systemOutput.ruleSetValue(values);
	}
	
	public int getInputSize() {
		return inputFuzz.length;
	}
	
	public boolean save(String filePath) {
		try {
			FileOutputStream file = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(this);
			file.close();
			return true;
		} catch (IOException e) {
			System.out.println("Incorrect savepath: " + filePath);
			return false;
		}
	}
	
	public static FuzzyControlSystem load(String filePath) {
		try {
			FileInputStream file = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(file);
			FuzzyControlSystem s = (FuzzyControlSystem)in.readObject();
			in.close();
			return s;
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Incorrect loadpath: " + filePath);
			return null;
		}
	}
	
	public void setVarFuzz(double[] minX, double[] maxX, String[] titles){
		for(int i = 0; i < inputFuzz.length; i++) {
			inputFuzz[i] = new VarFuzzification(minX[i], maxX[i], titles[i]);
		}
	}
	
	public String toReadalbeString() {
		String result = "";
		result += "System type: " + systemOutput.getTitle() + "\n";
		result += "Number of variables: " + inputFuzz.length + "\n";
		result += "Number of rules: " + ruleSet.size() + "\n";
		
		return result;
	}
}

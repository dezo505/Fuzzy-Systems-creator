package FuzzySystem.FuzzySystemCore;

import java.io.Serializable;
import java.util.ArrayList;

import FuzzySystem.FuzzySystemCore.FuzzySets.FuzzySet;

public class VarFuzzification implements Serializable{
	private static final long serialVersionUID = 8795822703533460050L;

	private String variableTitle;
	
	private ArrayList<FuzzySet> sets;
	
	private double minX;
	private double maxX;
	
	public VarFuzzification(double minX, double maxX, String title) {
		sets = new ArrayList<FuzzySet>();
		this.minX = minX;
		this.maxX = maxX;
		this.variableTitle = title;
	}
	
	public void setVarTitle(String newTitle) {
		variableTitle = newTitle;
	}
	
	public String getVarTitle() {
		return variableTitle;
	}
	
	public double getMinX() {
		return minX;
	}
	
	public double getMaxX() {
		return maxX;
	}
	
	public int getSetsNumber() {
		return sets.size();
	}
	
	public void addSet(FuzzySet newSet) {
		sets.add(newSet);
	}
	
	public FuzzySet getSet(int index){
		return sets.get(index);
	}
	
	public void removeSet(int index) {
		sets.remove(index);
	}
}

package FuzzySystem.FuzzySystemCore.FuzzySets;

import java.io.Serializable;
import java.awt.Color;

public abstract class FuzzySet implements Serializable, Cloneable{
	private static final long serialVersionUID = 6027183887645887737L;
	
	private String variableTitle;
	private String fuzzySetTitle;
	
	private Color color;
	
	private double minX;
	private double maxX;

	
	public FuzzySet(double min, double max, String variableTitle, String setTitle){
		this.variableTitle = variableTitle;
		this.fuzzySetTitle = setTitle;
		this.minX = min;
		this.maxX = max;
		this.color = Color.ORANGE;
	}
	
	public abstract double calculateValue(double x);
	
	public double centerOfGravity() {
		int accuracy = 20000;
		
		double delta = (maxX - minX)/accuracy;
		//delta = 0.001;
		
		double sumUp = 0.0;
		double sumDown = 0.0;
		
		for(double temp = minX - delta; temp - delta <= maxX; temp += delta) {
			sumDown += calculateValue(temp);
			sumUp += temp * calculateValue(temp);
		}
		
		if(sumDown == 0)return 0;
		
		double result = sumUp / sumDown;
		return result;
	}
	
	public String getVariableTitle() {
		return variableTitle;
	}
	
	public void setVariableTitle(String variableTitle){
		this.variableTitle = variableTitle;
	}
	
	public String getSetTitle() {
		return fuzzySetTitle;
	}
	
	public void setSetTitle(String setTitle){
		this.fuzzySetTitle = setTitle;
	}
	
	public void setColor(Color newColor) {
		color = newColor;
	}
	
	public Color getColor() {
		return color;
	}
	
	public double getMinX() {
		return minX;
	}
	
	public double getMaxX() {
		return maxX;
	}
	
	@Override
	public FuzzySet clone() {
		try {
			return (FuzzySet)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}

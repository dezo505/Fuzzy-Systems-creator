package FuzzySystem.FuzzySystemCore.FuzzySets;

public class ThresholdSet extends FuzzySet{
	private static final long serialVersionUID = -540259829084166500L;
	
	private double threshHoldValue;
	private boolean beforeThreshHold;
	
	public ThresholdSet(double min, double max, String variableTitle, String setTitle,
			double threshHoldValue, boolean beforeThreshHold) {
		super(min, max, variableTitle, setTitle);
		this.threshHoldValue = threshHoldValue;
		this.beforeThreshHold = beforeThreshHold;
	}
	
	@Override
	public double calculateValue(double x) {
		if(beforeThreshHold) {
			return (x < threshHoldValue)? 1.0: 0.0;
		}else {
			return (x < threshHoldValue)? 0.0: 1.0;
		}
	}
}

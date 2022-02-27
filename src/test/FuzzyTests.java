package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

import FuzzySystem.FuzzySystemCore.FuzzyControlSystem;
import FuzzySystem.FuzzySystemCore.VarFuzzification;
import FuzzySystem.FuzzySystemCore.FuzzySets.*;
import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.*;
import FuzzySystem.Rules.Rule;
import FuzzySystem.Rules.RuleOutput.*;


class FuzzySetTests {
	@Test
	void testConstructorSettersAndGetters(){
		FuzzySet testSet = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", 0, 10);
		
		assertEquals(10, testSet.getMaxX());
		assertEquals(-10, testSet.getMinX());
		assertEquals("VariableTitle", testSet.getVariableTitle());
		assertEquals("SetTitle", testSet.getSetTitle());
		
		Color color = Color.GREEN;
		testSet.setColor(color);
		
		assertEquals(color, testSet.getColor());
	}
	
	@Test 
	void testLinearSetCalculationAscending(){
		FuzzySet testSet1 = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", -10, 10);
		
		assertEquals(0.0, round(testSet1.calculateValue(-10), 6));
		assertEquals(0.2, round(testSet1.calculateValue(-6), 6));
		assertEquals(0.5, round(testSet1.calculateValue(0), 6));
		assertEquals(0.75,round(testSet1.calculateValue(5), 6));
		assertEquals(1.0, round(testSet1.calculateValue(10), 6));
		
		FuzzySet testSet2 = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", -5, 0);
		
		assertEquals(0.0, round(testSet2.calculateValue(-10), 6));
		assertEquals(0.2, round(testSet2.calculateValue(-4), 6));
		assertEquals(1.0, round(testSet2.calculateValue(0), 6));
		assertEquals(1.0,round(testSet2.calculateValue(5), 6));
		assertEquals(1.0, round(testSet2.calculateValue(10), 6));
	}
	
	@Test 
	void testLinearSetCalculationDescending(){
		FuzzySet testSet1 = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", 10, -10);
		
		assertEquals(1.0, round(testSet1.calculateValue(-10), 6));
		assertEquals(0.8, round(testSet1.calculateValue(-6), 6));
		assertEquals(0.5, round(testSet1.calculateValue(0), 6));
		assertEquals(0.25,round(testSet1.calculateValue(5), 6));
		assertEquals(0.0, round(testSet1.calculateValue(10), 6));
		
		FuzzySet testSet2 = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", 5, -5);
		assertEquals(1.0, round(testSet2.calculateValue(-10), 6));
		assertEquals(1.0, round(testSet2.calculateValue(-6), 6));
		assertEquals(0.5, round(testSet2.calculateValue(0), 6));
		assertEquals(0.0,round(testSet2.calculateValue(5), 6));
		assertEquals(0.0, round(testSet2.calculateValue(10), 6));
	}
	
	@Test
	void testTriangleSetCalculation() {
		FuzzySet testSet1 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -10, 5, 10, false);
		
		assertEquals(0.0, round(testSet1.calculateValue(-10), 6));
		assertEquals(0.5, round(testSet1.calculateValue(-2.5), 6));
		assertEquals(1.0, round(testSet1.calculateValue(5), 6));
		assertEquals(0.1, round(testSet1.calculateValue(9.5), 6));
		assertEquals(0.0, round(testSet1.calculateValue(10.0), 6));
		
		FuzzySet testSet2 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -10, 5, 10, true);
		
		assertEquals(1.0, round(testSet2.calculateValue(-10), 6));
		assertEquals(0.5, round(testSet2.calculateValue(-2.5), 6));
		assertEquals(0.0, round(testSet2.calculateValue(5), 6));
		assertEquals(0.9, round(testSet2.calculateValue(9.5), 6));
		assertEquals(1.0, round(testSet2.calculateValue(10.0), 6));
	}
	
	@Test
	void testTrapezeSetCalculation() {
		FuzzySet testSet1 = new TrapezeSet(-10, 10, "VariableTitle", "SetTitle", -5, 0, 5, 10, false);
		
		assertEquals(0.0, round(testSet1.calculateValue(-10), 6));
		assertEquals(0.0, round(testSet1.calculateValue(-5), 6));
		assertEquals(0.8, round(testSet1.calculateValue(-1), 6));
		assertEquals(1.0, round(testSet1.calculateValue(4), 6));
		assertEquals(0.2, round(testSet1.calculateValue(9), 6));
		
		FuzzySet testSet2 = new TrapezeSet(-10, 10, "VariableTitle", "SetTitle", -5, 0, 5, 10, true);
		
		assertEquals(1.0, round(testSet2.calculateValue(-10), 6));
		assertEquals(1.0, round(testSet2.calculateValue(-5), 6));
		assertEquals(0.2, round(testSet2.calculateValue(-1), 6));
		assertEquals(0.0, round(testSet2.calculateValue(4), 6));
		assertEquals(0.8, round(testSet2.calculateValue(9), 6));	
	}

	@Test
	void testCapedSetCalculation() {
		FuzzySet testSet1 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -5, 0, 5, false);
		FuzzySet testSet2 = new CapedSet(-10, 10, "VariableTitle", "SetTitle", testSet1, 0.3);
		
		assertEquals(0.0, round(testSet2.calculateValue(-7), 6));
		assertEquals(0.2, round(testSet2.calculateValue(-4), 6));
		assertEquals(0.3, round(testSet2.calculateValue(-1), 6));
		assertEquals(0.3, round(testSet2.calculateValue(3), 6));
		assertEquals(0.0, round(testSet2.calculateValue(9), 6));	
	}
	
	@Test
	void testSumSetCalculation() {
		FuzzySet testSet1 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -10, -5, 0, false);
		FuzzySet testSet2 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -5, 0, 5, false);
		FuzzySet testSet3 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -10, 5, 10, false);
		
		SumSet testSumSet = new SumSet(-10, 10, "VariableTitle", "SetTitle");
		testSumSet.addSet(testSet1);
		testSumSet.addSet(testSet2);
		testSumSet.addSet(testSet3);
		
		
		assertEquals(1.0, round(testSumSet.calculateValue(-5), 6));
		assertEquals(1.0, round(testSumSet.calculateValue(0), 6));
		assertEquals(1.0, round(testSumSet.calculateValue(5), 6));
		
		assertEquals(1.0, round(testSumSet.calculateValue(-5), 6));
		assertEquals(1.0, round(testSumSet.calculateValue(0), 6));
		assertEquals(1.0, round(testSumSet.calculateValue(5), 6));
		
		assertEquals(0.8, round(testSumSet.calculateValue(-4), 6));
		assertEquals(0.866667, round(testSumSet.calculateValue(3), 6));	
	}
	
	@Test
	void testVariableFuzzificationGetters() {
		VarFuzzification testFuzzification = new VarFuzzification(-100, 200, "VariableTitle");
		
		assertEquals("VariableTitle", testFuzzification.getVarTitle());
		assertEquals(-100, testFuzzification.getMinX());
		assertEquals(200, testFuzzification.getMaxX());
		
		FuzzySet testSet1 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -10, -5, 0, false);
		FuzzySet testSet2 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -5, 0, 5, false);
		FuzzySet testSet3 = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -10, 5, 10, false);
		
		testFuzzification.addSet(testSet1);
		testFuzzification.addSet(testSet2);
		testFuzzification.addSet(testSet3);
		
		assertEquals(3, testFuzzification.getSetsNumber());
		assertEquals(testSet2, testFuzzification.getSet(1));
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}

class FuzzyRulesTest{
	@Test
	void testRuleConstructor() {
		FuzzySet exampleSet = new TriangleSet(-10, 10, "VariableTitle", "SetTitle", -10, -5, 0, false);
		
		Rule testRule1 = new Rule(3);
		
		testRule1.setSet(1, exampleSet);
		assertEquals(exampleSet, testRule1.getSets()[1]);
		
		assertEquals(3, testRule1.getSets().length);
	}
	
	@Test
	void testRuleFullfilment2ArgTest() {
		FuzzySet exampleSet1 = new TriangleSet(0, 10, "VariableTitle", "SetTitle", 2, 8, 10, false);
		FuzzySet exampleSet2 = new TriangleSet(0, 5, "VariableTitle", "SetTitle", 0, 2.5, 5, false);
		
		Rule testRule = new Rule(2);
		
		testRule.setAndOr(0, true);
		testRule.setSet(0, exampleSet1);
		testRule.setSet(1, exampleSet2);
		
		assertEquals(1.0, round(testRule.fulfillment(new double[] {8.0, 2.5}), 3));
		assertEquals(0.5, round(testRule.fulfillment(new double[] {9.0, 3.0}), 3));
		assertEquals(0.4, round(testRule.fulfillment(new double[] {5.0, 4.0}), 3));
		assertEquals(0.0, round(testRule.fulfillment(new double[] {1.0, 2.5}), 3));	
		
		testRule.setAndOr(0, false);
		
		assertEquals(1.0, round(testRule.fulfillment(new double[] {8.0, 2.5}), 3));
		assertEquals(0.8, round(testRule.fulfillment(new double[] {9.0, 3.0}), 3));
		assertEquals(0.5, round(testRule.fulfillment(new double[] {5.0, 4.0}), 3));
		assertEquals(1.0, round(testRule.fulfillment(new double[] {1.0, 2.5}), 3));	
	}
	
	@Test
	void testRuleFullfilment3Arg() {
		FuzzySet exampleSet1 = new TriangleSet(0, 10, "VariableTitle", "SetTitle", 2, 8, 10, false);
		FuzzySet exampleSet2 = new TriangleSet(0, 10, "VariableTitle", "SetTitle", 0, 3, 6, false);
		FuzzySet exampleSet3 = new TriangleSet(0, 10, "VariableTitle", "SetTitle", 2, 3, 5, false);	
		
		Rule testRule = new Rule(3);
		
		testRule.setSet(0, exampleSet1);
		testRule.setSet(1, exampleSet2);
		testRule.setSet(2, exampleSet3);
		
		testRule.setAndOr(0, true);
		testRule.setAndOr(1, true);
		
		assertEquals(1.0, round(testRule.fulfillment(new double[] {8.0, 3.0, 3.0}), 3));
		assertEquals(0.5, round(testRule.fulfillment(new double[] {9.0, 3.0, 4.0}), 3));
		assertEquals(0.0, round(testRule.fulfillment(new double[] {5.0, 4.0, 6.0}), 3));
		assertEquals(0.25, round(testRule.fulfillment(new double[] {5.0, 2.0, 4.5}), 3));	
		
		testRule.setAndOr(0, false);
		testRule.setAndOr(1, false);
		
		assertEquals(1.0, round(testRule.fulfillment(new double[] {8.0, 3.0, 3.0}), 3));
		assertEquals(1.0, round(testRule.fulfillment(new double[] {9.0, 3.0, 4.0}), 3));
		assertEquals(0.5, round(testRule.fulfillment(new double[] {5.0, 5.0, 6.0}), 3));
		assertEquals(0.667, round(testRule.fulfillment(new double[] {5.0, 2.0, 4.5}), 3));
	}
	
	@Test
	void testTakagiSugenoRuleOutput(){
		double[][] functions = new double[][]{new double[]{2,5}, new double[]{5, -2}};
		
		TksRO testRO = new TksRO(functions);
		testRO.setRule(new Rule(2){
			private static final long serialVersionUID = 1L;

			@Override
			public double fulfillment(double[] values) {
				return 0.5;
			}
		});
		
		assertEquals(16.0, testRO.ruleValue(new double[]{7,3}));
		assertEquals(1.5, testRO.ruleValue(new double[]{0,0}));
		assertEquals(-7.5, testRO.ruleValue(new double[]{1,-4}));
		assertEquals(-14.0, testRO.ruleValue(new double[]{-3,-5}));
	}
	
	@Test 
	void testMamdaniRuleOutput(){
		FuzzySet exampleSet = new TriangleSet(0, 10, "VariableTitle", "SetTitle", 0, 5, 10, false);
		MamdaniRO testRO = new MamdaniRO(exampleSet);
		testRO.setRule(new Rule(2){
			private static final long serialVersionUID = 1L;

			@Override
			public double fulfillment(double[] values) {
				return 0.6;
			}
		});
		
		FuzzySet resultSet = (FuzzySet)testRO.ruleValue(new double[] {});
		assertEquals(0.0, round(resultSet.calculateValue(10),6));
		assertEquals(0.4, round(resultSet.calculateValue(8),6));
		assertEquals(0.6, round(resultSet.calculateValue(7),6));
		assertEquals(0.6, round(resultSet.calculateValue(6),6));
		assertEquals(0.6, round(resultSet.calculateValue(5),6));
		assertEquals(0.2, round(resultSet.calculateValue(1),6));
	}
	
	@Test
	void testTsukamotoRuleOutput() {
		FuzzySet exampleSetAsc  = new LinearFuzzySet(0, 10, "VariableTitle", "SetTitle", 2, 7);
		FuzzySet exampleSetDesc = new LinearFuzzySet(0, 10, "VariableTitle", "SetTitle", 7, 2);
		
		TsukamotoRO testROAsc = new TsukamotoRO(exampleSetAsc);
		TsukamotoRO testRODesc = new TsukamotoRO(exampleSetDesc);
		
		testROAsc.setRule(new Rule(2){
			private static final long serialVersionUID = 1L;

			@Override
			public double fulfillment(double[] values) {
				return 0.8;
			}
		});
		
		testRODesc.setRule(new Rule(2){
			private static final long serialVersionUID = 1L;

			@Override
			public double fulfillment(double[] values) {
				return 0.8;
			}
		});
		
		assertEquals(6.0, round((double)testROAsc .ruleValue(null), 6));
		assertEquals(3.0, round((double)testRODesc.ruleValue(null), 6));
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}

class FuzzySystemTests{
	@Test
	void testTakagiSugenoSystem(){
		FuzzySet[] exampleSets = new FuzzySet[4];
		
		exampleSets[0] = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", -10, 10);
		exampleSets[1] = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", 10, -10);
		
		exampleSets[2] = new LinearFuzzySet(-5, 5, "VariableTitle", "SetTitle", -5, 5);
		exampleSets[3] = new LinearFuzzySet(-5, 5, "VariableTitle", "SetTitle", 5, -5);
		
		TksRO ruleOutput1 = new TksRO(new double[][]{new double[]{1,0}, new double[] {3,2}});
		TksRO ruleOutput2 = new TksRO(new double[][]{new double[]{1,0}, new double[] {2,-4}});
		
		Rule rule1 = new Rule(2, ruleOutput1);
		rule1.setSet(0, exampleSets[0]);
		rule1.setSet(1, exampleSets[2]);
		rule1.setAndOr(true);
		
		Rule rule2 = new Rule(2, ruleOutput2);
		rule2.setSet(0, exampleSets[1]);
		rule2.setSet(1, exampleSets[3]);
		rule2.setAndOr(true);
		
		TksSO systemOutput = new TksSO();
		
		FuzzyControlSystem testSystem = new FuzzyControlSystem(2, systemOutput);
		testSystem.addRule(rule1);
		testSystem.addRule(rule2);
		
		double[] values1 = new double[] {0,0};
		assertEquals(-1.0, round(testSystem.outputValue(values1), 3));
		
		double[] values2 = new double[] {4,3};
		assertEquals(13.0, round(testSystem.outputValue(values2), 3));
		
		double[] values3 = new double[] {-2,5};
		assertEquals(15.0, round(testSystem.outputValue(values3), 3));
		
		double[] values4 = new double[] {-8,-2};
		assertEquals(-15.5, round(testSystem.outputValue(values4), 3));
		
		double[] values5 = new double[] {1,-2};
		assertEquals(-5.4, round(testSystem.outputValue(values5), 3));
		
		
	}
	
	@Test
	void testTsukamotoSystem(){
		FuzzySet[] exampleSets = new FuzzySet[4];
		
		exampleSets[0] = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", -10, 10);
		exampleSets[1] = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", 10, -10);
		
		exampleSets[2] = new LinearFuzzySet(-5, 5, "VariableTitle", "SetTitle", -5, 5);
		exampleSets[3] = new LinearFuzzySet(-5, 5, "VariableTitle", "SetTitle", 5, -5);
		
		TsukamotoRO ruleOutput1 = new TsukamotoRO(new LinearFuzzySet(0, 10, "VariableTitle", "SetTitle", 0, 10));
		TsukamotoRO ruleOutput2 = new TsukamotoRO(new LinearFuzzySet(0, 10, "VariableTitle", "SetTitle", 0, 6));
		
		Rule rule1 = new Rule(2, ruleOutput1);
		rule1.setSet(0, exampleSets[0]);
		rule1.setSet(1, exampleSets[2]);
		rule1.setAndOr(true);
		
		Rule rule2 = new Rule(2, ruleOutput2);
		rule2.setSet(0, exampleSets[1]);
		rule2.setSet(1, exampleSets[3]);
		rule2.setAndOr(true);
		
		TsukamotoSO systemOutput = new TsukamotoSO(0, 10);
		
		FuzzyControlSystem testSystem = new FuzzyControlSystem(2, systemOutput);
		testSystem.addRule(rule1);
		testSystem.addRule(rule2);
		
		double[] values1 = new double[] {0,0};
		assertEquals(4.0, round(testSystem.outputValue(values1), 3));
		
		double[] values2 = new double[] {4,3};
		assertEquals(5.711, round(testSystem.outputValue(values2), 3));
		
		double[] values3 = new double[] {-2,5};
		assertEquals(4.0, round(testSystem.outputValue(values3), 3));
		
		double[] values4 = new double[] {-8,-2};
		assertEquals(3.8, round(testSystem.outputValue(values4), 3));
		
		double[] values5 = new double[] {1,-2};
		assertEquals(2.82, round(testSystem.outputValue(values5), 3));
		
		
	}
	
	@Test
	void testMamdaniSystem1(){
		FuzzySet[] exampleSets = new FuzzySet[4];
		
		exampleSets[0] = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", -10, 10);
		exampleSets[1] = new LinearFuzzySet(-10, 10, "VariableTitle", "SetTitle", 10, -10);
		
		exampleSets[2] = new LinearFuzzySet(-5, 5, "VariableTitle", "SetTitle", -5, 5);
		exampleSets[3] = new LinearFuzzySet(-5, 5, "VariableTitle", "SetTitle", 5, -5);
		
		MamdaniRO ruleOutput1 = new MamdaniRO(new LinearFuzzySet(-20, 20, "VariableTitle", "SetTitle", -20, 20));
		MamdaniRO ruleOutput2 = new MamdaniRO(new LinearFuzzySet(-20, 20, "VariableTitle", "SetTitle", 20, -20));
		
		Rule rule1 = new Rule(2, ruleOutput1);
		rule1.setSet(0, exampleSets[0]);
		rule1.setSet(1, exampleSets[2]);
		rule1.setAndOr(true);
		
		Rule rule2 = new Rule(2, ruleOutput2);
		rule2.setSet(0, exampleSets[1]);
		rule2.setSet(1, exampleSets[3]);
		rule2.setAndOr(true);
		
		VarFuzzification mamdaniOutputFuzz = new VarFuzzification(-20, 20, "-");
		
		mamdaniOutputFuzz.addSet(new LinearFuzzySet(-20, 20, "VariableTitle", "SetTitle", -20, 20));
		mamdaniOutputFuzz.addSet(new LinearFuzzySet(-20, 20, "VariableTitle", "SetTitle", 20, -20));
		
		MamdaniSO systemOutput = new MamdaniSO(mamdaniOutputFuzz);
		
		FuzzyControlSystem testSystem = new FuzzyControlSystem(2, systemOutput);
		testSystem.addRule(rule1);
		testSystem.addRule(rule2);
		
		double[] values1 = new double[] {0,0};
		assertEquals(0.0, round(testSystem.outputValue(values1), 2));
		
		double[] values2 = new double[] {10,5};
		assertEquals(6.67 , round(testSystem.outputValue(values2), 2));
		
		
		double[] values3 = new double[] {-2,3};
		assertEquals(2.43, round(testSystem.outputValue(values3), 2));
		
		double[] values4 = new double[] {-8,-1};
		assertEquals(-4.86, round(testSystem.outputValue(values4), 2));
	}
	
	@Test
	void testMamdaniSystem2(){
		FuzzySet[] exampleSets = new FuzzySet[3];
		
		exampleSets[0] = new LinearFuzzySet(0, 10, "VariableTitle", "SetTitle", 5, 0);
		exampleSets[1] = new TriangleSet(0, 10, "VariableTitle", "SetTitle", 0, 5, 10, false);
		exampleSets[2] = new LinearFuzzySet(0, 10, "VariableTitle", "SetTitle", 5, 10);
		
		MamdaniRO ruleOutput1 = new MamdaniRO(new LinearFuzzySet(0, 30, "VariableTitle", "SetTitle", 15, 0));
		MamdaniRO ruleOutput2 = new MamdaniRO(new TriangleSet(0, 30, "VariableTitle", "SetTitle", 0, 15, 30, false));
		MamdaniRO ruleOutput3 = new MamdaniRO(new LinearFuzzySet(0, 30, "VariableTitle", "SetTitle", 15, 30));
		
		Rule rule1 = new Rule(3, ruleOutput1);
		rule1.setSet(0, exampleSets[0]);
		rule1.setSet(1, exampleSets[0]);
		rule1.setSet(2, exampleSets[0]);
		rule1.setAndOr(true);
		
		Rule rule2 = new Rule(3, ruleOutput2);
		rule2.setSet(0, exampleSets[1]);
		rule2.setSet(1, exampleSets[1]);
		rule2.setSet(2, exampleSets[1]);
		rule2.setAndOr(true);
		
		Rule rule3 = new Rule(3, ruleOutput3);
		rule3.setSet(0, exampleSets[2]);
		rule3.setSet(1, exampleSets[2]);
		rule3.setSet(2, exampleSets[2]);
		rule3.setAndOr(true);
		
		VarFuzzification mamdaniOutputFuzz = new VarFuzzification(0, 30, "-");
		
		mamdaniOutputFuzz.addSet(new LinearFuzzySet(0, 30, "VariableTitle", "SetTitle", 15, 0));
		mamdaniOutputFuzz.addSet(new TriangleSet(0, 30, "VariableTitle", "SetTitle", 0, 15, 30, false));
		mamdaniOutputFuzz.addSet(new LinearFuzzySet(0, 30, "VariableTitle", "SetTitle", 15, 30));
		
		MamdaniSO systemOutput = new MamdaniSO(mamdaniOutputFuzz);
		
		FuzzyControlSystem testSystem = new FuzzyControlSystem(3, systemOutput);
		testSystem.addRule(rule1);
		testSystem.addRule(rule2);
		testSystem.addRule(rule3);
		
		double[] values1 = new double[] {0,0,0};
		assertEquals(5.0, round(testSystem.outputValue(values1), 2));
		
		double[] values2 = new double[] {5,5,5};
		assertEquals(15.0 , round(testSystem.outputValue(values2), 2));
		
		double[] values3 = new double[] {10,10,10};
		assertEquals(25.0 , round(testSystem.outputValue(values3), 2));
		
		double[] values4 = new double[] {2, 3, 4};
		assertEquals(14.58, round(testSystem.outputValue(values4), 2));
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}


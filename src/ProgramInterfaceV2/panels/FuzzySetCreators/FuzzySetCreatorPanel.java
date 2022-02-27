package ProgramInterfaceV2.panels.FuzzySetCreators;

import FuzzySystem.FuzzySystemCore.FuzzySets.FuzzySet;
import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.ProgramPanel;

public abstract class FuzzySetCreatorPanel extends ProgramPanel{		// interface that informs that given class can generate FuzzySets
	private static final long serialVersionUID = 6671719175151390903L;
	
	public FuzzySetCreatorPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y,int width, int height){
		super(programGUI, panelParent, panelTitle, x, y, width, height);
	}
	
	protected abstract FuzzySet createFuzzySet();		//returns fuzzySets created by class
	protected abstract boolean isDataCorrect();		//check if given class can create fuzzy set right now
	public abstract FuzzySet getLastGeneratedSet();	//returns last created set
}

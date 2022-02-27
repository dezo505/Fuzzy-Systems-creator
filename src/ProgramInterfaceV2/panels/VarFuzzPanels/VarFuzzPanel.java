package ProgramInterfaceV2.panels.VarFuzzPanels;

import FuzzySystem.FuzzySystemCore.VarFuzzification;
import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.ProgramPanel;

public abstract class VarFuzzPanel extends ProgramPanel{
	private static final long serialVersionUID = -6592438490432605774L;
	
	protected VarFuzzPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle,
			int x, int y, int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
	}
	
	public abstract VarFuzzification getVarFuzz();
	public abstract void update();
}

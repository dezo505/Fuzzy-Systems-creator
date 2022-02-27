package ProgramInterfaceV2.panels.FuzzySetCreators;

import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.ProgramPanel;

public class FuzzyCreatorFactory {
	public static FuzzySetCreatorPanel createFuzzySetCreator(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, 
						int x, int y, int width, int height, double minX, double maxX, char type) {
		if(type == 'G' || type == 'g')
			return new GaussSetPanel(programGUI, panelParent, panelTitle,
					x, y, width, height, minX, maxX);
		if(type == 'T' || type == 't')
			return new TrapezeSetPanel(programGUI, panelParent, panelTitle,
					x, y, width, height, minX, maxX);
		if(type == 'R' || type == 'r')
			return new TriangleSetPanel(programGUI, panelParent, panelTitle,
					x, y, width, height, minX, maxX);
		if(type == 'E' || type == 'e')
			return new MonotoneGaussPanel(programGUI, panelParent, panelTitle,
					x, y, width, height, minX, maxX);
		if(type == 'L' || type == 'l')
			return new LinearSetPanel(programGUI, panelParent, panelTitle,
					x, y, width, height, minX, maxX);
		if(type == 'H' || type == 'h')
			return new ThresholdSetPanel(programGUI, panelParent, panelTitle,
					x, y, width, height, minX, maxX);
		return null;
	}
}

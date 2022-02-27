package ProgramInterfaceV2.panels.VarFuzzPanels;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import FuzzySystem.FuzzySystemCore.VarFuzzification;
import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.myComponents.GoToKidsComboBox;
import ProgramInterfaceV2.myComponents.RemoveSetButton;
import ProgramInterfaceV2.myComponents.VarFuzzPreview;
import ProgramInterfaceV2.panels.ProgramPanel;
import ProgramInterfaceV2.panels.FuzzySetCreators.FuzzyCreatorFactory;

public class TsukamotoOutputVarFuzzPanel extends VarFuzzPanel{
	private static final long serialVersionUID = -736298342641284730L;
	protected VarFuzzification varFuzz;	//varfuzz
	private VarFuzzPreview preview;		//varfuzz image
	private RemoveSetButton removeSet;	//button that let remove chosen set from varfuzz

	
	public TsukamotoOutputVarFuzzPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle,
						int x, int y, int width, int height, double minX, double maxX) {
		
		
		super(programGUI, panelParent, panelTitle, x, y, width, height);

		
		varFuzz = new VarFuzzification(minX, maxX, "Possible Tsukamoto output sets");
		
		addKid(FuzzyCreatorFactory.createFuzzySetCreator(programGUI, this, "Monotone Gauss creator", 
				this.getX(), this.getY(), width, height, minX, maxX, 'E'));
		addKid(FuzzyCreatorFactory.createFuzzySetCreator(programGUI, this, "Linear creator", 
				this.getX(), this.getY(), width, height, minX, maxX, 'L'));
		addKid(FuzzyCreatorFactory.createFuzzySetCreator(programGUI, this, "Threshold creator", 
				this.getX(), this.getY(), width, height, minX, maxX, 'H'));
		addComponents(width, height);
	}
	
	public VarFuzzification getVarFuzz() {
		return varFuzz;
	}
	
	@Override
	public void update(){		//update onScreen components
		removeSet.update();
		preview.update();
		try{
			((JFrame) SwingUtilities.getWindowAncestor((JPanel)this)).repaint();
		}catch(NullPointerException e) {}
	}

	@Override
	public void addComponents(int width, int height) {
		GoToKidsComboBox b1 = (new GoToKidsComboBox(programGUI, this, "Create set", width * 33 / 50, height * 42/50, width * 15/50, height * 2/50));
		b1.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
		b1.addTo(this);
		
		
		preview = new VarFuzzPreview(this, width/20, height/10, width * 18/20, height * 7/10, varFuzz);
		preview.addTo(this);
		preview.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
		
		removeSet = new RemoveSetButton(this, "Delete set", width * 17 / 50, height * 42/50, width * 15/50, height * 2/50);
		removeSet.addTo(this);
		removeSet.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
	}
	
	public void reset(double minX, double maxX, int rFuzzySetsSize){
		varFuzz = new VarFuzzification(minX, maxX, "-");
	}
	
	public void setTitle(String newTitle) {
		preview.setTitle(newTitle);
	}
}

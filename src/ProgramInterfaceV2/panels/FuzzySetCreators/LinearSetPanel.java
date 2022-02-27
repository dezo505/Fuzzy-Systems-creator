package ProgramInterfaceV2.panels.FuzzySetCreators;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import FuzzySystem.FuzzyCreator;
import FuzzySystem.FuzzySystemCore.FuzzySets.FuzzySet;
import ProgramInterfaceV2.panels.VarFuzzPanels.VarFuzzPanel;
import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.ProgramPanel;
import ProgramInterfaceV2.myComponents.ButtonTextField;
import ProgramInterfaceV2.myComponents.ColorPicker;
import ProgramInterfaceV2.myComponents.FuzzySetPreview;
import ProgramInterfaceV2.myComponents.GoToButton;
import ProgramInterfaceV2.myComponents.MutableVar;

public class LinearSetPanel extends FuzzySetCreatorPanel{ //create Fuzzy sets in gaussian distribution shape

	private static final long serialVersionUID = -3570967345254528614L;
	
	private LinearSetPanel self = this;
	
	private FuzzySet lastS;
	
	private double minX;
	private double maxX;
	
	private MutableVar<Double> firstX;		
	private MutableVar<Double> lastX;		
	private MutableVar<String> title;		//title of created set					//string
	
	private MutableVar<Integer>[] color;		//created set color		//int[3]
	
	@SuppressWarnings({ "unchecked" })
	public LinearSetPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle,
						int x, int y, int width, int height, double minX, double maxX) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
		
		color = new MutableVar[] {new MutableVar<Integer>(0), new MutableVar<Integer>(0), new MutableVar<Integer>(0)};
		
		
		this.minX = minX;
		this.maxX = maxX;
		
		firstX = new MutableVar<Double>(minX);
		lastX = new MutableVar<Double>(maxX);
		
		title = new MutableVar<String>("New linear set");
		
		addComponents(width, height);
	}

	@Override
	public FuzzySet createFuzzySet() {
		FuzzySet result = FuzzyCreator.linearFuzzySet(minX, maxX, firstX.getValue(), lastX.getValue(),
				title.getValue(), ((VarFuzzPanel)getPanelParent()).getVarFuzz().getVarTitle());
		
		result.setColor(new Color(color[0].getValue(), color[1].getValue(), color[2].getValue()));
		
		lastS = result;
		
		return result;
	}

	@Override
	public boolean isDataCorrect(){
		return (firstX.getValue() != lastX.getValue());
	}
	
	@Override
	public FuzzySet getLastGeneratedSet() {
		return lastS;
	}

	@Override
	public void addComponents(int width, int height) {
		ButtonTextField<Double> b1 =(new ButtonTextField<Double>(firstX, "set", "Lower X", programGUI.getFrame(),
				width * 23/30, height * 1/25, width * 2/10, height * 1/20));
		b1.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
		b1.addTo(this);
		
		ButtonTextField<Double> b2 = (new ButtonTextField<Double>(lastX, "set", "Higher X", programGUI.getFrame(),
				width * 23/30, height * 3/25, width * 2/10, height * 1/20));
		b2.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
		b2.addTo(this);
		
		ButtonTextField<String> b3 = (new ButtonTextField<String>(title, "set", "Set title", programGUI.getFrame(),
				width * 23/30, height * 5/25, width * 2/10, height * 1/20));
		b3.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
		b3.addTo(this);
		
		ColorPicker b4 = (new ColorPicker(color[0], color[1], color[2], "Set color", width * 23/30, height * 13/25,  width * 2/10, height * 6/25));
		b4.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
		b4.addTo(this);
		
		FuzzySetPreview preview = (new FuzzySetPreview(width * 1/10, height * 1/10, width * 6/10, height * 7/10, createFuzzySet()));
		preview.setColor(programGUI.getSecondaryColor(), programGUI.getTextColor());
		preview.addTo(this);
		
		JButton genSetButton = new JButton();
		genSetButton.setBackground(programGUI.getSecondaryColor());
		genSetButton.setForeground(programGUI.getTextColor());
		
		genSetButton.setText("Generate");
		genSetButton.setBounds(width * 23/30, height * 17/20, width * 2/10, height * 1/20);
		
		genSetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isDataCorrect()) {
					createFuzzySet();
					preview.update(lastS);
					self.repaint();
				}else {
					JOptionPane.showMessageDialog(programGUI.getFrame(), "Warning", "Invalid data", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		
		this.add(genSetButton);
		
		JButton addSetButton = new JButton();
		addSetButton.setBackground(programGUI.getSecondaryColor());
		addSetButton.setForeground(programGUI.getTextColor());
		addSetButton.setText("Add set");
		addSetButton.setBounds(width * 19/30, height * 17/20, width * 3/30, height * 1/20);
		
		addSetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				((VarFuzzPanel)getPanelParent()).getVarFuzz().addSet(getLastGeneratedSet());
				((VarFuzzPanel)getPanelParent()).update();
			}
		});
		
		this.add(addSetButton);
		
		JButton b5 = new GoToButton(programGUI, panelParent, "Return",
				width * 1/50, height * 1/50, width * 2/25, height * 1/25);
		b5.setBackground(programGUI.getSecondaryColor());
		b5.setForeground(programGUI.getTextColor());
		this.add(b5);
	}
}

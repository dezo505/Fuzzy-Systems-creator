package ProgramInterfaceV2.panels;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import FuzzySystem.FuzzySystemCore.VarFuzzification;
import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.MamdaniSO;
import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.TsukamotoSO;
import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.VarFuzzPanels.MamdaniOutputVarFuzzPanel;
import ProgramInterfaceV2.panels.VarFuzzPanels.TsukamotoOutputVarFuzzPanel;

public class RulesPanel extends ProgramPanel{
	private static final long serialVersionUID = 2064655340758293525L;
	
	private JComboBox<String> removableRules;
	private ProgramPanel systemOutputPanel;
	private ProgramPanel createRulePanel;
	private JTextArea rulesArea;
	private RulesPanel self = this;
	
	public RulesPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y, int width,
			int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
		
		if(programGUI.getSystem().getSystemOutput() instanceof MamdaniSO) {
			VarFuzzification vf = ((MamdaniSO)programGUI.getSystem().getSystemOutput()).getOutFuzz();
			systemOutputPanel = new MamdaniOutputVarFuzzPanel(programGUI, this, "Configure output", x, y, width, height,
					vf.getMinX(), vf.getMaxX());
		}else if(programGUI.getSystem().getSystemOutput() instanceof TsukamotoSO){
			double minimumX = ((TsukamotoSO)programGUI.getSystem().getSystemOutput()).getMinX();
			double maximumX = ((TsukamotoSO)programGUI.getSystem().getSystemOutput()).getMaxX();
			systemOutputPanel = new TsukamotoOutputVarFuzzPanel(programGUI, this, "Configure output", x, y, width, height,
					minimumX, maximumX);
		}else {
			systemOutputPanel = null;
		}
		
		createRulePanel = new CreateRulePanel(programGUI, this, "Creating rules", x, y, width, height);
		
		this.setBackground(programGUI.getMainColor());
		addComponents(width, height);
		update();
		
	}

	@Override
	public void addComponents(int width, int height) {
		rulesArea = new JTextArea();
		rulesArea.setBounds(0, 0, 3*width - 300, (height - 100) * 8/10);
		rulesArea.setBackground(programGUI.getSecondaryColor());
		rulesArea.setForeground(programGUI.getTextColor());
		rulesArea.setEditable(false);
		
		JScrollPane scrollableRulesArea = new JScrollPane(rulesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableRulesArea.getViewport().setPreferredSize(new Dimension( width - 100, (height - 100)));
		scrollableRulesArea.setBounds(50, 50, width - 100, (height - 100) * 8/10);
		scrollableRulesArea.getViewport().setViewPosition(new Point(0,0));
		scrollableRulesArea.setBackground(programGUI.getSecondaryColor());
		
		this.add(scrollableRulesArea);
		SwingUtilities.updateComponentTreeUI(this);
		
		JPanel buttonsArea = new JPanel();
		buttonsArea.setLayout(null);
		buttonsArea.setBounds(50, (height - 100) * 9/10 + 50, width - 100, height * 1/10);
		buttonsArea.setBackground(this.getBackground());
		
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(0, 0, buttonsArea.getWidth() * 1/7, buttonsArea.getHeight() * 1/2);
		deleteButton.setBackground(programGUI.getSecondaryColor());
		deleteButton.setForeground(programGUI.getTextColor());
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(removableRules.getSelectedIndex() == -1)return;
				programGUI.getSystem().getRules().remove(removableRules.getSelectedIndex());
				update();
			}
		});
		buttonsArea.add(deleteButton);
		
		removableRules = new JComboBox<String>();
		removableRules.setBounds(buttonsArea.getWidth() * 3/14, 0, buttonsArea.getWidth() * 1/7, buttonsArea.getHeight() * 1/2);
		removableRules.setBackground(programGUI.getSecondaryColor());
		removableRules.setForeground(programGUI.getTextColor());
		buttonsArea.add(removableRules);
		
		JButton createSystemOutput = new JButton("Configure output");
		createSystemOutput.setBounds(buttonsArea.getWidth() * 4/7, 0, buttonsArea.getWidth() * 1/7, buttonsArea.getHeight() * 1/2);
		createSystemOutput.setBackground(programGUI.getSecondaryColor());
		createSystemOutput.setForeground(programGUI.getTextColor());
		createSystemOutput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(systemOutputPanel == null) {
					JOptionPane.showMessageDialog(self, "you can't configure system output\nin this kind of system", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				programGUI.setMainPanel(systemOutputPanel);
				
			}
		});
		buttonsArea.add(createSystemOutput);

		
		JButton addRuleButton = new JButton("Create rule");
		addRuleButton.setBounds(buttonsArea.getWidth() * 6/7, 0, buttonsArea.getWidth() * 1/7, buttonsArea.getHeight() * 1/2);
		addRuleButton.setBackground(programGUI.getSecondaryColor());
		addRuleButton.setForeground(programGUI.getTextColor());
		addRuleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i  = 0; i < programGUI.getSystem().getInputSize(); i++){
					if(programGUI.getSystem().getInput(i).getSetsNumber() == 0) {
						JOptionPane.showMessageDialog(self, "You can't create rule\n,until you add atleast one set to each fuzzification", "Warning", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				programGUI.setMainPanel(createRulePanel);
			}
		});
		buttonsArea.add(addRuleButton);
		
		this.add(buttonsArea);
	}
	
	public void update() {
		updateComboBox();
		updateTextArea();
		this.repaint();
	}
	
	private void updateComboBox() {
		removableRules.removeAllItems();
		if(programGUI.getSystem() == null)return;
		if(programGUI.getSystem().getRules() == null)return;
		for(int i = 0; i < programGUI.getSystem().getRules().size(); i++) {
			removableRules.addItem("R" + String.valueOf(i+1));
		}
	}
	
	private void updateTextArea() {
		if(programGUI.getSystem() == null)return;
		if(programGUI.getSystem().getRules() == null)return;
		String newText = "";
		for(int i = 0; i < programGUI.getSystem().getRules().size(); i++) {
			newText += "R" + String.valueOf(i+1) + ": " + programGUI.getSystem().getRule(i).toReadableString() + "\n"; 
		}
		rulesArea.setText(newText);
	}
	
	public ProgramPanel getSystemOutputPanel() {
		return systemOutputPanel;
	}

}

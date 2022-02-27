package ProgramInterfaceV2.panels;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.*;
import FuzzySystem.Rules.Rule;
import FuzzySystem.Rules.RuleOutput.MamdaniRO;
import FuzzySystem.Rules.RuleOutput.RuleOutput;
import FuzzySystem.Rules.RuleOutput.TksRO;
import FuzzySystem.Rules.RuleOutput.TsukamotoRO;
import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.myComponents.GoToButton;
import ProgramInterfaceV2.panels.VarFuzzPanels.VarFuzzPanel;

public class CreateRulePanel extends ProgramPanel{
	private static final long serialVersionUID = -1143904988889023945L;

	private ArrayList<JComboBox<String>> chosenSets;
	private RuleOutputPanel ruleOutputPanel;
	private Rule lastGenerated;
	private JCheckBox andOrCheckBox;
	private CreateRulePanel self;
	
	protected CreateRulePanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y,
			int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
		lastGenerated = null;
		addComponents(width, height);
		update();
	}

	@Override
	public void addComponents(int width, int height) {
		JPanel basicRuleCreationPanel = new JPanel();	// 0, 0, 2/5 * width, 7/8 * height
		basicRuleCreationPanel.setBounds(0, 0, width * 5/10, height * 7/8);
		basicRuleCreationPanel.setBackground(programGUI.getMainColor());
		basicRuleCreationPanel.setLayout(null);
		
		JPanel scrollablePanel = new JPanel();
		scrollablePanel.setBounds(0, 0, width * 4/10, programGUI.getSystem().getInputSize() * 50 - 20);
		scrollablePanel.setBackground(programGUI.getMainColor());
		scrollablePanel.setLayout(null);
		
		chosenSets = new ArrayList<JComboBox<String>>();
		for(int i = 0; i < programGUI.getSystem().getInputSize(); i++) {
			JLabel varTitle = new JLabel();
			varTitle.setText(programGUI.getSystem().getInput(i).getVarTitle() + ":");
			varTitle.setBounds(10, 10 + 50 * i, width * 2/10 - 10, 30);
			varTitle.setForeground(programGUI.getTextColor());
			scrollablePanel.add(varTitle);
			
			JComboBox<String> setOptions = new JComboBox<String>();
			setOptions.setBounds(width * 2/10 - 10, 10 + 50 * i, width * 2/10 - 10, 30);
			setOptions.setForeground(programGUI.getTextColor());
			setOptions.setBackground(programGUI.getSecondaryColor());
			chosenSets.add(setOptions);
			scrollablePanel.add(setOptions);
		}
		
		JScrollPane scrollPane = new JScrollPane(scrollablePanel);
		scrollPane.setBounds(50, height * 1/10, width * 4/10, height * 3/5);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setViewSize(new Dimension(width * 1/5 + 30, programGUI.getSystem().getInputSize() * 50 - 20));
		scrollPane.getViewport().setViewPosition(new Point(0,0));
		scrollPane.setViewportView(scrollablePanel);
		basicRuleCreationPanel.add(scrollPane);
		basicRuleCreationPanel.revalidate();
		basicRuleCreationPanel.repaint();
		
		JButton b5 = new GoToButton(programGUI, panelParent, "Return",
				width * 1/50, height * 1/50, width * 2/25, height * 1/25);
		b5.setBackground(programGUI.getSecondaryColor());
		b5.setForeground(programGUI.getTextColor());
		this.add(b5);
		
		basicRuleCreationPanel.add(b5);
		
		andOrCheckBox = new JCheckBox();
		andOrCheckBox.setText("AND");
		andOrCheckBox.setBounds(50, height * 7/10, width * 1/5 + 30, height * 1/10);
		andOrCheckBox.setForeground(programGUI.getTextColor());
		andOrCheckBox.setBackground(programGUI.getMainColor());
		this.add(andOrCheckBox);
		
		JPanel previewPanel = new JPanel();	//
		previewPanel.setBounds(0, height * 7/8, width, height * 1/8);
		previewPanel.setBackground(programGUI.getMainColor());
		previewPanel.setLayout(null);
		
		JTextField rulePreview = new JTextField();
		rulePreview.setBackground(programGUI.getSecondaryColor());
		rulePreview.setForeground(programGUI.getTextColor());
		rulePreview.setBounds(50, previewPanel.getHeight() * 1/6, previewPanel.getWidth() * 1/2, previewPanel.getHeight() * 1/3);
		rulePreview.setEditable(false);
		previewPanel.add(rulePreview);
		
		JButton addRule = new JButton("Add");
		addRule.setBackground(programGUI.getSecondaryColor());
		addRule.setForeground(programGUI.getTextColor());
		addRule.setBounds(100 + previewPanel.getWidth() * 1/2, previewPanel.getHeight() * 1/6, previewPanel.getWidth() * 1/12, previewPanel.getHeight() * 1/3);
		addRule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lastGenerated == null)return;
				programGUI.getSystem().addRule(lastGenerated);
			}
		});
		previewPanel.add(addRule);
		
		JButton generateRule = new JButton("Generate");
		generateRule.setBackground(programGUI.getSecondaryColor());
		generateRule.setForeground(programGUI.getTextColor());
		generateRule.setBounds(150 + previewPanel.getWidth() * 7/12, previewPanel.getHeight() * 1/6, previewPanel.getWidth() * 1/12, previewPanel.getHeight() * 1/3);
		previewPanel.add(generateRule);
		generateRule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!ruleOutputPanel.isDataCorrect()) {
					JOptionPane.showMessageDialog(self, "Invalid data", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				lastGenerated = createRule();
				if(lastGenerated != null)rulePreview.setText(lastGenerated.toReadableString());
			}
		});
		previewPanel.add(addRule);
		
		if(programGUI.getSystem().getSystemOutput() instanceof MamdaniSO){
			ruleOutputPanel = new MamdaniROPanel(programGUI, this, "MamdaniRO", width * 3/5, 0, width * 5/10, height * 7/8);
		}
		else if(programGUI.getSystem().getSystemOutput() instanceof TksSO){
			ruleOutputPanel = new TksROPanel(programGUI, this, "TksRO", width * 3/5, 0, width * 5/10, height * 7/8);
		}else if(programGUI.getSystem().getSystemOutput() instanceof TsukamotoSO) {
			ruleOutputPanel = new TsukamotoROPanel(programGUI, this, "TsukamotoRO", width * 3/5, 0, width * 5/10, height * 7/8);
		}
		
		ruleOutputPanel.setBackground(programGUI.getMainColor());
		
				
		this.add(basicRuleCreationPanel);
		this.add(previewPanel);
		this.add(ruleOutputPanel);
	}
	
	@Override
	public void update() {
		for(int i = 0; i < programGUI.getSystem().getInputSize(); i++) {
			chosenSets.get(i).removeAllItems();
			for(int j = 0; j < programGUI.getSystem().getInput(i).getSetsNumber(); j++) {
				chosenSets.get(i).addItem( programGUI.getSystem().getInput(i).getSet(j).getSetTitle());
			}
		}
		if(ruleOutputPanel != null)ruleOutputPanel.update();
		SwingUtilities.updateComponentTreeUI(this);
		this.repaint();
	}
	
	public Rule createRule() {
		RuleOutput o = ruleOutputPanel.createRuleOutput();
		if(o == null)return null;
		
		Rule newRule = new Rule(programGUI.getSystem().getInputSize(), o);
		
		for(int i = 0; i < programGUI.getSystem().getInputSize(); i++) {
			newRule.setSet(i, programGUI.getSystem().getInput(i).getSet(chosenSets.get(i).getSelectedIndex()));
		}
		
		newRule.setAndOr(andOrCheckBox.isSelected());
		
		return newRule;
	}
}

abstract class RuleOutputPanel extends ProgramPanel{

	public RuleOutputPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y,
			int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
	}

	private static final long serialVersionUID = -4110044946670634691L;
	
	abstract RuleOutput createRuleOutput();
	public abstract boolean isDataCorrect();
}

class MamdaniROPanel extends RuleOutputPanel{
	private static final long serialVersionUID = -1767733602106887560L;
	private JComboBox<String> possibleSets;
	
	public MamdaniROPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y,
			int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
		addComponents(width, height);
	}

	@Override
	RuleOutput createRuleOutput() {
		if(possibleSets.getSelectedIndex() == -1)return null;
		
		return new MamdaniRO(((MamdaniSO)programGUI.getSystem().getSystemOutput()).getOutFuzz().getSet(possibleSets.getSelectedIndex()));
	}

	@Override
	public void addComponents(int width, int height) {
		possibleSets = new JComboBox<String>();
		possibleSets.setBounds(10 + width * 1/3, 100, width * 1/3, 30);
		possibleSets.setBackground(programGUI.getSecondaryColor());
		possibleSets.setForeground(programGUI.getTextColor());
		this.add(possibleSets);
		
		JLabel label = new JLabel("Output set:");
		label.setBounds(50, 100, width * 1/3, 30);
		label.setBackground(programGUI.getSecondaryColor());
		label.setForeground(programGUI.getTextColor());
		this.add(label);
	}
	
	@Override
	public void update() {
		possibleSets.removeAllItems();
		for(int i = 0; i < ((MamdaniSO)programGUI.getSystem().getSystemOutput()).getOutFuzz().getSetsNumber(); i++) {
			possibleSets.addItem(((MamdaniSO)programGUI.getSystem().getSystemOutput()).getOutFuzz().getSet(i).getSetTitle());
		}
		this.repaint();
	}

	@Override
	public boolean isDataCorrect() {
		if(possibleSets.getSelectedIndex() == -1)return false;
		return true;
	}
	
	
}

class TksROPanel extends RuleOutputPanel{
	private static final long serialVersionUID = -1767733602106887560L;
	private ArrayList<JTextField> bFactors;
	private ArrayList<JTextField> aFactors;
	
	public TksROPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y,
			int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
		addComponents(width, height);
	}

	@Override
	RuleOutput createRuleOutput(){
		double[][] functions = new double[programGUI.getSystem().getInputSize()][2];
		for(int i = 0; i < programGUI.getSystem().getInputSize(); i++) {
			functions[i][0] = Integer.valueOf(aFactors.get(i).getText());
			functions[i][1] = Integer.valueOf(bFactors.get(i).getText());
		}
		return new TksRO(functions);
	}

	@Override
	public void addComponents(int width, int height) {
		aFactors = new ArrayList<JTextField>();
		bFactors = new ArrayList<JTextField>();
		
		JPanel scrollablePanel = new JPanel();
		scrollablePanel.setBounds(0, height * 1/10, width * 7/10, height * 24/35);
		scrollablePanel.setBackground(programGUI.getMainColor());
		scrollablePanel.setLayout(null);
		
		for(int i = 0; i < programGUI.getSystem().getInputSize(); i++) {
			JLabel varTitle = new JLabel();
			varTitle.setText(programGUI.getSystem().getInput(i).getVarTitle() + ":");
			varTitle.setBounds(10, 10 + 50 * i, width * 1/5 - 10, 30);
			varTitle.setForeground(programGUI.getTextColor());
			scrollablePanel.add(varTitle);
			
			JTextField aFactor = new JTextField();
			aFactor.setBounds(width * 1/5 + 50, 10 + 50 * i, width * 1/5 - 10, 30);
			aFactor.setForeground(programGUI.getTextColor());
			aFactor.setBackground(programGUI.getSecondaryColor());
			aFactors.add(aFactor);
			scrollablePanel.add(aFactor);
			
			JTextField bFactor = new JTextField();
			bFactor.setBounds(width * 2/5 + 50, 10 + 50 * i, width * 1/5 - 10, 30);
			bFactor.setForeground(programGUI.getTextColor());
			bFactor.setBackground(programGUI.getSecondaryColor());
			bFactors.add(bFactor);
			scrollablePanel.add(bFactor);
		}
		
		JScrollPane scrollPane = new JScrollPane(scrollablePanel);
		scrollPane.setBounds(0 , height * 8/70, width * 7/10, height * 24/35);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setViewSize(new Dimension(width * 3/5 + 30, height * 3/5));
		scrollPane.getViewport().setViewPosition(new Point(0,0));
		scrollPane.setViewportView(scrollablePanel);
		
		this.add(scrollPane);
		this.revalidate();
		this.repaint();
	}
	
	private boolean isDouble(String string) {
		try {
			Double.valueOf(string);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}

	@Override
	public boolean isDataCorrect() {
		for(JTextField a: aFactors) {
			if(!isDouble(a.getText()))return false;
		}
		for(JTextField b: bFactors) {
			if(!isDouble(b.getText()))return false;
		}
		return true;
	}
}

class TsukamotoROPanel extends RuleOutputPanel{
	private static final long serialVersionUID = -1796773602106887560L;
	private JComboBox<String> possibleSets;
	private VarFuzzPanel possibleSetsPanel;
	
	public TsukamotoROPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y,
			int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
		possibleSetsPanel = (VarFuzzPanel)((RulesPanel)panelParent.getPanelParent()).getSystemOutputPanel();
		addComponents(width, height);
	}

	@Override
	RuleOutput createRuleOutput() {
		if(possibleSets.getSelectedIndex() == -1)return null;
		return new TsukamotoRO(possibleSetsPanel.getVarFuzz().getSet(possibleSets.getSelectedIndex()));
	}

	@Override
	public void addComponents(int width, int height) {
		possibleSets = new JComboBox<String>();
		possibleSets.setBounds(10 + width * 1/3, 100, width * 1/3, 30);
		possibleSets.setBackground(programGUI.getSecondaryColor());
		possibleSets.setForeground(programGUI.getTextColor());
		this.add(possibleSets);
		
		JLabel label = new JLabel("Output set:");
		label.setBounds(50, 100, width * 1/3, 30);
		label.setBackground(programGUI.getSecondaryColor());
		label.setForeground(programGUI.getTextColor());
		this.add(label);
	}
	
	@Override
	public void update() {
		possibleSets.removeAllItems();
		for(int i = 0; i < possibleSetsPanel.getVarFuzz().getSetsNumber(); i++) {
			possibleSets.addItem(possibleSetsPanel.getVarFuzz().getSet(i).getSetTitle());
		}
		this.repaint();
	}

	@Override
	public boolean isDataCorrect() {
		if(possibleSets.getSelectedIndex() == -1)return false;
		return true;
	}
}




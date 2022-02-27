package ProgramInterfaceV2.panels;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ProgramInterfaceV2.ProgramGUI;

public class TestSystemPanel extends ProgramPanel{
	private static final long serialVersionUID = -7518647003015518128L;

	private JTextField[] inputValues;
	private JTextArea rulesValue;
	private JTextArea systemInfo;
	private JTextField systemOutput;
	private TestSystemPanel self;
	
	public TestSystemPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int x, int y,
			int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y, width, height);
		addComponents(width, height);
	}
	
	@Override
	public void addComponents(int width, int height) {
		JPanel scrollableInputPanel = new JPanel();
		scrollableInputPanel.setLayout(null);
		scrollableInputPanel.setBounds(0, 0, 50 * programGUI.getSystem().getInputSize() + 20, height);
		scrollableInputPanel.setBackground(programGUI.getMainColor());
		
		inputValues = new JTextField[programGUI.getSystem().getInputSize()];
		for(int i = 0; i < programGUI.getSystem().getInputSize(); i++) {
			JLabel title = new JLabel();
			title.setText(programGUI.getSystem().getInput(i).getVarTitle());
			title.setBounds(width * 1/15 * 1/3, 20 + 50*i, width * 6/15 * 1/3, 30);
			title.setForeground(programGUI.getTextColor());
			scrollableInputPanel.add(title);
			
			inputValues[i] = new JTextField();
			inputValues[i].setBounds(width * 8/15 * 1/3, 20 + 50*i, width * 6/15 * 1/3, 30);
			inputValues[i].setBackground(programGUI.getSecondaryColor());
			inputValues[i].setForeground(programGUI.getTextColor());
			scrollableInputPanel.add(inputValues[i]);
		}
		
		JScrollPane scrollPane = new JScrollPane(scrollableInputPanel);
		scrollPane.setBounds(width * 1/3, height * 1/10, width * 1/3, height * 8/10);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setViewSize(new Dimension(width * 1/3, height * 8/10));
		scrollPane.getViewport().setViewPosition(new Point(0,0));
		scrollPane.setViewportView(scrollableInputPanel);
		this.add(scrollPane);
		this.revalidate();
		this.repaint();
		
		systemInfo = new JTextArea();
		systemInfo.setText(programGUI.getSystem().toReadalbeString());
		systemInfo.setEditable(false);
		systemInfo.setBackground(programGUI.getMainColor());
		systemInfo.setForeground(programGUI.getTextColor());
		systemInfo.setBounds(0,0, width * 1/3, height * 1/3);
		this.add(systemInfo);
		
		rulesValue = new JTextArea();
		rulesValue.setEditable(false);
		rulesValue.setBackground(programGUI.getMainColor());
		rulesValue.setForeground(programGUI.getTextColor());
		rulesValue.setBounds(width * 2/3,0, width * 1/3, height * 2/3);
		this.add(rulesValue);
		
		systemOutput = new JTextField();
		systemOutput.setEditable(false);
		systemOutput.setBackground(programGUI.getMainColor());
		systemOutput.setForeground(programGUI.getTextColor());
		systemOutput.setBounds(width * 9/12, height * 9/12, width * 2/12, 30);
		this.add(systemOutput);
		
		JButton genButton = new JButton();
		genButton.setText("Test");
		genButton.setBackground(programGUI.getMainColor());
		genButton.setForeground(programGUI.getTextColor());
		genButton.setBounds(width * 9/12, height * 10/12, width * 2/12, 30);
		genButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isDataCorrect()) {
					JOptionPane.showMessageDialog(self, "Invalid data", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				generate();
			}
		});
		this.add(genButton);
	}
	
	private boolean isDouble(String string) {
		try {
			Double.valueOf(string);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}

	public boolean isDataCorrect() {
		for(JTextField v: inputValues) {
			if(!isDouble(v.getText()))return false;
		}
		return true;
	}
	
	public void update(){
		systemInfo.setText(programGUI.getSystem().toReadalbeString());
		this.repaint();
	}
	
	public void generate() {
		double[] values = new double[programGUI.getSystem().getInputSize()];
		
		for(int i = 0; i < values.length; i++) {
			values[i] = Double.valueOf(inputValues[i].getText());
		}
		
		String rulePanelText = "";
		for(int i = 0; i < programGUI.getSystem().getRules().size(); i++) {
			rulePanelText += "R" + String.valueOf(i+1) + ": " + String.valueOf(programGUI.getSystem().getRule(i).fulfillment(values)) + "\n";
		}
		rulesValue.setText(rulePanelText);
		
		systemOutput.setText(String.valueOf(programGUI.getSystem().outputValue(values)));
	}

}

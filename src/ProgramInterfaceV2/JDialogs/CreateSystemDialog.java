package ProgramInterfaceV2.JDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;

import FuzzySystem.FuzzySystemCore.FuzzyControlSystem;
import FuzzySystem.FuzzySystemCore.VarFuzzification;
import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.MamdaniSO;
import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.TksSO;
import FuzzySystem.FuzzySystemCore.FuzzySystemOutput.TsukamotoSO;
import ProgramInterfaceV2.ProgramGUI;

public class CreateSystemDialog extends JDialog{
	private static final long serialVersionUID = 3101151250228278213L;
	
	private ProgramGUI programGUI;
	
	private FuzzyControlSystem cSys;
	private CreateSystemDialog f = this;
	
	public CreateSystemDialog(ProgramGUI programGUI, int width, int height){
		
		this.programGUI = programGUI;
		this.setBounds(0, 0,width, height);
		this.setLocationRelativeTo(programGUI.getFrame());
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.setUndecorated(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBackground(programGUI.getSecondaryColor());
		this.setResizable(false);
		
		JPanel firstPanel = new firstPanel(width, height);
		firstPanel.setBackground(programGUI.getSecondaryColor());
		this.add(firstPanel);
		this.setVisible(true);
		firstPanel.repaint(new Rectangle(0 , 0, width, height));
	}
	
	private class firstPanel extends JPanel{
		private static final long serialVersionUID = 5029597850803425743L;
		
		private JComboBox<String> comboBoxType;
		private JLabel comboBoxText;
		private JTextField textFieldNumber;
		private JLabel textFieldText;
		private JButton buttonCreate;
		private JButton buttonCancel;
		
		private firstPanel self;
		
		
		firstPanel(int width, int height){
			this.setLayout(null);
			
			this.setBackground(programGUI.getSecondaryColor());
			
			this.setBounds(0, 0, width, height);
			
			comboBoxText = new JLabel();
			comboBoxText.setText("System type:");
			comboBoxText.setBounds(40, 20, 120, 20);
			comboBoxText.setBackground(programGUI.getSecondaryColor());
			comboBoxText.setForeground(programGUI.getTextColor());
			this.add(comboBoxText);
			
			comboBoxType = new JComboBox<String>(new String[]{"Mamdani System","Takagi Sugeno System","Tsukamoto System"});
			comboBoxType.setBackground(programGUI.getSecondaryColor());
			comboBoxType.setForeground(programGUI.getTextColor());
			comboBoxType.setBounds(160, 20, width - 200, 20);
			this.add(comboBoxType);
			
			
			textFieldText = new JLabel();
			textFieldText.setText("Number of variables:");
			textFieldText.setBounds(40, 70, 120, 20);
			textFieldText.setBackground(programGUI.getMainColor());
			textFieldText.setForeground(programGUI.getTextColor());
			this.add(textFieldText);
			
			textFieldNumber = new JTextField();
			textFieldNumber.setBackground(programGUI.getMainColor());
			textFieldNumber.setForeground(programGUI.getTextColor());
			textFieldNumber.setBounds(160, 70, width - 200, 20);
			this.add(textFieldNumber);
			
			buttonCreate = new JButton();
			buttonCreate.setBounds(width - 160, height - 80, 120, 20);
			buttonCreate.setBackground(programGUI.getMainColor());
			buttonCreate.setForeground(programGUI.getTextColor());
			buttonCreate.setText("Next");
			
			buttonCreate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isInteger(textFieldNumber.getText()) && Integer.valueOf(textFieldNumber.getText()) > 0) {
						if(JOptionPane.showConfirmDialog(self, "Your system:\n" + comboBoxType.getItemAt(comboBoxType.getSelectedIndex()) + "\nNumber of variables: " + textFieldNumber.getText(), "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							f.getContentPane().removeAll();
							f.add(new SecondPanel(width, height, comboBoxType.getSelectedIndex(), Integer.valueOf(textFieldNumber.getText())));
							f.repaint(0, 0, 0, width, height);
						}
					}else {
						JOptionPane.showMessageDialog(self, "Invalid data", "Warning", JOptionPane.WARNING_MESSAGE);
						textFieldNumber.setText("");
					}
				}
			});
			
			this.add(buttonCreate);
			
			buttonCancel = new JButton();
			buttonCancel.setBounds(40, height - 80, 120, 20);
			buttonCancel.setBackground(programGUI.getSecondaryColor());
			buttonCancel.setForeground(programGUI.getTextColor());
			buttonCancel.setText("Cancel");
			
			buttonCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					f.dispose();
				}
			});
			
			this.add(buttonCancel);
		}
	}
	
	private class SecondPanel extends JPanel{
		private static final long serialVersionUID = -1892114452716934055L;

		JTextField[] nameTextFields;
		JTextField[] minTextFields;
		JTextField[] maxTextFields;
		
		SecondPanel(int width, int height, int givenType, int variablesNumber){
			JPanel scrollPanePanel = new JPanel();
			
			int newHeight = Math.max(height, 50 * variablesNumber + 130);
			
			this.setLayout(null);
			
			scrollPanePanel.setLayout(null);
			
			scrollPanePanel.setBackground(programGUI.getSecondaryColor());
			
			scrollPanePanel.setBounds(0, 0, 580, newHeight);
			
			scrollPanePanel.setPreferredSize(new Dimension(580, newHeight));
			
			this.add(scrollPanePanel);
			
			
			
			
			this.setBounds(0, 0, width, newHeight);
			
			JLabel nameLabel = new JLabel();
			nameLabel.setForeground(programGUI.getTextColor());
			nameLabel.setText("Variable title:");
			nameLabel.setBounds(50, 30, 160, 20);
			scrollPanePanel.add(nameLabel);
			
			JLabel minLabel = new JLabel();
			minLabel.setForeground(programGUI.getTextColor());
			minLabel.setText("Minimum:");
			minLabel.setBounds(50 + 150 + 25, 30, 160, 20);
			scrollPanePanel.add(minLabel);
			
			JLabel maxLabel = new JLabel();
			maxLabel.setForeground(programGUI.getTextColor());
			maxLabel.setText("Maximum:");
			maxLabel.setBounds(50 + 300 + 50, 30, 160, 20);
			scrollPanePanel.add(maxLabel);
			
			nameTextFields = new JTextField[variablesNumber];
			minTextFields = new JTextField[variablesNumber];
			maxTextFields = new JTextField[variablesNumber];
			
			for(int i = 0; i < variablesNumber; i++) {
				nameTextFields[i] = new JTextField();
				nameTextFields[i].setBounds(50, 80 + 50*i, 140, 20);
				nameTextFields[i].setBackground(programGUI.getMainColor());
				nameTextFields[i].setForeground(programGUI.getTextColor());
				scrollPanePanel.add(nameTextFields[i]);
				
				minTextFields[i] = new JTextField();
				minTextFields[i].setBounds(50 + 150 + 25, 80 + 50*i, 140, 20);
				minTextFields[i].setBackground(programGUI.getMainColor());
				minTextFields[i].setForeground(programGUI.getTextColor());
				scrollPanePanel.add(minTextFields[i]);
				
				maxTextFields[i] = new JTextField();
				maxTextFields[i].setBounds(50 + 300 + 50, 80 + 50*i, 140, 20);
				maxTextFields[i].setBackground(programGUI.getMainColor());
				maxTextFields[i].setForeground(programGUI.getTextColor());
				scrollPanePanel.add(maxTextFields[i]);
			}
			
			JButton nextStage = new JButton();
			nextStage.setText("Next");
			nextStage.setBounds(50 + 300 + 50, 80 + 50*variablesNumber, 140, 20);
			nextStage.setBackground(programGUI.getSecondaryColor());
			nextStage.setForeground(programGUI.getTextColor());
			
			nextStage.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean isDataCorrect = true;
					for(JTextField t: minTextFields) {
						if(!isDouble(t.getText())){
							isDataCorrect = false;
							break;
						}
					}
					if(isDataCorrect) {
						for(JTextField t: maxTextFields) {
							if(!isDouble(t.getText())){
								isDataCorrect = false;
								break;
							}
						}
					}
					
					if(!isDataCorrect) {
						JOptionPane.showMessageDialog(f, "Invalid data", "Warning", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					for(int i = 0; i < minTextFields.length; i++) {
						if(Double.valueOf(minTextFields[i].getText()) > Double.valueOf(maxTextFields[i].getText())) {
							JOptionPane.showMessageDialog(f, "Invalid data:\n Minimum: " + minTextFields[i].getText() + "\nMaximum: " + maxTextFields[i].getText(),
									"Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
					double[] minX = new double[minTextFields.length];
					double[] maxX = new double[minTextFields.length];
					String[] titles = new String[minTextFields.length];
					for(int i = 0; i < minX.length; i++) {
						minX[i] = Double.valueOf(minTextFields[i].getText());
						maxX[i] = Double.valueOf(maxTextFields[i].getText());
						titles[i] = nameTextFields[i].getText();
					}
					
					switch(givenType){
						case 0:
							f.getContentPane().removeAll();
							f.add(new ThirdMamdaniPanel(width, height, variablesNumber, minX, maxX, titles));
							f.repaint();
							break;
						case 1:
							if(JOptionPane.showConfirmDialog(f, "Do you want to finish creating?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
								break;
							}
							cSys = new FuzzyControlSystem(variablesNumber , new TksSO());
							cSys.setVarFuzz(minX, maxX, titles);
							programGUI.setSystem(cSys);
							f.dispose();
							break;
						case 2:
							f.getContentPane().removeAll();
							f.add(new ThirdTsukamotoPanel(width, height, variablesNumber, minX, maxX, titles));
							f.repaint();
							break;
					}
					
				
				}
			});
			
			scrollPanePanel.add(nextStage);
			
			JButton cancel = new JButton();
			cancel.setText("Cancel");
			cancel.setBounds(50, 80 + 50*variablesNumber, 140, 20);
			cancel.setBackground(programGUI.getSecondaryColor());
			cancel.setForeground(programGUI.getTextColor());
			
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					f.dispose();
				}
			});
			
			scrollPanePanel.add(cancel);
			

			
			JScrollPane scrollPane = new JScrollPane(scrollPanePanel);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(0, 0, 600, 400);
			scrollPane.getViewport().setViewSize(new Dimension(580,newHeight));
			scrollPane.getViewport().setViewPosition(new Point(0,0));
			this.add(scrollPane);	
			
			SwingUtilities.updateComponentTreeUI(f);
		}
	}
	
	class ThirdMamdaniPanel extends JPanel{
		private static final long serialVersionUID = 7324988639546660028L;
		
		ThirdMamdaniPanel(int width, int height, int variablesNumber, double[] minX, double[] maxX, String[] titles)
		{
			this.setLayout(null);
			
			this.setBackground(programGUI.getSecondaryColor());
			
			this.setBounds(0, 0, width, height);
			
			JLabel minLabel = new JLabel();
			minLabel.setForeground(programGUI.getTextColor());
			minLabel.setText("Output minimum:");
			minLabel.setBounds(50, 30, 160, 20);
			this.add(minLabel);
			
			JLabel maxLabel = new JLabel();
			maxLabel.setForeground(programGUI.getTextColor());
			maxLabel.setText("Output maximum:");
			maxLabel.setBounds(50 + 300 + 50, 30, 160, 20);
			this.add(maxLabel);
			
			JTextField minTextField = new JTextField();
			minTextField.setForeground(programGUI.getTextColor());
			minTextField.setBackground(programGUI.getMainColor());
			minTextField.setBounds(50, 80, 160, 20);
			this.add(minTextField);
			
			JTextField maxTextField = new JTextField();
			maxTextField.setForeground(programGUI.getTextColor());
			maxTextField.setBackground(programGUI.getMainColor());
			maxTextField.setBounds(50 + 300 + 50, 80, 160, 20);
			this.add(maxTextField);
			
			JButton finish = new JButton();
			finish.setText("Finish");
			finish.setBackground(programGUI.getMainColor());
			finish.setForeground(programGUI.getTextColor());
			finish.setBounds(50 + 300 + 50, 130, 160, 20);
			finish.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!(isDouble(minTextField.getText()) && isDouble(maxTextField.getText()) &&
						Double.valueOf(minTextField.getText()) < Double.valueOf(maxTextField.getText()))) {
						JOptionPane.showMessageDialog(f, "Invalid data:\n Minimum: " + minTextField.getText() + "\nMaximum: " + maxTextField.getText(), "Warning", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if(JOptionPane.showConfirmDialog(f, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					cSys = new FuzzyControlSystem(
							variablesNumber , 
							new MamdaniSO(
									new VarFuzzification(Double.valueOf(minTextField.getText()), 
														 Double.valueOf(maxTextField.getText()),
														 "Output fuzzification"))
						);
					cSys.setVarFuzz(minX, maxX, titles);
					programGUI.setSystem(cSys);
					f.dispose();
				}
			});
			this.add(finish);
			
			JButton cancel = new JButton();
			cancel.setText("Cancel");
			cancel.setBackground(programGUI.getMainColor());
			cancel.setForeground(programGUI.getTextColor());
			cancel.setBounds(50, 130, 160, 20);
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					f.dispose();
				}
			});
			
			this.add(cancel);
		}
	}
	
	class ThirdTsukamotoPanel extends JPanel{
		private static final long serialVersionUID = 7324988639546660028L;
		
		ThirdTsukamotoPanel(int width, int height, int variablesNumber, double[] minX, double[] maxX, String[] titles)
		{
			this.setLayout(null);
			
			this.setBackground(programGUI.getSecondaryColor());
			
			this.setBounds(0, 0, width, height);
			
			JLabel minLabel = new JLabel();
			minLabel.setForeground(programGUI.getTextColor());
			minLabel.setText("Output minimum:");
			minLabel.setBounds(50, 30, 160, 20);
			this.add(minLabel);
			
			JLabel maxLabel = new JLabel();
			maxLabel.setForeground(programGUI.getTextColor());
			maxLabel.setText("Output maximum:");
			maxLabel.setBounds(50 + 300 + 50, 30, 160, 20);
			this.add(maxLabel);
			
			JTextField minTextField = new JTextField();
			minTextField.setForeground(programGUI.getTextColor());
			minTextField.setBackground(programGUI.getMainColor());
			minTextField.setBounds(50, 80, 160, 20);
			this.add(minTextField);
			
			JTextField maxTextField = new JTextField();
			maxTextField.setForeground(programGUI.getTextColor());
			maxTextField.setBackground(programGUI.getMainColor());
			maxTextField.setBounds(50 + 300 + 50, 80, 160, 20);
			this.add(maxTextField);
			
			JButton finish = new JButton();
			finish.setText("Finish");
			finish.setBackground(programGUI.getMainColor());
			finish.setForeground(programGUI.getTextColor());
			finish.setBounds(50 + 300 + 50, 130, 160, 20);
			finish.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!(isDouble(minTextField.getText()) && isDouble(maxTextField.getText()) &&
						Double.valueOf(minTextField.getText()) < Double.valueOf(maxTextField.getText()))) {
						JOptionPane.showMessageDialog(f, "Invalid data:\n Minimum: " + minTextField.getText() + "\nMaximum: " + maxTextField.getText(), "Warning", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if(JOptionPane.showConfirmDialog(f, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					cSys = new FuzzyControlSystem(
							variablesNumber , 
							new TsukamotoSO(
								Double.valueOf(minTextField.getText()),
								Double.valueOf(maxTextField.getText())));
					cSys.setVarFuzz(minX, maxX, titles);
					programGUI.setSystem(cSys);
					f.dispose();
				}
			});
			this.add(finish);
			
			JButton cancel = new JButton();
			cancel.setText("Cancel");
			cancel.setBackground(programGUI.getMainColor());
			cancel.setForeground(programGUI.getTextColor());
			cancel.setBounds(50, 130, 160, 20);
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					f.dispose();
				}
			});
			
			this.add(cancel);
		}
	}
	
	private boolean isInteger(String stringInt) {
		try {
			Integer.valueOf(stringInt);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	private boolean isDouble(String stringDouble) {
		try {
			Double.valueOf(stringDouble);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
}

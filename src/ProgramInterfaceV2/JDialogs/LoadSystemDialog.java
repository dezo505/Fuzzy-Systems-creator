package ProgramInterfaceV2.JDialogs;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import FuzzySystem.FuzzySystemCore.FuzzyControlSystem;
import ProgramInterfaceV2.ProgramGUI;

public class LoadSystemDialog extends JDialog{
	private static final long serialVersionUID = 6615214840016644206L;
	ProgramGUI programGUI;
	LoadSystemDialog self = this;
	
	public LoadSystemDialog(ProgramGUI programGUI) {
		this.programGUI = programGUI;
		this.setBounds(0, 0, 600, 400);
		this.setLocationRelativeTo(programGUI.getFrame());
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.setUndecorated(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBackground(programGUI.getSecondaryColor());
		this.setResizable(false);
		addComponents();
		this.setVisible(true);
	}
	
	private void addComponents() {
		JLabel lodaLabel = new JLabel("Loading path:");
		lodaLabel.setBounds(40, 40, 150, 30);
		lodaLabel.setForeground(programGUI.getTextColor());
		this.add(lodaLabel);	
		
		JTextField filePathField = new JTextField();
		filePathField.setBounds(160, 40, 350, 30);
		filePathField.setBackground(programGUI.getSecondaryColor());
		filePathField.setForeground(programGUI.getTextColor());
		this.add(filePathField);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(40, 300, 235, 30);
		cancelButton.setBackground(programGUI.getSecondaryColor());
		cancelButton.setForeground(programGUI.getTextColor());
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
		this.add(cancelButton);
		
		JButton loadButton = new JButton("Load");
		loadButton.setBounds(325, 300, 235, 30);
		loadButton.setBackground(programGUI.getSecondaryColor());
		loadButton.setForeground(programGUI.getTextColor());
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FuzzyControlSystem f = FuzzyControlSystem.load(filePathField.getText());
				if(f != null){
					programGUI.setSystem(f);
					programGUI.recreatePanels();
					JOptionPane.showMessageDialog(self, "Loaded succesfully", "", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(self, "Loading failed", "", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		this.add(loadButton);
	}
}

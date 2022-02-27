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

import ProgramInterfaceV2.ProgramGUI;

public class SaveSystemDialog extends JDialog{
	private static final long serialVersionUID = 6615214840016644206L;
	ProgramGUI programGUI;
	SaveSystemDialog self = this;
	
	public SaveSystemDialog(ProgramGUI programGUI) {
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
		JLabel lodaLabel = new JLabel("Saving path:");
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
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(325, 300, 235, 30);
		saveButton.setBackground(programGUI.getSecondaryColor());
		saveButton.setForeground(programGUI.getTextColor());
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(programGUI.getSystem() != null && programGUI.getSystem().save(filePathField.getText())){
					JOptionPane.showMessageDialog(self, "Saved succesfully", "Information", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(self, "Saving failed", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		this.add(saveButton);
	}
}

package ProgramInterfaceV2.myComponents;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.ProgramPanel;

public class GoToKidsComboBox extends JButton{			//Button with comboBox that let changeCurrent panel to one of currentPanel kids
	private static final long serialVersionUID = -2893939025735953444L;
	private JComboBox<String> comboBox;
	private ProgramPanel orgPanel;

	public GoToKidsComboBox(ProgramGUI programGUI, ProgramPanel orginalPanel, String buttonTitle, int x, int y, int width, int height) {
		this.setText(buttonTitle);
		orgPanel = orginalPanel;
		
		String[] panelTitles = new String[orginalPanel.getKidsNumber()];
		for(int i = 0; i < orginalPanel.getKidsNumber(); i++)panelTitles[i] = orginalPanel.getKid(i).getPanelTitle();		
		comboBox = new JComboBox<String>(panelTitles);
		comboBox.setSelectedIndex(0);
		
		this.setBounds(x,y,width,height);
		
		this.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					programGUI.setMainPanel(orginalPanel.getKid(comboBox.getSelectedIndex()));
				}catch(NullPointerException exc){
					System.out.println("Panel kid is null");
				}
				
			}  
		});
	}
	
	public void addTo(JPanel p) {
		p.add(comboBox);
		p.add(this);
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width * 4/10, height);
		comboBox.setBounds(x + width * 5/10, y, + width * 5/10, height);
		
	}
	
	public void setFont(Font font){
		try {
		super.setFont(font);
		comboBox.setFont(font);
		}catch(NullPointerException e) {
			
		}
	}
	
	public void update() {
		comboBox.removeAllItems();
		for(int i = 0; i < orgPanel.getKidsNumber(); i++) {
			comboBox.addItem(orgPanel.getKid(i).getPanelTitle());
		}
	}
	
	public void setColor(Color newColor1, Color newColor2) {
		super.setBackground(newColor1);
		comboBox.setBackground(newColor1);
		
		super.setForeground(newColor2);
		comboBox.setForeground(newColor2);
	}

}

package ProgramInterfaceV2.myComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.ProgramPanel;

public class GoToButton extends JButton{		//button that changes GUI currentPanel
	
	private static final long serialVersionUID = 8314422872669992632L;

	public GoToButton(ProgramGUI programGUI, ProgramPanel goalPanel, String buttonTitle,
					int x, int y, int width, int height){
		this.setText(buttonTitle);
		this.setBounds(x, y, width, height);
		
		this.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					programGUI.setMainPanel(goalPanel);
				}catch(NullPointerException exc){
					System.exit(0);
				}
				
			}  
		});
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x,y,width,height);
	}
	
}

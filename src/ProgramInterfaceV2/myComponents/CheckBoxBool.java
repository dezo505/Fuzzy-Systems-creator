package ProgramInterfaceV2.myComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;



public class CheckBoxBool extends JCheckBox{
	private static final long serialVersionUID = 325575831391710354L;
	
	MutableVar<Boolean> holdenVar;
	
	public CheckBoxBool(MutableVar<Boolean> holdenVar, String boxTitle,
			int x, int y, int width, int height) {
		this.holdenVar = holdenVar;
		this.setText(boxTitle);
		setBounds(x, y, width, height);
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				holdenVar.setValue(!holdenVar.getValue());
			}
		});
	}
}

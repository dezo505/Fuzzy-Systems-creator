package ProgramInterfaceV2.myComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ProgramInterfaceV2.panels.VarFuzzPanels.VarFuzzPanel;


public class RemoveSetButton extends JButton{		//buttons that let remove given set from varFuzz
	private static final long serialVersionUID = 5958930477441189646L;

	private JComboBox<String> comboBox;
	private VarFuzzPanel panel;
	
	public RemoveSetButton(VarFuzzPanel panel, String textButton, int x, int y, int width, int height) {	
		setText(textButton);
		
		this.panel = panel;
		
		comboBox = new JComboBox<String>(new String[]{});
		setBounds(x,y,width,height);
		
		this.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.getVarFuzz().getSetsNumber() != 0) {
					panel.getVarFuzz().removeSet(comboBox.getSelectedIndex());
					panel.update();
				}
			}  
		});
	}
	
	public void addTo(JPanel p) {
		p.add(this);
		p.add(comboBox);		
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds   (x               , y, width * 4/10, height);
		comboBox.setBounds(x + width * 5/10, y, width * 5/10, height);
	}
	
	public void update() {
		comboBox.removeAllItems();
		for(int i = 0; i < panel.getVarFuzz().getSetsNumber(); i++)
			comboBox.addItem( panel.getVarFuzz().getSet(i).getSetTitle());
	}
	
	public void setColor(Color newColor1, Color newColor2) {
		super.setBackground(newColor1);
		if(comboBox != null)comboBox.setBackground(newColor1);
		super.setForeground(newColor2);
		if(comboBox != null)comboBox.setForeground(newColor2);
	}
}

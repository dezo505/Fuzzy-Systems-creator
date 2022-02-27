package ProgramInterfaceV2.panels.VarFuzzPanels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.panels.ProgramPanel;

public class EmptyPanel extends ProgramPanel{
	private static final long serialVersionUID = 1604559771662399244L;
	
	public EmptyPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle,
			 int x, int y, int width, int height) {
		super(programGUI, panelParent, panelTitle, x, y,  width, height);
		addComponents(width, height);
	}


	@Override
	public void update() {
		
	}

	@Override
	public void addComponents(int width, int height) {
		JLabel emptyPanelLabel = new JLabel();
		emptyPanelLabel.setText("You haven't created/loaded system yet :(");
		emptyPanelLabel.setBounds(0, 0, width, height);
		emptyPanelLabel.setForeground(new Color(255, 255, 255));
		emptyPanelLabel.setHorizontalAlignment(JLabel.CENTER);
		
		Font labelFont = emptyPanelLabel.getFont();
		int maxWidth = emptyPanelLabel.getFontMetrics(labelFont).stringWidth(emptyPanelLabel.getText());
		double widthRatio = (double)width / (double)maxWidth;
		int newFontSize = (int)(widthRatio * labelFont.getSize()) - 4;
		emptyPanelLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, newFontSize));
		
		
		this.add(emptyPanelLabel);
	}

}

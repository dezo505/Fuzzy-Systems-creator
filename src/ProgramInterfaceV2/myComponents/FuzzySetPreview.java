package ProgramInterfaceV2.myComponents;

import java.awt.Font;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import FuzzySystem.FuzzyGraphics.*;
import FuzzySystem.FuzzySystemCore.FuzzySets.FuzzySet;

public class FuzzySetPreview{		//displays fuzzySet preview
	private JLabel imageLabel;
	private JLabel titleLabel;
	private JLabel minXLabel;
	private JLabel maxXLabel;
	
	private int imWidth;
	private int imHeight;

	public FuzzySetPreview(int x, int y, int width, int height, FuzzySet fs){
		imageLabel = new JLabel();
		imageLabel.setIcon(new ImageIcon(FuzzyGraphics.fuzzySetImage(fs, width, height * 8/10, new int[] {128,0,128}).getImage()));
		
		imWidth = width;
		imHeight = height * 8/10;
		
		titleLabel = new JLabel(fs.getSetTitle() , SwingConstants.CENTER);
		
		minXLabel = new JLabel(String.valueOf(fs.getMinX()) , SwingConstants.LEFT);
		maxXLabel = new JLabel(String.valueOf(fs.getMaxX()) , SwingConstants.RIGHT);
		
		setBounds(x, y, width, height);
		
	}

	public void addTo(JPanel p) {
		p.add(imageLabel);
		p.add(titleLabel);
		p.add(minXLabel);
		p.add(maxXLabel);
	}

	public void setBounds(int x, int y, int width, int height) {
		titleLabel.setBounds(x 				 , y + height * 0/10, width      , height * 1/10);
		imageLabel.setBounds(x				 , y + height * 1/10, width      , height * 8/10);
		minXLabel.setBounds (x 				 , y + height * 9/10, width * 1/2, height * 1/10);
		maxXLabel.setBounds (x + width * 1/2 , y + height * 9/10, width * 1/2, height * 1/10);
		
		Font font = titleLabel.getFont();
		
		titleLabel.setFont(new Font(font.getName(), Font.BOLD, height /15));
		minXLabel.setFont (new Font(font.getName(), Font.BOLD, height /20));
		maxXLabel.setFont (new Font(font.getName(), Font.BOLD, height /20));
	}
	
	public void update(FuzzySet fs){
		titleLabel.setText(fs.getSetTitle());
		imageLabel.setIcon(new ImageIcon(FuzzyGraphics.fuzzySetImage(fs, imWidth, imHeight, new int[] {128,0,128}).getImage()));
	}
	
	
	public void setColor(Color newColor1, Color newColor2) {
		minXLabel.setBackground(newColor1);
		maxXLabel.setBackground(newColor1);
		titleLabel.setBackground(newColor1);
		
		minXLabel.setForeground(newColor2);
		maxXLabel.setForeground(newColor2);
		titleLabel.setForeground(newColor2);
	}
}

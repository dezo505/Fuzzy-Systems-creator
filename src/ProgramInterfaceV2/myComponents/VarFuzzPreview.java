package ProgramInterfaceV2.myComponents;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import FuzzySystem.FuzzyGraphics.FuzzyGraphics;
import FuzzySystem.FuzzyGraphics.MyImage;
import FuzzySystem.FuzzySystemCore.VarFuzzification;
import ProgramInterfaceV2.panels.ProgramPanel;

public class VarFuzzPreview{		//varFuzz preview
	private int[][] colors;
	
	private JLabel imageLabel;
	private JLabel titleLabel;
	private JLabel minXLabel;
	private JLabel maxXLabel;
	
	private Color foreground;
	
	private ProgramPanel panel;
	
	private ArrayList<JLabel> setTitles;
	
	private VarFuzzification  varFuzz;
	
	private int imWidth;
	private int imHeight;
	
	private int x, y, width, height;
	
	public VarFuzzPreview(ProgramPanel panel, int x, int y, int width, int height, VarFuzzification varFuzz) {
		imageLabel = new JLabel();
		
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		imWidth = width * 8/10;
		imHeight = height * 8/10;
		
		this.panel = panel;
		this.varFuzz = varFuzz;
		
		titleLabel = new JLabel(varFuzz.getVarTitle() , SwingConstants.CENTER);
		
		minXLabel = new JLabel(String.valueOf(varFuzz.getMinX()) , SwingConstants.LEFT);
		maxXLabel = new JLabel(String.valueOf(varFuzz.getMaxX()) , SwingConstants.RIGHT);
		
		setTitles = new ArrayList<JLabel>();
		
		imageLabel.setIcon(new ImageIcon(FuzzyGraphics.variableFuzzificationImage(varFuzz, imWidth, imHeight, colors).getImage()));
		
		if(varFuzz != null)titleLabel.setText(varFuzz.getVarTitle());
		
		setBounds(x, y, width, height);
		update();
	}
	
	public void update(){
		for(int i = 0; i < setTitles.size();) {
			panel.remove(setTitles.get(0));
			setTitles.remove(0);
		}
		
		setTitles = new ArrayList<JLabel>();
		
		for(int i = 0; i < varFuzz.getSetsNumber(); i++) {
			JLabel l = new JLabel(varFuzz.getSet(i).getSetTitle(), SwingConstants.LEFT);
			
			l.setBounds(x + width * 82/100, y + height * i/varFuzz.getSetsNumber(), width * 18/100, height / varFuzz.getSetsNumber());
			l.setFont(new Font("Charcoal CY", Font.PLAIN, height / 35));
			l.setForeground(foreground);
			
			int[] c = new int[] {varFuzz.getSet(i).getColor().getRed(), varFuzz.getSet(i).getColor().getGreen(), varFuzz.getSet(i).getColor().getBlue()};
			l.setIcon(new ImageIcon((new MyImage(height * 1/40, height * 1/40, c)).getImage()));
			
			setTitles.add(l);
			panel.add(l);
			
		}
		imageLabel.setIcon(new ImageIcon(FuzzyGraphics.variableFuzzificationImage(varFuzz, imWidth, imHeight, colors).getImage()));
		titleLabel.setText(varFuzz.getVarTitle());
	}
	
	
	public void addTo(JPanel p) {
		p.add(imageLabel);
		p.add(titleLabel);
		p.add(minXLabel);
		p.add(maxXLabel);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		titleLabel.setBounds(x 				 		  , y + height * 0/10, width * 8/10      , height * 1/10);
		imageLabel.setBounds(x		  				  , y + height * 1/10, width * 8/10      , height * 8/10);
		minXLabel.setBounds (x 				 		  , y + height * 9/10, width * 8/10 * 1/2, height * 1/10);
		maxXLabel.setBounds (x + (width * 8/10) * 1/2 , y + height * 9/10, width * 8/10 * 1/2, height * 1/10);
		
		titleLabel.setFont(new Font("Charcoal CY", Font.BOLD, height /15));
		minXLabel.setFont (new Font("Charcoal CY", Font.BOLD, height /20));
		maxXLabel.setFont (new Font("Charcoal CY", Font.BOLD, height /20));
	}
	
	public void setTitle(String newVarName) {
		varFuzz.setVarTitle(newVarName);
	}
	
	public void setColor(Color newColor1, Color newColor2) {
		titleLabel.setBackground(newColor1);
		minXLabel.setBackground(newColor1);
		maxXLabel.setBackground(newColor1);
		
		foreground = newColor2;
		titleLabel.setForeground(newColor2);
		minXLabel.setForeground(newColor2);
		maxXLabel.setForeground(newColor2);
	}
}

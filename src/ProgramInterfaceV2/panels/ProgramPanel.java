package ProgramInterfaceV2.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ProgramInterfaceV2.ProgramGUI;
import ProgramInterfaceV2.myComponents.GoToButton;



public abstract class ProgramPanel extends JPanel{		//JPanel with tree like construction
	private static final long serialVersionUID = 3291953981942598126L;
	
	protected ProgramGUI programGUI;	//GUI of given panel
	private String panelTitle;			//title of panel
	protected ProgramPanel panelParent;	//parent in tree
	private ArrayList<ProgramPanel> kidPanels = new ArrayList<ProgramPanel>();		//kids in tree
	
	protected ProgramPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle,
			int x, int y, int width, int height){
		
		this.setLayout(null);
		this.programGUI = programGUI;
		this.panelParent = panelParent;
		this.panelTitle = panelTitle;
		this.setPreferredSize(new Dimension(width, height));
	
		this.setBounds(x, y, width, height);
		
		this.setBackground(new Color(22, 25, 27));						
	}
	
	public String getPanelTitle() {
		return panelTitle;
	}
	
	public ProgramPanel getPanelParent() {
		return panelParent;
	}
	
	public void setPanelParent(ProgramPanel p) {
		panelParent = p;
	}
	
	public void addKid(ProgramPanel p) {
		kidPanels.add(p);
	}
	
	public void removeKid(int index) {
		kidPanels.remove(index);
	}
	
	public ProgramPanel getKid(int index) {
		return kidPanels.get(index);
	}
	
	public int getKidsNumber() {
		return kidPanels.size();
	}
	
	public ProgramGUI getPanelGUI() {
		return programGUI;
	}
	
	public JFrame getFrame() {
		return programGUI.getFrame();
	}
	
	public void update() {
		
	}
	
	public abstract void addComponents(int width, int height);
}
package ProgramInterfaceV2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ProgramInterfaceV2.panels.ProgramPanel;
import ProgramInterfaceV2.panels.RulesPanel;
import ProgramInterfaceV2.panels.TestSystemPanel;

import java.awt.Color;

public class SidePickPanel extends JPanel{
	private static final long serialVersionUID = 4779751958676073736L;
	private ProgramGUI programGUI;
	
	private SidePickPanel self = this;
	private ProgramPanel rulesPanel;
	private ProgramPanel testPanel;
	private ProgramPanel[] views;
	private JList<String> viewsList;
	
	final DefaultListModel<String> viewsListModel = new DefaultListModel<String>();
	
	public SidePickPanel(ProgramGUI programGUI, int x, int y, int width, int height, Color backGroundColor){
		this.programGUI = programGUI;
		this.setLayout(null);
		
		super.setBounds(x, y, width, height);
		super.setBackground(new Color(29,32,34));
		
		viewsList = new JList<String>(viewsListModel);
		viewsList.setBackground(programGUI.getSecondaryColor());
		viewsList.setForeground(programGUI.getTextColor());
		
		viewsList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(views.length == 0 || viewsList.getSelectedIndex() < 0)return;
				views[viewsList.getSelectedIndex()].update();
				programGUI.setMainPanel(views[viewsList.getSelectedIndex()]);
				programGUI.getFrame().repaint();
			}
		});
		
		
		viewsList.setBounds(0, 0, width, height * 3/4);
		this.add(viewsList);
		
		JButton rulesButton = new JButton("Rules");
		rulesButton.setBounds(width * 1/10, height * 3/4 + height * 1/4 * 1/9, width * 8/10, height * 1/4 * 2/9);
		rulesButton.setBackground(programGUI.getMainColor());
		rulesButton.setForeground(programGUI.getTextColor());
		rulesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(programGUI.getSystem() == null) {
					JOptionPane.showMessageDialog(programGUI.getFrame(), "There is no system yet", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(rulesPanel == null)
				rulesPanel = new RulesPanel(programGUI, null, "Rules",
						programGUI.getWidth() * 1/8, 0, programGUI.getWidth() * 7/8, programGUI.getHeight());
				
				programGUI.setMainPanel(rulesPanel);
				programGUI.getFrame().repaint();
			}
		});
		
		this.add(rulesButton);
		
		JButton testButton = new JButton("System test");
		testButton.setBounds(width * 1/10, height * 3/4 + height * 1/4 * 4/9, width * 8/10, height * 1/4 * 2/9);
		testButton.setBackground(programGUI.getMainColor());
		testButton.setForeground(programGUI.getTextColor());
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(programGUI.getSystem() == null) {
					JOptionPane.showMessageDialog(programGUI.getFrame(), "There is no system yet", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(testPanel == null)
					testPanel = new TestSystemPanel(programGUI, null, "System test",
						programGUI.getWidth() * 1/8, 0, programGUI.getWidth() * 7/8, programGUI.getHeight());
				
				programGUI.setMainPanel(testPanel);
				programGUI.getFrame().repaint();
			}
		});
		this.add(testButton);
	}
	
	public void update() {
		 viewsListModel.removeAllElements();
		 for(ProgramPanel p: views) {
			 viewsListModel.addElement(p.getPanelTitle());
		 }
	}
	
	public JPanel[] getViews() {
		return views;
	}
	
	public void deleteViews() {
		rulesPanel = null;
		testPanel = null;
		views = new ProgramPanel[0];
		update();
	}
	
	public void setPanels(ProgramPanel[] newViews) {
		views = newViews;
	}
	
	public void unselect() {
		viewsList.clearSelection();
	}
}
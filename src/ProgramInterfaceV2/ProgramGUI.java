package ProgramInterfaceV2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import FuzzySystem.FuzzySystemCore.FuzzyControlSystem;
import ProgramInterfaceV2.JDialogs.CreateSystemDialog;
import ProgramInterfaceV2.JDialogs.LoadSystemDialog;
import ProgramInterfaceV2.JDialogs.SaveSystemDialog;
import ProgramInterfaceV2.panels.ProgramPanel;
import ProgramInterfaceV2.panels.VarFuzzPanels.EmptyPanel;
import ProgramInterfaceV2.panels.VarFuzzPanels.GenericVarFuzzPanel;

public class ProgramGUI {
	private ProgramGUI self = this;
	private JFrame f;
	private ProgramPanel mainView;
	private SidePickPanel sideView;
	private FuzzyControlSystem programFuzzySystem; // fuzzySystem that is currently displayed

	private Color mainColor;
	private Color secondaryColor;
	private Color textColor;
	private Color menuBarsColor;
	
	private int width;
	private int height;

	ProgramGUI(int width, int height) {
		textColor = new Color(255, 255, 255);
		mainColor = new Color(22, 25, 27);
		menuBarsColor = new Color(12, 15, 18);
		secondaryColor = new Color(29, 32, 34);
		
		this.width = width;
		this.height = height;

		configureFrame(width, height);
		configureMenuBars();
		f.repaint(0, 0, width, height);
		f.setVisible(true);
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Color getMainColor() {
		return mainColor;
	}

	public Color getSecondaryColor() {
		return secondaryColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setMainPanel(ProgramPanel newMainPanel) {
		f.getContentPane().remove(mainView);
		mainView = newMainPanel;
		newMainPanel.update();
		f.add(mainView);
		f.repaint();
		sideView.unselect();
	}

	public void setSystem(FuzzyControlSystem pfs) {
		programFuzzySystem = pfs;
		recreatePanels();
	}

	public FuzzyControlSystem getSystem() {
		return programFuzzySystem;
	}

	public JFrame getFrame() {
		return f;
	}

	private void configureFrame(int width, int height) {
		mainView = new EmptyPanel(this, null, "empty", width * 1 / 8, 0, width * 7 / 8, height);
		mainView.setVisible(true);

		sideView = new SidePickPanel(this, 0, 0, width * 1 / 8, height, new Color(170, 170, 170));
		sideView.setVisible(true);

		f = new JFrame("Fuzzy system creator");
		f.setPreferredSize(new Dimension(width, height));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setLayout(null);

		Image icon = (new ImageIcon("src/images/chartIcon3.png")).getImage();
		f.setIconImage(icon);

		f.getContentPane().add(mainView);
		f.getContentPane().add(sideView);
	}

	private void configureMenuBars() {
		JMenuBar menuFile = new JMenuBar();

		JMenuItem i1 = new JMenuItem("Create new system");
		i1.setBackground(menuBarsColor);
		i1.setForeground(textColor);
		JMenuItem i2 = new JMenuItem("Save system");
		i2.setBackground(menuBarsColor);
		i2.setForeground(textColor);
		JMenuItem i3 = new JMenuItem("Load system");
		i3.setBackground(menuBarsColor);
		i3.setForeground(textColor);
		JMenuItem i4 = new JMenuItem("Reset system");
		i4.setBackground(menuBarsColor);
		i4.setForeground(textColor);

		i1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateSystemDialog(self, 600, 400);
			}
		});

		i2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(programFuzzySystem == null) {
					JOptionPane.showMessageDialog(f, "There is no system yet", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				new SaveSystemDialog(self);
			}
		});

		i3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadSystemDialog(self);
			}
		});

		i4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetSystem();
			}
		});

		menuFile.add(i1);
		menuFile.add(i2);
		menuFile.add(i3);
		menuFile.add(i4);

		menuFile.setVisible(true);

		f.setJMenuBar(menuFile);
	}

	public void recreatePanels() {
		FuzzyControlSystem s = programFuzzySystem;
		ProgramPanel[] newViews = new ProgramPanel[s.getInputSize()];
		for (int i = 0; i < s.getInputSize(); i++) {
			newViews[i] = new GenericVarFuzzPanel(this, null, s.getInput(i).getVarTitle(), mainView.getX(),
					mainView.getY(), mainView.getWidth(), mainView.getHeight(), s.getInput(i).getMinX(),
					s.getInput(i).getMaxX(), s.getInput(i).getVarTitle(), i);
		}
		sideView.setPanels(newViews);
		sideView.update();
		f.repaint();
	}
	
	private void deletePanels() {
		sideView.deleteViews();
		f.remove(mainView);
		mainView = new EmptyPanel(this, null, "empty", mainView.getX(), mainView.getY(), mainView.getWidth(), mainView.getHeight());
		f.add(mainView);
	}
	
	private void resetSystem() {
		programFuzzySystem = null;
		deletePanels();
		f.repaint();
	}
}

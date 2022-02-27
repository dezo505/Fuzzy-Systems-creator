package ProgramInterfaceV2.myComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import FuzzySystem.FuzzyGraphics.MyImage;

public class ColorPicker  extends JButton{		//component that let you set 3 values(r ,g, b) with sliders
	private static final long serialVersionUID = 4140680175150805881L;
		
		private JSlider redSlider;
		private JSlider greenSlider;
		private JSlider blueSlider;
		
		private JLabel colorPreview;
		
		private int imWidth, imHeight;
		
	public ColorPicker(MutableVar<Integer> r, MutableVar<Integer> g, MutableVar<Integer> b, String buttonText, int x, int y, int width, int height) {
		setText(buttonText);
		
		imWidth =  width * 7/16;
		imHeight = height * 3/12;
		
		redSlider = new JSlider (JSlider.HORIZONTAL, 0, 255, 0);
		redSlider.setMinorTickSpacing(16);
		redSlider.setMajorTickSpacing(64);
		redSlider.setPaintTicks(true);
		redSlider.setPaintLabels(true);
		
		redSlider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent event) {
	            update();
	        }
	    });
		
		greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		greenSlider.setMinorTickSpacing(16);
		greenSlider.setMajorTickSpacing(64);
		greenSlider.setPaintTicks(true);
		greenSlider.setPaintLabels(true);
		
		greenSlider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent event) {
	            update();
	        }
	    });
		
		blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		blueSlider.setMinorTickSpacing(16);
		blueSlider.setMajorTickSpacing(64);
		blueSlider.setPaintTicks(true);
		blueSlider.setPaintLabels(true);
		
		blueSlider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent event) {
	            update();
	        }
	    });
		
		colorPreview = new JLabel();
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				r.setValue(redSlider.getValue());
				g.setValue(greenSlider.getValue());
				b.setValue(blueSlider.getValue());
			}
			
		});
		
		setBounds(x, y, width, height);
		update();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		redSlider.setBounds(x, y, width, height * 15/60);
		greenSlider.setBounds(x, y + height * 15/60, width, height * 15/60);
		blueSlider.setBounds(x, y + height * 30/60, width, height * 15/60);
		
		super.setBounds(x, y + height * 9/12, width * 9/16, height * 3/12);
		
		colorPreview.setBounds(x + width * 9/16, y + height * 9/12, width * 7/16, height * 3/12);
	}
	
	public void addTo(JPanel p) {
		p.add(this);
		p.add(redSlider);
		p.add(greenSlider);
		p.add(blueSlider);
		p.add(colorPreview);
	}
	
	public void update(){	
		int[] rgb = new int[] {redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()};
		colorPreview.setIcon(new ImageIcon((new MyImage(imHeight, imWidth,  rgb)).getImage()));
	}
	
	public void setColor(Color newColor1, Color newColor2) {
		if(redSlider != null)redSlider.setBackground(newColor1);
		if(greenSlider != null)greenSlider.setBackground(newColor1);
		if(blueSlider != null)blueSlider.setBackground(newColor1);
		super.setBackground(newColor1);
		
		if(redSlider != null)redSlider.setForeground(newColor2);
		if(greenSlider != null)greenSlider.setForeground(newColor2);
		if(blueSlider != null)blueSlider.setForeground(newColor2);
		super.setForeground(newColor2);
	}
}

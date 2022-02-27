package ProgramInterfaceV2.myComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ButtonTextField <T> extends JButton implements MyComponent{
	private static final long serialVersionUID = 4458696559959144435L;
	
	private JTextField textField;
	private JLabel titleLabel;
	
	private MutableVar<T> holdenVar;
	
	public ButtonTextField(MutableVar<T> holdenVal, String buttonTitle, String title, JFrame f,
							int x, int y, int width, int height) {
		this.holdenVar = holdenVal; 
		textField = new JTextField();
		titleLabel = new JLabel();
		
		titleLabel.setText(title);
		this.setText(buttonTitle);
		
		setBounds(x, y, width, height);
		
		this.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean warning = false;
				if(holdenVar.getValue() instanceof Integer) {
					if(isStringInteger(textField.getText())) {
						holdenVar.setValue((T)Integer.valueOf(textField.getText()));
					}else {
						warning = true;
					}
				}else if(holdenVar.getValue() instanceof Double) {
					if(isStringDouble(textField.getText())) {
						holdenVar.setValue((T)Double.valueOf(textField.getText()));
					}else {
						warning = true;
					}
				}else if(holdenVar.getValue() instanceof String) {
					if(isStringInteger(textField.getText()))
					holdenVar.setValue((T)textField.getText());
				}
				
				if(warning)JOptionPane.showMessageDialog(f, "Warning", "Niepoprawne dane", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		textField.setText(holdenVar.getValue().toString());
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		titleLabel.setBounds(x, y, width, height * 1/4);
		super.setBounds(x, y + height * 2/4, width * 2/5, height * 2/4);
		textField.setBounds(x + width * 3/5, y + height * 2/4, width * 2/5, height * 2/4);
	}

	@Override
	public void addTo(JPanel p) {
		p.add(this);
		p.add(textField);
		p.add(titleLabel);
	}
	
	private boolean isStringInteger(String string) {
		for(int i = 0; i < string.length(); i++) {
			if(string.charAt(i) > '9' && string.charAt(i) < '0')return false;
		}
		return true;
	}
	
	private boolean isStringDouble(String string) {
		int dotCount = 0;
		for(int i = 0; i < string.length(); i++) {
			if(string.charAt(i) > '9' && string.charAt(i) < '0' && string.charAt(i) != ',')return false;
			if(string.charAt(i) == ',')dotCount++;
		}
		return (dotCount <= 1);
	}
	
	public void setColor(Color newColor1, Color newColor2){
		super.setBackground(newColor1);
		titleLabel.setBackground(newColor1);
		textField.setBackground(newColor1);
		
		super.setForeground(newColor2);
		titleLabel.setForeground(newColor2);
		textField.setForeground(newColor2);
	}
}

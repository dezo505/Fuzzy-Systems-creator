package ProgramInterfaceV2;

import com.formdev.flatlaf.FlatDarkLaf;

public class Main {

	public static void main(String[] args) {
		FlatDarkLaf.setup();
		new ProgramGUI(1600, 900);
	}

}

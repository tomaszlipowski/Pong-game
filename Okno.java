package com.main;

import javax.swing.JFrame;

public class Okno {
	JFrame frame;

	public Okno(String tytul, Game gra) {
		frame = new JFrame(tytul);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(gra);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}

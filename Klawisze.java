package com.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Klawisze extends KeyAdapter{
	
	private Paletki pLewa;
	private boolean goraLewa = false;
	private boolean dolLewa = false;
	
	private Paletki pPrawa;
	private boolean goraPrawa = false;
	private boolean dolPrawa = false;

	public Klawisze(Paletki pd1, Paletki pd2) {
		pLewa = pd1;
		pPrawa = pd2;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (Menu.bot == false) {
			if (key == KeyEvent.VK_UP) {
				pPrawa.zmienKierunek(-1);
				goraPrawa = true;
			}
			if (key == KeyEvent.VK_DOWN) {
				pPrawa.zmienKierunek(1);
				dolPrawa = true;
			}
		}
		if (key == KeyEvent.VK_W) {
			pLewa.zmienKierunek(-1);
			goraLewa = true;
		}
		if (key == KeyEvent.VK_S) {
			pLewa.zmienKierunek(1);
			dolLewa = true;
		}
	}
	
		
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			goraPrawa = false;
		}
		if (key == KeyEvent.VK_DOWN) {
			dolPrawa = false;
		}
		if (key == KeyEvent.VK_W) {
			goraLewa = false;
		}
		if (key == KeyEvent.VK_S) {
			dolLewa = false;
		}
		if (!goraLewa && !dolLewa)
			pLewa.stop();
		if (!goraPrawa && !dolPrawa)
			pPrawa.stop();
	}
	
}

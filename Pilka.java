package com.main;

import java.awt.Color;
import java.awt.Graphics;

public class Pilka {
	
	public static final int rozmiar = 16;

	private int x, y;
	private int kierunekX, kierunekY;
	private int predkosc = 8;
	
	public Pilka() {
		reset();
	}

	private void reset() {
		
		x = Game.SZEROKOSC / 2 - rozmiar / 2;
		y = Game.WYSOKOSC / 2 - rozmiar / 2;
		
		kierunekX = Game.sign(Math.random() * 2.0 - 1);
		kierunekY = Game.sign(Math.random() * 2.0 - 1); 
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void zmienKierunekY() {
		kierunekY *= -1;
	}
	
	public void zmienKierunekX() {
		kierunekX *= -1;
	}

	public void rysuj(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, rozmiar, rozmiar);
	}

	public void update(Paletki p1, Paletki p2) {
		
		x += kierunekX * predkosc;
		y += kierunekY * predkosc;
		
		// zderzenia_ze_sciana
		if (y + rozmiar >= Game.WYSOKOSC || y <= 0)
			zmienKierunekY();
		if (x + rozmiar >= Game.SZEROKOSC) {
			p1.dodajPunkt();
			reset();
		}
		if (x <= 0) {
			p2.dodajPunkt();
			reset();
		}
	}

}

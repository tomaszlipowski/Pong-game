package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paletki {

	private int x, y;
	private int predkosc = 0;
	private int szybkosc = 15;
	int wynik = 0;
	Color kolor;
	private boolean lewa;
	int gszerokosc, gwysokosc;
	
	public Paletki(Color k, boolean left, int xx, int yy, int szerokosc, int wysokosc) {
		x=xx;
		y=yy;
		kolor = k;
		this.lewa = left;
		gszerokosc = szerokosc;
		gwysokosc = wysokosc;
		}
	
	public void dodajPunkt() {
		wynik++;
			 
	}

	public void rysuj(Graphics g) {
		g.setColor(kolor);
		if (Menu.bot && this.lewa == false)
			g.fillRect(x, y - 130, gszerokosc, gwysokosc);
		else
			g.fillRect(x, y, gszerokosc, gwysokosc);
		int sx;
		String scoreText = Integer.toString(wynik);
		Font czcionka = new Font("Roboto", Font.PLAIN, 50);
		
		int szerokoscCzcionki = g.getFontMetrics(czcionka).stringWidth(scoreText) + 1;
		int odstep = 25;
		
		if (lewa)
			sx = Game.SZEROKOSC / 2 - odstep - szerokoscCzcionki;
		else
			sx= Game.SZEROKOSC / 2 + odstep;
		
		g.setFont(czcionka);
		g.drawString(scoreText, sx, 50);
	}

	public void update(Pilka p) {
		y = Game.ensureRange(y += predkosc, 0, Game.WYSOKOSC - gwysokosc);
		
		int pilkaX = p.getX();
		int pilkaY = p.getY();
		
		if (lewa) {
			if (pilkaX <= gszerokosc && pilkaY + Pilka.rozmiar >= y && pilkaY <= y + gwysokosc)
				p.zmienKierunekX();
		} 
		else 
			if (pilkaX + Pilka.rozmiar >= Game.SZEROKOSC - gszerokosc &&  pilkaY + Pilka.rozmiar >= y &&  pilkaY <= y + gwysokosc)
				p.zmienKierunekX();
	}

	public void zmienKierunek(int kierunek) {
		predkosc = szybkosc * kierunek;
		
	}
	public void stop() {
		predkosc = 0;
	}

}

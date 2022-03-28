package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Menu extends MouseAdapter{

	public boolean aktywne;
	static public boolean bot = false;
	
	
	private Rectangle przyciskPlay;
	private String playTekst = "Play";
	private boolean pRozjasnienie = false;
	
	private Rectangle przyciskQuit;
	private String quitTekst = "Quit";
	private boolean qRozjasnienie = false;
	
	private Rectangle przyciskWyniki;
	private String wynikiTekst = "Scores";
	private boolean wRozjasnienie = false;
	
	private Rectangle przyciskSolo;
	private String soloTekst = "Solo";
	private boolean sRozjasnienie = false;
	
	TimerTask h;
	Timer zegar = new Timer();
	
	Color kolorZ = new Color (40, 188, 66);
	Color kolorC = new Color (190, 18, 26);
	Color kolorX = new Color (250, 252, 58);
	Color kolorN = new Color (50, 52, 158);
	
	private Font czcionka;
	private Font czcionka2;
	public String nick;
	public String nick2;
	int iloscCzasu = 0;
	String wygrany;
	int wynik;
	
	public Menu(Game game) {
		h = new TimerTask() {
			public void run() {
				iloscCzasu++;
			}
		};
		
		aktywne = true;
		game.start();
		
		int szerokoscPrzycisku, wysokoscPrzycisku, x, y;
		
		szerokoscPrzycisku = 300;
		wysokoscPrzycisku = 150;
		
		y = Game.WYSOKOSC / 4 - wysokoscPrzycisku / 2;
		x = Game.SZEROKOSC / 4 - szerokoscPrzycisku / 2;
		przyciskPlay = new Rectangle(x, y, szerokoscPrzycisku, wysokoscPrzycisku);
		
		x = Game.SZEROKOSC * 3/4 - szerokoscPrzycisku / 2;
		przyciskQuit = new Rectangle(x, y, szerokoscPrzycisku, wysokoscPrzycisku);
		
		y = Game.WYSOKOSC * 3/4 - wysokoscPrzycisku / 2 + 30;
		x = Game.SZEROKOSC / 4 - szerokoscPrzycisku / 2;
		przyciskWyniki = new Rectangle(x, y, szerokoscPrzycisku, wysokoscPrzycisku);
		
		x = Game.SZEROKOSC * 3/4 - szerokoscPrzycisku / 2;
		przyciskSolo = new Rectangle(x, y, szerokoscPrzycisku, wysokoscPrzycisku);
		
		czcionka = new Font("Roboto", Font.PLAIN, 95);
		czcionka2 = new Font("Roboto", Font.PLAIN, 50);
	}
	
	public void rysuj(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(czcionka);
		
		g.setColor(Color.black);
		if (pRozjasnienie)
			g.setColor(Color.gray);
		g2d.fill(przyciskPlay);
		
		g.setColor(Color.black);
		if (qRozjasnienie)
			g.setColor(Color.gray);
		g2d.fill(przyciskQuit);
		
		g.setColor(Color.black);
		if (wRozjasnienie)
			g.setColor(Color.gray);
		g2d.fill(przyciskWyniki);
		
		g.setColor(Color.black);
		if (sRozjasnienie)
			g.setColor(Color.gray);
		g2d.fill(przyciskSolo);
		
		g.setColor(Color.white);
		g2d.draw(przyciskPlay);
		g2d.draw(przyciskQuit);
		g2d.draw(przyciskWyniki);
		g2d.draw(przyciskSolo);
		
		int szerokoscCzcionki;
		int wysokoscCzcionki;
		
		szerokoscCzcionki = g.getFontMetrics(czcionka).stringWidth(playTekst);
		wysokoscCzcionki = g.getFontMetrics(czcionka).getHeight();
		
		g.setColor(kolorZ);
		g2d.drawString(playTekst, (int) (przyciskPlay.getX() + przyciskPlay.getWidth() / 2 - szerokoscCzcionki / 2),
				(int) (przyciskPlay.getY() + przyciskPlay.getHeight() / 2 + wysokoscCzcionki / 4));
		

		szerokoscCzcionki = g.getFontMetrics(czcionka).stringWidth(quitTekst);
		wysokoscCzcionki = g.getFontMetrics(czcionka).getHeight();
		
		g.setColor(kolorC);
		g2d.drawString(quitTekst, (int) (przyciskQuit.getX() + przyciskQuit.getWidth() / 2 - szerokoscCzcionki / 2),
				(int) (przyciskQuit.getY() + przyciskQuit.getHeight() / 2 + wysokoscCzcionki / 4));
		
		szerokoscCzcionki = g.getFontMetrics(czcionka).stringWidth(wynikiTekst);
		wysokoscCzcionki = g.getFontMetrics(czcionka).getHeight();
		
		g.setColor(kolorX);
		g2d.drawString(wynikiTekst, (int) (przyciskWyniki.getX() + przyciskWyniki.getWidth() / 2 - szerokoscCzcionki / 2),
				(int) (przyciskWyniki.getY() + przyciskWyniki.getHeight() / 2 + wysokoscCzcionki / 4));
		
		szerokoscCzcionki = g.getFontMetrics(czcionka).stringWidth(soloTekst);
		wysokoscCzcionki = g.getFontMetrics(czcionka).getHeight();
		
		g.setColor(kolorN);
		g2d.drawString(soloTekst, (int) (przyciskSolo.getX() + przyciskSolo.getWidth() / 2 - szerokoscCzcionki / 2),
				(int) (przyciskSolo.getY() + przyciskSolo.getHeight() / 2 + wysokoscCzcionki / 4));
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		
		if (przyciskPlay.contains(p)) {
			Frame frame = new Frame("Logowanie");
			Panel panel = new Panel();
			panel.setLayout(new GridLayout(2,1));
			panel.add(new Label("Podaj login:"));
			panel.setFont(czcionka2);
			TextField login = new TextField("");
			login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					if(nick==null) {
						nick = login.getText();
						login.setText("");
					}
					else{
						nick2 = login.getText();
						frame.dispose();
						zegar.scheduleAtFixedRate(h, 1000, 10);
						aktywne = false;
					}
				}});
			panel.add(login);
			frame.add(panel);
			int width = 300;
			int height = 300;			 
			frame.setSize(width, height);	
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			
		}
		else if (przyciskWyniki.contains(p)) {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader("wyniki.txt"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			String line;
			try {
				int lwyniki = 0;
				int indeks = lwyniki;
				String[][] wyniki = new String[50][2];
				while((line = in.readLine()) != null)
				{	
					System.out.println(line);
					String arr[] = line.split(" ");
					int x = Integer.parseInt(arr[3]);
					int y = Integer.parseInt(arr[5]);
					if (x > y) {
						wygrany = arr[0];
					}
					else {
						wygrany = arr[2];
					}

					indeks = lwyniki;
					for (int i=0; i<lwyniki; i++) {
						if (wyniki[i][0].equals(wygrany)) {
							indeks = i;
							int z = Integer.parseInt(wyniki[indeks][1]) + 1;
							wyniki[indeks][1] = "" + z;
						}
					}
					if (indeks == lwyniki) {
						wyniki[lwyniki][0] = wygrany;
						wyniki[lwyniki][1] = "1";
						lwyniki++;
					}
				    
				}
				for (int j=0; j<lwyniki; j++) {
					System.out.println(wyniki[j][0] +  " " +wyniki[j][1]);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		else if (przyciskSolo.contains(p)) {
			bot = true;
			Frame frame3 = new Frame("Logowanie");
			Panel panel3 = new Panel();
			panel3.setLayout(new GridLayout(2,1));
			panel3.add(new Label("Podaj login:"));
			panel3.setFont(czcionka2);
			TextField login3 = new TextField("");
			login3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
				nick = login3.getText();
				frame3.dispose();
				zegar.scheduleAtFixedRate(h, 1000, 10);
				aktywne = false;
				}
			});
			panel3.add(login3);
			frame3.add(panel3);
			int width = 300;
			int height = 300;	
			frame3.setSize(width, height);	
			frame3.setLocationRelativeTo(null);
			frame3.setVisible(true);
			}
		else if (przyciskQuit.contains(p))
			System.exit(0);
	}
	
	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();
		
		pRozjasnienie = przyciskPlay.contains(p);
		qRozjasnienie = przyciskQuit.contains(p);
		wRozjasnienie = przyciskWyniki.contains(p);
		sRozjasnienie = przyciskSolo.contains(p);
	}

}

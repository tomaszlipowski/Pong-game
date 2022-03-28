package com.main;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
	
	Okno okno;

	private static final long serialVersionUID = -668240625892092763L;

	public static final int SZEROKOSC = 1000;
	public static final int WYSOKOSC = SZEROKOSC * 9 / 16;

	public boolean dziala = false;
	private Thread watek;
	float z;

	private Pilka pilka;
	private Paletki pLewa;
	private Paletki pPrawa;
	static public int szerokosc = 22, wysokosc = 105;
	static public int wysokosc_solo = 350;
	
	Color kolorZ = new Color (40, 188, 66);
	Color kolorC = new Color (190, 18, 26);

	public Menu menu;

	public Game() {
		
		canvasSetup();

		okno=new Okno("Pong", this);
	    robpaletki();
		initialize();

		this.addKeyListener(new Klawisze(pLewa, pPrawa));
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.setFocusable(true);
	}
	
	void robpaletki(){
		pilka = new Pilka();
		pLewa = new Paletki(kolorZ, true, 0 , Game.WYSOKOSC / 2 - wysokosc / 2, szerokosc, wysokosc);
		if (Menu.bot == false)
			pPrawa = new Paletki(kolorC, false, Game.SZEROKOSC - szerokosc, Game.WYSOKOSC / 2 - wysokosc / 2, szerokosc, wysokosc);
		else {
			pPrawa = new Paletki(kolorC, false, Game.SZEROKOSC - szerokosc, Game.WYSOKOSC / 2 - wysokosc_solo / 2, szerokosc, wysokosc_solo);
		}
	}

	void initialize() {
		menu = new Menu(this);

	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(SZEROKOSC, WYSOKOSC));
		this.setMaximumSize(new Dimension(SZEROKOSC, WYSOKOSC));
		this.setMinimumSize(new Dimension(SZEROKOSC, WYSOKOSC));
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		String zwyciezca = "zielony";
		String gracz;
		String gracz1;
		long timer = System.currentTimeMillis();
		while (dziala) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
			}
			if (dziala) {
				rysuj();
				}
            if (pLewa.wynik == 3 || pPrawa.wynik == 3) {
            	Graphics g = this.getGraphics();
            	Font czcionka = new Font("Roboto", Font.PLAIN, 100);
        		g.setFont(czcionka);
        		gracz = menu.nick;
        		if (Menu.bot)
        			gracz1 = "BOT";
        		else
        			gracz1 = menu.nick2;
            	if (pLewa.wynik > pPrawa.wynik) {
            		
            		g.setColor(kolorZ);
            		g.drawString("Wygra³ " + zwyciezca, 150, WYSOKOSC / 2);
            		}
            	else {
            		zwyciezca = "czerwony";
            		g.setColor(kolorC);
            		g.drawString("Wygra³ " + zwyciezca, 150, WYSOKOSC / 2);
            	}
            	
            	float z = (float) menu.iloscCzasu / 100;
            	String.valueOf(z);
            	g.drawString(String.valueOf(z), 400, 450);
            	try {
            		File file = new File("wyniki.txt");
            		FileWriter fr = new FileWriter(file, true);
            		fr.write(gracz + " " + "vs. " + gracz1 + " " + pLewa.wynik + " : " + pPrawa.wynik + " " + "czas: " + z + "s" + "\n");
            		fr.close();}
            		catch (IOException e) {
            			e.printStackTrace();
            		}
            	dziala=false;
            	Menu.bot = false;
            	try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
			
		
			 	}
		okno.frame.dispose();
		new Game();
            
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				}
	
		
		stop();
	}

	public void rysuj() {

		BufferStrategy buffer = this.getBufferStrategy();

		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();

		rysujTlo(g);

		if (menu.aktywne)
			menu.rysuj(g);

		pilka.rysuj(g);

		pLewa.rysuj(g);
		if(Menu.bot) {
			pPrawa.gwysokosc=wysokosc_solo;
			}
		pPrawa.rysuj(g);
		g.dispose();
		buffer.show();
	}

	private void rysujTlo(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, SZEROKOSC, WYSOKOSC);

		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(SZEROKOSC / 2, 0, SZEROKOSC / 2, WYSOKOSC);
	}

	public void update() {
		if (!menu.aktywne) {
			pilka.update(pLewa, pPrawa);
			
			pLewa.update(pilka);
			pPrawa.update(pilka);
		}
	}

	public void start() {
		watek = new Thread(this);
		watek.start();
		dziala = true;
	}

	public void stop() {
		try {
			watek.join();
			dziala = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		new Game();
	}

	public static int sign(double d) {
		if (d <= 0)
			return -1;
		return 1;
	}

	public static int ensureRange(int val, int min, int max) {
		return Math.min(Math.max(val, min), max);
	}

}

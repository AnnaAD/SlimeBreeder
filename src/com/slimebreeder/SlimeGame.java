package com.slimebreeder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;


public class SlimeGame {
	private final int WIDTH;
	private final int HEIGHT;
	private ArrayList<Slime> slimeList;
	private ArrayList<Slime> selectedSlimes;
	private final int NUM_SLIMES=2;
	private BufferedImage background;
	private BufferedImage moneyIcon;
	
	private int money;

	public  SlimeGame(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		money = 100;
		background = loadImage("res/background.png");
		Slime.setSlimeImage(loadImage("res/slime.png"));
		moneyIcon = loadImage("res/moneyicon.png");
		Slime.setGameHeight(HEIGHT);
		Slime.setGameWidth(WIDTH);
		slimeList = new ArrayList<Slime>();
		for(int i=0;i<NUM_SLIMES;i++)
			slimeList.add(new Slime());
		slimeList.add(new Slime(slimeList.get(0),slimeList.get(1)));
		selectedSlimes = new ArrayList<Slime>();
	}
	
	public static BufferedImage loadImage(String filepath) {
		BufferedImage output = null;
		try {
		    output = ImageIO.read(new File(filepath));
		} catch (IOException e) {
			System.out.println("Failed to load image");
		}
		return output;
	}
	
	public void update() {
		for (Slime s : slimeList) {
			s.update();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(background, 0,0,WIDTH, HEIGHT,null);
		g.drawImage(moneyIcon, 5, 5, moneyIcon.getWidth()/2, moneyIcon.getHeight()/2,null);
		Font f = new Font("SansSerif", Font.PLAIN, 30);
		g.setFont(f);
		g.drawString(Integer.toString(money), 10+moneyIcon.getWidth()/2, g.getFontMetrics(f).getHeight()/2 + moneyIcon.getHeight()/4);
		Font n = new Font("TimesRoman", Font.ITALIC, 30);
		g.setFont(n);
		g.drawString("Slime Breeder", 555, 150);
		Collections.sort(slimeList, Slime.compareByY());
		for (Slime s : slimeList) {
			s.render(g);
		}
		
	}
	
	public void clicked(int x, int y) {
		for (Slime s : slimeList) {
			if(x > s.getX() && x < s.getX()+s.getSize() && y > s.getY() && y < s.getY()+s.getSize()) {
				System.out.println("Slime clicked at "+x+","+y );
				selectedSlimes.add(s);
			}
		}
		if(selectedSlimes.size() >= 2) {
			breed();
		}
		
	}
	
	public void breed() {
		slimeList.add(new Slime(selectedSlimes.get(0), selectedSlimes.get(1)));
		selectedSlimes.clear();
	}
}

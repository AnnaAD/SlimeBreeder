package com.slimebreeder;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
	private Font f;
	private Button sell;
	
	
	private int money;

	public  SlimeGame(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		money = 100;
		background = loadImage("res/background.png");
		Slime.setImages(loadImage("res/slime.png"),loadImage("res/shadow.png"));
		moneyIcon = loadImage("res/moneyicon.png");
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("res/PatrickHand-Regular.ttf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
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
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB); //makes text look so much better! At least on my computer...
		g.drawImage(background, 0,0,WIDTH, HEIGHT,null);
		
		g.drawImage(moneyIcon, 5, 5, moneyIcon.getWidth()/2, moneyIcon.getHeight()/2,null);
		Font nf = f.deriveFont(40f);
		g.setFont(nf);
		g.drawString(Integer.toString(money), 10+moneyIcon.getWidth()/2, moneyIcon.getHeight()/4 + g.getFontMetrics(nf).getHeight()/2 - 8);
		
		g.setFont(f.deriveFont(35f));
		g.drawString("Slime Breeder", 555, 150);
		
		Collections.sort(slimeList, Slime.compareByY());
		for (Slime s : slimeList) {
			s.render(g);
		}
		for(Slime s : selectedSlimes){
			s.renderShadow(g);
		}
		
	}
	
	public void clicked(int x, int y) {
		for (Slime s : slimeList) {
			if(x > s.getX() && x < s.getX()+s.getSize() && y > s.getY() && y < s.getY()+s.getSize()) {
				System.out.println("Slime clicked at "+x+","+y );
				if(selectedSlimes.contains(s)){
					selectedSlimes.remove(s);
				}else{
					selectedSlimes.add(s);
				}
			}
		}
		if(selectedSlimes.size() >= 2) {
			breed();
		}		
	}
	
	public void breed() {
		if(selectedSlimes.get(0).getAge() >= 2 & selectedSlimes.get(1).getAge() >= 2) {
			slimeList.add(new Slime(selectedSlimes.get(0), selectedSlimes.get(1)));
		}else
			System.out.println("Cannot breed baby Slimes");
		selectedSlimes.clear();
	}
	public void buySlime() {
		slimeList.add(new Slime());
		money-=30;
	}
	
	//If only 1 slime is selected, sells that slime
	public void sellSlime() {
		if(selectedSlimes.size()==1) {
			money += selectedSlimes.get(1).getValue();
			slimeList.remove(selectedSlimes.get(1));
			selectedSlimes.clear();
			}else{
			System.out.println("Wrong number of Slimes selected");
		}
	}
}

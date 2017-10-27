package com.slimebreeder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class SlimeGame {
	private final int WIDTH;
	private final int HEIGHT;
	private ArrayList<Slime> slimeList;
	private ArrayList<Slime> selectedSlimes;
	private final int NUM_SLIMES=2;

	public  SlimeGame(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		Slime.setSlimeImage(loadImage("res/slime.png"));
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
		g.setColor(new Color(0xE3CEF6));
		g.fillRect(0, 0, WIDTH, HEIGHT );
		
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

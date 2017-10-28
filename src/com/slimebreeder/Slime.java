package com.slimebreeder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Slime {
	private static BufferedImage slimeImage;
	private BufferedImage colorImage;
	
	private Color color;
	private int size;
	private int age; 
	private String name;
	private int pedigree;
	private int x;
	private int y;
	private static int gameWidth;
	private static int gameHeight;
	private int [] destination;
	
	private int ageCount=0;
	
	public Slime() {
		color = new Color((int)(Math.random()*0x1000000));
		size = (int) (Math.random()*20+70);
		age = 2;
		name = name;
		pedigree = pedigree;
		x=(int)(Math.random()*(gameWidth-50)+25);
		y=(int)(Math.random()*(gameHeight-100)+80);
		colorImage = tintImage(slimeImage);
		//colorImage = colorImage(slimeImage,color.getRed(),color.getBlue(),color.getGreen())
	}

	public Slime(Slime parent1, Slime parent2) {
		int r = (parent1.getColor().getRed() + parent2.getColor().getRed()) / 2;
        int g = (parent1.getColor().getGreen() + parent2.getColor().getGreen()) / 2;
        int b = (parent1.getColor().getBlue() + parent2.getColor().getBlue()) / 2;
        color = new Color(r,g,b);
        size = ((parent1.getSize() + parent2.getSize())/2 - 40) + (int)((Math.random() * 10) - 5);
        pedigree = (parent1.getSize() + parent2.getSize())/2 + (int)((Math.random() * 10) - 5);
        x = (parent1.getX() + parent2.getX())/2;
        y = (parent1.getY() + parent2.getY())/2;
        colorImage = tintImage(slimeImage);

	}
	
	public Slime(Color color, int size, int age, String name, int pedigree) {
		this.color = color;
		this.size = size;
		this.age = age;
		this.name = name;
		this.pedigree = pedigree;
	}
	
	public BufferedImage tintImage(BufferedImage img){
		BufferedImage output = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
		
		for (int x = 0; x < img.getWidth(); x++) {
	        for (int y = 0; y < img.getHeight(); y++) {
	        	Color pixelColor = new Color(img.getRGB(x, y), true);
	            int r = (pixelColor.getRed() + color.getRed()) / 2;
	            int g = (pixelColor.getGreen() + color.getGreen()) / 2;
	            int b = (pixelColor.getBlue() + color.getBlue()) / 2;
	            int a = pixelColor.getAlpha();
	            int rgba = (a << 24) | (r << 16) | (g << 8) | b;
	            if(pixelColor.getRed() > 3) {
	            	output.setRGB(x, y, rgba);
	            } else {
	            	output.setRGB(x, y, pixelColor.getRGB());
	            }
	        }
	        
	    }
		return output;
	}
	
	public void move() {
		
		if(destination==null){
			if(Math.random()<0.002){
				destination=new int[2];
				destination[0] = (int)(Math.random()*(gameWidth - 50)+25);
				destination[1] = (int)(Math.random()*(gameHeight-100) + 80);
			}	
		}else{
			//System.out.println("Position: "+x+","+y+" Destination: "+destination[0]+","+destination[1]);
			if(destination[0]>x){
				x++;
			}else if (destination[0] < x) {
				x--;
			} 
			if(destination[1]>y) {
				y++;
			} else if (destination[1] < y) {
				y--;
			} 
			if(destination[1] == y && destination[0] == x) {
				destination = null;
			}
		}
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void update() {
		move();
		
		ageCount++;
		
		if(ageCount >= 1000) {
			System.out.println(age);
			ageCount = 0;
			age++;
			if(age <= 2) {
				size += 20;
			}
		}
	}
	
	public void render(Graphics g) {
		
		g.drawImage(colorImage, x, y, size, size, null);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	
	public int getSize() {
		return size;
	}

	public static void setSlimeImage(BufferedImage slimeImage) {
		Slime.slimeImage = slimeImage;
	}
	
	
	public static void setGameWidth(int gameWidth) {
		Slime.gameWidth = gameWidth;
	}

	public static void setGameHeight(int gameHeight) {
		Slime.gameHeight = gameHeight;
	}

	
}

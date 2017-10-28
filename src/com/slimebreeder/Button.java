package com.slimebreeder;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button {
	private int x, y;
	private BufferedImage image;
	
	public Button(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public void checkClicked(int mX, int mY){
		
	}
	
	public void render(Graphics g) {
		
	}
}

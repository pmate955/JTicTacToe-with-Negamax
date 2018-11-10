package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Map {
	
	public int[][] arr;
	private ImageIcon empty, circle, cross;
	public boolean stepPlayer;
	public int cells;
	private int numberToWin;
	
	public Map(int width, int height) {
		arr = new int[width][height];
		cells = width*height;
		stepPlayer = true;
		numberToWin = 5;
		init();
		try {
			this.empty = new ImageIcon((BufferedImage)ImageIO.read(this.getClass().getResource("/icons/empty.PNG")));
			this.circle = new ImageIcon((BufferedImage)ImageIO.read(this.getClass().getResource("/icons/circle.PNG")));
			this.cross = new ImageIcon((BufferedImage)ImageIO.read(this.getClass().getResource("/Icons/cross.PNG")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				arr[i][j] = 0;
			}
		}
	}
	
	public ImageIcon getIcon(int x, int y) {
		if(arr[x][y] == 0) {
			return empty;
		} else if(arr[x][y] == 1) {
			return circle;
		} else {
			return cross;
		}
	}
	
	public boolean doStep(int x, int y) {
		if(x >= 0 && y >= 0 && x < arr.length && y < arr[0].length && arr[x][y] == 0) {
			arr[x][y] = (stepPlayer?1:2);
			if(isWin(x,y, stepPlayer?1:2)) {
				System.out.println("WIN");
			}
			stepPlayer = !stepPlayer;
			cells--;
			
			return true;
		}
		return false;		
	}
	
	public boolean isWin(int x, int y, int color) {
		int found = 0;
		for(int x2 = 0; x2 < arr.length ;x2++) {		//Check rows
			if(arr[x2][y] == color) {
				found++;
			} else {
				if(found >= numberToWin) {
					return true;
				}
				found = 0;
			}
		}
		found = 0;
		for(int y2 = 0; y2 < arr[0].length; y2++) {		//Check columns
			if(arr[x][y2] == color) {
				found++;
			} else {
				if(found >= numberToWin) {
					return true;
				}
				found = 0;
			}
		}
		int startX = x, startY = y;
		while(startX > 0 && startY > 0) {				//Go to first position
			startX--;
			startY--;
		}
		found = 0;
		while(startX < arr.length && startY < arr[0].length) {		//first diagonal
			if(arr[startX++][startY++] == color) {
				found++;
			} else {
				if(found >= numberToWin) {
					return true;
				}
				found = 0;
			}
		}
		startX = x;
		startY = y;
		while(startX < arr.length-1 && startY > 0) {
			startX++;
			startY--;
		}
		found = 0;
		while(startX >= 0 && startY < arr[0].length) {
			if(arr[startX--][startY++] == color) {
				found++;
			} else {
				if(found >= numberToWin) {
					return true;
				}
				found = 0;
			}
		}
		return false;
	}
}

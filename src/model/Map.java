package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Map {
	
	public int[][] arr;
	private ImageIcon empty, circle, cross;
	
	public Map(int width, int height) {
		arr = new int[width][height];
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
}

package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Map;

public class GameFrame extends JFrame {
	
	private int height;
	private int width;
	private JLabel[][] labels;
	private Map gameMap;
	
	public GameFrame(int height, int width) {
		this.height = height;
		this.width = width;
		this.setTitle("TicTacToe with Negamax");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(height*30,width*30+30);
		this.setLayout(new BorderLayout());
		this.add(generateGamePanel(), BorderLayout.NORTH);
		this.setResizable(false);
		this.gameMap = new Map(width, height);
		this.drawMap();
		this.setVisible(true);
	}
	
	private JPanel generateGamePanel() {
		JPanel out = new JPanel();
		out.setLayout(new GridLayout(width, height));
		labels = new JLabel[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				JLabel lbl = new JLabel();
				out.add(lbl);
				labels[i][j] = lbl;
			}
		}
		return out;
	}
	
	private void drawMap() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				labels[i][j].setIcon(gameMap.getIcon(i, j));
			}
		}
		this.repaint();
	}
	

	public static void main(String[] args) {
		GameFrame f = new GameFrame(15,15);

	}

}

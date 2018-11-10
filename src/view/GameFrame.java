package view;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	public GameFrame() {
		this.setTitle("TicTacToe with Negamax");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setResizable(false);
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		GameFrame f = new GameFrame();

	}

}

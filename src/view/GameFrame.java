package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Map;
import players.AIPlayer;
import players.HumanPlayer;
import players.RandomPlayer;

public class GameFrame extends JFrame implements MouseListener{
	
	private int height;
	private int width;
	private JLabel[][] labels;
	private Map gameMap;
	private Object[] players;
	
	public GameFrame(int height, int width, int numberToWin) {
		this.height = height;
		this.width = width;
		this.setTitle("TicTacToe with Negamax");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(height*30+6,width*30+25);
		this.setLayout(new BorderLayout());
		this.add(generateGamePanel(), BorderLayout.NORTH);
		players = new Object[2];
		players[0] = new HumanPlayer(new int[width][height], true, numberToWin);
		players[1] = new AIPlayer(new int[width][height], 2, numberToWin);
		this.setResizable(false);
		this.gameMap = new Map(width, height, numberToWin);
		this.drawMap();
		this.addMouseListener(this);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.startGame();
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
	
	private void startGame() {
		int index = 0;
		int[] lastStep = null;
		while(gameMap.cells > 0 && gameMap.winner == -1) {
			if(players[index] instanceof HumanPlayer) {
				do {
				while(!((HumanPlayer)players[index]).hasStep) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				lastStep = ((HumanPlayer)players[index]).getStep();
				} while(!gameMap.doStep(lastStep[0], lastStep[1]));
				
			} else if(players[index] instanceof RandomPlayer){
				lastStep = ((RandomPlayer)players[index]).getStep(lastStep);
				if(!gameMap.doStep(lastStep[0], lastStep[1])) {
					System.out.println("NOT VALID - random " + lastStep[0] + " " + lastStep[1]);
				}
			} else if(players[index] instanceof AIPlayer) {
			lastStep = ((AIPlayer)players[index]).getStep(lastStep);
				System.out.println(((AIPlayer)players[index]).gethash());
				if(!gameMap.doStep(lastStep[0], lastStep[1])) {
					System.out.println("NOT VALID - AI " + lastStep[0] + " " + lastStep[1]);
				}
			}
			
			if(index == 1) {
				index = 0;
			} else {
				index++;
			}
			this.drawMap();
		}
		if(gameMap.winner == -1) {
			JOptionPane.showMessageDialog(this, "Game over, no more cell");
		} else {
			JOptionPane.showMessageDialog(this, players[gameMap.winner-1].getClass().getName() + " WIN");
		}
		
	}

	public static void main(String[] args) {
		GameFrame f = new GameFrame(5,5, 4);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	//	if(gameMap.stepPlayer) {
			int x = (arg0.getX()-6) / 30;
			int y = (arg0.getY()-25) / 30 ;
			
			for(int i = 0; i < 2; i++) {
				if(players[i] instanceof HumanPlayer) {
					((HumanPlayer)players[i]).setStep(y, x);
				}
			}
			/*if(gameMap.doStep(y, x)) {
				this.drawMap();
				if(gameMap.isWin(y, x, gameMap.stepPlayer?2:1)) {
					JOptionPane.showMessageDialog(this, gameMap.stepPlayer?"Circle":"Cross");
				}
			}*/
	//	}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

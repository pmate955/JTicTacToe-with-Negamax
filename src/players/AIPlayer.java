package players;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {
	
	private int color;
	public int[][] arr;
	private int numberToWin;
	private int maxCells;
	
	public AIPlayer(int[][] arr, int color, int numberToWin) {
		this.arr = arr;
		this.color = color;
		this.maxCells = arr.length * arr[0].length;
		this.numberToWin = numberToWin;
	}
	
	public int[] getStep(int[] lastStep) {
		arr[lastStep[0]][lastStep[1]] = getEnemyColor();
		maxCells--;
		int[] step = negaMax(10,this.color, Integer.MIN_VALUE,Integer.MAX_VALUE);
		arr[step[1]][step[2]] = this.color;
		maxCells--;
		return new int[] {step[1],step[2]};
		
	}
	
	private int[] negaMax(int depth, int color, int alpha, int beta) {
		int bestScore = (color == this.color) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		int[] output = new int[2];
		List<int[]> steps = getSteps();
		if(depth == 0 || steps.isEmpty()) {
			bestScore = evaluate();
			return new int[] {bestScore, -1, -1};
		} else {
			for(int[] act : steps) {
				arr[act[0]][act[1]] = color;
				if(color == this.color) {
					currentScore = negaMax(depth - 1, getEnemyColor(), alpha, beta)[0];
					if(currentScore > alpha) {
						alpha = currentScore;
						output = act;
					}
				} else {
					currentScore = negaMax(depth - 1, this.color, alpha, beta)[0];
					if(currentScore < beta) {
						beta = currentScore;
						output = act;
					}
				}
				
				
				arr[act[0]][act[1]] = 0;
				if (alpha >= beta)															//Az alfa-béta vágás bekövetkezése
					break;
			}
		}
		return new int[] { (this.color == color) ? alpha : beta, output[0], output[1]};
		
	}
	
	public List<int[]> getSteps(){
		List<int[]> output = new ArrayList<int[]>();
		for(int x = 0; x < arr.length; x++) {
			for(int y = 0; y < arr[0].length; y++) {
				if(arr[x][y] == 0) {
					output.add(new int[] {x,y});
				} else if(isWin(x,y,arr[x][y])) {
					return new ArrayList<int[]>();
				}
			}
		}
		return output;
	}
	
	public int evaluate() {
		int output = 0;
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				if(isWin(i,j,this.color)) {
					return 10;
				}
				if(isWin(i,j,getEnemyColor())) {
					return -10;
				}
			}
		}
		return output;
		
	}
	
	private int getEnemyColor() {
		if(this.color == 1) return 2;
		return 1;
	}
	
	public boolean isWin(int x, int y, int color) {
		int found = 0;
		for(int x2 = 0; x2 < arr.length ;x2++) {		//Check rows
			if(arr[x2][y] == color) {
				found++;
				if(found >= numberToWin) {
					return true;
				}
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
				if(found >= numberToWin) {
					return true;
				}
			} else {
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
				if(found >= numberToWin) {
					return true;
				}
			} else {				
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
				if(found >= numberToWin) {
					return true;
				}
			} else {				
				found = 0;
			}
		}
		return false;
	}
}

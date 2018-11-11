package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AIPlayer {
	
	private int color;
	public int[][] arr;
	private int numberToWin;
	private int maxCells;
	private long[][] bit_strings;
	private HashMap<Long, HashEntry> mp;
	
	public AIPlayer(int[][] arr, int color, int numberToWin) {
		this.arr = arr;
		this.color = color;
		this.maxCells = arr.length * arr[0].length;
		this.numberToWin = numberToWin;
		bit_strings = new long[maxCells][2];
		mp = new HashMap<Long, HashEntry>();
		for(int i = 0; i < maxCells; i++) {
			for(int j = 0; j < 2; j++) {
				bit_strings[i][j] =  (int) (((long) (Math.random() * Long.MAX_VALUE)) & 0xFFFFFFFF);
			}
		}
		
	}
	
	public int[] getStep(int[] lastStep) {
		arr[lastStep[0]][lastStep[1]] = getEnemyColor();
		maxCells--;
		int[] step = negaMax(numberToWin*2,this.color, Integer.MIN_VALUE,Integer.MAX_VALUE);
		arr[step[1]][step[2]] = this.color;
		System.out.println(step[0]);
		maxCells--;
		return new int[] {step[1],step[2]};
		
	}
	
	private int[] negaMax(int depth, int color, int alpha, int beta) {
		int bestScore = (color == this.color) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int alphaOrig = alpha;
		HashEntry h = null;
		long val = this.gethash();
		if(mp.containsKey(val)) {
			h = mp.get(val);
			if(h.depth >= depth) {
				if(h.type.equals("lower_bound")) {
					if(alpha < h.score) {
						alpha = h.score;
					}
				} else if(h.type.equals("upper_bound")) {
					if(beta > h.score) {
						beta = h.score;
					}
				} else {
					return h.bestStep;
				}
				if(alpha>=beta)
				      return h.bestStep;
			}
		}
		
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
		if(h == null) {
			int value = (this.color == color) ? alpha : beta;
			String type = "";
			if(value <= alphaOrig) {
				type = "upper_bound";
			} else if(value >= beta) {
				type = "lower_bound";
			} else {
				type = "exact";
			}
			h = new HashEntry((this.color == color) ? alpha : beta, type, new int[]{ (this.color == color) ? alpha : beta, output[0], output[1]}, depth);
			mp.put(val, h);
		}
		return new int[] { (this.color == color) ? alpha : beta, output[0], output[1]};
		
	}
	
	int[] xDir = {0,1,1,-1,-1,0,-1,1};
	int[] yDir = {1,0,1,-1,0,-1,1,-1};
	
	private boolean hasNeighbor(int x, int y) {
		for(int i = 0; i < xDir.length; i++) {
			int newX = x + xDir[i];
			int newY = y + yDir[i];
			if(newX >= 0 && newY >= 0 && newX < arr.length && newY < arr[0].length) {
				if(arr[newX][newY] != 0) return true;
			}
		}
		return false;
	}
	
	public List<int[]> getSteps(){
		List<int[]> output = new ArrayList<int[]>();
		for(int x = 0; x < arr.length; x++) {
			for(int y = 0; y < arr[0].length; y++) {
				if(arr[x][y] == 0) {
					/*if(hasNeighbor(x, y))*/ output.add(new int[] {x,y});
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
	
	public int evaluate2() {
		int output = getRows(color);
		output -= getRows(getEnemyColor());
		return output;
	}
	
	public int getRows(int color) {
		int output = 0;
		for(int i = 0 ; i < arr.length; i++) {
			int found = 0;
			for(int j = 0; j < arr[0].length; j++) {
				if(arr[i][j] == color) {
					found++;
					if(found == numberToWin) {
						output += 100;
					}
				} else if(found > 0){
					found = 0;
				}
			}
		}
		for(int j = 0; j < arr[0].length; j++) {
			int found = 0;
			for(int i = 0 ; i < arr.length; i++) {
				if(arr[i][j] == color) {
					found++;
					if(found == numberToWin) {
						output += 100;
					}
				} else if(found > 0){
					found = 0;
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
	
	public long gethash() {
		long l = 0l;
		int x = 0;
		int y = 0;
		for(int i = 0; i < bit_strings.length; i++) {
			if(arr[x][y] != 0) {
				l ^= bit_strings[i][arr[x][y]-1];
			}
			x++;
			if(x == arr.length) {
				x = 0;
				y++;
			}
		}
		return l;
	}
	
	private class HashEntry{
		int score;
		int depth;
		String type;
		int[] bestStep;
		
		public HashEntry(int score, String type, int[] bestStep, int depth) {
			super();
			this.score = score;
			this.type = type;
			this.bestStep = bestStep;
			this.depth = depth;
		}
		
		
	}
}

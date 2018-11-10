package players;

import java.util.Random;

public class RandomPlayer {

	private int color;
	public int[][] arr;
	private int numberToWin;
	private Random r;
	
	public RandomPlayer(int[][] arr, int color, int numberToWin, Random r) {
		this.arr = arr;
		this.color = color;
		this.numberToWin = numberToWin;
		this.r = r;
	}
	
	public int[] getStep(int[] lastStep) {
		arr[lastStep[0]][lastStep[1]] = getEnemyColor();
		int x = r.nextInt(arr.length);
		int y = r.nextInt(arr[0].length);
		while(arr[x][y] != 0) {
			x = r.nextInt(arr.length);
			y = r.nextInt(arr[0].length);
		}
		arr[x][y] = this.color;
		return new int[] {x,y};
	}
	
	private int getEnemyColor() {
		if(this.color == 1) return 2;
		return 1;
	}
	
	private void printMap() {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
}

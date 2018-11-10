package players;

public class HumanPlayer {
	
	private boolean color;
	public int[][] arr;
	private int numberToWin;
	public int[] step;
	public boolean hasStep;
	
	public HumanPlayer(int[][] arr, boolean color, int numberToWin) {
		this.arr = arr;
		this.color = color;
		this.numberToWin = numberToWin;
		this.hasStep = false;
	}
	
	public int[] getStep() {
		hasStep = false;
		return step;
	}
	
	public void setStep(int x, int y) {
		step =  new int[] {x,y};
		hasStep = true;
	}
}

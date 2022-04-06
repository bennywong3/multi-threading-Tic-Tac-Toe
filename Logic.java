/**
 * This class Logic do all the calculations, 
 * like counting number of rounds and deciding the winner,
 * and it stores the inner function getLogic(), X() and O().
 * 
 * @author Benny Wong UID:3035568881
 * @version 1.0
 */
public class Logic {
	private final Object lock = new Object();
	private int round = 0;
	private int accumulator;
	private boolean xturn;
	String[] storeXO = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
	
	/*
	 * This function tells if it is X's turn or not,
	 * accumulate number of rounds and record the move into array.
	 */
	public void X(int n) {
		synchronized (lock) {
			xturn = true;
			storeXO[n]="X";
			accumulator = n;
			round++;
		}
	}
	
	/*
	 * This function tells if it is O's turn or not,
	 * accumulate number of rounds and record the move into array.
	 */
	public void O(int n) {
		synchronized (lock) {
			xturn = false;
			storeXO[n]="O";
			accumulator = n;
			round++;
		}
	}
	
	/*
	 * This function returns a number that help decide the winner.
	 * If the number is in range 3-11, X wins.
	 * O wins if the number is in range 12-30.
	 * 21-29 means it is X turn and 30-38 means it is O turn.
	 * Larger than 39 means draw.
	 */
	public int getLogic() {
		synchronized (lock) {
			if((storeXO[0] == storeXO[4] && storeXO[0] == storeXO[8])
					|| (storeXO[2] == storeXO[4] && storeXO[2] == storeXO[6])
					|| (storeXO[0] == storeXO[3] && storeXO[0] == storeXO[6])
					|| (storeXO[1] == storeXO[4] && storeXO[1] == storeXO[7])
					|| (storeXO[2] == storeXO[5] && storeXO[2] == storeXO[8])
					|| (storeXO[0] == storeXO[1] && storeXO[0] == storeXO[2])
					|| (storeXO[3] == storeXO[4] && storeXO[3] == storeXO[5])
					|| (storeXO[6] == storeXO[7] && storeXO[6] == storeXO[8])) {
				if (xturn) {
					return 3+accumulator;
				} else {
					return 12+accumulator;
				}
			} else if (round == 9) {
				return 39+accumulator;
			} else {
				if (xturn) {
					return 21+accumulator;
				} else {
					return 30+accumulator;
				}
			}
		}
	}
}
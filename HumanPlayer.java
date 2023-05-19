import java.util.Scanner;

/**
 * Creates a human player
 * @author dongates
 *
 */
public class HumanPlayer extends Player {
	
	/**
	 * constructs the human player
	 * @param marker marker this player is using. 1 is X. -1 is O.
	 */
	public HumanPlayer(int marker) {
		super(marker);
	}
	
	/**
	 * gets a move from the human player.  Returns move minus one so it can be correctly indexed
	 * into the 3x3 game grid
	 * @param grid parameter not really used but needed for abstract method signature in superclass
	 * @return move human player enters prepped for the game grid
	 */
	@Override
	public int getMove(int[][] grid) {
		Scanner inputMove = new Scanner(System.in);
		int move;
		System.out.print("Make your move!  Please enter a number 1-9: ");
		while (!inputMove.hasNextInt()) {
			System.out.print("Not a number, smartass.  Please enter a number 1-9: ");
		}
		move = inputMove.nextInt() - 1;
		//input.close();
		return move;
	}
}

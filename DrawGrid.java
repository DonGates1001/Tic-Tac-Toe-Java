/**
 * draws the grid of a tic tac toe game
 * @author dongates
 *
 */
public class DrawGrid {
	
	/** Escape sequence for red console print */
	public static final String RED = "\u001B[31m";
	/** Escape sequence for blue console print */
	public static final String BLUE = "\u001B[34m";
	/** Escape sequence for reset to default color output */
	public static final String DEFAULT = "\u001B[0m";
	
	/** one represents an X */
	public static final int X = 1;
	/** negative one represents an O */
	public static final int O = -1;
	/** zero represents an available cell in the game grid */
	public static final int EMPTY = 0;
	
	/** 
	 * prints the current game state to the console
	 * @param grid 3 x 3 array representing a tic tac toe grid
	 */
	public void printGrid(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			int j = 0;
			for (; j < grid[0].length - 1; j++) {
				drawCell(grid[i][j]);
				System.out.print("|");
			}
			drawCell(grid[i][j]);
			System.out.println();
			if (i < grid.length - 1 ) {
				drawHorizontal();
			}
		}
		System.out.println();
	}
	
	/**
	 * helper that creates a break line in the game grid
	 */
	private void drawHorizontal() {
		System.out.println("-+-+-");
	}
	
	/**
	 * determines what to print to console based on the contents of a game grid cell
	 * @param cell int represent the value occupying the cell
	 */
	private void drawCell(int cell) {
		if (cell == X) {
			System.out.print(RED + "X" + DEFAULT);
		} else if (cell == O) {
			System.out.print(BLUE + "O" + DEFAULT);
		} else {
			System.out.print(" ");
		}
	}
	
}

import java.util.Scanner;

/**
 * Runs the game Tic-Tac-Toe for one or two players
 * @author dongates
 *
 */
public class TicTacToe {
	//game grid
	private static int[][] grid = new int[3][3];
	
	/** enums for possible game outcomes */
	public enum GameState {WIN, LOSS, DRAW, CONTINUE}
	
	/**
	 * runs program
	 * @param args cl args
	 */
	public static void main(String[] args) {
		//Players
		Player p1 = null;
		Player p2 = null;
		
		//used to determine which is the human player
		int whichPlayer;
		
		//input Scanner
		Scanner input = new Scanner(System.in);
		
		int players = getPlayers();
		while (players != 1 && players != 2) {
			System.out.println("Wrong number of players.");
			players = getPlayers();
		}
		
		System.out.print("Do you want first move? (y/n)");
		String turn = input.nextLine();
		if ("y".equals(turn)) {
			p1 = new HumanPlayer(1);
			whichPlayer = 1;
		} else {
			p2 = new HumanPlayer(-1);
			whichPlayer = 2;
		}
		
		if (players == 2 && whichPlayer == 1) {
			p2 = new HumanPlayer(-1);
		} else if (players == 2 && whichPlayer == 2) {
			p1 = new HumanPlayer(1);
		}
		
		if (players == 1 && whichPlayer == 1) {
			p2 = new AIPlayer(-1);
		} else if (players == 1 && whichPlayer == 2) {
			p1 = new AIPlayer(1);
		}
		
		System.out.println("Let's play Tic-Tac-Toe!");
		System.out.println("When prompted, enter a number 1-9 to make your move, corresponding to the grid:");
		System.out.println();
		System.out.println("***********");
		System.out.println("*         *");
		System.out.println("*  1|2|3  *");
		System.out.println("*  -+-+-  *");
		System.out.println("*  4|5|6  *");
		System.out.println("*  -+-+-  *");
		System.out.println("*  7|8|9  *");
		System.out.println("*         *");
		System.out.println("***********");
		System.out.println();
		
		DrawGrid draw = new DrawGrid();
		draw.printGrid(grid);
		
		//determines whether game continues
		GameState gameOver = GameState.CONTINUE;
		
		while (gameOver == GameState.CONTINUE) {
			if (p1 instanceof HumanPlayer) {
				int play = p1.getMove(grid);
				while (!checkCell(play, grid)) {
					System.out.println("Sorry, that cell is not available.");
					play = p1.getMove(grid);
				}
				playMove(play, p1);
			} else {
				int play = p1.getMove(grid);
				playMove(play, p1);
			}
			//print game state
			draw.printGrid(grid);
			
			//check for win or draw
			gameOver = checkGrid(grid, p1.getMarker());
			if (gameOver == GameState.WIN) {
				System.out.println("Tic-Tac-Toe, Player 1 wins!!");
			}
			if (gameOver == GameState.DRAW) {
				System.out.println("It's a draw!");
			}
			
			
			//player 2's turn if game is not over
			
			if (gameOver == GameState.CONTINUE) {
				if (p2 instanceof HumanPlayer) {
					int play = p2.getMove(grid);
					while (!checkCell(play, grid)) {
						System.out.println("Sorry, that cell is not available.");
						play = p2.getMove(grid);
					}
					playMove(play, p2);
				} else {
					int play = p2.getMove(grid);
					playMove(play, p2);
				}
				//print game state
				draw.printGrid(grid);
				
				//check for win or draw
				gameOver = checkGrid(grid, p2.getMarker());
				if (gameOver == GameState.WIN) {
					System.out.println("Tic-Tac-Toe, Player 2 wins!!");
				}
				if (gameOver == GameState.DRAW) {
					System.out.println("It's a draw!");
				}
			}
			
		}
		input.close();
	}
	
	/**
	 * determines if a given move is allowed.
	 * @param move number where move is meant to be placed
	 * @param grid grid to check whether specified move is legal
	 * @return true if the cell is free, otherwise false if it's occupied or out of bounds
	 */
	public static boolean checkCell(int move, int[][] grid) {
		//Ensure move is in the grid's range
		if (move > 8 || move < 0) {
			return false;
		}
		//obtain grid coordinates
		int i = move / 3;
		int j = move % 3;
		if (grid[i][j] == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * gets number of players from the user
	 * @return 1 or 2 players
	 */
	private static int getPlayers() {
		Scanner numPlayers = new Scanner(System.in);
		int players = 0;
		System.out.print("How many players, 1 or 2? ");
		while (!numPlayers.hasNextInt()) {
			System.out.println("You have to enter a number, sweetie.");
			System.out.print("How many players, 1 or 2? ");
		}
		players = numPlayers.nextInt();
		return players;
	}
	
	/**
	 * checks grid to determine if game is win, loss, draw, or play should continue
	 * @param grid grid to check
	 * @param marker marker of player's grid to be checked.  1 is X, -1 is O
	 * @return 0 for win, 1 for loss, 2 for draw, 3 for continue play
	 */
	public static GameState checkGrid(int[][] grid, int marker) {
		final int ROW = 3;
		final int COL = 3;
		int diagonal = 0;
		//check rows
		for (int i = 0; i < COL; i++) {
			int horizontal = 0;
			for (int j = 0; j < ROW; j++) {
				horizontal += grid[i][j];
			}
			if ((horizontal >= marker * 3 && marker > 0) || (horizontal <= marker * 3 && marker < 0)) {
				return GameState.WIN;
			}
			if ((horizontal <= marker * -3 && marker > 0) || (horizontal >= marker * -3 && marker < 0)) {
				return GameState.LOSS;
			}
			
		}
		//check columns
		for (int i = 0; i < COL; i++) {
			int vertical = 0;
			for (int j = 0; j < ROW; j++) {
				vertical += grid[j][i];
			}
			if ((vertical >= 3 * marker && marker > 0) || (vertical <= 3 * marker && marker < 0)) {
				return GameState.WIN;
			}
			if ((vertical <= -3 * marker && marker > 0) || (vertical >= -3 * marker && marker < 0)) {
				return GameState.LOSS;
			}
			
		}
		//check diagonals
		for (int i = 0; i < ROW; i++) {
			diagonal += grid[i][i];
		}
		if ((diagonal >= 3 * marker && marker > 0) || (diagonal <= 3 * marker && marker < 0)) {
			return GameState.WIN;
		}
		if ((diagonal <= -3 * marker && marker > 0) || (diagonal >= -3 * marker && marker < 0)) {
			return GameState.LOSS;
		}
		diagonal = 0;
		for (int i = 0; i < ROW; i++) {
			int j = 2 - i;
			diagonal += grid[i][j];
		}
		if ((diagonal >= 3 * marker && marker > 0) || (diagonal <= 3 * marker && marker < 0)) {
			return GameState.WIN;
		}
		if ((diagonal <= -3 * marker && marker > 0) || (diagonal >= -3 * marker && marker < 0)) {
			return GameState.LOSS;
		}
		
		//check for draw
		if (checkDraw(grid)) {
			return GameState.DRAW;
		}
		
		//Only option left is to continue play
		return GameState.CONTINUE;
	}
	
	/**
	 * converts an integer into a move on the 3x3 board
	 * @param move integer representing selected move
	 * @param p player making the move
	 */
	private static void playMove(int move, Player p) {
		int i = move / 3;
		int j = move % 3;
		grid[i][j] = p.getMarker();
	}
	
	/**
	 * Check for a drawn board, where all grid cells have been played and no player
	 * has achieved Tic Tac Toe
	 * @return true if board is in drawn state, otherwise false
	 */
	private static boolean checkDraw(int[][] checkGrid) {
		//if any cell holds zero, there is no draw
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (checkGrid[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
 }

/**
 * class representing a computer opponent for a tic-tac-toe game
 * @author dongates
 *
 */
public class AIPlayer extends Player {
	
	/**
	 * Constructs the computer player which chooses move by algorithm
	 * @param marker marker AI is using. 1 is X. -1 is O.
	 */
	public AIPlayer(int marker) {
		super(marker);
	}

	@Override
	public int getMove(int[][] grid) {
		DecisionNode root = createNode(null, grid);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				//checks if this cell is free
				//if it's free, we want to place a marker in it
				if (root.getGrid()[i][j] == 0) {
					int[][] gridAfterMove = new int[3][3];
					//position in array of moves
					int idx = i * 3 + j;
					//value of current move if it terminates the game
					int score = 6;
					//copy the grid
					gridAfterMove = copyGrid(root.getGrid());
					//make the move
					gridAfterMove[i][j] = getMarker();
					//check for win or draw
					TicTacToe.GameState boardStatus = TicTacToe.checkGrid(gridAfterMove, getMarker());
					//if win, this move should be played
					if (boardStatus == TicTacToe.GameState.WIN) {
						return idx;
					//if draw, then this is the only move left and must be played
					} else if (boardStatus == TicTacToe.GameState.DRAW) {
						return idx;
					} else {
						tryMoreMoves(root, root, idx, score - 1, gridAfterMove);
					}
				}
			}
		}
		int bestMove = findBestMove(root.getChildren(), root.getGrid());
		return bestMove;
	}
	
	/**
	 * creates a new DecisionNode
	 * @param parent parent of this DecisionNode
	 * @param grid grid this DecisionNode will contain
	 * @return the newly created node
	 */
	private DecisionNode createNode(DecisionNode parent, int[][] grid) {
		DecisionNode node = new DecisionNode(parent, grid);
		return node;
	}
	
	/**
	 * used to find max value in an array of possible moves
	 * @param moves array of possible moves
	 * @return max index of max value in array
	 */
	private int findBestMove(int[] moves, int[][] grid) {
		int max = -100000;
		int index = 0;
		for (int i = 0; i < moves.length; i++) {
			//ensure the cell is available and check if its content is a new max
			if (grid[i / 3][i % 3] == 0 && moves[i] > max) {
				max = moves[i];
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Helper that checks for paths game might take and assigns values based on game outcomes
	 * @param root initial node containing grid and children for indexing
	 * @param parent node containing information for the next move
	 * @param index original index of this path of game outcomes.  Used to track which move on
	 * board we are currently analyzing
	 * @param score a value for the move.  The further down the game path, the lower the weight of this value
	 * @param grid grid currently under consideration
	 */
	private void tryMoreMoves(DecisionNode root, DecisionNode parent, int index, int score, int[][] grid) {
		DecisionNode currentNode = createNode(parent, grid);
		int currentMarker = getMarker();
		//determine if it's the opponent's turn
		if (score % 2 == 1) {
			currentMarker *= -1;
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (currentNode.getGrid()[i][j] == 0) {
					int[][] gridAfterMove = new int[3][3];
					//copy the grid
					gridAfterMove = copyGrid(currentNode.getGrid());
					//make the move
					gridAfterMove[i][j] = currentMarker;
					//check for win, loss, or draw
					TicTacToe.GameState boardStatus = TicTacToe.checkGrid(gridAfterMove, getMarker());
					//if win, increment root index by score
					if (boardStatus == TicTacToe.GameState.WIN) {
						root.getChildren()[index] += score * 5;
					//if loss, decrement root index by score.
					} else if (boardStatus == TicTacToe.GameState.LOSS) {
						//increase the penalty if this move hands opponent win on their next move
						if (score == 5) {
							root.getChildren()[index] -= score * 20;
						} else {
							root.getChildren()[index] -= score * 1;
						}
					//if draw, add nothing to root index and break out of conditions
					} else if (boardStatus == TicTacToe.GameState.DRAW) {
						root.getChildren()[index] -= 0;
					} else {
						tryMoreMoves(root, currentNode, index, score - 1, gridAfterMove);
					}
				}
			}
		}
	}
	
	/**
	 * creates a copy of a grid to preserve the original
	 * @param grid grid to be copied
	 * @return copy of the original grid
	 */
	private int[][] copyGrid(int[][] grid) {
		int[][] gridCopy = new int[3][3];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				gridCopy[x][y] = grid[x][y];
			}
		}
		return gridCopy;
	}
	
	/**
	 * A node that can represent a grid state and establish a chain of descent
	 * @author dongates
	 *
	 */
	private class DecisionNode {
		/** the parent of this node */
		private DecisionNode parent;
		/** grid state being considered in this node to determine possible next moves */
		private int[][] grid;
		/** children representing possible next moves in the game */
		private int[] children;
		
		/**
		 * constructs a DecisionNode
		 * @param parent parent of this node
		 * @param grid grid contained in this node
		 */
		public DecisionNode(DecisionNode parent, int[][] grid) {
			setParent(parent);
			setGrid(grid);
			setChildren();
		}

		/**
		 * @return the parent
		 */
		public DecisionNode getParent() {
			return parent;
		}

		/**
		 * @param parent the parent to set
		 */
		public void setParent(DecisionNode parent) {
			this.parent = parent;
		}
		
		/**
		 * @return the grid
		 */
		public int[][] getGrid() {
			return grid;
		}

		/**
		 * @param grid the grid to set
		 */
		public void setGrid(int[][] grid) {
			this.grid = grid;
		}

		/**
		 * @return the children
		 */
		public int[] getChildren() {
			return children;
		}

		/**
		 * creates an array of 0-8 to allow establishment of lines of descent for 
		 * board positions
		 */
		public void setChildren() {
			children = new int[9];
		}
		
	}
}

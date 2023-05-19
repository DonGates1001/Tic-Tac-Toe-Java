/**
 * abstract Player for Tic Tac Toe that is basis for either human or AI player
 * @author dongates
 *
 */
public abstract class Player {
	/** indicates if player is X or O, 1 is X, -1 is O */
	private int marker;
	
	/**
	 * Constructs a Player
	 * @param marker marker this player uses for the game. 1 is X. -1 is O
	 */
	public Player(int marker) {
		setMarker(marker);
	}
	
	/**
	 * Sets the player as either X or O.
	 * @param marker 1 for X, any other number for O
	 */
	private void setMarker(int marker) {
		if (marker == 1) {
			this.marker = 1;
		} else {
			this.marker = -1;
		}
	}
	
	/**
	 * retrieve the value of marker.  Will be helpful in assessing state of game grid
	 * @return the value of the player's marker
	 */
	public int getMarker() {
		return marker;
	}
	
	/**
	 * This method gets implemented differently depending on whether player is human or computer
	 * @param grid the current grid used to determine next move
	 * @return integer representing the selected move
	 */
	abstract int getMove(int[][] grid);
	
}

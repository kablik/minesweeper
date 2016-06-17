package minesweeper.core;

import java.util.Random;

import minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
			if (tile instanceof Clue && ((Clue) getTile(row, column)).getValue() == 0) {
				openAdjacentTiles(row, column);
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		Random rnd1 = new Random();
		Random rnd2 = new Random();

		int k = 0;
		while (k < getMineCount()) {
			int riadok = rnd1.nextInt(getRowCount());
			int stlpec = rnd2.nextInt(getColumnCount());
			if (tiles[riadok][stlpec] == null) {
				tiles[riadok][stlpec] = new Mine();
				k++;
			}
		}

		for (int j = 0; j < getRowCount(); j++) {
			for (int c = 0; c < getColumnCount(); c++) {
				if (tiles[j][c] == null) {
					tiles[j][c] = new Clue(countAdjacentMines(j, c));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		int riadky = getColumnCount();
		int stlpce = getRowCount();
		boolean solved = false;

		if ((riadky * stlpce) - getNumberOf(Tile.State.OPEN) == mineCount) {
			solved = true;
		}
		return solved;
	}

	int getNumberOf(Tile.State state) {
		int numberOfOpenTiles = 0;
		for (int riadok = 0; riadok < getColumnCount(); riadok++) {
			for (int stlpec = 0; stlpec < getRowCount(); stlpec++) {
				if (getTile(stlpec, riadok).getState() == state) {
					numberOfOpenTiles++;
				}
			}
		}
		return numberOfOpenTiles;
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {

						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	private void openAdjacentTiles(int riadok, int stlpec) {
		for (int riadokPosuvanie = -1; riadokPosuvanie <= 1; riadokPosuvanie++) {
			int aktualnyRiadok = riadok + riadokPosuvanie;
			if (aktualnyRiadok >= 0 && aktualnyRiadok < rowCount) {
				for (int stlpecPosuvanie = -1; stlpecPosuvanie <= 1; stlpecPosuvanie++) {
					int aktualnyStlpec = stlpec + stlpecPosuvanie;
					if (aktualnyStlpec >= 0 && aktualnyStlpec < columnCount) {
						if (((Clue) tiles[riadok][stlpec]).getValue() == 0 && tiles[aktualnyRiadok][aktualnyStlpec].getState() == State.CLOSED) {
							tiles[aktualnyRiadok][aktualnyStlpec].setState(State.OPEN);
							openAdjacentTiles(aktualnyRiadok, aktualnyStlpec);
						}
					}
				}
			}
		}
	}
	
	public int getRemainingMineCount(){
		int remainingMines = mineCount;
		for (int i = 0; i < getColumnCount(); i++){
			for (int j = 0; j < getRowCount(); j++){
				if (tiles[i][j].getState() == State.MARKED){
					remainingMines--;
				}
			}
		}
		return remainingMines;
	}
}

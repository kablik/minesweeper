package minyTesty;

import static org.junit.Assert.*;
import org.junit.Test;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;

public class FieldTestByTask {

	static final int ROWS = 9;
	static final int COLUMNS = 9;
	static final int MINES = 10;

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void testRowsColumnsMines() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		assertEquals(ROWS, field.getRowCount());
		assertEquals(COLUMNS, field.getColumnCount());
		assertEquals(MINES, field.getMineCount());
	}

	@Test
	public void tileNotNull() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				assertNotNull(field.getTile(i, j));
			}
		}
	}

	@Test
	public void numberOfMines() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		int pocetMin = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (field.getTile(i, j) instanceof Mine) {
					pocetMin++;
				}
			}
		}
		assertEquals(MINES, pocetMin);
	}

	@Test
	public void numberOfClues() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		int pocetMin = 0;
		int pocetClue = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (field.getTile(i, j) instanceof Mine) {
					pocetMin++;
				} else if (field.getTile(i, j) instanceof Clue) {
					pocetClue++;
				}
			}
		}
		int Clue = ROWS * COLUMNS - pocetMin;
		assertEquals(MINES, pocetMin);
		assertEquals(Clue, pocetClue);
	}
}

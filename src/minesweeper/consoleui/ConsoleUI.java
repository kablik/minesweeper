package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import minesweeper.core.Tile.State;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.
	 * Field)
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			processInput();
			if (field.getState() == GameState.SOLVED){
				System.out.println("vyhral si");
			} else if (field.getState() == GameState.FAILED){
				System.out.println("prehral si");
				System.exit(0);
			}
		} while (true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		char hlavicka = 'A';

		for (int i = -1; i < field.getColumnCount(); i++) {
			if (i == -1) {
				System.out.print("  ");
			} else {
				System.out.print(i + " ");
			}
		}
		System.out.println();

		for (int stlpec = 0; stlpec < field.getColumnCount(); stlpec++) {
			System.out.print(hlavicka);
			hlavicka++;
			for (int riadok = 0; riadok < field.getRowCount(); riadok++) {
				if (field.getTile(riadok, stlpec).getState() == State.CLOSED) {
					System.out.print(" -");
				} else if (field.getTile(riadok, stlpec).getState() == State.MARKED) {
					System.out.print(" M");
				} else if (field.getTile(riadok, stlpec).getState() == State.OPEN) {
					if (field.getTile(riadok, stlpec) instanceof Clue) {
						Tile tempTile = field.getTile(riadok, stlpec);
						System.out.print(" " + ((Clue) tempTile).getValue());
					} else if (field.getTile(riadok, stlpec) instanceof Mine) {
						System.out.print(" X");
					}
				}
			}
			System.out.println();
		}
		System.out.println("------------------------------------------");
		System.out.println("Poèet neoznaèených mín je: " + field.getRemainingMineCount());
		System.out.println("Aktuálny èas hrania hry je: " + Minesweeper.getInstance().getPlayingSeconds() + " sekúnd");
		System.out.println();
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		System.out.println("Zadajte èo chcete vykona: (X – ukonèenie hry, MA1 – oznaèenie dlaždice v riadku A a ståpci 1, OB4 – odkrytie dlaždice v riadku B a ståpci 4): ");
		try {
			String prikazHraca = readLine();
			handleInput(prikazHraca);
		} catch (WrongFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void handleInput(String input) throws WrongFormatException{
		Pattern ukoncenieHry = Pattern.compile("X|x");
		Pattern oznacenieMiny = Pattern.compile("(M|m)([A-Ia-i])([0-8])");
		Pattern otvoreniePolicka = Pattern.compile("(O|o)([A-Ia-i])([0-8])");
		Matcher matcherUkoncenie = ukoncenieHry.matcher(input);
		Matcher matcherOznacenie = oznacenieMiny.matcher(input);
		Matcher matcherOtvorenie = otvoreniePolicka.matcher(input);
		if (matcherUkoncenie.matches()) {
			System.out.println("end");
			System.exit(0);	
		} else if (matcherOznacenie.matches()) {
			String riadokPismeno = matcherOznacenie.group(2);
			String stlpec = matcherOznacenie.group(3);
			char riadokChar = riadokPismeno.charAt(0);
			int stlpecInteger = Integer.parseInt(stlpec);
			int riadokInteger = Character.getNumericValue(riadokChar) - 10;
			field.markTile(stlpecInteger, riadokInteger);
		} else if (matcherOtvorenie.matches()) {
			String riadokPismeno = matcherOtvorenie.group(2);
			String stlpec = matcherOtvorenie.group(3);
			char riadokChar = riadokPismeno.charAt(0);
			int stlpecInteger = Integer.parseInt(stlpec);
			int riadokInteger = Character.getNumericValue(riadokChar) - 10;
			field.openTile(stlpecInteger, riadokInteger);
		} else {
			throw new WrongFormatException("Bol zadany zly format vstupu !!!");
		}
	}
}

package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;



/**
 * Main application class.
 */
public class Minesweeper {
	long startMillis = System.currentTimeMillis();
	
	public int getPlayingSeconds(){
		long casHrania = System.currentTimeMillis() - startMillis;
		long pocetSekund = casHrania / 1000;
		return (int) pocetSekund;
	}
    /** User interface. */
    private UserInterface userInterface;
 
    /**
     * Constructor.
     */
    private Minesweeper() {
        userInterface = new ConsoleUI();
        
        Field field = new Field(9, 9, 10);
        userInterface.newGameStarted(field);
    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
    }
}

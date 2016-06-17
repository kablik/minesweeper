package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
    /** List of best player times. */
    private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

    /**
     * Returns an iterator over a set of  best times.
     * @return an iterator
     */
    public Iterator<PlayerTime> iterator() {
        return playerTimes.iterator();
    }

    /**
     * Adds player time to best times.
     * @param name name ot the player
     * @param time player time in seconds
     */
//    private String readLine(){
//    	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    	try {
//			return input.readLine();
//		} catch (IOException e) {
//			return null;
//		}
//    }
    public void addPlayerTime(String name, int time) {
    	PlayerTime hrac = new PlayerTime (name, time);
    	
    	
    	
//    	System.out.println("Zadajte vaše meno");
//		String meno = readLine();
//		int cas = getPlayingSeconds();
//        throw new UnsupportedOperationException("Method addPlayerTime not yet implemented");
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    public String toString() {
        throw new UnsupportedOperationException("Method toString not yet implemented");
    }

    /**
     * Player time.
     */
    public static class PlayerTime implements Comparable<PlayerTime>{
        /** Player name. */
        private final String name;

        /** Playing time in seconds. */
        private final int time;

        /**
         * Constructor.
         * @param name player name
         * @param time playing game time in seconds
         */
        public PlayerTime(String name, int time) {
            this.name = name;
            this.time = time;
        }
        
        String getName(){
			return name;
        }
        
        int getTime(){
			return time;
        }
        
        public int compareTo(PlayerTime o){
			return time;
        	
        	
        }
        
        
        
    }
}
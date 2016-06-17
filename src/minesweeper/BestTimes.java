package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name ot the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		PlayerTime hrac = new PlayerTime(name, time);

		int pocetHracovVListe = playerTimes.size();
		if (pocetHracovVListe > 0) {
			PlayerTime prvyHracVZozname = playerTimes.get(0);
			if (prvyHracVZozname.compareTo(hrac) == 1) {
				playerTimes.add(0, hrac);
			}
			for (int i = 0; i < playerTimes.size(); i++) {
				PlayerTime hracVZozname1 = playerTimes.get(i);
				PlayerTime hracVZozname2 = playerTimes.get(i+1);
				if (hracVZozname1.compareTo(hrac) == -1 && hracVZozname2.compareTo(hrac) == 1){
					playerTimes.add(i+1, hrac);
				}
			}
		}Collections.sort(playerTimes);
	}

	void reset(){
		playerTimes.removeAll(playerTimes);
	}
	

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		Formatter f = new Formatter();
		Iterator<PlayerTime> i = playerTimes.iterator();
		int poradie = 0;
		while (i.hasNext()) {
			PlayerTime p = i.next();
			f.format("%d. %s", ++poradie, p);
		}
		return f.toString();
	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		String getName() {
			return name;
		}

		int getTime() {
			return time;
		}

		public int compareTo(PlayerTime o) {
			int output = 0;
			if (this.time < o.time) {
				output = -1;
			} else if (this.time > o.time) {
				output = 1;
			} else if (this.time == o.time) {
				output = 0;
			}
			return output;
		}
	}
}
/*
 * TAKEN FROM MICHAEL's A2 and HEAVILY MODDED
 */

package voxspell.inputoutput;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.concurrent.Task;
import voxspell.gamelogic.SpellingGame;

/**
 * <h1>WordListReader</h1> This class reads words in to a SpellingGame in
 * another thread so the GUI doesn't freeze
 * <p>
 * The text file read should be in a format of "%Level " on a line and
 * everything below it belongs to the first level. The next "%Level " implies
 * the second level and so on
 * 
 * @version 0.5.0
 * @author mkem114 (primary)
 * @author tkro003 (secondary)
 * @since
 */
public class WordListReader {
	/**
	 * Default file name to read words from
	 */
	public static final String DEFAULTFILENAME = "nzcer-wordlist.txt";
	private List<SpellingGame> _listeners;
	private BufferedReader _file;

	/**
	 * Creates a WordListReader object assuming the default file name
	 * 
	 * @throws FileNotFoundException
	 *             Thrown when the file is in the wrong place or non-existing
	 */
	public WordListReader() throws FileNotFoundException {
		this(DEFAULTFILENAME);
	}

	/**
	 * Creates a WordListReader to read from the specified file name
	 * 
	 * @param filename
	 *            Name of file to read from
	 * @throws FileNotFoundException
	 *             Thrown when the file is in the wrong place or non-existing
	 */
	public WordListReader(String filename) throws FileNotFoundException {
		_file = new BufferedReader(new FileReader(filename));
		_listeners = new LinkedList<SpellingGame>();
	}

	/**
	 * Starts the reading process
	 * 
	 * @throws IOException
	 */
	public void readWords() throws IOException {
		new Thread(new WordReader(_listeners)).start();
	}

	/**
	 * Add's a SpellingGame to be updated once the reading is complete
	 * 
	 * @param sg
	 *            SpellingGame to add
	 * @return The WordListReader reference for chaining calls
	 */
	public WordListReader addObserver(SpellingGame sg) {
		_listeners.add(sg);
		return this;
	}

	/**
	 * <h1>WordReader</h1> Private class that actually does all the work on a
	 * background thread
	 * 
	 * @version 0.5.0
	 * @author mkem114
	 * @since 2016-09-18
	 */
	private class WordReader extends Task<Object> {
		List<SpellingGame> _listeners;

		/**
		 * Only constructor because a list of games is required
		 * 
		 * @param games
		 */
		public WordReader(List<SpellingGame> games) {
			_listeners = games;
		}

		/**
		 * Where the actual reading and updating occurs, invoked on the
		 * background thread
		 */
		@Override
		protected Object call() throws Exception {
			List<List<String>> allWords = new ArrayList<>();
			while (true) {
				String w = _file.readLine();
				if (w == null) {
					break;
				} else {
					if (w.matches("%Level .*")) {
						allWords.add(new ArrayList<String>());
					} else {
						w = w.toLowerCase();
						allWords.get(allWords.size() - 1).add(w);
					}
				}
			}
			for (SpellingGame sg : _listeners) {
				sg.updateWords(allWords);
			}

			return null;
		}
	}
}
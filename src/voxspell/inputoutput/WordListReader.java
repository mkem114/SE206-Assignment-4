/*
 * TAKEN FROM MICHAEL's A2 and HEAVILY MODDED
 */

package voxspell.inputoutput;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
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
 * @since 2016-10-11
 */
public class WordListReader {
	/**
	 * Default file name to read words from
	 */
	public static final String DEFAULTWORDLISTFILENAME = "defaultWordlist.txt";
	public static final String DEFAULTHOMOPHONESFILENAME = "./VOXSpell/homophones.txt";
	private List<SpellingGame> _listeners;
	private BufferedReader _wordlistFile;
	private BufferedReader _homophonesFile;

	/**
	 * Creates a WordListReader object assuming the default file name
	 * 
	 * @throws FileNotFoundException
	 *             Thrown when the file is in the wrong place or non-existing
	 */
	public WordListReader() throws FileNotFoundException {
		this(DEFAULTWORDLISTFILENAME);
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
		_wordlistFile = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
		_homophonesFile = new BufferedReader(new FileReader(DEFAULTHOMOPHONESFILENAME));
		_listeners = new LinkedList<>();
	}

	/**
	 * Starts the reading process
	 * 
	 * @throws IOException
	 */
	public void readWords() throws IOException {
		new Thread(new WordReaderHelper(_listeners)).start();
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
	 * <h1>WordReaderHelper</h1> Private class that actually does all the work on a
	 * background thread
	 * 
	 * @version 0.5.0
	 * @author mkem114
	 * @since 2016-09-18
	 */
	private class WordReaderHelper extends Task<Object> {
		List<SpellingGame> _listeners;

		/**
		 * Only constructor because a list of games is required
		 * 
		 * @param games The spelling games to be informed when the word list is read
		 */
		public WordReaderHelper(List<SpellingGame> games) {
			_listeners = games;
		}

		/**
		 * Where the actual reading and updating occurs, invoked on the
		 * background thread
		 */
		@Override
		protected Object call() throws Exception {
			HashSet<String> homophones = readHomophones();
			List<List<String>> allWords = new ArrayList<>();
			while (true) {
				String w = _wordlistFile.readLine();
				//w = w.trim();
				if (w == null) {
					break;
				} else {
					if (w.matches("%Level .*")) {
						allWords.add(new ArrayList<>());
					} else if (!w.contains(" ")) {
						w = w.toLowerCase();
						if (!homophones.contains(w)) {
							allWords.get(allWords.size() - 1).add(w);
						}
					}
				}
			}
			for (SpellingGame sg : _listeners) {
				sg.updateWords(allWords);
			}

			return null;
		}

		private HashSet<String> readHomophones() throws Exception {
			HashSet<String> homophones = new HashSet<>();

			while (true) {
				String rawLine = _homophonesFile.readLine();
				if (rawLine == null) {
					break;
				} else {
					String[] words = rawLine.split(", ");
					for (String w : words) {
						homophones.add(w.toLowerCase());
					}
				}
			}

			return homophones;
		}
	}
}
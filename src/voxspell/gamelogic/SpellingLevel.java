package voxspell.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>SpellingLevel</h1> Represents a game level of spelling, they are
 * distinguished by the difficulty of words they contain and their relative
 * numbers (e.g. Level 1)
 * 
 * @version 1.0
 * @author mkem114
 * @since 2016-09-18
 */
public class SpellingLevel implements Serializable {
	/**
	 * Serializing ID re: SpellingGame
	 */
	private static final long serialVersionUID = -9174985624580121154L;
	private int _number;
	private List<QuizWord> _words;
	private List<SpellingQuiz> _quizzes;
	private SpellingGame _game;

	/**
	 * Creates a new spelling level based on a level number and the spelling
	 * game they belong to
	 * 
	 * @param number
	 *            Level number
	 * @param game
	 *            Spelling game
	 */
	public SpellingLevel(int number, SpellingGame game) {
		_words = new ArrayList<QuizWord>();
		_quizzes = new ArrayList<SpellingQuiz>();
		_number = number;
		_game = game;
	}

	/**
	 * Number of the level
	 * 
	 * @return Level number
	 */
	public int levelNum() {
		return _number;
	}

	/**
	 * Adds a word to the level to be quizzed
	 * 
	 * @param word
	 *            Word to be added
	 */
	public void addWord(String word) {
		QuizWord wordToAdd = new QuizWord(word);
		_words.add(wordToAdd);
	}

	/**
	 * Returns the statistics of the level; each time a word has been mastered,
	 * faulted and failed
	 * 
	 * @return A list of statistics going [word, mastered, faulted, failed]
	 */
	public ArrayList<ArrayList<String>> statistics() {
		ArrayList<ArrayList<String>> stats = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < _words.size(); i++) {
			QuizWord word = _words.get(i);
			stats.add(new ArrayList<String>());
			stats.get(i).add(word.word());
			stats.get(i).add(Integer.toString(word.timesMastered()));
			stats.get(i).add(Integer.toString(word.timesFaulted()));
			stats.get(i).add(Integer.toString(word.timesFailed()));
		}
		return stats;
	}

	/**
	 * The overall accuracy as a percentage the amount of times each word has
	 * been mastered compared to the overall attempts
	 * 
	 * @return Level accuracy as a percentage
	 */
	public double accuracy() {
		int incorrect = 0;
		int attempted = 0;
		for (QuizWord word : _words) {
			incorrect += word.timesFailed();
			incorrect += word.timesFaulted();
			attempted += word.timesAttempted();
		}
		incorrect *= 100;
		if (attempted == 0) {
			return 0;
		}
		return (100 - incorrect / attempted);
	}

	/**
	 * Starts a new spelling quiz on this level
	 * 
	 * @return The new spelling quiz
	 */
	public SpellingQuiz newQuiz() {
		NewSpellingQuiz nsq = new NewSpellingQuiz(this);
		_quizzes.add(nsq);
		return nsq;
	}

	/**
	 * Starts a new review quiz on this level
	 * 
	 * @return The review quiz
	 */
	public SpellingQuiz reviewQuiz() {
		_quizzes.add(new ReviewSpellingQuiz(this));
		return _quizzes.get(_quizzes.size() - 1);
	}

	/**
	 * The list of words on the level
	 * 
	 * @return List of quiz words on the level
	 */
	public List<QuizWord> words() {
		return _words;

	}

	/**
	 * This level has been completed by the user
	 */
	public void complete() {
		_game.levelUp();
	}
}
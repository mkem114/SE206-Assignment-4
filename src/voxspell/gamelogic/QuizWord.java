//Taken from Michael's A2 and lightly modified

package voxspell.gamelogic;

import java.io.Serializable;

/**
 * <h1>QuizWord</h1> Represents a word to be quizzed
 * 
 * @version 1.1
 * @author mkem114
 * @since 2016-08-22
 */
public class QuizWord implements Serializable {
	/**
	 * Generated serialisation ID re:SpellingGame
	 */
	private static final long serialVersionUID = 8661649014339280467L;
	private String _word;
	private int _mastered;
	private int _faulted;
	private int _failed;
	private boolean _upForReview;

	/**
	 * Creates a new quiz word based on a word by assuming it has no game
	 * history
	 * 
	 * @param word
	 *            Word to be quizzed
	 */
	public QuizWord(String word) {
		this(word, 0, 0, 0);
		_upForReview = false;
	}

	/**
	 * Creates a new quiz word based on the word and it's game history
	 * 
	 * @param word
	 *            The actual word
	 * @param mastered
	 *            How many times it has been gotten correct
	 * @param faulted
	 *            How many times it was correct on the second go
	 * @param failed
	 *            How many times it was flat out wrong
	 */
	public QuizWord(String word, int mastered, int faulted, int failed) {
		_word = word;
		_mastered = mastered;
		_faulted = faulted;
		_failed = failed;
		_upForReview = false;
	}

	/**
	 * Prints a description of the quiz word object
	 */
	public String toString() {
		if (_word == null) {
			return "Undefined Word";
		} else {
			return _word + "\t" + " mastered:" + _mastered + " faulted:" + _faulted + " failed:" + _failed;
		}
	}

	/**
	 * Checks another quizword is the same word and statistics
	 */
	public boolean equals(Object o) {
		try {
			QuizWord qw = (QuizWord) (o);
			if (_word == qw.word() && _mastered == qw.timesMastered() && _faulted == qw.timesFaulted()
					&& _failed == qw.timesFailed() && _upForReview == qw.toBeReviewed()) {
				return true;
			}
		} catch (ClassCastException e) {
			return false;
		}
		return false;
	}

	/**
	 * If the word will appear in review mode then true is returned
	 * 
	 * @return Whether it will be in review mode
	 */
	public boolean toBeReviewed() {
		return _upForReview;
	}

	/**
	 * The word that is being quizzed
	 * 
	 * @return word
	 */
	public String word() {
		return _word;
	}

	/**
	 * How many time the word was guessed correct on the first attempt
	 * 
	 * @return Times mastered
	 */
	public int timesMastered() {
		return _mastered;
	}

	/**
	 * The word has been guessed correctly on the first go
	 */
	public void hasMastered() {
		_mastered++;
		if (_upForReview) {
			_upForReview = false; // if you get it right in play you dont have
									// to review
		}
	}

	/**
	 * How many times the word has been spelt correctly the second time by the
	 * user
	 * 
	 * @return Times faulted
	 */
	public int timesFaulted() {
		return _faulted;
	}

	/**
	 * The word was spelt correctly the second time by the user
	 */
	public void hasFaulted() {
		_faulted++;
	}

	/**
	 * How many times the word has been flat out failed by the player
	 * 
	 * @return Times failed
	 */
	public int timesFailed() {
		return _failed;
	}

	/**
	 * The word has been flat out failed by the player
	 */
	public void hasFailed() {
		_failed++;
		_upForReview = true;
	}

	/**
	 * How many times the word has been attempted
	 * 
	 * @return Times attempted
	 */
	public int timesAttempted() {
		return _mastered + _faulted + _failed;
	}
}

package voxspell.gamelogic;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import voxspell.inputoutput.TextToSpeech;

/**
 * <h1>SpellingQuiz</h1> Represents a spelling quiz with ten words
 * 
 * @version 1.1
 * @author mkem114
 * @author tkro003 (minor fixes)
 * @since 2016-09-08
 *
 */
public abstract class SpellingQuiz implements Serializable {
	/**
	 * Generated serialization ID
	 */
	private static final long serialVersionUID = -6330487489105124066L;
	public static final int numWordsToQuiz = 10;
	public static final int numWordsForReward = 9;
	public static final int numWordsToLevel = 9; // not used

	/**
	 * <h1>QuizState</h1> The current state of the quiz
	 * 
	 * @author mkem114
	 * @since 2016-09-08
	 */
	public enum QuizState {
		FINISHED, FAILED, FAULTED, MASTERED, NEW, SECONDGO
	}

	private final List<QuizWord> _quizWords;
	private final NumGenerator _numGen;

	private int _correct;
	private int _timesPlayed;
	private SpellingLevel _level;
	private QuizWord _currentWord;
	private QuizState _state;

	/**
	 * Creates a new spelling quiz of the specified level
	 * 
	 * @param level
	 *            The level to start the quiz on
	 */
	public SpellingQuiz(SpellingLevel level) {
		_correct = 0;
		_level = level;
		_timesPlayed = 1;
		_numGen = new NumGenerator();
		_quizWords = generateWordList();
		_state = QuizState.NEW;
		_currentWord = _quizWords.get(0);
		TextToSpeech.access().speak("Round starting: spell " + _currentWord.word());
	}

	/**
	 * Checks if the user is eligible for a reward
	 * 
	 * @return whether the user is eligible for a reward
	 */
	public boolean reward() {
		if (_correct >= numWordsForReward) {
			return true;
		}
		return false;
	}

	/**
	 * Obtains the number of correct answers on the level
	 * 
	 * @return the number of correct answers
	 */
	public int numCorrect() {
		return _correct;
	}

	/**
	 * Gets the level number
	 * 
	 * @return the level
	 */
	public int levelNum() {
		return _level.levelNum();
	}

	/**
	 * Gets the overall level accuracy
	 * 
	 * @return level accuracy
	 */
	public double levelAccuracy() {
		return _level.accuracy();
	}

	/**
	 * Calculates the running total accuracy of this quiz (0% correct if the
	 * user has't answered anything)
	 * 
	 * @return quiz accuracy
	 */
	public double quizAccuracy() {
		if (wordNum() == 1) {
			return 0;
		}
		return 100 * _correct / (wordNum() - 1);
	}

	/**
	 * Gets the progress in terms of how many words the user has answered
	 * already
	 * 
	 * @return the word count
	 */
	public int wordNum() {
		return _quizWords.indexOf(_currentWord) + 1;
	}

	/**
	 * Gets the state of the word
	 * 
	 * @return word state
	 */
	public QuizState stateIn() {
		return _state;
	}

	/**
	 * Generates a word list based on the type of quiz needed by calling the
	 * number generator.
	 * 
	 * @return A list of words to be quizzed
	 */
	private List<QuizWord> generateWordList() {
		List<QuizWord> rawList = getWords();
		List<Integer> index;

		if (rawList.size() < numWordsToQuiz) {
			return rawList;
		} else {
			List<QuizWord> generatedList = new Stack<QuizWord>();
			index = _numGen.UniqueRandomInteger(0, rawList.size(), numWordsToQuiz);
			Collections.shuffle(index);
			for (Integer i : index) {
				generatedList.add(rawList.get(i));
			}
			return generatedList;
		}
	}

	/**
	 * Gets all the words of a specified level
	 * 
	 * @return All the words corresponding to the level
	 */
	protected List<QuizWord> levelWords() {
		return _level.words();
	}

	/**
	 * Generates the word list for the quiz (by going to the subclasses)
	 * 
	 * @return Word list for the quiz
	 */
	protected abstract List<QuizWord> getWords();

	/**
	 * Checks whether a word has been spelled correctly
	 * 
	 * @param guess
	 *            the user's input attempt
	 * @return The state of the word that was tested
	 */
	public QuizState check(String guess) {
		guess = guess.toLowerCase();
		if (_state == QuizState.NEW) {
			if (guess.equals(_currentWord.word())) {
				// TextToSpeech.access().speak("Correct");
				_correct++;
				_currentWord.hasMastered();
				_state = QuizState.MASTERED;
			} else {
				TextToSpeech.access().speak("Incorrect, try once more ......... " + _currentWord.word() + " ......... "
						+ _currentWord.word());
				_state = QuizState.SECONDGO;
			}
		} else if (_state == QuizState.SECONDGO) {
			if (guess.equals(_currentWord.word())) {
				// TextToSpeech.access().speak("correct");
				_currentWord.hasFaulted();
				_state = QuizState.FAULTED;
			} else {
				// TextToSpeech.access().speak("incorrect");
				_currentWord.hasFailed();
				_state = QuizState.FAILED;
			}
		} else {
			// TODO throw some exception
		}
		return _state;
	}

	/**
	 * Checks whether there is another word in the quiz and says the response to
	 * the user's input
	 * 
	 * @return If there are words left in the quiz
	 */
	public boolean loadNext() {
		if (wordNum() == numWordsToQuiz || wordNum() == _quizWords.size()) {
			if (_state == QuizState.FAILED) {
				TextToSpeech.access().speak("Incorrect. Round over.");
			} else {
				TextToSpeech.access().speak("Correct. Round over.");
			}
			if (_correct >= numWordsToLevel) {

				_level.complete();
			}
			return false;
		} else {
			_currentWord = _quizWords.get(wordNum());
			_timesPlayed = 1;
			if (_state == QuizState.MASTERED) {
				TextToSpeech.access().speak("Correct, spell " + _currentWord.word());
			} else if (_state == QuizState.FAULTED) {
				TextToSpeech.access().speak("Correct, spell " + _currentWord.word());
			} else if (_state == QuizState.FAILED) {
				TextToSpeech.access().speak("Incorrect. Next word: spell " + _currentWord.word());
			} else {
				TextToSpeech.access().speak(_currentWord.word());

			}
			_state = QuizState.NEW;
			// TODO fix correct and stuff being said or double up of word to
			// spell
			return true;
			// TODO merge with thing into next word funciton
		}
	}

	/**
	 * Gets the size of the words to be quizzed in case there are fewer than ten
	 * words
	 * 
	 * @return the number of words in a quiz
	 */
	public int actualSize() {
		return _quizWords.size();
	}

	/**
	 * Speaks the word that is quizzed again in case the user presses the
	 * "replay" button.
	 * 
	 * @return whether the user can replay anymore
	 */
	public boolean replay() {
		if (_timesPlayed < 2) {
			TextToSpeech.access().speak(_currentWord.word());
			_timesPlayed++;
			return true;
		}
		return false;
	}

	/**
	 * Similar to replay(), but doesn't have text-to-speech capabilities
	 * 
	 * @return whether the user can replay anymore
	 */
	public boolean replayDisabled() {
		return _timesPlayed >= 2;
	}
}
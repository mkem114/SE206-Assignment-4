package voxspell.gamelogic;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ReviewSpellingQuiz</h1> Represents a quiz that is based on words the user
 * has gotten wrong in a particular level
 * 
 * @version 1.0
 * @author mkem114
 * @since 2016-09-18
 */
public class ReviewSpellingQuiz extends SpellingQuiz {

	/**
	 * Generated serializing ID re:SpellingGame
	 */
	private static final long serialVersionUID = -7087041871083239036L;

	/**
	 * Creates a review spelling quiz for a given level
	 * 
	 * @param level
	 *            The level to review
	 */
	public ReviewSpellingQuiz(SpellingLevel level) {
		super(level);
	}

	/**
	 * Creates the list of words to review based on what has been gotten wrong
	 */
	@Override
	protected List<QuizWord> getWords() {
		List<QuizWord> rawList = levelWords();
		List<QuizWord> reviewWords = new ArrayList<>();
		for (QuizWord word : rawList) {
			if (word.toBeReviewed() == true) {
				reviewWords.add(word);
			}
		}
		return reviewWords;
	}

}

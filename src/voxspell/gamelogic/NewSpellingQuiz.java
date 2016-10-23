package voxspell.gamelogic;

import java.util.List;

/**
 * <h1>NewSpellingQuiz</h1> This class is responsible for quizzing the user on a
 * random subset of words in a SpellingLevel
 * 
 * @version 1.0
 * @author mkem114
 * @since 2016-09-18
 */
public class NewSpellingQuiz extends SpellingQuiz {

	/**
	 * Serializing ID or something re: SpellingGame
	 */
	private static final long serialVersionUID = 8837633183914844410L;

	/**
	 * Creates a new SpellingQuiz at a given level
	 * 
	 * @param level the level you start at
	 */
	public NewSpellingQuiz(SpellingLevel level) {
		super(level);
	}

	/**
	 * In a new spelling quiz all words in a level are eligible for quizzing
	 */
	@Override
	protected List<QuizWord> getWords() {
		return level().words();
	}

}

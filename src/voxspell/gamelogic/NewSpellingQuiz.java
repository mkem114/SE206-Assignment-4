package voxspell.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <h1>NewSpellingQuiz</h1> This class is responsible for quizzing the user on a
 * random subset of words in a SpellingLevel
 *
 * @author mkem114
 * @version 1.0
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
        List<QuizWord> rawList = level().words();
        Collections.shuffle(rawList);
        if (rawList.size() <= 10) {
            return rawList;
        } else {
            List<QuizWord> workingList = new ArrayList<>(rawList);
            workingList.sort(new Comparator<QuizWord>() {
                public int compare(QuizWord word1, QuizWord word2) {
                    if (word1.timesFailed() > word2.timesFailed()) {
                        return 1;
                    } else if (word1.timesFailed() == word2.timesFailed()) {
                        if (word1.timesFaulted() > word2.timesFaulted()) {
                            return 1;
                        } else if (word1.timesFaulted() == word2.timesFaulted()) {
                            if (word2.timesAttempted() > word1.timesAttempted()) {
                                return 1;
                            } else if (word2.timesAttempted() == word1.timesAttempted()) {
                                return 0;
                            }
                        }
                    }
                    return -1;
                }
            });
            List<QuizWord> playList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                playList.add(workingList.get(i));
            }
            Collections.shuffle(playList);
            return playList;
        }
    }
}

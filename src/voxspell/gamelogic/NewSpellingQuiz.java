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
     * Creates a list of words to quiz the user on based on how many times they have been attempted and how many times
     * they have been failed
     *
     * @return The list of words the user will be quizzed on
     */
    @Override
    protected List<QuizWord> getWords() {
        // Gets list of words to work with
        List<QuizWord> rawList = level().words();
        // Shuffles words
        Collections.shuffle(rawList);
        // If there's less than 10 words it doesn't matter
        if (rawList.size() <= 10) {
            return rawList;
        } else { // Need to get 10 words
            //Copy words
            List<QuizWord> workingList = new ArrayList<>(rawList);
            // Sort words based on least tried then most failed then most faulted
            workingList.sort(new Comparator<QuizWord>() {
                public int compare(QuizWord word1, QuizWord word2) {
                    if (word2.timesAttempted() > word1.timesAttempted()) {
                        return 1;
                    } else if (word2.timesAttempted() == word1.timesAttempted()) {
                        if (word1.timesFaulted() > word2.timesFaulted()) {
                            return 1;
                        } else if (word1.timesFaulted() == word2.timesFaulted()) {
                            if (word1.timesFailed() > word2.timesFailed()) {
                                return 1;
                            } else if (word1.timesFailed() == word2.timesFailed()) {
                                return 0;
                            }
                        }
                    }
                    return -1;

                }
            });
            // Takes 10 most urgent words
            List<QuizWord> playList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                playList.add(workingList.get(i));
            }
            // Shuffles
            Collections.shuffle(playList);
            return playList;
        }
    }
}

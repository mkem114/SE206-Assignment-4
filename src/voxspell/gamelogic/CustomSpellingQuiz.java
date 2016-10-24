package voxspell.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <h1>CustomSpellingQuiz</h1> This class is responsible for quizzing the user on a
 * random subset of words in a SpellingLevel that is customised
 *
 * @author mkem114
 * @version 1.0
 * @since 2016-09-18
 */
public class CustomSpellingQuiz extends SpellingQuiz {

    /**
     * Creates a new SpellingQuiz at a given level
     *
     * @param level the level you start at
     */
    public CustomSpellingQuiz(SpellingLevel level) {
        super(level);
    }

    /**
     * Creates a list of words to quiz the user on based on how many times they have been attempted;
     *
     * @return The list of words the user will be quizzed on
     */
    @Override
    protected List<QuizWord> getWords() {
        // Retrieve all the possible words
        List<QuizWord> rawList = level().words();
        // Shuffle list
        Collections.shuffle(rawList);

        // If there is only 20 less words it doesn't matter
        if (rawList.size() <= 20) {
            return rawList;
        } else { // We need to reduce to 20 words
            // Copy words we have to work with
            List<QuizWord> workingList = new ArrayList<>(rawList);
            // Sort words by times attempted
            workingList.sort(new Comparator<QuizWord>() {
                public int compare(QuizWord word1, QuizWord word2) {
                    if (word1.timesAttempted() > word2.timesAttempted()) {
                        return 1;
                    } else if (word1.timesAttempted() == word2.timesAttempted()) {
                        return 0;
                    }
                    return -1;
                }
            });
            // Creates a list of the 20 least attempted
            List<QuizWord> playList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                playList.add(workingList.get(i));
            }
            Collections.shuffle(playList);
            return playList;
        }
    }
}

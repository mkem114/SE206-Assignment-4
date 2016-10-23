package voxspell.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mkem114 on 10/10/16.
 */
public class CustomSpellingQuiz extends SpellingQuiz {

    public CustomSpellingQuiz(SpellingLevel level) {
        super(level);
    }

    @Override
    protected List<QuizWord> getWords() {
        List<QuizWord> rawList = level().words();
        Collections.shuffle(rawList);
        if (rawList.size() <= 20) {
            return rawList;
        } else {
            List<QuizWord> workingList = new ArrayList<>(rawList);
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
            List<QuizWord> playList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                playList.add(workingList.get(i));
            }
            Collections.shuffle(playList);
            return playList;
        }
    }
}

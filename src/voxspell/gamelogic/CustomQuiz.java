package voxspell.gamelogic;

import java.util.List;

/**
 * Created by mkem114 on 10/10/16.
 */
public class CustomQuiz extends SpellingQuiz {
    public CustomQuiz(SpellingLevel level) {
        super(level);
    }

    @Override
    protected List<QuizWord> getWords() {
        return levelWords();
    }
}

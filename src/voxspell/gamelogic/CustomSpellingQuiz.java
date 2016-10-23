package voxspell.gamelogic;

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
        return level().words();
    }
}

package voxspell.gamelogic;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import voxspell.gui.App;
import voxspell.inputoutput.TextToSpeech;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>SpellingQuiz</h1> Represents a spelling quiz with ten words
 *
 * @author mkem114
 * @author tkro003 (minor fixes)
 * @version 1.1
 * @since 2016-09-08
 */
public abstract class SpellingQuiz implements Serializable {
    public static final double GUARANTEED_LEVEL_UP = 1.0;
    public static final double REWARD = 0.9;
    /**
     * Generated serialization ID
     */
    private static final long serialVersionUID = -6330487489105124066L;
    private final List<QuizWord> _quizWords;
    private int _correct;
    private SpellingLevel _level;
    private QuizWord _currentWord;
    private QuizState _state;

    /**
     * Creates a new spelling quiz of the specified level
     *
     * @param level The level to start the quiz o}
     */
    public SpellingQuiz(SpellingLevel level) {
        _correct = 0;
        _level = level;
        _quizWords = getWords();
        _state = QuizState.NEW;
        _currentWord = _quizWords.get(0);
        TextToSpeech.access().speak("Please spell " + _currentWord.word());
    }

    public SpellingLevel level() {
        return _level;
    }

    /**
     * Checks if the user is eligible for a reward
     *
     * @return whether the user is eligible for a reward
     */
    public boolean reward() {
        return _correct / (double) wordNum() > REWARD;
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
     * Generates the word list for the quiz (by going to the subclasses)
     *
     * @return Word list for the quiz
     */
    protected abstract List<QuizWord> getWords();

    /**
     * Checks whether a word has been spelled correctly
     *
     * @param guess the user's input attempt
     * @return The state of the word that was tested
     */
    public QuizState check(String guess) {
        _level.save();
        guess = guess.toLowerCase();
        if (_state == QuizState.NEW) {
            if (guess.equals(_currentWord.word())) {
                // TextToSpeech.access().speak("Correct");
                _correct++;
                _currentWord.hasMastered();
                _state = QuizState.MASTERED;
            } else {
                TextToSpeech.access().speak("Incorrect, try once more... " + _currentWord.word() + ".");
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
        _level.next();
        _level.save();
        if (wordNum() == _quizWords.size()) {
            if (_state == QuizState.FAILED) {
                TextToSpeech.access().speak("Incorrect. Round over.");
            } else {
                TextToSpeech.access().speak("Correct. Round over.");
            }

            return false;
        } else {
            _currentWord = _quizWords.get(wordNum());

            if (_state == QuizState.MASTERED) {
                TextToSpeech.access().speak("Awesome! Now spell " + _currentWord.word());
            } else if (_state == QuizState.FAULTED) {
                TextToSpeech.access().speak("Correct! Now spell " + _currentWord.word());
            } else if (_state == QuizState.FAILED) {
                TextToSpeech.access().speak("Incorrect, better luck next time!");
                giveAnswer();
                TextToSpeech.access().speak("Now spell " + _currentWord.word());
            } else {
                TextToSpeech.access().speak(_currentWord.word());
            }

            _state = QuizState.NEW;
            // TODO fix correct and stuff being said or double up of word to
            // spell
            return true;
            // TODO merge with thing into next word function
        }
    }

    /**
     * Speaks the word that is quizzed again in case the user presses the
     * "replay" button.
     *
     * @return whether the user can replay anymore
     */
    public void replay() {
        TextToSpeech.access().speak(_currentWord.word());
    }

    public int numToQuiz() {
        return _quizWords.size();
    }

    public double experience() {
        return _level.experience();
    }

    public void next() {
        _level.next();
    }

    private void giveAnswer() {
        Alert answer = new Alert(Alert.AlertType.INFORMATION);
        answer.setTitle("You got the word wrong!");
        answer.setHeaderText(null);
        answer.setContentText("The correct answer was: " + _currentWord.word());
        DialogPane pane = answer.getDialogPane();
        pane.getStylesheets().add(App.class.getResource("views/protoTheme.css").toExternalForm());
        answer.showAndWait();
    }

    /**
     * <h1>QuizState</h1> The current state of the quiz
     *
     * @author mkem114
     * @since 2016-09-08
     */
    public enum QuizState {
        //DELETED FINISHED
        FAILED, FAULTED, MASTERED, NEW, SECONDGO
    }
}
package voxspell.gamelogic;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import voxspell.inputoutput.TextToSpeech;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>SpellingLevel</h1> Represents a game level of spelling, they are
 * distinguished by the difficulty of words they contain and their relative
 * numbers (e.g. Level 1)
 *
 * @author mkem114
 * @version 1.0
 * @since 2016-09-18
 */
public class SpellingLevel implements Serializable {
    /**
     * Serializing ID re: SpellingGame
     */
    private static final long serialVersionUID = -9174985624580121154L;
    private String _name;
    private List<QuizWord> _words;
    private List<SpellingQuiz> _quizzes;
    private SpellingGame _game;

    /**
     * Creates a new spelling level based on a level number and the spelling
     * game they belong to
     *
     * @param name Level name
     * @param game Spelling game
     */
    public SpellingLevel(String name, SpellingGame game) {
        _words = new ArrayList<>();
        _quizzes = new ArrayList<>();
        _name = name;
        _game = game;
    }

    /**
     * Name of the level
     *
     * @return Level name
     */
    public String name() {
        return _name;
    }

    /**
     * Adds a word to the level to be quizzed
     *
     * @param word Word to be added
     */
    public void addWord(String word) {
        // Stops duplicate words
        for (QuizWord search : _words) {
            if (search.word().equals(word)) {
                return;
            }
        }
        // Adds word
        QuizWord wordToAdd = new QuizWord(word);
        _words.add(wordToAdd);
    }

    /**
     * Returns the statistics of the level; each time a word has been mastered,
     * faulted and failed
     *
     * @return A list of statistics going [word, mastered, faulted, failed]
     */
    public ObservableList<QuizWord> statistics() {
        return new ObservableListWrapper<>(_words);
    }

    /**
     * The overall accuracy as a percentage the amount of times each word has
     * been mastered compared to the overall attempts
     *
     * @return Level accuracy as a percentage
     */
    public double accuracy() {
        // Counts mastered and failed
        int correct = 0;
        int attempted = 0;
        for (QuizWord word : _words) {
            correct += word.timesMastered();
            attempted += word.timesAttempted();
        }

        //Calculates percentage
        correct *= 100;
        if (attempted == 0) {
            return 0;
        }
        return (correct / attempted);
    }

    /**
     * Starts a new spelling quiz on this level
     *
     * @return The new spelling quiz
     */
    public SpellingQuiz newQuiz() {
        NewSpellingQuiz nsq = new NewSpellingQuiz(this);
        _quizzes.add(nsq);
        return nsq;
    }

    /**
     * Starts a new custom spelling quiz on this level
     *
     * @return The new spelling quiz
     */
    public SpellingQuiz customQuiz() {
        SpellingQuiz csq = new CustomSpellingQuiz(this);
        _quizzes.add(csq);
        return csq;
    }

    /**
     * Determines the experience the player has in this level by calculating as a decimal how many words in this level
     * are mastered at least once
     *
     * @return Value between 0 and 1 as decimal fraction of experience
     */
    public double experience() {
        int mastered = 0;
        for (QuizWord word : _words) {
            if (word.timesMastered() > 0) {
                mastered++;
            }
        }
        if (mastered == _words.size()) {
            return 1;
        }
        return mastered / (double) _words.size();
    }

    /**
     * Returns list of words in this level
     *
     * @return List of level's words
     */
    protected List<QuizWord> words() {
        return _words;
    }

    /**
     * Attempts to move the player to the next level
     */
    public void next() {
        _game.levelUp();
    }

    /**
     * Save's the player's game
     */
    protected void save() {
        _game.save();
    }

    /**
     * Preview a random word in this level by speaking it
     */
    protected void preview() {
        TextToSpeech.access().speak(_words.get(new NumGenerator().randomInt(0, _words.size() - 1)).word());
    }
}
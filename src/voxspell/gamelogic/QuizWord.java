package voxspell.gamelogic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * <h1>QuizWord</h1> Represents a word to be quizzed
 *
 * @author mkem114
 * @version 1.1
 * @since 2016-08-22
 */
public class QuizWord implements Serializable {
    /**
     * Generated serialisation ID re:SpellingGame
     */
    private static final long serialVersionUID = 8661649014339280467L;
    private String _word;
    private int _mastered;
    private int _faulted;
    private int _failed;

    /**
     * Creates a new quiz word based on a word by assuming it has no game
     * history
     *
     * @param word Word to be quizzed
     */
    public QuizWord(String word) {
        this(word, 0, 0, 0);
    }

    /**
     * Creates a new quiz word based on the word and it's game history
     *
     * @param word     The actual word
     * @param mastered How many times it has been gotten correct
     * @param faulted  How many times it was correct on the second go
     * @param failed   How many times it was flat out wrong
     */
    public QuizWord(String word, int mastered, int faulted, int failed) {
        _word = word;
        _mastered = mastered;
        _faulted = faulted;
        _failed = failed;
    }

    /**
     * Prints a description of the quiz word object
     */
    @Override
    public String toString() {
        if (_word == null) {
            return "Undefined Word";
        } else {
            return _word + "\t" + " mastered:" + _mastered + " faulted:" + _faulted + " failed:" + _failed;
        }
    }

    /**
     * Checks another quizword is the same word and statistics
     */
    public boolean equals(Object o) {
        try {
            QuizWord qw = (QuizWord) (o);
            if (_word.equals(qw.word()) && _mastered == qw.timesMastered() && _faulted == qw.timesFaulted()
                    && _failed == qw.timesFailed()) {
                return true;
            }
        } catch (ClassCastException e) {
            return false;
        }
        return false;
    }

    /**
     * The word that is being quizzed
     *
     * @return word
     */
    public String word() {
        return _word;
    }

    /**
     * How many time the word was guessed correct on the first attempt
     *
     * @return Times mastered
     */
    public int timesMastered() {
        return _mastered;
    }

    /**
     * The word has been guessed correctly on the first go
     */
    public void hasMastered() {
        _mastered++;
    }

    /**
     * How many times the word has been spelt correctly the second time by the
     * user
     *
     * @return Times faulted
     */
    public int timesFaulted() {
        return _faulted;
    }

    /**
     * The word was spelt correctly the second time by the user
     */
    public void hasFaulted() {
        _faulted++;
    }

    /**
     * How many times the word has been flat out failed by the player
     *
     * @return Times failed
     */
    public int timesFailed() {
        return _failed;
    }

    /**
     * The word has been flat out failed by the player
     */
    public void hasFailed() {
        _failed++;
    }

    /**
     * How many times the word has been attempted
     *
     * @return Times attempted
     */
    public int timesAttempted() {
        return _mastered + _faulted + _failed;
    }

    /**
     * Returns the word as a String property
     *
     * @return the word
     */
    public StringProperty wordProperty() {
        return new SimpleStringProperty(word());
    }

    /**
     * Returns the amount of times the word is mastered  as a String property
     *
     * @return Times word's been mastered
     */
    public StringProperty masteredProperty() {
        return new SimpleStringProperty(_mastered + "");
    }

    /**
     * Returns the amount of times the word has been faulted as a String property
     *
     * @return Times the word's been faulted
     */
    public StringProperty faultedProperty() {
        return new SimpleStringProperty(_faulted + "");
    }

    /**
     * Returns the amount of times the word has been failed as a String property
     *
     * @return Times the word's been failed
     */
    public StringProperty failedProperty() {
        return new SimpleStringProperty(_failed + "");
    }
}

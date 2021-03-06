package voxspell.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.SpellingQuiz;
import voxspell.gamelogic.SpellingQuiz.QuizState;
import voxspell.gui.App;

import java.io.IOException;

/**
 * <h1>voxspell.gui.controllers.GameController</h1> Controller class responsible for the spelling quiz
 * game/
 *
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 */
public class CustomGameController {

    private SpellingQuiz _quiz;

    @FXML
    private Label currentLevelLabel;

    @FXML
    private ProgressBar progress;

    @FXML
    private Label currentProgressLabel;

    @FXML
    private Label correctPercentageLabel;

    @FXML
    private Label allTimeCorrectPercentageLabel;

    @FXML
    private TextField attemptInput;

    @FXML
    private Button replayBtn;

    @FXML
    private Button submitBtn;

    @FXML
    private Button backBtn;

    /**
     * Returns the user back to the Main Menu
     *
     * @param event Pressing the back button
     */
    @FXML
    void backToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/MainMenu.fxml"));
            BorderPane menu = loader.load();

            BorderPane border = App.inst().root();
            border.setCenter(menu);
            App.inst().saveGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Code to execute when user presses the Check button (Executes code that
     * checks if the input is correct)
     *
     * @param event Fired when ENTER key or button is pressed
     */
    @FXML
    void onSubmit(ActionEvent event) {
        // Avoid case errors
        String guess = attemptInput.getText().toLowerCase();
        // Remove non-alphabet
        guess = guess.replaceAll("[^a-z]", "");
        // Account for null input
        if (!guess.equals("")) {
            // Process guess
            updateProgress();
            if (_quiz.check(guess) != QuizState.SECONDGO) {
                if (!_quiz.loadNext()) {
                    goLevelComplete();
                }
            }
            attemptInput.clear();
            updateProgress();
        } else {
            attemptInput.clear();
        }
    }

    /**
     * Code to execute on press of Replay button. Only allow one replay per word
     *
     * @param event When "Replay" button is pressed
     */
    @FXML
    void requestReplay(ActionEvent event) {
        _quiz.replay();
    }

    /**
     * Updates the labels in the scene
     */
    void updateProgress() {
        // Updates word progression
        currentProgressLabel.setText(_quiz.wordNum() - 1 + "/" + _quiz.numToQuiz());
        // Update quiz accuracy
        correctPercentageLabel.setText((int) _quiz.quizAccuracy() + "%");
        // Updated level accuracy
        allTimeCorrectPercentageLabel.setText((int) _quiz.level().accuracy() + "%");
        // Update quiz progress bar
        progress.setProgress(_quiz.wordNum() / (double) _quiz.numToQuiz());
    }

    /**
     * Updates the level label in the scene Used in initialization
     */
    void updateLevel() {
        currentLevelLabel.setText(_quiz.level().name());
    }

    /**
     * Sets up the game with a new spelling quiz object
     */
    public void setGame() {
        _quiz = App.inst().game().customQuiz();
        updateProgress();
        updateLevel();
    }

    /**
     * Executed when the the quiz has been completed The user is sent to a
     * level-complete screen with final stats
     */
    public void goLevelComplete() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/CustomLevelComplete.fxml"));
            AnchorPane lvlcomplete = loader.load();

            CustomLevelCompleteController controller = loader.getController();
            controller.setComplete(_quiz);

            BorderPane border = App.inst().root();
            border.setCenter(lvlcomplete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

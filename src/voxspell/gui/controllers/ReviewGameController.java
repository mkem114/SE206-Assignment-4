package voxspell.gui.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.SpellingQuiz;
import voxspell.gamelogic.SpellingQuiz.QuizState;
import voxspell.gui.App;

/**
 * <h1>voxspell.gui.controllers.ReviewGameController</h1> Controller class responsible for the review
 * game mode.
 * 
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 *
 */
public class ReviewGameController {

	private SpellingQuiz _quiz;

	@FXML
	private Label currentLevelLabel;

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

	/**
	 * Action to take when submit button is pressed which checks whether the
	 * word is correct
	 * 
	 * @param event
	 *            On pressing Submit
	 */
	@FXML
	void onSubmit(ActionEvent event) {
		String guess = attemptInput.getText().toLowerCase();
		if (!guess.equals("")) {
			updateProgress();
			if (_quiz.check(guess) != QuizState.SECONDGO) {
				if (!_quiz.loadNext()) {
					backToMenu();
				}
			}
			if (!_quiz.replayDisabled()) {
				replayBtn.setDisable(false);
			}
			attemptInput.clear();
			updateProgress();
		}
	}

	/**
	 * Allows one (1) replay of the voice if the user did not hear it the first
	 * time
	 * 
	 * @param event
	 *            On pressing Replay
	 */
	@FXML
	void requestReplay(ActionEvent event) {
		if (!_quiz.replay()) {
			replayBtn.setDisable(true);
		}
		if (_quiz.replayDisabled()) { // check after first click
			replayBtn.setDisable(true);
		}
	}

	/**
	 * Updates the current level label (initialization purposes)
	 */
	void updateLevel() {
		currentLevelLabel.setText(_quiz.levelNum() + "");
	}

	/**
	 * Updates the labels that show user progress/stats
	 */
	void updateProgress() {
		currentProgressLabel.setText((int) _quiz.wordNum() - 1 + "/" + _quiz.actualSize());
		correctPercentageLabel.setText((int) _quiz.quizAccuracy() + "%");
		allTimeCorrectPercentageLabel.setText((int) _quiz.levelAccuracy() + "%");
	}

	/**
	 * Sets up a review SpellingQuiz object when this scene is loaded
	 */
	public void setGame() {
		_quiz = App.inst().game().reviewQuiz();
		updateProgress();
		updateLevel();
	}

	/**
	 * Return to the main menu once the Review is complete (Design decision: no
	 * need to show stats in Review as we're focusing on just getting the words
	 * right)
	 */
	public void backToMenu() {
		// for review mode we don't need to show a level end screen
		// so we go back to menu
		try {
			App.inst().saveGame();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("views/MainMenu.fxml"));
			BorderPane menu = loader.load();

			BorderPane border = App.inst().root();
			border.setCenter(menu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

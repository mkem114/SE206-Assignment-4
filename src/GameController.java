import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.SpellingQuiz;
import voxspell.gamelogic.SpellingQuiz.QuizState;

import java.io.IOException;

/**
 * <h1>GameController</h1> Controller class responsible for the spelling quiz
 * game/
 * 
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 *
 */
public class GameController {

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
	 * Code to execute when user presses the Check button (Executes code that
	 * checks if the input is correct)
	 * 
	 * @param event
	 *            Fired when ENTER key or button is pressed
	 */
	@FXML
	void onSubmit(ActionEvent event) {
		String guess = attemptInput.getText().toLowerCase();
		if (!guess.equals("")) {

			updateProgress();
			if (_quiz.check(guess) != QuizState.SECONDGO) {
				if (!_quiz.loadNext()) {
					goLevelComplete();
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
	 * Code to execute on press of Replay button. Only allow one replay per word
	 * 
	 * @param event
	 *            When "Replay" button is pressed
	 */
	@FXML
	void requestReplay(ActionEvent event) {
		if (!_quiz.replay()) {
			replayBtn.setDisable(true);
		}
		if (_quiz.replayDisabled()) { // check after first press
			replayBtn.setDisable(true);
		}
	}

	/**
	 * Updates the labels in the scene
	 */
	void updateProgress() {
		currentProgressLabel.setText((int) _quiz.wordNum() - 1 + "/" + _quiz.numWordsToQuiz);
		correctPercentageLabel.setText((int) _quiz.quizAccuracy() + "%");
		allTimeCorrectPercentageLabel.setText((int) _quiz.levelAccuracy() + "%");
	}

	/**
	 * Updates the level label in the scene Used in initialization
	 */
	void updateLevel() {
		currentLevelLabel.setText(_quiz.levelNum() + "");
	}

	/**
	 * Sets up the game with a new spelling quiz object
	 */
	public void setGame() {
		_quiz = App.inst().game().newQuiz();
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
			loader.setLocation(App.class.getResource("voxspell/gui/LevelComplete.fxml"));
			AnchorPane lvlcomplete = (AnchorPane) loader.load();

			LevelCompleteController controller = loader.<LevelCompleteController>getController();
			controller.setComplete(_quiz);

			BorderPane border = App.inst().root();
			border.setCenter(lvlcomplete);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

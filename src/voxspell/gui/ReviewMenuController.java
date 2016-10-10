package voxspell.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.SpellingGame;

/**
 * <h1>voxspell.gui.ReviewMenuController</h1> Controller class with level selection tools
 * just for Review Mode
 * 
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 *
 */
public class ReviewMenuController implements Initializable {

	// Code for ComboBox setup
	private ObservableList<String> _ob;
	private ArrayList<Integer> _levels = new ArrayList<>();
	private ArrayList<String> _levelsReadable = new ArrayList<>();

	@FXML
	private Button backBtn;

	@FXML
	private ComboBox<String> reviewLevelSelect;

	@FXML
	private Button startReviewBtn;

	/**
	 * Start a review game based on the selected level
	 * 
	 * @param event
	 *            On press of Select button
	 */
	@FXML
	void goReview(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("ReviewGame.fxml"));
			AnchorPane game = loader.load();
			ReviewGameController controller = loader.<ReviewGameController>getController();
			controller.setGame();

			BorderPane border = App.inst().root();
			border.setCenter(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private Label noWordsLabel;

	/**
	 * Checks each level to see if there are words that need to be reviewed, and
	 * add them to the ComboBox arrays
	 */
	private void populate() {
		for (int i = 1; i <= SpellingGame.numLevels; i++) {
			if (App.inst().game().isReviewable(i)) {
				_levels.add(i);
				_levelsReadable.add("Level " + i);
			}
		}
		_ob = FXCollections.observableArrayList(_levelsReadable);
	}

	/**
	 * Return to the main menu
	 * 
	 * @param event
	 *            On pressing of Back button
	 */
	@FXML
	void goBack(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("MainMenu.fxml"));
			BorderPane menu = (BorderPane) loader.load();

			BorderPane border = App.inst().root();
			border.setCenter(menu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Code to run when this scene is loaded Sets up the ComboBox, or notify the
	 * user that there are no words that need review
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populate();

		if (_levels.size() == 0) {
			reviewLevelSelect.setVisible(false);
			startReviewBtn.setVisible(false);
		} else {
			noWordsLabel.setVisible(false);
			reviewLevelSelect.getItems().addAll(_ob);
			reviewLevelSelect.getSelectionModel().select(0); // currently
																// defaults to
																// first one
		}

	}
}

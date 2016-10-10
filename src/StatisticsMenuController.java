import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.SpellingGame;

/**
 * <h1>StatisticsMenuController</h1> Controller class responsible for displaying
 * statistics
 * 
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 *
 */
public class StatisticsMenuController implements Initializable {

	// Human readable level dropdown
	private ObservableList<String> _ob;
	private ArrayList<Integer> _levels = new ArrayList<>();
	private ArrayList<String> _levelsReadable = new ArrayList<>();

	@FXML
	private Button backBtn;
	@FXML
	private TextArea statsArea;

	@FXML
	private ComboBox<String> statsLevelPicker;

	@FXML
	private Label accuracyLevel;

	/**
	 * Return to main menu
	 * 
	 * @param event
	 *            On pressing of the Back button
	 */
	@FXML
	void goBack(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("voxspell/gui/MainMenu.fxml"));
			BorderPane menu = (BorderPane) loader.load();

			BorderPane border = App.inst().root();
			border.setCenter(menu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the text area with all the words in that level (ordered based on
	 * wordlist itself) and provides correctness percentage (mastered / all
	 * attempts), as well as the number of times mastered, faulted, and failed.
	 * 
	 * @param level
	 *            The level we want to know stats about
	 */
	public void updateTextArea(int level) {
		statsArea.clear();
		ArrayList<ArrayList<String>> stats = App.inst().game().getLevel(level).statistics();
		Collections.sort(stats, new Comparator<ArrayList<String>>() {
			@Override
			public int compare(ArrayList<String> w1, ArrayList<String> w2) {
				return w1.get(0).compareTo(w2.get(0));
			}
		});

		for (ArrayList<String> wordStat : stats) {
			String word = wordStat.get(0);
			int correct = Integer.parseInt(wordStat.get(1));
			int fault = Integer.parseInt(wordStat.get(2));
			int fail = Integer.parseInt(wordStat.get(3));
			int attempt = correct + fault + fail;
			int accuracy; // accuracy was added in towards the end so there
							// should be better way for this
			if (attempt == 0) {
				accuracy = 0;
			} else {
				accuracy = (int) (((double) correct / (double) attempt) * 100);
			}
			statsArea.appendText(word + " (" + accuracy + "% correct)\nmastered: " + correct + " faulted: " + fault
					+ " failed: " + fail + "\n\n");
		}
		statsArea.positionCaret(0);
	}

	/**
	 * Initializing code to show Level 1 when user opens the Stats menu
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populate();
		statsLevelPicker.getItems().addAll(_ob);
		statsLevelPicker.getSelectionModel().select(0);
		accuracyLevel.setText(App.inst().game().accuracy(1) + "%");
		updateTextArea(1);

	}

	/**
	 * Fills up the ComboBox with the different levels
	 */
	private void populate() {
		for (int i = 1; i <= SpellingGame.numLevels; i++) {
			_levels.add(i);
			_levelsReadable.add("Level " + i);
		}
		_ob = FXCollections.observableArrayList(_levelsReadable);
	}

	/**
	 * Ensures that the text area is updated when the user changes the level
	 * from dropdown
	 */
	public void changeLevel() {
		int selectedLevel = _levels.get(statsLevelPicker.getSelectionModel().getSelectedIndex());
		accuracyLevel.setText(App.inst().game().accuracy(selectedLevel) + "%");
		updateTextArea(selectedLevel);
	}

}

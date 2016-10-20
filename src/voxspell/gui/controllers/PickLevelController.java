package voxspell.gui.controllers;

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
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.SpellingGame;
import voxspell.gui.App;

/**
 * <h1>voxspell.gui.controllers.PickLevelController</h1> Controller class responsible for level
 * selection.
 * 
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 *
 */
public class PickLevelController implements Initializable {

	/**
	 * List of levels
	 */
	private ObservableList<String> _ob;

	/**
	 * List of levels as "integers"
	 */
	private ArrayList<Integer> _levels = new ArrayList<>();

	/**
	 * List of levels in human readable form (i.e. 'Level 1, 2, 3, ...')
	 */
	private ArrayList<String> _levelsReadable = new ArrayList<>();

	@FXML
	private Button playBtn;

	@FXML
	private ComboBox<String> levelPicker;

	/**
	 * Start the game at a certain level but go to the Main Menu.
	 * 
	 * @param evt
	 *            Pressing the Select button
	 */
	@FXML
	public void goPlay(ActionEvent evt) {
		int selectedLevel = _levels.get(levelPicker.getSelectionModel().getSelectedIndex());
		App.inst().chooseLevel(selectedLevel); // get the level chosen in the
												// ComboBox

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("views/MainMenu.fxml"));
			BorderPane menu = loader.load();

			BorderPane border = App.inst().root();
			border.setCenter(menu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populate the ComboBox with the levels
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		populate();
		levelPicker.getItems().addAll(_ob);
		levelPicker.getSelectionModel().select(0); // currently defaults to
													// first one

	}

	/**
	 * Sets up the arrays needed for the readable and integer forms for the
	 * ComboBox
	 */
	private void populate() {
		// populates the combobox
		for (int i = 1; i <= SpellingGame.numLevels; i++) {
			_levels.add(i);
			_levelsReadable.add("Level " + i);
		}
		// Display the readable form in the Combo Box
		_ob = FXCollections.observableArrayList(_levelsReadable);
	}
}

package voxspell.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import voxspell.gui.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
	private List<String> _levels = new ArrayList<>();

	@FXML
	private Button playBtn;

	@FXML
	private Button previewBtn;

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
		String selectedLevel = levelPicker.getSelectionModel().getSelectedItem();
		// get the level chosen in the ComboBox
		App.inst().chooseLevel(selectedLevel);

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

	@FXML
	private void goPreview(ActionEvent event) {
		App.inst().game().previewLevel(levelPicker.getSelectionModel().getSelectedItem());
	}

	/**
	 * Populate the ComboBox with the levels
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		refresh(new ActionEvent());
	}

	@FXML
	public void refresh(Event evt) {
		if (levelPicker.getItems().size() == 0) {
			if (!(App.inst() == null || App.inst().game() == null || App.inst().game().levels() == null)) {
				// populates the combobox
				_levels = App.inst().game().levels();
				// Display the readable form in the Combo Box
				_ob = FXCollections.observableArrayList(_levels);
				levelPicker.getItems().addAll(_ob);
				levelPicker.getSelectionModel().selectFirst(); // currently defaults to
				// first one
			}
		}
	}
}

package voxspell.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import voxspell.gui.App;
import voxspell.inputoutput.WordListReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>voxspell.gui.controllers.PickLevelController</h1> Controller class responsible for level
 * selection.
 * 
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 *
 */
public class PickCustomLevelController implements Initializable {

	@FXML
	private Button playBtn;

	@FXML
	private Label pickedFile;

	@FXML
	private Button openBtn;

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
		App.inst().game().customLevel(levelPicker.getSelectionModel().getSelectedItem());

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("views/CustomGame.fxml"));
			AnchorPane game = loader.load();
			CustomGameController controller = loader.getController();
			controller.setGame();

			BorderPane border = App.inst().root();
			border.setCenter(game);
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
	}

	private void populate(){
		levelPicker.getItems().addAll(App.inst().game().customLevels());
		levelPicker.getItems().addAll(App.inst().game().levels());
		levelPicker.getSelectionModel().selectFirst();
	}

	@FXML
	public void goOpen(ActionEvent evt) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Wordlist File");
		File wordlist = fileChooser.showOpenDialog(new Stage());
		if (wordlist != null) {
			WordListReader reader = new WordListReader(wordlist);
			reader.addObserver(App.inst().game());
			populate();
		}
	}
}

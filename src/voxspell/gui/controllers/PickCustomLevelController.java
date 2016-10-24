package voxspell.gui.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * <h1>voxspell.gui.controllers.PickLevelController</h1> Controller class responsible for level
 * selection.
 *
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 */
public class PickCustomLevelController implements Initializable {

    @FXML
    private Button playBtn;

    @FXML
    private Label pickedFile;

    @FXML
    private Button openBtn;

    @FXML
    private Button previewBtn;

    @FXML
    private ComboBox<String> levelPicker;
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
     * Start the game at a certain level but go to the Main Menu.
     *
     * @param evt Pressing the Select button
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

    /**
     * Loads the level combobox with all the levels
     */
    private void populate() {
        // Clears box
        levelPicker.getItems().clear();
        // Add custom levels
        levelPicker.getItems().addAll(App.inst().game().customLevels());
        // Add normal levels
        levelPicker.getItems().addAll(App.inst().game().levels());
        // Pick the first
        levelPicker.getSelectionModel().selectFirst();
    }

    /**
     * Adds any levels to the combobox that aren't already in it
     *
     * @param evt -
     */
    @FXML
    private void refresh(Event evt) {
        // Creates a list of all levels
        ArrayList<String> allLevels = new ArrayList<>(App.inst().game().customLevels());
        allLevels.addAll(App.inst().game().levels());
        // If it's not in the combobox then add it
        for (String newLevel : allLevels) {
            if (!levelPicker.getItems().contains(newLevel)) {
                levelPicker.getItems().add(newLevel);
            }
        }
    }

    /**
     * Gives the user a file chooser then hands off loading the wordlist file selected
     *
     * @param evt -
     */
    @FXML
    public void goOpen(ActionEvent evt) {
        // Make file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Wordlist File");
        File wordlist = fileChooser.showOpenDialog(new Stage()); // Give it a new window
        // If they selected something
        if (wordlist != null) {
            // Read the list
            WordListReader reader = new WordListReader(wordlist);
            reader.addObserver(App.inst().game());
            try {
                reader.readWords();
            } catch (Exception e) {
                e.printStackTrace();
                //TODO
            }
        }
    }

    /**
     * Requests a level be previewed
     *
     * @param event
     */
    @FXML
    private void goPreview(ActionEvent event) {
        App.inst().game().previewLevel(levelPicker.getSelectionModel().getSelectedItem());
    }
}

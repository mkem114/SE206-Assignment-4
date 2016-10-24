package voxspell.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import voxspell.gui.App;
import voxspell.inputoutput.TextToSpeech;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>voxspell.gui.controllers.OptionsMenuController</h1> Controller class responsible for changing the
 * TTS voices and resetting the game
 *
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 */
public class OptionsMenuController implements Initializable {

    /**
     * Creates a list of all the available voices
     */
    private ObservableList<String> ob = FXCollections.observableArrayList(TextToSpeech.access().voices());

    @FXML
    private Button backBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private ComboBox<String> ttsSelect;

    @FXML
    private Button previewBtn;

    /**
     * Return to the main menu
     *
     * @param evt On pressing back button
     */
    @FXML
    public void goBack(ActionEvent evt) {
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
     * Overrides the initialize method for an initializable controller Add all
     * the voices to a ComboBox and select the currently active voice
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Puts the voice names in the combobox
        ttsSelect.getItems().addAll(ob);
        //Aligns the combobox selection with the current voice selection
        ttsSelect.getSelectionModel().select(TextToSpeech.access().selectedVoice());
    }

    /**
     * Updates the voice to the newly selected one
     */
    public void changeVoice() {
        TextToSpeech.access().chooseVoice(ttsSelect.getSelectionModel().getSelectedItem());
    }

    /**
     * Resets the game
     *
     * @param evt On pressing "Reset" button
     */
    @FXML
    public void resetGame(ActionEvent evt) {
        App.inst().resetGame();
    }

    /**
     * Allows preview of the voice
     */
    public void previewVoice() {
        TextToSpeech.access().introduceVoice();
    }

}

package voxspell.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import voxspell.gui.App;
import voxspell.inputoutput.BackgroundMusic;

import java.io.IOException;

/**
 * <h1>voxspell.gui.controllers.MainMenuController</h1> Controller class responsible to be the "home
 * screen" the user sees
 *
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 */
public class MainMenuController {

    private BackgroundMusic _music = new BackgroundMusic();

    @FXML
    private Button playBtn;

    @FXML
    private Button customBtn;

    @FXML
    private Button statisticsBtn;

    @FXML
    private Button optionsBtn;

    @FXML
    private Button musicBtn;

    /**
     * Play/pauses the background music
     *
     * @param event -
     */
    @FXML
    public void musicSwitch(ActionEvent event) {
        _music.toggle();
    }

    /**
     * Press to start a quiz
     *
     * @param evt On pressing the Play button
     */
    @FXML
    public void goPlay(ActionEvent evt) {
        try {
            _music.pause();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/Game.fxml"));
            AnchorPane game = loader.load();
            GameController controller = loader.getController();
            controller.setGame();

            BorderPane border = App.inst().root();
            border.setCenter(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Press to go to the options menu
     *
     * @param evt On pressing the Options button
     */
    @FXML
    public void goOptions(ActionEvent evt) {
        try {
            _music.pause();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/OptionsMenu.fxml"));
            AnchorPane options = loader.load();

            BorderPane border = App.inst().root();
            border.setCenter(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goCustom(ActionEvent evt) {
        try {
            _music.pause();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/PickCustomLevel.fxml"));
            AnchorPane review = loader.load();

            BorderPane border = App.inst().root();
            border.setCenter(review);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go to the statistics menu
     *
     * @param evt On press of the Statistics button
     */
    @FXML
    public void goStatistics(ActionEvent evt) {
        try {
            _music.pause();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/StatisticsMenu.fxml"));
            AnchorPane stats = loader.load();

            BorderPane border = App.inst().root();
            border.setCenter(stats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package voxspell.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.SpellingQuiz;
import voxspell.gui.App;

import java.io.IOException;

/**
 * <h1>voxspell.gui.controllers.LevelCompleteController</h1> Controller class responsible for end-of-game
 * screen with statistics.
 *
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 */
public class LevelCompleteController {
    private SpellingQuiz _quiz;

    @FXML
    private Button backBtn;

    @FXML
    private Button continueBtn;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label allTimeLabel;

    @FXML
    private Label qualifyLabel;

    @FXML
    private Button rewardBtn;

    /**
     * Code that executes on initialization Determines if user qualifies for a
     * reward or is able to go to the next level
     *
     * @param quiz Spelling quiz object that has been completed
     */
    public void setComplete(SpellingQuiz quiz) {
        _quiz = quiz;
        if (!_quiz.reward()) {
            // User doesn't get a video reward button
            qualifyLabel.setText("Unfortunately you don't qualify for a video reward.");
            rewardBtn.setVisible(false);
            continueBtn.setVisible(true);
        } else {
            qualifyLabel.setText("You qualify for a video reward!");
            rewardBtn.setVisible(true);
            continueBtn.setVisible(true);
        }
        // Updates quiz and level accuracy
        scoreLabel.setText(_quiz.numCorrect() + "/" + _quiz.wordNum());
        allTimeLabel.setText(_quiz.level().accuracy() + "%");
    }

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
     * Starts a new SpellingQuiz for the next level and prepares to change to
     * the Game scene.
     *
     * @param event On press of the Continue button
     * @throws IOException If the file doesn't exist
     */
    @FXML
    void nextLevel(ActionEvent event) throws IOException {

        try {
            _quiz.next();
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
     * Opens the Video Player for the reward Passes the quiz along to ensure
     * return to the right complete screen
     *
     * @param event On press of the video button
     */
    @FXML
    void showReward(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/VideoPlayer.fxml"));
            AnchorPane vid = loader.load();
            VideoPlayerController controller = loader.getController();
            controller.passQuiz(_quiz);

            BorderPane border = App.inst().root();
            border.setCenter(vid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package voxspell.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import voxspell.gamelogic.SpellingQuiz;
import voxspell.gui.App;
import voxspell.resources.ResourceLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>voxspell.gui.controllers.VideoPlayerController</h1> Controller class responsible for showing the
 * reward video at the end of a quiz
 *
 * @author tkro003
 */
public class VideoPlayerController implements Initializable {
    // Loads reward video
    private Media media = new Media(ResourceLoader.inst().videoReward());
    // Doesn't start playing
    private boolean isPlaying = false;
    // Quiz to return to when done
    private SpellingQuiz _quiz = null;

    @FXML
    private Button backBtn;

    @FXML
    private Button playPauseBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private Button rewindBtn;

    @FXML
    private Button forwardBtn;

    @FXML
    private MediaView mediaView;

    @FXML
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    /**
     * Alternate between play or pause, if the video is paused or is playing.
     */
    public void playOrPause() {
        if (isPlaying) {
            mediaPlayer.pause();
            playPauseBtn.setText("Play");
            isPlaying = !isPlaying;
        } else {
            mediaPlayer.play();
            playPauseBtn.setText("Pause");
            isPlaying = !isPlaying;
        }
    }

    /**
     * Stops the video and make sure the play/pause button is on "play"
     */
    public void stop() {
        mediaPlayer.stop();
        isPlaying = false;
        playPauseBtn.setText("Play"); // Whenever a video is stopped we want the
        // Play button to show
    }

    /**
     * Seek to 5 seconds ago
     */
    public void rewind() {
        // TODO maybe instead of rewind 5 we give the user a choice...
        Duration now = mediaPlayer.getCurrentTime();
        Duration earlier = now.subtract(new Duration(5000)); // rewind by 5000
        // seconds
        mediaPlayer.seek(earlier);
    }

    /**
     * Seek to 5 seconds after current time
     */
    public void fastForward() {
        Duration now = mediaPlayer.getCurrentTime();
        Duration later = now.add(new Duration(5000)); // rewind by 5000 seconds
        mediaPlayer.seek(later);

    }

    /**
     * Go back to the previous (level complete) screen, but ensure the video is
     * closed before doing so
     */
    @FXML
    public void goBack() {
        stop(); // prevents buffer from staying open or whatever
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/LevelComplete.fxml"));
            AnchorPane lvlcomplete = loader.load();
            // If there's a quiz it's done
            if (_quiz != null) {
                LevelCompleteController controller = loader.getController();
                controller.setComplete(_quiz);
            }
            BorderPane border = App.inst().root();
            border.setCenter(lvlcomplete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialization code to set the media player of the MediaView to the one
     * that has the chosen media. (Allows for further extension if we have more
     * media choices)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediaPlayer.setOnEndOfMedia(new Runnable() {

            @Override
            public void run() {
                mediaPlayer.stop();
                mediaPlayer.seek(Duration.ZERO);
                isPlaying = false;
                playPauseBtn.setText("Play");

            }

        });
        mediaView.setMediaPlayer(mediaPlayer);
    }

    /**
     * Sets up a quiz object so the program remembers how it got to the video
     * view.
     *
     * @param quiz The last completed quiz
     */
    public void passQuiz(SpellingQuiz quiz) {
        _quiz = quiz;

    }
}

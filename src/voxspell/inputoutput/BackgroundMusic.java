package voxspell.inputoutput;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.concurrent.Task;

/**
 * Created by mkem114 on 10/10/16.
 */
public class BackgroundMusic {
    public static final String[] _playlist = {"music.wav"};
    private static BackgroundMusic _instance = null;
    private int _currentIndex;
    private Media _currentSong;
    private MediaPlayer _mediaPlayer;

    public static BackgroundMusic inst() {
        if (_instance == null) {
            _instance = new BackgroundMusic();
        }
        return _instance;
    }

    public BackgroundMusic() {
        _currentIndex = 0;
        startNewPlayer();
    }

    public void pause() {
        _mediaPlayer.pause();
    }

    public void resume() {
        _mediaPlayer.play();
    }

    private void startNewPlayer() {
        try {
            _currentSong = new Media((new File("voxspell/music/"+_playlist[_currentIndex])).toURI().toString());
            _mediaPlayer = new MediaPlayer(_currentSong);
            _mediaPlayer.setVolume(0.1);
            _mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            _mediaPlayer.play();
        } catch (NullPointerException e) {
            //TODO
        }
    }
}

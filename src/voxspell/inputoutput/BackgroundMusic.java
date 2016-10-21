package voxspell.inputoutput;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by mkem114 on 10/10/16.
 */
public class BackgroundMusic {
    public static final String[] _playlist = {"music.wav"};
    public static final String _musicLocation = "./VOXSpell/music/";
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
            System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            _currentSong = new Media((new File(_musicLocation+_playlist[_currentIndex])).toURI().toString());
            _mediaPlayer = new MediaPlayer(_currentSong);
            //TODO Volume to 0.1
            _mediaPlayer.setVolume(0.1);
            _mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            _mediaPlayer.play();
        } catch (Exception e) {
            //TODO
        }
    }
}

package voxspell.inputoutput;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by mkem114 on 10/10/16.
 */
public class BackgroundMusic {
    public static final double VOLUME_LEVEL = 0.1;
    public static final String[] PLAYLIST = {"music.wav"};
    public static final String MUSIC_LOCATION = "./VOXSpell/music/";
    private static BackgroundMusic _instance = null;
    private int _currentIndex;
    private Media _currentSong;
    private MediaPlayer _mediaPlayer;

    public BackgroundMusic() {
        _currentIndex = 0;
        startNewPlayer();
    }

    public static BackgroundMusic inst() {
        if (_instance == null) {
            _instance = new BackgroundMusic();
        }
        return _instance;
    }

    public void pause() {
        _mediaPlayer.pause();
    }

    public void resume() {
        _mediaPlayer.play();
    }

    private void startNewPlayer() {
        try {
            //System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            _currentSong = new Media((new File(MUSIC_LOCATION + PLAYLIST[_currentIndex])).toURI().toString());
            _mediaPlayer = new MediaPlayer(_currentSong);
            //TODO Volume to 0.1
            _mediaPlayer.setVolume(VOLUME_LEVEL);
            _mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            _mediaPlayer.play();
        } catch (Exception e) {
            //TODO
        }
    }
}

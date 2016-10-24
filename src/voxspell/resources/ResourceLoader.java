package voxspell.resources;

import javafx.scene.image.Image;

import java.io.File;
import java.io.InputStream;

/**
 * Created by mkem114 on 24/10/16.
 */
public class ResourceLoader {
    public static final String APP_ICON_NAME = "appIcon.png";
    public static final String REWARD__NAME = "./VOXSpell/.reward.mp4";
    public static final String APP_THEME_NAME = "appTheme.css";
    public static final String ICON_LOCATION = "icons/";
    public static final String DEFAULT_WORDLIST_NAME = "defaultWordlist.txt";
    private static ResourceLoader _instance = null;

    public static ResourceLoader inst() {
        if (_instance == null) {
            _instance = new ResourceLoader();
        }
        return _instance;
    }

    public Image appIcon() {
        return new Image(getClass().getResourceAsStream(APP_ICON_NAME));
    }

    public String appTheme() {
        return getClass().getResource(APP_THEME_NAME).toExternalForm();
    }

    public InputStream defaultWordlist() {
        return getClass().getResourceAsStream(DEFAULT_WORDLIST_NAME);
    }

    public String videoReward() {
        return (new File(REWARD__NAME).toURI().toString());
    }

    public InputStream resourceStream(String name) {
        return getClass().getResourceAsStream(name);
    }
}

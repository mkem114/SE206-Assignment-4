package voxspell.resources;

import javafx.scene.image.Image;

import java.io.File;
import java.io.InputStream;

/**
 * ResourceLoader
 * responsible for loading any resources in the resource package
 * Created by mkem114 on 24/10/16.
 */
public class ResourceLoader {
    public static final String APP_ICON_NAME = "appIcon.png";
    public static final String REWARD__NAME = "./VOXSpell/.reward.mp4";
    public static final String APP_THEME_NAME = "appTheme.css";
    public static final String ICON_LOCATION = "icons/";
    public static final String DEFAULT_WORDLIST_NAME = "defaultWordlist.txt";
    private static ResourceLoader _instance = null;

    /**
     * Singleton pattern
     *
     * @return instance
     */
    public static ResourceLoader inst() {
        if (_instance == null) {
            _instance = new ResourceLoader();
        }
        return _instance;
    }

    /**
     * Gets the app icon
     *
     * @return icon
     */
    public Image appIcon() {
        return new Image(getClass().getResourceAsStream(APP_ICON_NAME));
    }

    /**
     * Gets apps theme
     *
     * @return theme
     */
    public String appTheme() {
        return getClass().getResource(APP_THEME_NAME).toExternalForm();
    }

    /**
     * Gets default wordlist
     *
     * @return wordlist
     */
    public InputStream defaultWordlist() {
        return getClass().getResourceAsStream(DEFAULT_WORDLIST_NAME);
    }

    /**
     * gets video reward resource
     *
     * @return video reward
     */
    public String videoReward() {
        return (new File(REWARD__NAME).toURI().toString());
    }

    /**
     * Gets an arbitary resource
     *
     * @param name resource name
     * @return resource
     */
    public InputStream resourceStream(String name) {
        return getClass().getResourceAsStream(name);
    }
}

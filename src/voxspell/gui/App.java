package voxspell.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import voxspell.gamelogic.SpellingGame;
import voxspell.inputoutput.SaveGame;
import voxspell.resources.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

// REFERENCE: The implementation technique was inspired by this website:
// http://javajdk.net/tutorial/multiple-javafx-scenes-sharing-one-menubar/

/**
 * <h1>voxspell.gui.App</h1> This is the entry point to the voxspell program, from this class
 * the instance of the application is made. It represents the instance of the
 * application
 * <p>
 * Initialises the GUI and the model(backend). In addition it sorts out saving
 * and loading the game as a whole on startup
 * <p>
 * This class is a singleton because there should only be one instance per
 * instance of the application
 * 
 * @version 1.0
 * @author tkro003 (GUI)
 * @author mkem114 (Savegame)
 * @since 2016-09-08
 */
public class App extends Application {

	/**
	 * File name of the file with the list of words to load
	 */
	private static App _instance;
	private BorderPane _root = new BorderPane();
	private SaveGame _save;
	private SpellingGame _game;
	private Stage _primaryStage;

	/**
	 * Access the singleton instance
	 * 
	 * @return voxspell.gui.App instance
	 */
	public static App inst() {
		return _instance;
	}

	/**
	 * The entry point of the program
	 *
	 * @param args
	 *            Ignored, whatever arguments the application is started with
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Access to the primary stage
	 *
	 * @return Primary stage
	 */
	public BorderPane root() {
		return _root;
	}

	/**
	 * Access to the game instance
	 *
	 * @return Game instance
	 */
	public SpellingGame game() {
		return _game;
	}

	/**
	 * Initialise the voxspell.gui.App on application start
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		_instance = this;
		this._primaryStage = primaryStage;
		this._primaryStage.setTitle("VOXSpell");
		setLayout();
	}

	/**
	 * Loads the last saved game
	 *
	 * @return If the loading was successful
	 */
	// loads the game
	public boolean loadGame() {
		_save = new SaveGame();
		try {
			_game = _save.load();
			return true;
		} catch (FileNotFoundException e) {
			_game = new SpellingGame();
			_save = new SaveGame(_game);
			_save.save();
			resetGame();
			return false;
		}
	}

	/**
	 * Saves the game to a file
	 */
	// saves the game
	public void saveGame() {
		_save.save();
	}

	/**
	 * Resets the game by deleting the old one and making a new one
	 */
	// resets the game - prompts user to re-select level
	public void resetGame() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("views/PickLevel.fxml"));
			AnchorPane pickLevel;
			pickLevel = loader.load();
			_root.setCenter(pickLevel);
			saveGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new game with a selected starting level
	 *
	 * @param lvl
	 *            The level to start from
	 */
	public void chooseLevel(String lvl) {
		_game = new SpellingGame();
		_game.startingLevel(lvl);
		_save = new SaveGame(_game);
	}

	/**
	 * Starts the GUI
	 */
	public void setLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();

			if (loadGame() && _game != null) { // if there's a save game then go
												// directly to the main menu
				loader.setLocation(getClass().getResource("views/MainMenu.fxml"));
				BorderPane menu = loader.load();
				_root.setCenter(menu);
			} else { // otherwise get the user to select the level they want to
						// start at
				loader.setLocation(getClass().getResource("views/PickLevel.fxml"));
				AnchorPane pickLevel = loader.load();
				_root.setCenter(pickLevel);
			}

			// Sets window size
			Scene scene1 = new Scene(_root, 600, 400);
			// Set styling
			scene1.getStylesheets().add(ResourceLoader.inst().appTheme());
			// Sets the icon
			_primaryStage.getIcons().add(ResourceLoader.inst().appIcon());
			// Starts application
			_primaryStage.setScene(scene1);
			// Make the application un-resizable
			_primaryStage.setResizable(false);
			_primaryStage.sizeToScene(); // prevents border from setResizable
			_primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected URL fxmlFile(String name) {
		return getClass().getResource("views/" + name + ".fxml");
	}
}

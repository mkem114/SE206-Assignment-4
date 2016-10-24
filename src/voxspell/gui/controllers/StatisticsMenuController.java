package voxspell.gui.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import voxspell.gamelogic.QuizWord;
import voxspell.gamelogic.SpellingLevel;
import voxspell.gui.App;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <h1>voxspell.gui.controllers.StatisticsMenuController</h1> Controller class responsible for displaying
 * statistics
 * 
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 *
 */
public class StatisticsMenuController implements Initializable {

	@FXML
	private Button backBtn;

	@FXML
	private ComboBox<String> statsLevelPicker;

	@FXML
	private Label accuracyLevel;

	@FXML
	private TableView<QuizWord> statsTable;

	@FXML
	private TableColumn<QuizWord, String> wordColumn;

	@FXML
	private TableColumn<QuizWord, String> masteredColumn;

	@FXML
	private TableColumn<QuizWord, String> faultedColumn;

	@FXML
	private TableColumn<QuizWord, String> failedColumn;

	/**
	 * Return to main menu
	 * 
	 * @param event
	 *            On pressing of the Back button
	 */
	@FXML
	void goBack(ActionEvent event) {
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
	 * Initializing code to show Level 1 when user opens the Stats menu
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> allLevels = new ObservableListWrapper<>(App.inst().game().levels());
		allLevels.addAll(App.inst().game().customLevels());
		statsLevelPicker.setItems(allLevels);
		statsLevelPicker.getSelectionModel().selectFirst();
		wordColumn.setCellValueFactory(cellData -> cellData.getValue().wordProperty());
		masteredColumn.setCellValueFactory(cellData -> cellData.getValue().masteredProperty());
		faultedColumn.setCellValueFactory(cellData -> cellData.getValue().faultedProperty());
		failedColumn.setCellValueFactory(cellData -> cellData.getValue().failedProperty());
		changeLevel();
	}

	/**
	 * Ensures that the text area is updated when the user changes the level
	 * from dropdown
	 */
	public void changeLevel() {
		List<SpellingLevel> stats = App.inst().game().statistics();
		
		for (SpellingLevel level : stats) {
			if (level.name().equals(statsLevelPicker.getValue())) {
				accuracyLevel.setText(level.accuracy()+"%");
				statsTable.setItems(level.statistics().sorted());
				break;
			}
		}
	}
}

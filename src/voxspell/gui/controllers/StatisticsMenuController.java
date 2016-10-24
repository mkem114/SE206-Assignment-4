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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <h1>voxspell.gui.controllers.StatisticsMenuController</h1> Controller class responsible for displaying
 * statistics
 *
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
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
     * @param event On pressing of the Back button
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
        // Wraps the a list of all the levels
        ObservableList<String> allLevels = new ObservableListWrapper<>(App.inst().game().levels());
        allLevels.addAll(App.inst().game().customLevels());
        // Populates the combobox with the levels
        statsLevelPicker.setItems(allLevels);
        // Defaults to first level
        statsLevelPicker.getSelectionModel().selectFirst();
        // Assign each of table columns a property of QuizWords
        wordColumn.setCellValueFactory(cellData -> cellData.getValue().wordProperty());
        masteredColumn.setCellValueFactory(cellData -> cellData.getValue().masteredProperty());
        faultedColumn.setCellValueFactory(cellData -> cellData.getValue().faultedProperty());
        failedColumn.setCellValueFactory(cellData -> cellData.getValue().failedProperty());
        // Loads the table's numbers
        changeLevel();
    }

    /**
     * Ensures that the text area is updated when the user changes the level
     * from dropdown
     */
    public void changeLevel() {
        // Gets stats for each level
        List<SpellingLevel> stats = App.inst().game().statistics();
        // For all levels
        for (SpellingLevel level : stats) {
            // Once found the picked one
            if (level.name().equals(statsLevelPicker.getValue())) {
                // Update the level accuracy
                accuracyLevel.setText(level.accuracy() + "%");
                // Update the table with the numbers
                statsTable.setItems(level.statistics());
                // Sort the table by words ascending lexicographically
                statsTable.getItems().sort(new Comparator<QuizWord>() {
                    /**
                     * Compares two quiz words to see which comes first lexicographically
                     * @param o1 first QuizWord to compare
                     * @param o2 second QuizWord to compare
                     * @return see Comparator interface
                     */
                    @Override
                    public int compare(QuizWord o1, QuizWord o2) {
                        return o1.word().compareTo(o2.word());
                    }
                });
                break;
            }
        }
    }
}

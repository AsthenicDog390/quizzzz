package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderboardCtrl implements Initializable {

    private final MainCtrl mainCtrl;

    private final ServerUtils server;

    @FXML
    private TableView<Player> table;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, Integer> score;

    /**
     * Generator for the final leaderboard screen controller where final scores are showed after finishing a game.
     * @param server - the server where the singleplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public LeaderboardCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Setting the scores in the Leaderboard element on the scene.
     * @param players - a list with players containing also their scores.
     */
    public void setLeaderboard(List<Player> players) {
        Collections.sort(players, (Player p1, Player p2) -> p2.getScore()-p1.getScore());
        this.table.getItems().setAll(players);

    }

    /**
     * Initializer function for setting up the Tableview Columns "name and "player".
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        // name.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        score.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        // score.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getScore()));
    }

    /**
     * Function returning the user to the main menu.
     */
    public void showMainMenu() {
        mainCtrl.gameEnded();
        // Change it to the input player name after Player class is done and merged
        mainCtrl.showMainMenu();
    }

    /**
     * Setting the leaderboard with a player.
     * @param p - the player to be added in the leaderboard.
     */
    public void setLead(Player p) {
        table.setItems(createLead(p));
    }

    /**
     * Setting and ObservableList with a player for the leaderboard.
     * @param p - the player to be added.
     * @return - and ObservableList containing the freshly added player.
     */
    public ObservableList<Player> createLead(Player p) {
        ObservableList<Player> data = FXCollections.observableArrayList(p);
        return data;
    }

    /**
     * Function for ending the current running game.
     */
    public void gameEnded() {
        mainCtrl.gameEnded();
        mainCtrl.showMainMenu();
    }
}
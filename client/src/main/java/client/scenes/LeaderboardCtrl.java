package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;


public class LeaderboardCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> name;
    @FXML
    private TableColumn<Player, Integer> score;

    @Inject
    public LeaderboardCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void setLeaderboard(List<Player> scores) {
        List<Player> players = new ArrayList<>();
        for(Player h:scores){
            if (h.getIsSingleplayer()){
                players.add(h);
                System.out.println(h);
            }
        }
        this.table.getItems().setAll(players);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
       // name.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        score.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
       // score.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getScore()));
    }

    public void showMainMenu() {
        mainCtrl.gameEnded();
        // Change it to the input player name after Player class is done and merged
    }

    public void setLead(Player p) {
        table.setItems(createLead(p));
    }

    public ObservableList<Player> createLead(Player p) {
        ObservableList<Player> data = FXCollections.observableArrayList(p);
        return data;
    }

    public void gameEnded() {
        mainCtrl.gameEnded();
    }
}
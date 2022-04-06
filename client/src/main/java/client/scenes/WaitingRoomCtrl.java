package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class WaitingRoomCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private Label test;

    @FXML
    private ListView<Player> listView;

    private ObservableList<Player> observableList = FXCollections.observableArrayList();

    public void initialize() {
        listView.setItems(observableList);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Player item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
                test.setText("Players: " + observableList.size());
            }
        });

        // Change it to the input player name after Player class is done and merged
    }

    public void setPlayerList(List<Player> players) {
        observableList.setAll(players);
    }

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    @FXML
    public void startGame(ActionEvent e) {
        mainCtrl.startMultiplayerGame();
    }
}
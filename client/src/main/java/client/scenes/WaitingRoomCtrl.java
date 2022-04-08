package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;

public class WaitingRoomCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private Label test;

    @FXML
    private ListView<Player> listView;

    private final ObservableList<Player> observableList = FXCollections.observableArrayList();

    /**
     * Initializer for the waiting room player list.
     */
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

    /**
     * Function for setting the Player List of the waiting room.
     * @param players is a list of "Player" object to be added to the waiting room.
     */
    public void setPlayerList(List<Player> players) {
        observableList.setAll(players);
    }

    /**
     * Constructor for the waiting room controller.
     * @param server - the server where the multiplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Function showing and opening Main Menu Sreen.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Function starting the game and sending start game message to server.
     */
    @FXML
    public void startGame(ActionEvent e) {
        mainCtrl.startMultiplayerGame();
    }
}
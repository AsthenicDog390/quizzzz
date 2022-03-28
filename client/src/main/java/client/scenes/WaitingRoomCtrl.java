package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class WaitingRoomCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label test;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> observableList = FXCollections.observableArrayList();

    public void initialize(){

        observableList.addAll("George", "Gerrit", "Friso", "Radu", "Alex");
       listView.setItems(observableList);
        test.setText("Players: " + observableList.size());
    }

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    public void showStartingScreen() {
        mainCtrl.showStartingScreen();
    }
}
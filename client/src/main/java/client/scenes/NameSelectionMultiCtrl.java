package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.exceptions.NameAlreadyPickedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NameSelectionMultiCtrl implements Initializable {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField nameField;

    @FXML
    private AnchorPane errorPopup;

    @FXML
    private AnchorPane background;

    /**
     * Constructor for the multiple choice type of Multiplayer Name Selection Screen controller.
     * @param server - the server where the multiplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public NameSelectionMultiCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Function for submiting the name and adding it so that its later displayed on the multiplayer waiting room.
     * @param e - the start game button pressed.
     */
    @FXML
    private void submitName(ActionEvent e) {
        String name = nameField.getText();

        try {
            mainCtrl.startMultiPlayerGame(name);
            showWaitingRoom();
        } catch (NameAlreadyPickedException ex) {
            background.setOpacity(0.8f);
            nameField.setStyle("-fx-background-color: red");
            nameField.setEditable(false);
            errorPopup.setVisible(true);
        }
    }

    /**
     * Function that closes the error popup when a player inputs a name  is already taken.
     * @param e - the close popup button being pressed
     */
    @FXML
    private void closeErrorPopup(ActionEvent e) {
        background.setOpacity(1);
        errorPopup.setVisible(false);
        nameField.setStyle("-fx-background-color: white");
        nameField.setEditable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorPopup.setVisible(false);
    }

    /**
     * Function showing and opening the waiting room.
     */
    public void showWaitingRoom() {
        mainCtrl.showWaitingRoom();
    }

    /**
     * Function showing and opening the main menu.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }
}

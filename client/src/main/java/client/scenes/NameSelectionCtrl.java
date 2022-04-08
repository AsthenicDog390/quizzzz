package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NameSelectionCtrl implements Initializable {
    private final MainCtrl mainCtrl;

    private final ServerUtils server;

    @FXML
    private TextField nameField;

    @FXML
    private AnchorPane errorPopup;

    @FXML
    private AnchorPane background;

    /**
     * Constructor for the name selection single player screen.
     * @param server - the server where the singleplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public NameSelectionCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Function that is used for getting the input name by the player and starting single player with it
     * @param e - the press of the start game button
     */
    @FXML
    private void submitName(ActionEvent e) {
        String name = nameField.getText();
        mainCtrl.startSinglePlayerGame(name);
    }

    /**
     *  Function that is used for closing the Error pop up when entering an invalid name
     * @param e - the press of the close popup
     */
    @FXML
    private void closeErrorPopup(ActionEvent e) {
        background.setOpacity(1);
        errorPopup.setVisible(false);
        nameField.setStyle("-fx-background-color: white");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorPopup.setVisible(false);
    }

    /**
     * Function showing and opening the Starting Screen.
     */
    public void showStartingScreen() {
        mainCtrl.showStartingScreen();
    }

    /**
     * Function showing and opening the main menu.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }
}

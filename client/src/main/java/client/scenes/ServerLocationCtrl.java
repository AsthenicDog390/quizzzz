package client.scenes;

import client.utils.Config;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerLocationCtrl {

    private final MainCtrl mainCtrl;

    private Config config;

    @FXML
    private TextField URL;

    /**
     * Constructor for the main menu screen controller.
     * @param config - the server where the multiplayer will be running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public ServerLocationCtrl(MainCtrl mainCtrl, Config config) {
        this.mainCtrl = mainCtrl;
        this.config = config;
    }

    /**
     * Function for entering the input server.
     * @param e - the button being pressed.
     */
    @FXML
    public void enterGame(ActionEvent e) {
        this.config.setServerLocation(this.URL.getText());
        mainCtrl.showMainMenu();
    }
}

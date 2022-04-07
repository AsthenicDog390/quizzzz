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

    @Inject
    public ServerLocationCtrl(MainCtrl mainCtrl, Config config) {
        this.mainCtrl = mainCtrl;
        this.config = config;
    }

    @FXML
    public void enterGame(ActionEvent e) {
        this.config.setServerLocation(this.URL.getText());
        mainCtrl.showMainMenu();
    }
}

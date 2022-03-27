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

    @Inject
    public NameSelectionCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    private void submitName(ActionEvent e) {
        String name = nameField.getText();
        if (!nameIsValid(name)) {
            background.setOpacity(0.8f);
            errorPopup.setVisible(true);
            nameField.setStyle("-fx-background-color: red");
        } else {
            mainCtrl.startSinglePlayerGame();
        }
    }

    @FXML
    private void closeErrorPopup(ActionEvent e) {
        background.setOpacity(1);
        errorPopup.setVisible(false);
        nameField.setStyle("-fx-background-color: white");
    }

    private boolean nameIsValid(String name) {
        return !"test".equals(name);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorPopup.setVisible(false);
    }

    @Inject
    public NameSelectionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }
}

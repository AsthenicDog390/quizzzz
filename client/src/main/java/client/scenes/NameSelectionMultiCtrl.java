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

public class NameSelectionMultiCtrl implements Initializable {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField nameField;

    @FXML
    private AnchorPane errorPopup;

    @FXML
    private AnchorPane background;

    @Inject
    public NameSelectionMultiCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private void submitName(ActionEvent e) {
        String name = nameField.getText();
        if (!nameIsValid(name)) {
            background.setOpacity(0.8f);
            errorPopup.setVisible(true);
            nameField.setStyle("-fx-background-color: red");
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

    public void showWaitingRoom() {
        mainCtrl.showWaitingRoom();
    }

    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }
}

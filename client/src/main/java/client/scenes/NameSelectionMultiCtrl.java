package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.exceptions.NameAlreadyPickedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
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

    public void showWaitingRoom(){
        mainCtrl.showWaitingRoom();
    }

    public void showMainMenu(){mainCtrl.showMainMenu();}
}

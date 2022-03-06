package client.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NameSelectionCtrl implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private AnchorPane errorPopup;
    @FXML
    private AnchorPane background;

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
}

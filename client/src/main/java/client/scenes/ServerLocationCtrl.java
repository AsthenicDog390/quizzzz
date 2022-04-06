package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerLocationCtrl {

    private final MainCtrl mainCtrl;

    @FXML
    private TextField URL;

    @Inject
    public ServerLocationCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML
    public void enterGame(ActionEvent e) {

    }
}

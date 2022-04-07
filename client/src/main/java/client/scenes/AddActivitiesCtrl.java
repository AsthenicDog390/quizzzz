package client.scenes;

import client.utils.ActivityUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddActivitiesCtrl {
    private MainCtrl mainCtrl;

    private ActivityUtils activityUtils;

    @FXML
    private TextField fileLocationField;

    @FXML
    private Text Popup;

    /**
     * Constructor for the controller where we import all the activities we are going to use in the game.
     * @param mainCtrl - the main controller where the game runs on.
     * @param activityUtils - helper used for adding the activities into a local repository.
     */
    @Inject
    public AddActivitiesCtrl(MainCtrl mainCtrl, ActivityUtils activityUtils) {
        this.mainCtrl = mainCtrl;
        this.activityUtils = activityUtils;
    }

    @FXML
    private void pickFile(ActionEvent e) {
        var loc = mainCtrl.pickFileLocation();
        if (loc != null) {
            fileLocationField.setText(loc);
        }
    }

    @FXML
    private void submitActivitiesFile(ActionEvent e) {
        var loc = fileLocationField.getText();
        if ("".equals(loc)) {
            return;
        }

        Popup.setText("loading...");
        Popup.setVisible(true);

        new Thread(() -> {
            activityUtils.addActivitiesFromZipFile(loc);
            showFinishedPopup();
        }).start();
    }

    void showFinishedPopup() {
        Popup.setText("Done");
    }

    @FXML
    void showMainMenu(){
        Popup.setVisible(false);
        mainCtrl.showMainMenu();
    }
}
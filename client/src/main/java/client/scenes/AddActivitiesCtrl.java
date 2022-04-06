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
    private Text finishedPopup;

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

        finishedPopup.setVisible(false);

        new Thread(() -> {
            activityUtils.addActivitiesFromZipFile(loc);
            showFinishedPopup();
        }).start();
    }

    void showFinishedPopup() {
        finishedPopup.setVisible(true);
    }
}

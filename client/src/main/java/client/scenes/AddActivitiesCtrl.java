package client.scenes;

import client.utils.ActivityUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddActivitiesCtrl {
    private MainCtrl mainCtrl;
    private ActivityUtils activityUtils;

    @FXML
    private TextField fileLocationField;

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

        activityUtils.addActivitiesFromZipFile(loc);
    }
}

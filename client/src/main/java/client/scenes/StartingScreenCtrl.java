package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

public class StartingScreenCtrl {
    public StartingScreenCtrl() {
        countdownTimer.setText("0");
    }

    @FXML
    private Text countdownTimer;

    @Inject
    public StartingScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {

    }

    public void start() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 5;

            public void run() {

                countdownTimer.setText(Integer.toString(i));
                i--;

                if (i < 0) {
                    timer.cancel();
                    countdownTimer.setText("0");
                }
            }
        }, 0, 1000);
    }
}

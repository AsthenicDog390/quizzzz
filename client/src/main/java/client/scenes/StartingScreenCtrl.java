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

    /**
     * Constructor for the starting screen controller.
     * @param server - the server where the singleplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public StartingScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {

    }

    /**
     * Function that starts the countdown displayed on the starting screen
     * Displays countdown from 5 to 1 decrementing every second.
     */
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

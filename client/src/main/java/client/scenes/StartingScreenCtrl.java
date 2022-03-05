package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

public class StartingScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Text countdownTimer;

    @Inject
    public StartingScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void start() {
        new Timer().scheduleAtFixedRate(new TimerTask() {


            public void run() {
                int i = 5;
                countdownTimer.setText(Integer.toString(i));
                i--;

                if(i < 0) {
                    cancel();
                    //start game
                }
            }
        }, 0, 1000);
    }
}

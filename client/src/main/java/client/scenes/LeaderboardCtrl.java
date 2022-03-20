package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.scene.control.ProgressBar;
import javafx.util.Duration;


public class LeaderboardCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;



    @Inject
    public LeaderboardCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        Progresss();
    }

    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    public void Progresss() {

        ProgressBar prog = new ProgressBar();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(prog.progressProperty(), 0)),
                new KeyFrame(Duration.minutes(0.33), e-> {

                    mainCtrl.showMainMenu();
                }, new KeyValue(prog.progressProperty(), 1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


}


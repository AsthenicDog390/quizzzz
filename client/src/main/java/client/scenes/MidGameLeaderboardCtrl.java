package client.scenes;

import client.comparators.ScoreComparator;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MidGameLeaderboardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public MidGameLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private ProgressBar progressBar;
    @FXML
    private DialogPane dialogPane;
    @FXML
    private Button YesExit;
    @FXML
    private Button NoExit;
    @FXML
    private ListView<String> player;
    @FXML
    private ListView<Integer> score;

    public void setPlayerScores(List<Player> players, Player currentPlayer){
        players.sort(new ScoreComparator());
        List<String> names = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        for(int i = 1 ; i <= 3 ; i++){
            names.add(i + ". " + players.get(i-1).getName());
            scores.add(players.get(i-1).getScore());
        }
        int poz = players.indexOf(currentPlayer) + 1;
        names.add(poz + ". " + currentPlayer.getName());
        scores.add(currentPlayer.getScore());
        this.player = new ListView<String>((ObservableList<String>) names);
        this.score = new ListView<Integer>((ObservableList<Integer>) scores);
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Starting 2 timers corresponding to the progress bar
     */
    public void startTimer() {
        Timer gameTimer = new Timer();
        Timer progressBarTimer = new Timer();
        /**
         * Task for not letting the progress bar go under 0
         */
        TimerTask timeOut = new TimerTask() {
            @Override
            public void run() {
                progressBarTimer.cancel();
            }
        };

        /**
         * Task for decreasing the progress bar with a specific amount every 40ms
         */
        TimerTask lowerBar = new TimerTask() {
            @Override
            public void run() {
                double progress = progressBar.getProgress();
                if(progress>0.004)
                    progressBar.setProgress(progress-0.004);
            }
        };
        gameTimer.schedule(timeOut,10000);
        progressBarTimer.schedule(lowerBar,0,40);
    }

    public void showDialogExit() {
        dialogPane.setVisible(true);
    }

    public void hideDialogExit() {
        dialogPane.setVisible(false);
    }

}

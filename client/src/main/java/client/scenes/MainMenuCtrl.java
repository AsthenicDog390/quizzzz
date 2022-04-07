package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class MainMenuCtrl {

    private final MainCtrl mainCtrl;

    private final ServerUtils server;

    /**
     * Constructor for the main menu screen controller.
     * @param server - the server where the singleplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public MainMenuCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Function opening the how to play menu.
     */
    public void showHowToPlay() {
        mainCtrl.showHowToPlay();
    }

    //Used for testing purposes
    public void showLead() {
        //mainCtrl.showLeaderboard();
    }

    /**
     * Function showing and opening the waiting room.
     */
    public void showWaitingRoom() {
        mainCtrl.showWaitingRoom();
    }

    /**
     * Function showing the name selection page, for a single player game.
     */
    public void showNameSelection() {
        mainCtrl.showNameSelection();
    }

    /**
     * Function showing the name selection page, for a multi-player game.
     */
    public void showNameSelectionMulti() {
        mainCtrl.showNameSelectionMulti();
    }

    /**
     * Function used for closing down the whole app.
     */
    public void closeApp() {
        System.exit(0);
    }

    public void showAddAtivities() {
        mainCtrl.showAddActivities();
    }
}


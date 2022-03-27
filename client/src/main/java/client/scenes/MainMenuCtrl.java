package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class MainMenuCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @Inject
    public MainMenuCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void showHowToPlay() {
        mainCtrl.showHowToPlay();
    }


    public void showWaitingRoom() {
        mainCtrl.showWaitingRoom();
    }

    public void showNameSelection() {
        mainCtrl.showNameSelection();
    }

    public void startSinglePlayerGame() {
        mainCtrl.startSinglePlayerGame();
    }

    public void closeApp() {
        System.exit(0);
    }

    public void showNameScreen(){mainCtrl.showNameSelection();}

}


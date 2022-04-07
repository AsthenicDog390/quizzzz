package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class HowToPlayCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    /**
     * Constructor for the "How to play" screen controller where instructions for the game are given.
     * @param server - the server where the singleplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public HowToPlayCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Function returning the user to the main menu.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }
}

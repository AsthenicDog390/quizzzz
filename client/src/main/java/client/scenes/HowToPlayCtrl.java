package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class HowToPlayCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public HowToPlayCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }
}

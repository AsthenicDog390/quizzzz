package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class MainMenuCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public MainMenuCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
}

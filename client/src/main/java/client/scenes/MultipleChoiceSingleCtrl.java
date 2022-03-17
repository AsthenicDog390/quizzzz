package client.scenes;

import client.utils.ServerUtils;

public class MultipleChoiceSingleCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    public MultipleChoiceSingleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
}

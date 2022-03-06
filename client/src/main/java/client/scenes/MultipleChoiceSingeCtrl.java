package client.scenes;

import client.utils.ServerUtils;

public class MultipleChoiceSingeCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    public MultipleChoiceSingeCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
}

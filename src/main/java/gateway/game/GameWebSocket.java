package gateway.game;

import controller.game.IGameController;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class GameWebSocket extends WebSocketClient {
    private final IGameController controller;

    public GameWebSocket(URI serverUri, IGameController controller) {
        super(serverUri);
        this.controller = controller;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}

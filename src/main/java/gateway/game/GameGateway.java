package gateway.game;

import controller.game.IGameController;

import java.net.URI;

public class GameGateway {
    public static interface IGameGateway {
    }

    public static class MockGameGateway {

    }

    public static class GameGatewayImpl implements IGameGateway {
        private final GameWebSocket gameWebSocket;

        public GameGatewayImpl(IGameController gameController) throws Exception {
            gameWebSocket = new GameWebSocket(new URI("ws://localhost:7000"), gameController);
            gameWebSocket.connect();
        }
    }
}

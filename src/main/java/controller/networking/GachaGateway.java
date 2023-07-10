package controller.networking;

import controller.home.Gacha;
import model.util.User;

interface IGachaGateway {
    public Gacha.GachaResult play() throws Exception;
}

public class GachaGateway {
    public class MockGachaGateway implements IGachaGateway {
        public Gacha.GachaResult play() throws Exception {
            return new Gacha().play(new User(1234, "test", 1000, 1000));
        }
    }
}


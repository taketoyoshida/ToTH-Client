package controller.networking;

import controller.home.GachaMock;
import model.util.User;

public class GachaGateway {
    public interface IGachaGateway {
        public GachaMock.GachaResult play() throws Exception;

        public GachaMock.GachaResult[] play10() throws Exception;
    }

    public class MockGachaGateway implements IGachaGateway {
        public GachaMock.GachaResult play() throws Exception {
            return new GachaMock().play(new User(1234, "test", 1000, 1000));
        }

        public GachaMock.GachaResult[] play10() throws Exception {
            GachaMock.GachaResult[] results = new GachaMock.GachaResult[10];
            for (int i = 0; i < 10; i++) {
                results[i] = new GachaMock().play(new User(1234, "test", 1000, 1000));
            }
            return results;
        }
    }
}


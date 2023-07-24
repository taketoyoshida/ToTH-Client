package controller.networking;

import controller.home.GachaMock;
import model.util.User;

public class GachaGateway {
    public interface IGachaGateway {
        public GachaMock.GachaResult play(User user) throws Exception;

        public GachaMock.GachaResult[] play10(User user) throws Exception;
    }

    public static class MockGachaGateway implements IGachaGateway {
        public GachaMock.GachaResult play(User user) throws Exception {
            return new GachaMock().play(user);
        }

        public GachaMock.GachaResult[] play10(User user) throws Exception {
            GachaMock.GachaResult[] results = new GachaMock.GachaResult[10];
            for (int i = 0; i < 10; i++) {
                results[i] = new GachaMock().play(user);
            }
            return results;
        }
    }
}


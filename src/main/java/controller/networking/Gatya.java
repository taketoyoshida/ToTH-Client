package controller.networking;
import model.Player;
import model.Blueprint;

import java.util.Random;

public class Gatya {
    /*public static void main(String[] args){
        Player player = new Player();
        player.addCoin(10000);
        Gatya gatya = new Gatya();

        Blueprint[] bp = new Blueprint[3];
        bp[0] = new Blueprint(1,"剣",0.9);
        bp[1] = new Blueprint(2,"盾",0.09);
        bp[2] = new Blueprint(3,"女",0.01);

        for(int i=0;i<100;i++) {
            System.out.println("ガチャ結果 : " + gatya.gatya(player, bp));
        }
    }*/

    public String gatya(Player player, Blueprint[] bp){
        if(player.getCoin()<100){
            System.out.println("coinが足りません");

        }else{
            player.addCoin(-100);
            Random rand = new Random();
            double randomValue = rand.nextDouble();

            double cumulativeProbability = 0.0;
            for (int i = 0; i < bp.length; i++) {
                cumulativeProbability += bp[i].getRate();
                if (randomValue <= cumulativeProbability) {
                    return bp[i].getName();
                }
            }

        }
        return null;
    }
}

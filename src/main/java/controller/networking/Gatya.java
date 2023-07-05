package controller.networking;
import model.Player;
import model.Blueprint;

import java.util.Random;

public class Gatya {
    /*public static void main(String[] args){//コメントアウト解除で100連無料ガチャ！
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

    public String gatya(Player player, Blueprint[] bp){//ガチャを引くシステム
        if(player.getCoin()<100){//コインが足りない場合
            System.out.println("coinが足りません");

        }else{//コインがあり、ガチャが引ける場合
            player.addCoin(-100);//コイン消費
            Random rand = new Random();
            double randomValue = rand.nextDouble();

            double cumulativeProbability = 0.0;
            for (int i = 0; i < bp.length; i++) {
                cumulativeProbability += bp[i].getRate();
                if (randomValue <= cumulativeProbability) {
                    return bp[i].getName();//ガチャ結果を返す
                }
            }

        }
        return null;
    }
}

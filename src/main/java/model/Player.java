package model;

import model.Material;
import model.Status;

public class Player {
    String name;        //名前
    Status status;      //ステータス
    Material material;  //素材
    int coin; //coin所持数
    int remainAction;   //残り行動数
    boolean isDead;     //死亡判定
    int rank;

    /* プレイヤー作成 */
    void makePlayer(String name, Status status, Material material, int coin){
        this.name = name;
        this.status = status;
        this.material = material;
        this.coin=coin;
    }

    public int getCoin() {
        return coin;
    }

    public void addCoin(int value){
        coin=coin+value;

    }
}

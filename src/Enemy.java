public class Enemy {

    // 敵の例
    EnemyStatus mob1 = new EnemyStatus(1,"Mob1", 1,1,1,1,1);
    EnemyStatus mob2 = new EnemyStatus(2,"Mob2", 2,2,1,1,1);

    void action(){
        //動き方
    }
    void attack(){
        //射程内にプレイヤーがいたら攻撃する

    }


}

class EnemyStatus{
    int ID;
    String name;
    int HP;
    int ATK;
    int MOV;
    int RNG;
    int LV;

    public EnemyStatus(int id,String name, int hp, int atk, int mov,int rng, int lv){
        // HPと攻撃
        this.ID = id;
        this.name = name;
        this.HP = hp;
        this.ATK = atk;
        this.MOV = mov;
        this.RNG = rng;
        this.LV = lv;
    }
}
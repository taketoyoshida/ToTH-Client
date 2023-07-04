package model;

public class EnemyStatus {
    Position pos;
    int ID;
    String name;
    int HP;
    int ATK;
    int MOV;
    int RNG;
    int LV;

    public EnemyStatus(int id, String name, int hp, int atk, int mov, int rng, int lv, Position pos) {
        // HPと攻撃
        this.ID = id;
        this.name = name;
        this.HP = hp;
        this.ATK = atk;
        this.MOV = mov;
        this.RNG = rng;
        this.LV = lv;
        this.pos = pos;
    }
}

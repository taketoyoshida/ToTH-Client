package model;

public enum EnemyInfo {
    ENEMY_1(1, "敵1", 10, 5, 1, 1,"test.png"),
    ENEMY_2(2, "敵2", 10, 5, 1, 1,"test.png"),
    ENEMY_3(3, "敵3", 10, 5, 1, 1,"test.png"),
    ENEMY_4(4, "敵4", 10, 5, 1, 1,"test.png"),
    ENEMY_5(5, "敵5", 10, 5, 1, 1,"test.png"),
    ENEMY_6(6, "敵6", 10, 5, 1, 1,"test.png"),
    ENEMY_7(7, "敵7", 10, 5, 1, 1,"test.png"),
    ENEMY_8(8, "敵8", 10, 5, 1, 1,"test.png"),
    ENEMY_9(9, "敵9", 10, 5, 1, 1,"test.png"),
    ENEMY_10(10, "敵10", 10, 5, 1, 1,"test.png"),
    ENEMY_11(11, "敵11", 10, 5, 1, 1,"test.png"),
    ENEMY_12(12, "敵12", 10, 5, 1, 1,"test.png");

    public final int ID;
    public final String name;
    public final int HP;
    public final int ATK;
    public final int MOV;
    public final int RNG;
    public Position POS;
    public String imgPath;

    EnemyInfo(
    int id,
    String name,
    int hp,
    int atk,
    int mov,
    int rng,
    String imgPath
    ) {
        this.ID = id;
        this.name = name;
        this.HP = hp;
        this.ATK = atk;
        this.MOV = mov;
        this.RNG = rng;

    }

    public String getAssetPath(){
        return "./assets/imgs/enemy" + this.imgPath;
    }
}


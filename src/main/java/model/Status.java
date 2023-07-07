package model;

public class Status {
    private int HP;     // HP
    private int ATK;    // 攻撃力
    private int MOV;    // 行動力
    private int EYE;    // 視野
    private int RNG;    // 射程

    public Status(int HP, int ATK, int MOV, int EYE, int RNG) {
        this.HP = HP;
        this.ATK = ATK;
        this.MOV = MOV;
        this.EYE = EYE;
        this.RNG = RNG;
    }

    public int getHP() {
        return HP;
    }

    public int getATK() {
        return ATK;
    }

    public int getMOV() {
        return MOV;
    }

    public int getEYE() {
        return EYE;
    }

    public int getRNG() {
        return RNG;
    }
}

package model;

public class Status {
    private int HP;     // HP
    private int ATK;    // 攻撃力
    private int MOV;    // 行動力
    private int RNG;    // 射程

    public Status(int HP, int ATK, int MOV,  int RNG) {
        this.HP = HP;
        this.ATK = ATK;
        this.MOV = MOV;
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

    public int getRNG() {
        return RNG;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public void setMOV(int MOV) {
        this.MOV = MOV;
    }

    public void setRNG(int RNG) {
        this.RNG = RNG;
    }
}

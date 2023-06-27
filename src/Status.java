public class Status {
    private int HP;     //HP
    private int ATK;    //攻撃力
    private int MOV;    //行動力
    private int EYE;  //視野
    private int RNG;  //射程

    public Status(int hp, int atk, int mov, int eye, int rng){
        setStatus(hp,atk,mov,eye,rng);
    }
    public void setStatus(int hp, int atk, int mov, int eye, int rng) {
        HP = hp;
        ATK = atk;
        MOV = mov;
        EYE = eye;
        RNG = rng;
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

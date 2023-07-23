package model;

public class Enemy {
    private int id;
    private String name;
    private int hp;
    private int atk;
    private int mov;
    private int rng;
    private Position position;

    public Enemy(int id, String name, int hp, int atk, int mov, int rng, Position position) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.mov = mov;
        this.rng = rng;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getMov() {
        return mov;
    }

    public int getRng() {
        return rng;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public void printEnemyStatus(){
        System.out.println(" HP | ATK | MOV | RNG");
        System.out.printf("%3d | %3d | %3d | %3d\n", hp, atk, mov, rng);  }
}

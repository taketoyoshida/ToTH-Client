package model;

import model.game.Equipment;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;                        // 名前
    private Status status;                      // ステータス
    private Map<Material, Integer> materials;   // 試合中に獲得した素材
    private int remainAction;                   // 残り行動数
    private boolean isDead;                     // 死亡判定
    private int rank;                           // ランク
    private int score;                          // スコア
    private int reward;                         // ゲーム終了後の報酬（ゴールド）
    private int aliveTurn;                      // 生存ターン数
    private Map<EquipmentPosition, Equipment> equippedItems; // 装備
    /* 持ってる装備を格納する変数が必要 */
    private int deadCount = 0;
    private Position pos;
    private Teban teban;

    public Player(Teban teban,  String name, Status status, int rank,Position pos) {
        this.teban = teban;
        this.name = name;
        this.status = status;
        this.materials = new HashMap<>();
        this.remainAction = 0;
        this.isDead = false;
        this.rank = rank;
        this.score = 0;
        this.reward = 0;
        this.aliveTurn = 0;
        this.equippedItems = new HashMap<>();
        this.pos = pos;
    }

    public void setTeban(Teban teban) {
        this.teban = teban;
    }
    public Teban getTeban() {
        return teban;
    }
    public String getName() {
        return name;
    }
    public Status getStatus() {
        return status;
    }
    public int getHP() {
        return status.getHP();
    }
    public int getATK() {
        return status.getATK();
    }
    public int getMOV() {
        return status.getMOV();
    }
    public int getRNG() {
        return status.getRNG();
    }
    public Position getPos() {
        return pos;
    }
    public int getRow() {
        return pos.getRow();
    }
    public int getCol() {
        return pos.getCol();
    }
    public void setPos(Position pos) {
        this.pos = pos;
    }
    public void setRow(int row) {
        this.pos.setRow(row);
    }
    public void setCol(int col) {
        this.pos.setCol(col);
    }

    public void printStatus() {
        System.out.println(" HP | ATK | MOV | RNG");
        System.out.printf("%3d | %3d | %3d | %3d\n", status.getHP(), status.getATK(), status.getMOV(), status.getRNG());
    }

    public Map<Material, Integer> getMaterials() {
        return materials;
    }

    public int getRemainAction() {
        return remainAction;
    }

    public void setRemainAction(int remainAction) {
        this.remainAction = remainAction;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void increaseDeadCount() {
        deadCount++;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore(int points) {
        this.score += points;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getAliveTurn() {
        return aliveTurn;
    }

    public void setAliveTurn(int aliveTurn) {
        this.aliveTurn = aliveTurn;
    }

    public void increaseAliveTurn() {
        this.aliveTurn++;
    }

    public void addMaterial(Material material, int quantity) {
        materials.put(material, materials.getOrDefault(material, 0) + quantity);
    }


    public boolean removeMaterial(Material material, int quantity) {
        int currentQuantity = materials.getOrDefault(material, 0);
        if (currentQuantity < quantity) {
            return false;
        }
        materials.put(material, currentQuantity - quantity);
        return true;
    }

    public int getMaterialQuantity(Material material) {
        return materials.getOrDefault(material, 0);
    }


    private void decreaseHP(int damage) {
        status.setHP(status.getHP() - damage);
        if (status.getHP() <= 0) {
            isDead = true;
        }
    }

    private void respawn() {
        //resetStatus();
        isDead = false;
        deadCount++;
    }
    public enum Teban{
        SENTE, GOTE
    }

}


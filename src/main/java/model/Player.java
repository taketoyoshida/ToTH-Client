package model;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;                        // 名前
    private Status status;                      // ステータス
    private Map<Material, Integer> materials;   // 素材
    private int remainAction;                   // 残り行動数
    private boolean isDead;                     // 死亡判定
    private int rank;                           // ランク
    private int score;                          // スコア
    private Map<EquipmentPosition, Equipment> equippedItems; // 装備

    public Player(String name, Status status) {
        this.name = name;
        this.status = status;
        this.materials = new HashMap<>();
        this.remainAction = 0;
        this.isDead = false;
        this.rank = 0;
        this.score = 0;
        this.equippedItems = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
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

    public void equipItem(Equipment item) {
        if (equippedItems.containsKey(item.getPosition())) {
            unequipItem(item.getPosition());
        }
        equippedItems.put(item.getPosition(), item);
        updateStatus();
    }

    public void unequipItem(EquipmentPosition position) {
        equippedItems.remove(position);
        updateStatus();
    }

    private void updateStatus() {
        int totalHP = status.getHP();
        int totalATK = status.getATK();
        int totalMOV = status.getMOV();
        int totalRNG = status.getRNG();

        for (Equipment equipment : equippedItems.values()) {
            Status equipmentStatus = equipment.getStatus();
            totalHP += equipmentStatus.getHP();
            totalATK += equipmentStatus.getATK();
            totalMOV += equipmentStatus.getMOV();
            totalRNG += equipmentStatus.getRNG();
        }

        status.setHP(totalHP);
        status.setATK(totalATK);
        status.setMOV(totalMOV);
        status.setRNG(totalRNG);
    }

}


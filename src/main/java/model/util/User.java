package model.util;

import model.*;
import model.game.Equipment;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class User {
    int ID;
    private String username;
    private Map<Blueprint, Integer> blueprints; //設計図
    private EnumMap<EquipmentItem, Integer> equipments;//所持装備
    private Map<EquipmentPosition, Equipment> equippedItems; // 装備しているアイテム
    private EnumMap<Material, Integer> materials;   // 素材
    private int balance;
    private int rank;
    private Player player;
    private final int baseHP = 100;
    private final int baseATK = 150;
    private final int baseMOV = 3;
    private final int baseRNG = 2;
    private Status status; //装備選択後のステータス

    public User(int id, String username, int balance, int rank) {
        this.ID = id;
        this.username = username;
        this.balance = balance;
        this.materials = new EnumMap<>(Material.class);
        this.blueprints = new HashMap<>();
        this.equipments = new EnumMap<>(EquipmentItem.class);
        this.equippedItems = new HashMap<>();
        this.rank = rank;
        this.status = new Status(baseHP, baseATK, baseMOV, baseRNG);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void mutateBalance(int amount) throws Exception {
        if (balance + amount < 0) {
            throw new Exception("残高が足りません");
        }
        balance += amount;
    }
    public void increaseBalance(int amount) {
        balance += amount;
    }

    public void setRank() {
        int rate = status.getHP() + status.getATK();
        if (rate < 30) {
            rank = 1;
        } else if (30 <= rate && rate < 50) {
            rank = 2;
        } else if (50 <= rate && rate < 80) {
            rank = 3;
        } else if (80 <= rate && rate < 120) {
            rank = 4;
        } else if (120 <= rate && rate < 160) {
            rank = 5;
        } else if (160 <= rate) {
            rank = 6;
        }
    }

    public int getRank() {
        return rank;
    }

    public Map<Material, Integer> getMaterials() {
        return materials;
    }

    public int getMaterialQuantity(Material material) {
        return materials.getOrDefault(material, 0);
    }

    public void addMaterial(Material material, int amount) {
        materials.put(material, materials.getOrDefault(material, 0) + amount);
    }

    public void consumeMaterial(Material material, int amount) throws Exception {
        if (materials.getOrDefault(material, 0) < amount) {
            throw new Exception("Not enough materials");
        }
        materials.put(material, materials.get(material) - amount);
    }

    public void upgradeEquipment(EquipmentItem equipmentItem) {
        equipments.put(equipmentItem, equipments.getOrDefault(equipmentItem, 0) + 1);
    }

    public int getEquipmentLevel(EquipmentItem equipment) {
        return equipments.getOrDefault(equipment, 0);
    }

    public void addBlueprint(Blueprint blueprint, int quantity) {
        blueprints.put(blueprint, blueprints.getOrDefault(blueprint, 0) + quantity);
    }


    public boolean removeBlueprint(Blueprint blueprint, int quantity) {
        int currentQuantity = blueprints.getOrDefault(blueprint, 0);
        if (currentQuantity < quantity) {
            return false;
        }
        blueprints.put(blueprint, currentQuantity - quantity);
        return true;
    }

    public int getBlueprintQuantity(Blueprint blueprint) {
        return blueprints.getOrDefault(blueprint, 0);
    }

    public void createEquipment(EquipmentItem equipmentItem) throws Exception {
        if (this.getMaterialQuantity(Material.WOOD) >= equipmentItem.req_wood
                && this.getMaterialQuantity(Material.IRON) >= equipmentItem.req_iron
                && this.getMaterialQuantity(Material.DIAMOND) >= equipmentItem.req_diamond
                && this.getMaterialQuantity(Material.BRONZE) >= equipmentItem.req_bronze
                && this.getMaterialQuantity(Material.LEATHER) >= equipmentItem.req_leather) {
            this.consumeMaterial(Material.WOOD, equipmentItem.req_wood);
            this.consumeMaterial(Material.IRON, equipmentItem.req_iron);
            this.consumeMaterial(Material.DIAMOND, equipmentItem.req_diamond);
            this.consumeMaterial(Material.BRONZE, equipmentItem.req_bronze);
            this.consumeMaterial(Material.LEATHER, equipmentItem.req_leather);
            this.removeBlueprint(new Blueprint(equipmentItem), 1);
            this.upgradeEquipment(equipmentItem);
            return;
        }
        throw new Exception("素材が足りません");
    }

    public void equipItem(EquipmentItem item) {
        if (equippedItems.containsKey(item.getPosition())) {
            unequipItem(item.getPosition());
        }
        int lvl = getEquipmentLevel(item);
        Equipment equipment = new Equipment(item, lvl);
        if (lvl != 0) {
            System.out.println("装備しました");
            equippedItems.put(item.getPosition(), equipment);
        }
        resetStatus();
    }

    public void unequipItem(EquipmentPosition position) {
        equippedItems.remove(position);
        resetStatus();
    }

    private void resetStatus() {
        int totalHP = baseHP;
        int totalATK = baseATK;
        int totalMOV = baseMOV;
        int totalRNG = baseRNG;

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

        setRank();
    }

    public Status getStatus() {
        resetStatus();
        return status;
    }

    public int getRankPoint() {   //次のランクまでのポイントを返す
        int rate = status.getHP() + status.getATK();
        switch (this.rank) {
            case 1:
                return 30 - rate;
            case 2:
                return 50 - rate;
            case 3:
                return 80 - rate;
            case 4:
                return 120 - rate;
            case 5:
                if (rate >= 160)
                    return -1;
                else if (rate < 160)
                    return 160 - rate;
            default:
                return -1;
        }
    }

    public Equipment getEquipment(EquipmentPosition position) {
        return equippedItems.get(position);
    }

    public int getEquipmentQuantity(EquipmentItem equipmentItem) {
        return equipments.getOrDefault(equipmentItem, 0);
    }

}

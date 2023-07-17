package model.util;

import model.Blueprint;
import model.EquipmentItem;
import model.Material;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class User {
    int ID;
    private String username;
    private Map<Blueprint, Integer> blueprints; //設計図
    private EnumMap<EquipmentItem, Integer> equipments;//所持装備
    private EnumMap<Material, Integer> materials;   // 素材
    private int balance;
    private int rank;

    public User(int id, String username, int balance, int rank) {
        this.ID = id;
        this.username = username;
        this.balance = balance;
        this.rank = rank;
        this.materials = new EnumMap<>(Material.class);
        this.blueprints = new HashMap<>();
        this.equipments = new EnumMap<>(EquipmentItem.class);
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

    public void setRank(int rank) {
        this.rank = rank;
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
                && this.getMaterialQuantity(Material.BRONZE) >= equipmentItem.req_bronze &&
                this.getMaterialQuantity(Material.LEATHER) >= equipmentItem.req_leather) {
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
}

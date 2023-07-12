package model.util;

import model.Material;
import model.Equipment;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class User {
    int ID;
    private String username;
    private Map<Equipment,Integer> equipments;//所持装備
    // 素材・設計図
    private EnumMap<Material, Integer> materials;// 素材
    private int balance;
    private int rank;

    public User(int id, String username, int balance, int rank) {
        this.ID = id;
        this.username = username;
        this.balance = balance;
        this.rank = rank;
        this.materials = new EnumMap<>(Material.class);
        this.equipments=new HashMap<>();
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

    public void addMaterial(Material material, int amount) {
        materials.put(material, materials.getOrDefault(material, 0) + amount);
    }

    public void consumeMaterial(Material material, int amount) throws Exception {
        if (materials.getOrDefault(material, 0) < amount) {
            throw new Exception("Not enough materials");
        }
        materials.put(material, materials.get(material) - amount);
    }
    public boolean equipmentPossesion(Equipment equipment){

       return equipments.getOrDefault(equipment,0)>=1;
    }
    public void addEquipment(Equipment equipment) {

        equipments.put(equipment, equipments.getOrDefault(equipment, 0) + 1);

    }
}

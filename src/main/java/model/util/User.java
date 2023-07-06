package model.util;

import model.Material;

import java.util.Map;

public class User {
    int ID;
    private String username;
    //所持装備・素材・設計図
    private Map<Material, Integer> materials;   // 素材
    private int balance;
    private int rank;

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
}

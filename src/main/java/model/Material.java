package model;

import model.Status;
import java.awt.image.BufferedImage;

public class Material {
    private String name;                 // 名前
    private BufferedImage photo;         // 写真
    private int quantity;                // 数量

    public Material(String name, BufferedImage photo) {
        this.name = name;
        this.photo = photo;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (quantity >= amount) {
            this.quantity -= amount;
        } else {
            System.out.println("Error: Not enough materials.");
        }
    }
}


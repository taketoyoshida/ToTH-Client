package model.game;

import model.EquipmentItem;
import model.Status;
import model.EquipmentPosition;

// このクラスはゲーム内での実装のみにおいて使用可能。
public class Equipment {
    public final EquipmentItem item;
    private int level;

    public Equipment(EquipmentItem item, int level) {
        this.item = item;
        this.level = level;
    }

    public Status getStatus() {
        return item.getStatus(level);
    }

    public EquipmentPosition getPosition() {
        return item.position;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void levelUp() {
        this.level++;
    }
}

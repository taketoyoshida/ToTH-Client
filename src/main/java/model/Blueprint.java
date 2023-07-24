package model;

import java.util.Objects;

public final class Blueprint {
    private final EquipmentItem baseItem;

    public Blueprint(EquipmentItem baseItem) {
        this.baseItem = baseItem;
    }

    public EquipmentItem baseItem() {
        return baseItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Blueprint) obj;
        return Objects.equals(this.baseItem, that.baseItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseItem);
    }

    @Override
    public String toString() {
        return "Blueprint[" +
                "baseItem=" + baseItem + ']';
    }

}

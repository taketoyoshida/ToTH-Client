package model;

public enum EquipmentItem {
    EQUIPMENT1(1, "equipment1", EquipmentPosition.HEAD),
    EQUIPMENT2(2, "equipment2", EquipmentPosition.BODY),
    EQUIPMENT3(3, "equipment3", EquipmentPosition.RIGHT_HAND),
    EQUIPMENT4(4, "equipment4", EquipmentPosition.LEFT_HAND);

    public final int ID;
    public final String name;
    public final EquipmentPosition position;

    EquipmentItem(int id, String name, EquipmentPosition pos) {
        this.ID = id;
        this.name = name;
        this.position = pos;
    }
}

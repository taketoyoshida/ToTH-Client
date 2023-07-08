package model;

public enum EquipmentItem {
    LEATHER_HELMET(1, "皮のヘルメット", EquipmentPosition.HEAD),
    COPPER_HELMET(2, "銅のヘルメット", EquipmentPosition.HEAD),
    IRON_HELMET(3, "鉄のヘルメット", EquipmentPosition.HEAD),
    DIAMOND_HELMET(4, "ダイヤのヘルメット", EquipmentPosition.HEAD),
    LEATHER_ARMOR(5, "皮のアーマー", EquipmentPosition.BODY),
    COPPER_ARMOR(6, "銅のアーマー", EquipmentPosition.BODY),
    IRON_ARMOR(7, "鉄のアーマー", EquipmentPosition.BODY),
    DIAMOND_ARMOR(8, "ダイヤのアーマー", EquipmentPosition.BODY),
    WOOD_SWORD(9, "木の剣", EquipmentPosition.RIGHT_HAND),
    IRON_SWORD(10, "鉄の剣", EquipmentPosition.RIGHT_HAND),
    DIAMOND_SWORD(11, "ダイヤの剣", EquipmentPosition.RIGHT_HAND),
    WOOD_SPEAR(12, "木の槍", EquipmentPosition.RIGHT_HAND),
    IRON_SPEAR(13, "鉄の槍", EquipmentPosition.RIGHT_HAND),
    DIAMOND_SPEAR(14, "ダイヤの槍", EquipmentPosition.RIGHT_HAND),
    WOOD_ARROW(15, "木の弓", EquipmentPosition.RIGHT_HAND),
    IRON_ARROW(16, "鉄の弓", EquipmentPosition.RIGHT_HAND),
    DIAMOND_ARROW(17, "ダイヤの弓", EquipmentPosition.RIGHT_HAND),
    WOOD_SHIELD(18, "木の盾", EquipmentPosition.LEFT_HAND),
    IRON_SHIELD(19, "鉄の盾", EquipmentPosition.LEFT_HAND),
    DIAMOND_SHIELD(20, "ダイヤの盾", EquipmentPosition.LEFT_HAND),
    WOOD_DAGGER(21, "木の短剣", EquipmentPosition.LEFT_HAND),
    IRON_DAGGER(22, "鉄の短剣", EquipmentPosition.LEFT_HAND),
    DIAMOND_DAGGER(23, "ダイヤの短剣", EquipmentPosition.LEFT_HAND);

    public final int ID;
    public final String name;
    public final EquipmentPosition position;

    EquipmentItem(int id, String name, EquipmentPosition pos) {
        this.ID = id;
        this.name = name;
        this.position = pos;
    }
}

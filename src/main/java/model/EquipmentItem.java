package model;

public enum EquipmentItem {
    LEATHER_HELMET(1, "皮のヘルメット", EquipmentPosition.HEAD, 2, 0, 0, 2, 0),
    COPPER_HELMET(2, "銅のヘルメット", EquipmentPosition.HEAD, 4, 0, 0, 4, 2),
    IRON_HELMET(3, "鉄のヘルメット", EquipmentPosition.HEAD, 4, 3, 0, 4, 1),
    DIAMOND_HELMET(4, "ダイヤのヘルメット", EquipmentPosition.HEAD, 4, 4, 2, 6, 1),
    LEATHER_ARMOR(5, "皮のアーマー", EquipmentPosition.BODY, 4, 0, 0, 4, 0),
    COPPER_ARMOR(6, "銅のアーマー", EquipmentPosition.BODY, 6, 0, 0, 6, 4),
    IRON_ARMOR(7, "鉄のアーマー", EquipmentPosition.BODY, 6, 5, 0, 6, 2),
    DIAMOND_ARMOR(8, "ダイヤのアーマー", EquipmentPosition.BODY, 6, 5, 3, 8, 2),
    WOOD_SWORD(9, "木の剣", EquipmentPosition.RIGHT_HAND, 4, 0, 0, 0, 0),
    IRON_SWORD(10, "鉄の剣", EquipmentPosition.RIGHT_HAND, 2, 4, 0, 0, 1),
    DIAMOND_SWORD(11, "ダイヤの剣", EquipmentPosition.RIGHT_HAND, 2, 2, 4, 0, 0),
    WOOD_SPEAR(12, "木の槍", EquipmentPosition.RIGHT_HAND, 3, 0, 0, 0, 0),
    IRON_SPEAR(13, "鉄の槍", EquipmentPosition.RIGHT_HAND, 2, 4, 0, 0, 1),
    DIAMOND_SPEAR(14, "ダイヤの槍", EquipmentPosition.RIGHT_HAND, 2, 2, 5, 0, 2),
    WOOD_ARROW(15, "木の弓", EquipmentPosition.RIGHT_HAND, 5, 0, 0, 2, 0),
    IRON_ARROW(16, "鉄の弓", EquipmentPosition.RIGHT_HAND, 3, 4, 0, 2, 0),
    DIAMOND_ARROW(17, "ダイヤの弓", EquipmentPosition.RIGHT_HAND, 0, 2, 6, 2, 2),
    WOOD_SHIELD(18, "木の盾", EquipmentPosition.LEFT_HAND, 4, 1, 0, 0, 0),
    IRON_SHIELD(19, "鉄の盾", EquipmentPosition.LEFT_HAND, 2, 4, 0, 0, 1),
    DIAMOND_SHIELD(20, "ダイヤの盾", EquipmentPosition.LEFT_HAND, 0, 2, 6, 0, 2),
    WOOD_DAGGER(21, "木の短剣", EquipmentPosition.LEFT_HAND, 2, 0, 0, 0, 0),
    IRON_DAGGER(22, "鉄の短剣", EquipmentPosition.LEFT_HAND, 2, 3, 0, 0, 1),
    DIAMOND_DAGGER(23, "ダイヤの短剣", EquipmentPosition.LEFT_HAND, 1, 1, 3, 0, 3);

    public final int ID;
    public final String name;
    public final EquipmentPosition position;
    public final int req_wood, req_iron, req_diamond, req_leather, req_copper;

    EquipmentItem(
            int id,
            String name,
            EquipmentPosition pos,
            int wood, int iron, int diamond, int leather, int copper
    ) {
        this.ID = id;
        this.name = name;
        this.position = pos;
        this.req_wood = wood;
        this.req_iron = iron;
        this.req_diamond = diamond;
        this.req_leather = leather;
        this.req_copper = copper;
    }
}

package model;

public enum EquipmentItem {
    leatherHelmet(1, "皮のヘルメット", EquipmentPosition.HEAD),
    copperHelmet(2, "銅のヘルメット", EquipmentPosition.HEAD),
    ironHelmet(3, "鉄のヘルメット", EquipmentPosition.HEAD),
    diamondHelmet(4, "ダイヤのヘルメット", EquipmentPosition.HEAD),
    leatherArmor(5, "皮のアーマー", EquipmentPosition.BODY),
    copperArmor(6, "銅のアーマー", EquipmentPosition.BODY),
    ironArmor(7, "鉄のアーマー", EquipmentPosition.BODY),
    diamondArmor(8, "ダイヤのアーマー", EquipmentPosition.BODY),
    woodSword(9, "木の剣", EquipmentPosition.RIGHT_HAND),
    ironSword(10, "鉄の剣", EquipmentPosition.RIGHT_HAND),
    diamondSword(11, "ダイヤの剣", EquipmentPosition.RIGHT_HAND),
    woodSpear(12, "木の槍", EquipmentPosition.RIGHT_HAND),
    ironSpear(13, "鉄の槍", EquipmentPosition.RIGHT_HAND),
    diamondSpear(14, "ダイヤの槍", EquipmentPosition.RIGHT_HAND),
    woodArrow(15, "木の弓", EquipmentPosition.RIGHT_HAND),
    ironArrow(16, "鉄の弓", EquipmentPosition.RIGHT_HAND),
    diamondArrow(17, "ダイヤの弓", EquipmentPosition.RIGHT_HAND),
    woodShield(18, "木の盾", EquipmentPosition.LEFT_HAND),
    ironShield(19, "鉄の盾", EquipmentPosition.LEFT_HAND),
    diamondShield(20, "ダイヤの盾", EquipmentPosition.LEFT_HAND),
    woodDagger(21, "木の短剣", EquipmentPosition.LEFT_HAND),
    ironDagger(22, "鉄の短剣", EquipmentPosition.LEFT_HAND),
    diamondDagger(23, "ダイヤの短剣", EquipmentPosition.LEFT_HAND);

    public final int ID;
    public final String name;
    public final EquipmentPosition position;

    EquipmentItem(int id, String name, EquipmentPosition pos) {
        this.ID = id;
        this.name = name;
        this.position = pos;
    }
}

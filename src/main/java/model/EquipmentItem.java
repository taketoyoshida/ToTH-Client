package model;

public enum EquipmentItem {
    LEATHER_HELMET(1, "皮のヘルメット", EquipmentPosition.HEAD, 2, 0, 0, 2, 0, "leather_helmet.png"),
    COPPER_HELMET(2, "銅のヘルメット", EquipmentPosition.HEAD, 4, 0, 0, 4, 2, "copper_helmet.png"),
    IRON_HELMET(3, "鉄のヘルメット", EquipmentPosition.HEAD, 4, 3, 0, 4, 1, "iron_helmet.png"),
    DIAMOND_HELMET(4, "ダイヤのヘルメット", EquipmentPosition.HEAD, 4, 4, 2, 6, 1, "diamond_helmet.png"),
    LEATHER_ARMOR(5, "皮のアーマー", EquipmentPosition.BODY, 4, 0, 0, 4, 0, "leather_armor.png"),
    COPPER_ARMOR(6, "銅のアーマー", EquipmentPosition.BODY, 6, 0, 0, 6, 4, "copper_armor.png"),
    IRON_ARMOR(7, "鉄のアーマー", EquipmentPosition.BODY, 6, 5, 0, 6, 2, "iron_armor.png"),
    DIAMOND_ARMOR(8, "ダイヤのアーマー", EquipmentPosition.BODY, 6, 5, 3, 8, 2, "diamond_armor.png"),
    WOOD_SWORD(9, "木の剣", EquipmentPosition.RIGHT_HAND, 4, 0, 0, 0, 0, "wood_sword.png"),
    IRON_SWORD(10, "鉄の剣", EquipmentPosition.RIGHT_HAND, 2, 4, 0, 0, 1, "iron_sword.png"),
    DIAMOND_SWORD(11, "ダイヤの剣", EquipmentPosition.RIGHT_HAND, 2, 2, 4, 0, 0, "diamond_sword.png"),
    WOOD_SPEAR(12, "木の槍", EquipmentPosition.RIGHT_HAND, 3, 0, 0, 0, 0, "wood_spear.png"),
    IRON_SPEAR(13, "鉄の槍", EquipmentPosition.RIGHT_HAND, 2, 4, 0, 0, 1, "iron_spear.png"),
    DIAMOND_SPEAR(14, "ダイヤの槍", EquipmentPosition.RIGHT_HAND, 2, 2, 5, 0, 2, "diamond_spear.png"),
    WOOD_ARROW(15, "木の弓", EquipmentPosition.RIGHT_HAND, 5, 0, 0, 2, 0, "wood_arrow.png"),
    IRON_ARROW(16, "鉄の弓", EquipmentPosition.RIGHT_HAND, 3, 4, 0, 2, 0, "iron_arrow.png"),
    DIAMOND_ARROW(17, "ダイヤの弓", EquipmentPosition.RIGHT_HAND, 0, 2, 6, 2, 2, "diamond_arrow.png"),
    WOOD_SHIELD(18, "木の盾", EquipmentPosition.LEFT_HAND, 4, 1, 0, 0, 0, "wood_shield.png"),
    IRON_SHIELD(19, "鉄の盾", EquipmentPosition.LEFT_HAND, 2, 4, 0, 0, 1, "iron_shield.png"),
    DIAMOND_SHIELD(20, "ダイヤの盾", EquipmentPosition.LEFT_HAND, 0, 2, 6, 0, 2, "diamond_shield.png"),
    WOOD_DAGGER(21, "木の短剣", EquipmentPosition.LEFT_HAND, 2, 0, 0, 0, 0, "wood_dagger.png"),
    IRON_DAGGER(22, "鉄の短剣", EquipmentPosition.LEFT_HAND, 2, 3, 0, 0, 1, "iron_dagger.png"),
    DIAMOND_DAGGER(23, "ダイヤの短剣", EquipmentPosition.LEFT_HAND, 1, 1, 3, 0, 3, "diamond_dagger.png");

    public final int ID;
    public final String name;
    public final EquipmentPosition position;
    public final int req_wood, req_iron, req_diamond, req_leather, req_copper;
    public final String imgPath;


    EquipmentItem(
            int id,
            String name,
            EquipmentPosition pos,
            int wood, int iron, int diamond, int leather, int copper, String imgPath
    ) {
        this.ID = id;
        this.name = name;
        this.position = pos;
        this.req_wood = wood;
        this.req_iron = iron;
        this.req_diamond = diamond;
        this.req_leather = leather;
        this.req_copper = copper;
        this.imgPath = imgPath;
    }

    public String getAssetPath() {
        return "./assets/imgs/equips/" + this.imgPath;
    }

    public Status getBaseStatus() {
        // TODO: Equipmentに応じて値を変更する必要がある。
        return new Status(20, 20, 20, 20);
    }

    public Status getStatus(int level) {
        level -= 1;
        Status status = this.getBaseStatus();
        switch (this) {//装備名で上げ幅を決定
            case WOOD_SWORD, WOOD_SPEAR, WOOD_ARROW, WOOD_DAGGER -> {
                status.setHP(status.getHP() + level);
                status.setATK(status.getATK() + level);
            }
            case IRON_SWORD, IRON_ARROW, IRON_SPEAR, IRON_DAGGER -> {
                status.setHP(status.getHP() + level * 3);
                status.setATK(status.getATK() + level * 3);
            }
            case DIAMOND_SWORD, DIAMOND_SPEAR, DIAMOND_ARROW, DIAMOND_DAGGER -> {
                status.setHP(status.getHP() + level * 5);
                status.setATK(status.getATK() + level * 5);
            }
            case WOOD_SHIELD, LEATHER_HELMET, LEATHER_ARMOR -> {
                status.setHP(status.getHP() + level);
            }
            case IRON_SHIELD, IRON_ARMOR, IRON_HELMET -> {
                status.setHP(status.getHP() + level * 3);
            }
            case DIAMOND_HELMET, DIAMOND_SHIELD, DIAMOND_ARMOR -> {
                status.setHP(status.getHP() + level * 5);
            }
            case COPPER_ARMOR, COPPER_HELMET -> {
                status.setHP(status.getHP() + level * 2);
            }
        }
        return status;
    }
}

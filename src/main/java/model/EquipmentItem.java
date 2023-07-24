package model;

public enum EquipmentItem {
    LEATHER_HELMET(1, "皮のヘルメット", EquipmentPosition.HEAD, 2, 0, 0, 2, 0, "leather_helmet.png", new Status(5, 0, 1, 0)),
    BRONZE_HELMET(2, "銅のヘルメット", EquipmentPosition.HEAD, 4, 0, 0, 4, 2, "bronze_helmet.png", new Status(10, 0, 1, 0)),
    IRON_HELMET(3, "鉄のヘルメット", EquipmentPosition.HEAD, 4, 3, 0, 4, 1, "iron_helmet.png", new Status(20, 0, 0, 0)),
    DIAMOND_HELMET(4, "ダイヤのヘルメット", EquipmentPosition.HEAD, 4, 4, 2, 6, 1, "diamond_helmet.png", new Status(25, 0, 1, 0)),
    LEATHER_ARMOR(5, "皮のアーマー", EquipmentPosition.BODY, 4, 0, 0, 4, 0, "leather_armor.png", new Status(10, 0, 2, 0)),
    BRONZE_ARMOR(6, "銅のアーマー", EquipmentPosition.BODY, 6, 0, 0, 6, 4, "bronze_armor.png", new Status(15, 0, 1, 0)),
    IRON_ARMOR(7, "鉄のアーマー", EquipmentPosition.BODY, 6, 5, 0, 6, 2, "iron_armor.png", new Status(30, 0, 0, 0)),
    DIAMOND_ARMOR(8, "ダイヤのアーマー", EquipmentPosition.BODY, 6, 5, 3, 8, 2, "diamond_armor.png", new Status(40, 0, 1, 0)),
    WOOD_SWORD(9, "木の剣", EquipmentPosition.RIGHT_HAND, 4, 0, 0, 0, 0, "wood_sword.png", new Status(0, 3, 1, 0)),
    IRON_SWORD(10, "鉄の剣", EquipmentPosition.RIGHT_HAND, 2, 4, 0, 0, 1, "iron_sword.png", new Status(0, 10, 0, 0)),
    DIAMOND_SWORD(11, "ダイヤの剣", EquipmentPosition.RIGHT_HAND, 2, 2, 4, 0, 0, "diamond_sword.png", new Status(0, 20, 0, 1)),
    WOOD_SPEAR(12, "木の槍", EquipmentPosition.RIGHT_HAND, 3, 0, 0, 0, 0, "wood_spear.png", new Status(0, 3, 0, 1)),
    IRON_SPEAR(13, "鉄の槍", EquipmentPosition.RIGHT_HAND, 2, 4, 0, 0, 1, "iron_spear.png", new Status(0, 10, 0, 1)),
    DIAMOND_SPEAR(14, "ダイヤの槍", EquipmentPosition.RIGHT_HAND, 2, 2, 5, 0, 2, "diamond_spear.png", new Status(0, 20, 0, 2)),
    WOOD_ARROW(15, "木の弓", EquipmentPosition.RIGHT_HAND, 5, 0, 0, 2, 0, "wood_arrow.png", new Status(0, 5, 0, 2)),
    IRON_ARROW(16, "鉄の弓", EquipmentPosition.RIGHT_HAND, 3, 4, 0, 2, 0, "iron_arrow.png", new Status(0, 15, 0, 3)),
    DIAMOND_ARROW(17, "ダイヤの弓", EquipmentPosition.RIGHT_HAND, 0, 2, 6, 2, 2, "diamond_arrow.png", new Status(0, 25, 0, 3)),
    WOOD_SHIELD(18, "木の盾", EquipmentPosition.LEFT_HAND, 4, 1, 0, 0, 0, "wood_shield.png", new Status(3, 0, 0, 0)),
    IRON_SHIELD(19, "鉄の盾", EquipmentPosition.LEFT_HAND, 2, 4, 0, 0, 1, "iron_shield.png", new Status(10, 0, 0, 0)),
    DIAMOND_SHIELD(20, "ダイヤの盾", EquipmentPosition.LEFT_HAND, 0, 2, 6, 0, 2, "diamond_shield.png", new Status(30, 0, 0, 0)),
    WOOD_DAGGER(21, "木の短剣", EquipmentPosition.LEFT_HAND, 2, 0, 0, 0, 0, "wood_dagger.png", new Status(0, 2, 0, 0)),
    IRON_DAGGER(22, "鉄の短剣", EquipmentPosition.LEFT_HAND, 2, 3, 0, 0, 1, "iron_dagger.png", new Status(0, 8, 0, 0)),
    DIAMOND_DAGGER(23, "ダイヤの短剣", EquipmentPosition.LEFT_HAND, 1, 1, 3, 0, 3, "diamond_dagger.png", new Status(0, 15, 0, 0));

    public static final int LENGTH = EquipmentItem.values().length;
    public final int ID;
    public final String name;
    public final EquipmentPosition position;
    public final int req_wood, req_iron, req_diamond, req_leather, req_bronze;
    public final String imgPath;
    public final Status baseStatus;


    EquipmentItem(
            int id,
            String name,
            EquipmentPosition pos,
            int wood, int iron, int diamond, int leather, int bronze, String imgPath, Status baseStatus
    ) {
        this.ID = id;
        this.name = name;
        this.position = pos;
        this.req_wood = wood;
        this.req_iron = iron;
        this.req_diamond = diamond;
        this.req_leather = leather;
        this.req_bronze = bronze;
        this.imgPath = imgPath;
        this.baseStatus = baseStatus;
    }

    public String getAssetPath() {
        return "./assets/imgs/equips/" + this.imgPath;
    }

    public Status getStatus(int level) {
        level -= 1;
        Status status = new Status(baseStatus.getHP(),baseStatus.getATK(),
                baseStatus.getMOV(),baseStatus.getRNG());


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
            case BRONZE_ARMOR, BRONZE_HELMET -> {
                status.setHP(status.getHP() + level * 2);
            }
        }
        return status;
    }

    public EquipmentPosition getPosition() {
        return this.position;
    }
}

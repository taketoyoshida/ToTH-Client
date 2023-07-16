package model;

import model.util.User;
import model.EquipmentItem;

public class Equipment {
    public final EquipmentItem item;
    private Status status;
    private int level;

    public Equipment(EquipmentItem item, Status sta, int level) {
        this.item = item;
        this.status = sta;
        this.level = level;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status sta) {
        this.status = sta;
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

    public static Equipment createEquipment(User user, Blueprint blueprint) throws Exception {
        if (user.getMaterialQuantity(Material.WOOD) >= blueprint.baseItem().req_wood && user.getMaterialQuantity(Material.IRON) >= blueprint.baseItem().req_iron && user.getMaterialQuantity(Material.DIAMOND) >= blueprint.baseItem().req_diamond && user.getMaterialQuantity(Material.BRONZE) >= blueprint.baseItem().req_copper && user.getMaterialQuantity(Material.LEATHER) >= blueprint.baseItem().req_leather) {
            user.consumeMaterial(Material.WOOD, blueprint.baseItem().req_wood);
            user.consumeMaterial(Material.IRON, blueprint.baseItem().req_iron);
            user.consumeMaterial(Material.DIAMOND, blueprint.baseItem().req_diamond);
            user.consumeMaterial(Material.BRONZE, blueprint.baseItem().req_copper);
            user.consumeMaterial(Material.LEATHER, blueprint.baseItem().req_leather);
            user.removeBlueprint(blueprint, 1);
            // TODO: Dummy Status should be replaced, refer #46
            return new Equipment(blueprint.baseItem(), new Status(20, 20, 20, 20), 1);
        }
        throw new Exception("素材が足りません");
    }

    Equipment leatherHelmet = new Equipment(EquipmentItem.LEATHER_HELMET, new Status(5, 0, 1, 0), 0);
    Equipment copperHelmet = new Equipment(EquipmentItem.COPPER_HELMET, new Status(10, 0, 1, 0), 0);
    Equipment ironHelmet = new Equipment(EquipmentItem.IRON_HELMET, new Status(20, 0, 0, 0), 0);
    Equipment diamondHelmet = new Equipment(EquipmentItem.DIAMOND_HELMET, new Status(25, 0, 1, 0), 0);
    Equipment leatherArmor = new Equipment(EquipmentItem.LEATHER_ARMOR, new Status(10, 0, 2, 0), 0);
    Equipment copperArmor = new Equipment(EquipmentItem.COPPER_ARMOR, new Status(15, 0, 1, 0), 0);
    Equipment ironArmor = new Equipment(EquipmentItem.IRON_ARMOR, new Status(30, 0, 0, 0), 0);
    Equipment diamondArmor = new Equipment(EquipmentItem.DIAMOND_ARMOR, new Status(40, 0, 1, 0), 0);
    Equipment woodSword = new Equipment(EquipmentItem.WOOD_SWORD, new Status(0, 3, 0, 0), 0);
    Equipment ironSword = new Equipment(EquipmentItem.IRON_SWORD, new Status(0, 10, 0, 0), 0);
    Equipment diamondSword = new Equipment(EquipmentItem.DIAMOND_SWORD, new Status(0, 20, 0, 1), 0);
    Equipment woodSpear = new Equipment(EquipmentItem.WOOD_SPEAR, new Status(0, 3, 0, 1), 0);
    Equipment ironSpear = new Equipment(EquipmentItem.IRON_SPEAR, new Status(0, 10, 0, 1), 0);
    Equipment diamondSpear = new Equipment(EquipmentItem.DIAMOND_SPEAR, new Status(0, 20, 0, 2), 0);
    Equipment woodArrow = new Equipment(EquipmentItem.WOOD_ARROW, new Status(0, 5, 0, 2), 0);
    Equipment ironArrow = new Equipment(EquipmentItem.IRON_ARROW, new Status(0, 15, 0, 3), 0);
    Equipment diamondArrow = new Equipment(EquipmentItem.DIAMOND_ARROW, new Status(0, 25, 0, 3), 0);
    Equipment woodShield = new Equipment(EquipmentItem.WOOD_SHIELD, new Status(3, 0, 0, 0), 0);
    Equipment ironShield = new Equipment(EquipmentItem.IRON_SHIELD, new Status(10, 0, 0, 0), 0);
    Equipment diamondShield = new Equipment(EquipmentItem.DIAMOND_SHIELD, new Status(30, 0, 0, 0), 0);
    Equipment woodDagger = new Equipment(EquipmentItem.WOOD_DAGGER, new Status(0, 2, 0, 0), 0);
    Equipment ironDagger = new Equipment(EquipmentItem.IRON_DAGGER, new Status(0, 8, 0, 0), 0);
    Equipment diamondDagger = new Equipment(EquipmentItem.DIAMOND_DAGGER, new Status(0, 15, 0, 0), 0);

}

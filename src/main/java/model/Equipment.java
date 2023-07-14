package model;

import model.util.User;

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
        if (user.getMaterialQuantity(Material.WOOD) >= blueprint.baseItem().req_wood
                && user.getMaterialQuantity(Material.IRON) >= blueprint.baseItem().req_iron
                && user.getMaterialQuantity(Material.DIAMOND) >= blueprint.baseItem().req_diamond
                && user.getMaterialQuantity(Material.COPPER) >= blueprint.baseItem().req_copper &&
                user.getMaterialQuantity(Material.LEATHER) >= blueprint.baseItem().req_leather) {
            user.consumeMaterial(Material.WOOD, blueprint.baseItem().req_wood);
            user.consumeMaterial(Material.IRON, blueprint.baseItem().req_iron);
            user.consumeMaterial(Material.DIAMOND, blueprint.baseItem().req_diamond);
            user.consumeMaterial(Material.COPPER, blueprint.baseItem().req_copper);
            user.consumeMaterial(Material.LEATHER, blueprint.baseItem().req_leather);
            user.removeBlueprint(blueprint, 1);
            // TODO: Dummy Status should be replaced, refer #46
            return new Equipment(blueprint.baseItem(), new Status(20, 20, 20, 20), 1);
        }
        throw new Exception("素材が足りません");
    }
}

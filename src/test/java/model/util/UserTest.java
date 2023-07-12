package model.util;

import model.Blueprint;
import model.EquipmentItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserTest {
    @Test
    void testBlueprintManagement() {
        User user = new User(1, "test", 100000, 1);
        user.addBlueprint(new Blueprint(EquipmentItem.COPPER_HELMET), 1);
        user.addBlueprint(new Blueprint(EquipmentItem.COPPER_HELMET), 1);
        user.addBlueprint(new Blueprint(EquipmentItem.COPPER_HELMET), 1);
        user.addBlueprint(new Blueprint(EquipmentItem.DIAMOND_DAGGER), 1);
        assertEquals(user.getBlueprintQuantity(new Blueprint(EquipmentItem.COPPER_HELMET)), 3);
        assertEquals(user.getBlueprintQuantity(new Blueprint(EquipmentItem.DIAMOND_DAGGER)), 1);
        assert user.removeBlueprint(new Blueprint(EquipmentItem.COPPER_HELMET), 1);
        assertEquals(user.getBlueprintQuantity(new Blueprint(EquipmentItem.COPPER_HELMET)), 2);
        assert user.removeBlueprint(new Blueprint(EquipmentItem.COPPER_HELMET), 2);
        assertEquals(user.getBlueprintQuantity(new Blueprint(EquipmentItem.COPPER_HELMET)), 0);
        assert !user.removeBlueprint(new Blueprint(EquipmentItem.DIAMOND_DAGGER), 2);
    }
}
package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentItemTest {
    @Test
    void testGetStatus() {
        EquipmentItem item1 = EquipmentItem.COPPER_HELMET;
        EquipmentItem item2 = EquipmentItem.WOOD_ARROW;
        EquipmentItem item3 = EquipmentItem.DIAMOND_ARMOR;
        assertEquals(item1.getStatus(3), item1.getStatus(3));
        assertEquals(item1.getStatus(3), item1.getStatus(3));
        assertEquals(item1.getStatus(3), item1.getStatus(3));
        assertEquals(item2.getStatus(3), item2.getStatus(3));
        assertEquals(item2.getStatus(3), item2.getStatus(3));
        assertEquals(item2.getStatus(3), item2.getStatus(3));
        assertEquals(item3.getStatus(3), item3.getStatus(3));
        assertEquals(item3.getStatus(3), item3.getStatus(3));
        assertEquals(item3.getStatus(3), item3.getStatus(3));
    }
}
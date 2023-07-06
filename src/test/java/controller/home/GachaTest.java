package controller.home;

import model.EquipmentItem;
import model.util.User;

import java.util.EnumMap;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GachaTest {

    @org.junit.jupiter.api.Test
    void play() {
        User user = new User(1, "test", 100000, 1);
        // test Gacha.play(user) will not result in error for 10times.
        EnumMap<EquipmentItem, Integer> count = new EnumMap<>(EquipmentItem.class);
        for (int i = 0; i < 1000; i++) {
            try {
                var result = Gacha.play(user);
                count.put(result.bp.baseItem, count.getOrDefault(result.bp.baseItem, 0) + 1);
            } catch (Exception e) {
                fail("Gacha.play(user) should not result in error");
            }
        }
        for (var item : EquipmentItem.values()) {
            System.out.println(item.name() + " : " + count.getOrDefault(item, 0));
        }
    }
}
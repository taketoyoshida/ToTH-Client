package controller.home;

import model.Blueprint;
import model.EquipmentItem;
import model.util.User;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class GachaMock {

    private static final TreeMap<Double, EquipmentItem> probabilitySheet;

    static {
        probabilitySheet = new TreeMap<>();
        // orders does not matter as it uses TreeMap
        probabilitySheet.put(0.1, EquipmentItem.LEATHER_HELMET);
        probabilitySheet.put(0.15, EquipmentItem.BRONZE_HELMET);
        probabilitySheet.put(0.18, EquipmentItem.IRON_HELMET);
        probabilitySheet.put(0.19, EquipmentItem.DIAMOND_HELMET);
        probabilitySheet.put(0.26, EquipmentItem.LEATHER_ARMOR);
        probabilitySheet.put(0.31, EquipmentItem.BRONZE_ARMOR);
        probabilitySheet.put(0.34, EquipmentItem.IRON_ARMOR);
        probabilitySheet.put(0.35, EquipmentItem.DIAMOND_ARMOR);
        probabilitySheet.put(0.43, EquipmentItem.WOOD_SWORD);
        probabilitySheet.put(0.47, EquipmentItem.IRON_SWORD);
        probabilitySheet.put(0.48, EquipmentItem.DIAMOND_SWORD);
        probabilitySheet.put(0.56, EquipmentItem.WOOD_SPEAR);
        probabilitySheet.put(0.60, EquipmentItem.IRON_SPEAR);
        probabilitySheet.put(0.61, EquipmentItem.DIAMOND_SPEAR);
        probabilitySheet.put(0.69, EquipmentItem.WOOD_ARROW);
        probabilitySheet.put(0.73, EquipmentItem.IRON_ARROW);
        probabilitySheet.put(0.74, EquipmentItem.DIAMOND_ARROW);
        probabilitySheet.put(0.82, EquipmentItem.WOOD_SHIELD);
        probabilitySheet.put(0.86, EquipmentItem.IRON_SHIELD);
        probabilitySheet.put(0.87, EquipmentItem.DIAMOND_SHIELD);
        probabilitySheet.put(0.95, EquipmentItem.WOOD_DAGGER);
        probabilitySheet.put(0.99, EquipmentItem.IRON_DAGGER);
        probabilitySheet.put(1.00, EquipmentItem.DIAMOND_DAGGER);

    }

    public static GachaResult play(User user) throws Exception {//ガチャを引くシステム
        user.mutateBalance(-100);//コイン消費
        Random rand = new Random();
        double randomValue = rand.nextDouble() * probabilitySheet.lastKey();
        Map.Entry<Double, EquipmentItem> entry = probabilitySheet.higherEntry(randomValue);
        if (entry == null) throw new Exception("Unexpected gacha result");
        EquipmentItem item = entry.getValue();
        return new GachaResult(new Blueprint(item));
    }

    public static class GachaResult {
        public final Blueprint bp;

        public GachaResult(Blueprint bp) {
            this.bp = bp;
        }
    }
}

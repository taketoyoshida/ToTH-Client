package controller.home;

import graphql.org.antlr.v4.runtime.tree.Tree;
import model.Blueprint;
import model.EquipmentItem;
import model.util.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Gacha {

    private static final TreeMap<Double, EquipmentItem> probabilitySheet;

    static {
        probabilitySheet = new TreeMap<>();
        // orders does not matter as it uses TreeMap
        probabilitySheet.put(0.1, EquipmentItem.LEATHER_HELMET);
        probabilitySheet.put(0.05, EquipmentItem.COPPER_HELMET);
        probabilitySheet.put(0.03, EquipmentItem.IRON_HELMET);
        probabilitySheet.put(0.01, EquipmentItem.DIAMOND_HELMET);
        probabilitySheet.put(0.07, EquipmentItem.LEATHER_ARMOR);
        probabilitySheet.put(0.05, EquipmentItem.COPPER_ARMOR);
        probabilitySheet.put(0.03, EquipmentItem.IRON_ARMOR);
        probabilitySheet.put(0.01, EquipmentItem.DIAMOND_ARMOR);
        probabilitySheet.put(0.08, EquipmentItem.WOOD_SWORD);
        probabilitySheet.put(0.04, EquipmentItem.IRON_SWORD);
        probabilitySheet.put(0.01, EquipmentItem.DIAMOND_SWORD);
        probabilitySheet.put(0.08, EquipmentItem.WOOD_SPEAR);
        probabilitySheet.put(0.04, EquipmentItem.IRON_SPEAR);
        probabilitySheet.put(0.01, EquipmentItem.DIAMOND_SPEAR);
        probabilitySheet.put(0.08, EquipmentItem.WOOD_ARROW);
        probabilitySheet.put(0.04, EquipmentItem.IRON_ARROW);
        probabilitySheet.put(0.01, EquipmentItem.DIAMOND_ARROW);
        probabilitySheet.put(0.08, EquipmentItem.WOOD_SHIELD);
        probabilitySheet.put(0.04, EquipmentItem.IRON_SHIELD);
        probabilitySheet.put(0.01, EquipmentItem.DIAMOND_SHIELD);
        probabilitySheet.put(0.08, EquipmentItem.WOOD_DAGGER);
        probabilitySheet.put(0.04, EquipmentItem.IRON_DAGGER);
        probabilitySheet.put(0.01, EquipmentItem.DIAMOND_DAGGER);

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
}

class GachaResult {
    public final Blueprint bp;

    public GachaResult(Blueprint bp) {
        this.bp = bp;
    }
}
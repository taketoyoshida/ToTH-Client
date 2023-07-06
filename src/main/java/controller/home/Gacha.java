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
        // this is dummy value
        // orders does not matter as it uses TreeMap
        probabilitySheet.put(0.2, EquipmentItem.EQUIPMENT1);
        probabilitySheet.put(0.5, EquipmentItem.EQUIPMENT2);
        probabilitySheet.put(0.8, EquipmentItem.EQUIPMENT3);
        probabilitySheet.put(1.0, EquipmentItem.EQUIPMENT4);
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
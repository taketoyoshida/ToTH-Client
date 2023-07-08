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
        probabilitySheet.put(0.1, EquipmentItem.leatherHelmet);
        probabilitySheet.put(0.05, EquipmentItem.copperHelmet);
        probabilitySheet.put(0.03, EquipmentItem.ironHelmet);
        probabilitySheet.put(0.01, EquipmentItem.diamondHelmet);
        probabilitySheet.put(0.07, EquipmentItem.leatherArmor);
        probabilitySheet.put(0.05, EquipmentItem.copperArmor);
        probabilitySheet.put(0.03, EquipmentItem.ironArmor);
        probabilitySheet.put(0.01, EquipmentItem.diamondArmor);
        probabilitySheet.put(0.08, EquipmentItem.woodSword);
        probabilitySheet.put(0.04, EquipmentItem.ironSword);
        probabilitySheet.put(0.01, EquipmentItem.diamondSword);
        probabilitySheet.put(0.08, EquipmentItem.woodSpear);
        probabilitySheet.put(0.04, EquipmentItem.ironSpear);
        probabilitySheet.put(0.01, EquipmentItem.diamondSpear);
        probabilitySheet.put(0.08, EquipmentItem.woodArrow);
        probabilitySheet.put(0.04, EquipmentItem.ironArrow);
        probabilitySheet.put(0.01, EquipmentItem.diamondArrow);
        probabilitySheet.put(0.08, EquipmentItem.woodShield);
        probabilitySheet.put(0.04, EquipmentItem.ironShield);
        probabilitySheet.put(0.01, EquipmentItem.diamondShield);
        probabilitySheet.put(0.08, EquipmentItem.woodDagger);
        probabilitySheet.put(0.04, EquipmentItem.ironDagger);
        probabilitySheet.put(0.01, EquipmentItem.diamondDagger);

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
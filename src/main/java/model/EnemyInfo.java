package model;

import java.util.HashMap;
import java.util.Map;

public enum EnemyInfo {
    ENEMY_1(1, "敵1", 10, 7, 1, 1, "test.png",
            createDropRatesMap(0.5, 0.05, 0.0, 0.05, 0.05)),
    ENEMY_2(2, "敵2", 10, 5, 2, 1, "test.png",
            createDropRatesMap(0.2, 0.05, 0.0, 0.35, 0.05)),
    ENEMY_3(3, "敵3", 30, 10, 1, 2, "test.png",
            createDropRatesMap(0.55, 0.05, 0.0, 0.1, 0.05)),
    ENEMY_4(4, "敵4", 25, 15, 3, 1, "test.png",
            createDropRatesMap(0.05, 0.15, 0.02, 0.18, 0.15)),
    ENEMY_5(5, "敵5", 40, 20, 2, 2, "test.png",
            createDropRatesMap(0.3, 0.1, 0.02, 0.2, 0.1)),
    ENEMY_6(6, "敵6", 50, 15, 1, 2, "test.png",
            createDropRatesMap(0.2, 0.2, 0.04, 0.04, 0.2)),
    ENEMY_7(7, "敵7", 70, 25, 3, 2, "test.png",
            createDropRatesMap(0.35, 0.15, 0.0, 0.2, 0.2)),
    ENEMY_8(8, "敵8", 70, 20, 2, 1, "test.png",
            createDropRatesMap(0.05, 0.15, 0.1, 0.0, 0.2)),
    ENEMY_9(9, "敵9", 80, 30, 3, 2, "test.png",
            createDropRatesMap(0.1, 0.3, 0.05, 0.0, 0.3)),
    ENEMY_10(10, "敵10", 100, 40, 1, 1, "test.png",
            createDropRatesMap(0.3, 0.1, 0.15, 0.1, 0.1)),
    ENEMY_11(11, "敵11", 100, 50, 2, 2, "test.png",
            createDropRatesMap(0.2, 0.4, 0.1, 0.0, 0.3)),
    ENEMY_12(12, "敵12", 150, 20, 2, 1, "test.png",
            createDropRatesMap(0.1, 0.0, 0.3, 0.2, 0.0));


    public final int ID;
    public final String name;
    public final int HP;
    public final int ATK;
    public final int MOV;
    public final int RNG;
    public final Position POS;
    public String imgPath;
    public final Map<Material, Double> dropRates;

    EnemyInfo(int id, String name, int hp, int atk, int mov, int rng, String imgPath, Map<Material, Double> dropRates) {
        this.ID = id;
        this.name = name;
        this.HP = hp;
        this.ATK = atk;
        this.MOV = mov;
        this.RNG = rng;
        this.POS = null;
        this.imgPath = imgPath;
        this.dropRates = dropRates;
    }

    public String getAssetPath() {
        return "./assets/imgs/enemy" + this.imgPath;
    }

    public Map<Material, Double> getDropRates() {
        return dropRates;
    }

    private static Map<Material, Double> createDropRatesMap(double woodRate, double ironRate,
                                                            double diamondRate, double leatherRate, double bronzeRate) {
        Map<Material, Double> dropRates = new HashMap<>();
        dropRates.put(Material.WOOD, woodRate);
        dropRates.put(Material.IRON, ironRate);
        dropRates.put(Material.DIAMOND, diamondRate);
        dropRates.put(Material.LEATHER, leatherRate);
        dropRates.put(Material.BRONZE, bronzeRate);

        double noDropRate = 1.0 - (woodRate + ironRate + diamondRate + leatherRate + bronzeRate);
        dropRates.put(null, noDropRate);

        return dropRates;
    }
}



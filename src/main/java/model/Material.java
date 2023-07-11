package model;

public enum Material {
    WOOD("木","material_sample.png"),
    IRON("鉄","material_sample.png"),
    DIAMOND("ダイヤ","material_sample.png"),
    LEATHER("皮","material_sample.png"),
    COPPER("銅","material_sample.png");

    private final String name;
    public final String imgPath;

    Material(String name, String imgPath) {
        this.name = name;
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public String getAssetPath() {
        return ".assets/imgs/materials/" + this.imgPath;
    }
}


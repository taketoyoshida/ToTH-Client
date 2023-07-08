package model;

public enum Material {
    WOOD("木"),
    IRON("鉄"),
    DIAMOND("ダイヤ"),
    LEATHER("皮"),
    COPPER("銅");

    private final String name;

    Material(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


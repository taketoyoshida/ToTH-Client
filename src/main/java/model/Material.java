package model;

public enum Material {
    wood("木"),
    iron("鉄"),
    diamond("ダイヤ"),
    leather("皮"),
    copper("銅");

    private final String name;

    Material(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


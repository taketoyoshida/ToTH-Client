package model;

public enum Material {
    MATERIAL1("Material1"),
    MATERIAL2("Material2"),
    MATERIAL3("Material3"),
    MATERIAL4("Material4"),
    MATERIAL5("Material5");

    private final String name;

    Material(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


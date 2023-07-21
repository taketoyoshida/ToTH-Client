package model;

public enum Material {
    WOOD("木", "Wood.png", "<html>武器や防具の素材となる木材<br>" +
            "加工しやすく、軽くて扱いやすい<br><br>"
            + "基本的な材料だが、良い装備には欠かせない<br>" +
            "しなやかさがなくては、何物も実用品たりえないのだ"),
    IRON("鉄", "Iron.png", "<html>武器や防具の素材となる鉄材<br>" +
            "少し重いが丈夫である<br><br>"
            + "かねてより、鉄は文明と密接な関係があった<br>" +
            "その製法や産出が、戦争を産むほどに"),
    DIAMOND("ダイヤ", "Diamond.png", "<html>武器や防具の素材となる金剛石<br>" +
            "きらびやかで、とてつもなく堅牢である<br><br>"
            + "この宝石は、鮮やかに持ち主の力を誇示する<br>" +
            "装身具としても、あるいは武器としても"),
    LEATHER("革", "Leather.png", "<html>武器や防具の素材となるなめし革<br>" +
            "柔軟で、熱や水に強い<br><br>"
            + "自然に対するためには、自然の装備が好適である<br>" +
            "もっとも、狩人同士の戦いではそうとは限らない"),
    BRONZE("銅", "Bronze.png", "<html>武器や防具の素材となる青銅<br>" +
            "鋳造しやすく、そのわりに丈夫である<br><br>"
            + "鉄ほどの強度はないが、その分扱いやすい<br>" +
            "そのために、銅の鎧は駆け出しの狩人に適する");

    public static final int LENGTH = Material.values().length;

    private final String name;
    public final String imgPath;
    public final String txt;

    Material(String name, String imgPath, String txt) {
        this.name = name;
        this.imgPath = imgPath;
        this.txt = txt;
    }

    public String getName() {
        return name;
    }

    public String getAssetPath() {
        return "./assets/imgs/materials/" + this.imgPath;
    }

    public String getTxt() {
        return txt;
    }
}


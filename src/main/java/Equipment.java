public class Equipment {
    Status status01 = new Status(0, 3, 0, 0, 0);
    EquipmentData woodSword = new EquipmentData(1, "木の剣", 2, status01);
}

class EquipmentData {
    int ID;
    String name;
    int position;   // 0:頭 1:胴 2:右手 3: 左手
    Status status;

    public EquipmentData(int id, String name, int pos, Status sta) {
        this.ID = id;
        this.name = name;
        this.position = pos;
        this.status = sta;
    }
}

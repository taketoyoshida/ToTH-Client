package model;

public class Equipment {
    int ID;
    String name;
    int position;   // 0:頭 1:胴 2:右手 3: 左手
    Status status;

    public Equipment(int id, String name, int pos, Status sta) {
        this.ID = id;
        this.name = name;
        this.position = pos;
        this.status = sta;
    }
}

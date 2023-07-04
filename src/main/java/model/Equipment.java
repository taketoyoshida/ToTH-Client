package model;

public class Equipment {
    public final EquipmentItem item;
    private Status status;

    public Equipment(int id, String name, EquipmentItem item, Status sta) {
        this.item = item;
        this.status = sta;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status sta) {
        this.status = sta;
    }
}

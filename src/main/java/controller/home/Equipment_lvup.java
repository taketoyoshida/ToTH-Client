package controller.home;

import model.Status;
import model.Equipment;
import model.EquipmentItem;
import model.util.User;

public class Equipment_lvup {//装備を作成完了すぐに後に実行したい
    public Equipment lvup(User user ,Equipment equipment) {
        if(user.equipmentPossesion(equipment)) {//装備を持ってい場合実行
            switch (equipment.item.name) {//装備名で上げ幅を決定
                case "木の剣":
                case "木の槍":
                case "木の弓":
                case "木の短剣":
                    Status sta = equipment.getStatus();
                    sta.setHP(sta.getHP() + 1);
                    sta.setATK(sta.getATK() + 1);

                    equipment.updateStatus(sta);
                    break;

                case "鉄の剣":
                case "鉄の槍":
                case "鉄の弓":
                case "鉄の短剣":
                    Status sta2 = equipment.getStatus();
                    sta2.setHP(sta2.getHP() + 3);
                    sta2.setATK(sta2.getATK() + 3);

                    equipment.updateStatus(sta2);
                    break;

                case "ダイヤの剣":
                case "ダイヤの槍":
                case "ダイヤの弓":
                case "ダイヤの短剣":
                    Status sta3 = equipment.getStatus();
                    sta3.setHP(sta3.getHP() + 5);
                    sta3.setATK(sta3.getATK() + 5);

                    equipment.updateStatus(sta3);
                    break;

                case "木の盾":
                case "木のアーマー":
                case "木のヘルメット":
                    Status sta4 = equipment.getStatus();
                    sta4.setHP(sta4.getHP() + 1);

                    equipment.updateStatus(sta4);
                    break;

                case "鉄の盾":
                case "鉄のアーマー":
                case "鉄のヘルメット":
                    Status sta5 = equipment.getStatus();
                    sta5.setHP(sta5.getHP() + 3);

                    equipment.updateStatus(sta5);
                    break;

                case "ダイヤの盾":
                case "ダイヤのアーマー":
                case "ダイヤのヘルメット":
                    Status sta6 = equipment.getStatus();
                    sta6.setHP(sta6.getHP() + 5);

                    equipment.updateStatus(sta6);
                    break;

                case "皮のアーマー":
                    Status sta7 = equipment.getStatus();
                    sta7.setHP(sta7.getHP() + 2);

                    equipment.updateStatus(sta7);
                    break;



            }
        }else{
            user.addEquipment(equipment);//持っていなかった場合は装備を追加
        }
        return  equipment;
    }
}


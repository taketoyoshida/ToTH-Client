package controller.home;

import model.Status;
import model.Equipment;
import model.util.User;

public class EquipmentUpgrade {//装備を作成完了すぐに後に実行したい

    public void upgrade(User user, Equipment equipment) {
        if (user.hasEquipment(equipment)) {//装備を持っている場合実行
            switch (equipment.item) {//装備名で上げ幅を決定
                case WOOD_SWORD:
                case WOOD_SPEAR:
                case WOOD_ARROW:
                case WOOD_DAGGER:
                    Status sta = equipment.getStatus();
                    sta.setHP(sta.getHP() + 1);
                    sta.setATK(sta.getATK() + 1);
                    equipment.levelUp();

                    equipment.updateStatus(sta);
                    break;

                case IRON_SWORD:
                case IRON_ARROW:
                case IRON_SPEAR:
                case IRON_DAGGER:
                    Status sta2 = equipment.getStatus();
                    sta2.setHP(sta2.getHP() + 3);
                    sta2.setATK(sta2.getATK() + 3);
                    equipment.levelUp();

                    equipment.updateStatus(sta2);
                    break;

                case DIAMOND_SWORD:
                case DIAMOND_SPEAR:
                case DIAMOND_ARROW:
                case DIAMOND_DAGGER:
                    Status sta3 = equipment.getStatus();
                    sta3.setHP(sta3.getHP() + 5);
                    sta3.setATK(sta3.getATK() + 5);
                    equipment.levelUp();

                    equipment.updateStatus(sta3);
                    break;

                case WOOD_SHIELD:
                case LEATHER_HELMET:
                case LEATHER_ARMOR:
                    Status sta4 = equipment.getStatus();
                    sta4.setHP(sta4.getHP() + 1);
                    equipment.levelUp();

                    equipment.updateStatus(sta4);
                    break;

                case IRON_SHIELD:
                case IRON_ARMOR:
                case IRON_HELMET:
                    Status sta5 = equipment.getStatus();
                    sta5.setHP(sta5.getHP() + 3);
                    equipment.levelUp();

                    equipment.updateStatus(sta5);
                    break;

                case DIAMOND_HELMET:
                case DIAMOND_SHIELD:
                case DIAMOND_ARMOR:
                    Status sta6 = equipment.getStatus();
                    sta6.setHP(sta6.getHP() + 5);
                    equipment.levelUp();

                    equipment.updateStatus(sta6);
                    break;

                case COPPER_ARMOR:
                case COPPER_HELMET:
                    Status sta7 = equipment.getStatus();
                    sta7.setHP(sta7.getHP() + 2);
                    equipment.levelUp();

                    equipment.updateStatus(sta7);
                    break;


            }
        } else {
            //持っていなかった場合は装備を追加
            user.addEquipment(equipment);
        }
    }
}


package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;
import model.game.Equipment;
import model.util.User;

import static java.awt.Font.BOLD;

public class EquipmentDock extends JFrame implements ActionListener {

    private final WindowBase base;
    private User user;
    private int target;
    private Equipment[] slottedEquipment = new Equipment[4];
    private Equipment[] infoSlot = new Equipment[2];
    private BackButton bButton = new BackButton();
    private JLayeredPane menuPane = new JLayeredPane();
    private JLayeredPane infoPane = new JLayeredPane();
    private JLayeredPane listPane = new JLayeredPane();
    private JLayeredPane slotPane = new JLayeredPane();
    private ImageIcon iconBackground = new ImageIcon("./assets/imgs/Equipment_backImg.png");    //画像のディレクトリは調整してもろて
    private ImageIcon iconList = new ImageIcon("./assets/imgs/EquipBaseList2.png");
    private ImageIcon iconSlot1 = new ImageIcon("./assets/imgs/EquipSlot2.png");
    private ImageIcon iconSector = new ImageIcon("./assets/imgs/PlayerSlot.png");
    private ImageIcon iconTextField = new ImageIcon("./assets/imgs/DockText.png");
    private ImageIcon iconSlot2 = new ImageIcon("./assets/imgs/EquipSlot3.png");
    private ImageIcon iconSlot3 = new ImageIcon("./assets/imgs/EquipSlot4.png");
    private ImageIcon iconItem = new ImageIcon("./assets/imgs/TestItemShield.png");
    private ImageIcon iconButton = new ImageIcon("./assets/imgs/TestButton3.png");
    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(iconBackground);        //画像はlabelで取り込む
    JLabel labelSlotHead = new JLabel(iconSlot1);
    JLabel labelSlotBody = new JLabel(iconSlot1);
    JLabel labelSlotMainhand = new JLabel(iconSlot1);
    JLabel labelSlotOffhand = new JLabel(iconSlot1);
    JLabel labelSector = new JLabel(iconSector);
    JLabel textFieldLabel = new JLabel(iconTextField);
    JLabel labelSlotNow = new JLabel(iconSlot2);
    JLabel labelSlotNext = new JLabel(iconSlot2);

    JScrollPane scrollPane = new JScrollPane();
    JViewport viewport = scrollPane.getViewport();
    JButton b1 = new JButton(iconButton);
    JButton equipButton = new JButton("装備する");
    private EquipCompoundButton[] equipButtons = new EquipCompoundButton[EquipmentItem.LENGTH];
    private static EquipCompoundButton[] slotButtons = new EquipCompoundButton[4];

    Equipment[] dummy = new Equipment[4];

    private static class EquipCompoundButton extends JButton {   //原型が紐づけられたボタンの構造体
        final EquipmentItem equipment;
        final boolean isSlot;

        EquipCompoundButton(Icon icon, EquipmentItem equipment, boolean isSlot) {
            super(icon);
            this.equipment = equipment;
            this.isSlot = isSlot;
        }
    }


    public EquipmentDock(WindowBase base, User user) {

        this.base = base;
        this.user = user;
        label1.setBounds(0, 0, 832, 512);//背景の描画とレイヤーの設定
        menuPane.add(label1);
        menuPane.setLayer(label1, -10);

        /*使用する配列の初期化*/
        dummy[0] = new Equipment(EquipmentItem.LEATHER_HELMET, 0);
        dummy[1] = new Equipment(EquipmentItem.LEATHER_ARMOR, 0);
        dummy[2] = new Equipment(EquipmentItem.WOOD_SWORD, 0);
        dummy[3] = new Equipment(EquipmentItem.WOOD_SHIELD, 0);

        target = -1;
        for (int i = 0; i < slotButtons.length; i++) {
            slotButtons[i] = new EquipCompoundButton(iconSlot3, null, true);
        }
        for (int i = 0; i < slottedEquipment.length; i++) {
            slottedEquipment[i] = dummy[i];
        }
        for (int i = 0; i < infoSlot.length; i++) {
            infoSlot[i] = dummy[i];
        }


        start();

        base.change(menuPane);

    }

    public void menu() {//ボタンのみなさんの召喚

        menuPane.setLayout(null);      //ボタン配置の設定

        bButton.setButtonRight(menuPane);
        bButton.button().addActionListener(this);

        /*部位ごとの現在の装備の表示*/
        labelSlotHead.setBounds(16, 64, 128, 128);
        menuPane.add(labelSlotHead);
        menuPane.setLayer(labelSlotHead, 0);
        labelSlotBody.setBounds(16, 208, 128, 128);
        menuPane.add(labelSlotBody);
        menuPane.setLayer(labelSlotBody, 0);
        labelSlotMainhand.setBounds(336, 64, 128, 128);
        menuPane.add(labelSlotMainhand);
        menuPane.setLayer(labelSlotMainhand, 0);
        labelSlotOffhand.setBounds(336, 208, 128, 128);
        menuPane.add(labelSlotOffhand);
        menuPane.setLayer(labelSlotOffhand, 0);
        labelSector.setBounds(144, 64, 192, 272);
        menuPane.add(labelSector);
        menuPane.setLayer(labelSector, 0);
        slotPane.setBounds(16, 64, 448, 272);
        menuPane.add(slotPane);
        menuPane.setLayer(slotPane, 10);

        /*現在の装備と入れ替え予定の装備の表示*/
        textFieldLabel.setBounds(16, 352, 448, 128);
        menuPane.add(textFieldLabel);
        menuPane.setLayer(textFieldLabel, -5);
        labelSlotNow.setBounds(16, 384, 96, 96);
        menuPane.add(labelSlotNow);
        menuPane.setLayer(labelSlotNow, 0);
        labelSlotNext.setBounds(192, 384, 96, 96);
        menuPane.add(labelSlotNext);
        menuPane.setLayer(labelSlotNext, 0);
        JLabel labelTextNow = new JLabel("[装備中]");
        labelTextNow.setBounds(23, 354, 1000, 27);
        labelTextNow.setFont(new Font("MS ゴシック", BOLD, 22));
        menuPane.add(labelTextNow);
        menuPane.setLayer(labelTextNow, 10);
        JLabel labelTextNext = new JLabel("[装備予定]");
        labelTextNext.setBounds(188, 354, 1000, 27);
        labelTextNext.setFont(new Font("MS ゴシック", BOLD, 22));
        menuPane.add(labelTextNext);
        menuPane.setLayer(labelTextNext, 10);
        equipButton.setBounds(384, 384, 64, 64);
        equipButton.addActionListener(this);
        menuPane.add(equipButton);
        menuPane.setLayer(equipButton, 10);
        infoPane.setBounds(16, 352, 448, 128);
        menuPane.add(infoPane);
        menuPane.setLayer(infoPane, 10);

        //printStatus();

        viewport.setView(listPane);
        scrollPane.setBounds(500, 16, 256 + 18, 462 + 18);
        menuPane.add(scrollPane);
        menuPane.setLayer(scrollPane, 10);
    }

    public void start() {
        menu();
        getEquipment();
        getList();
    }

    public void getList() {               //持ってる装備のリストを表示するメソッド
        listPane.removeAll();
        ImageIcon[] equipIcon = new ImageIcon[EquipmentItem.LENGTH];
        EquipmentItem[] eItems = EquipmentItem.values();
        int limit = eItems.length, pf = 462, exist = 0;
        EquipmentItem[] existEquipment = new EquipmentItem[limit];
        for (int i = 0; i < limit; i++) {
            switch (target) {    //どの部位の表示かによって表示する装備を変える
                case -1:
                    if (user.getEquipmentQuantity(eItems[i]) > 0) {  //一つ以上所持していて
                        for (int j = 0; j < slottedEquipment.length; j++) {  //装備中でない場合に
                            if (slottedEquipment[j] != null &&
                                    slottedEquipment[j].getItem() == eItems[i]) {
                                break;
                            }
                        }
                        existEquipment[exist] = eItems[i];   //表示する
                        exist++;
                    }
                    break;
                case 0:
                    if (user.getEquipmentQuantity(eItems[i]) > 0 &&
                            eItems[i].getPosition() == EquipmentPosition.HEAD) {
                        for (int j = 0; j < slottedEquipment.length; j++) {
                            if (slottedEquipment[j] != null &&
                                    slottedEquipment[j].getItem() == eItems[i]) {
                                break;
                            }
                        }
                        existEquipment[exist] = eItems[i];
                        exist++;
                    }
                    break;
                case 1:
                    if (user.getEquipmentQuantity(eItems[i]) > 0 &&
                            eItems[i].getPosition() == EquipmentPosition.BODY) {
                        for (int j = 0; j < slottedEquipment.length; j++) {
                            if (slottedEquipment[j] != null &&
                                    slottedEquipment[j].getItem() == eItems[i]) {
                                break;
                            }
                        }
                        existEquipment[exist] = eItems[i];
                        exist++;
                    }
                    break;
                case 2:
                    if (user.getEquipmentQuantity(eItems[i]) > 0 &&
                            eItems[i].getPosition() == EquipmentPosition.RIGHT_HAND) {
                        for (int j = 0; j < slottedEquipment.length; j++) {
                            if (slottedEquipment[j] != null &&
                                    slottedEquipment[j].getItem() == eItems[i]) {
                                break;
                            }
                        }
                        existEquipment[exist] = eItems[i];
                        exist++;
                    }
                    break;
                case 3:
                    if (user.getEquipmentQuantity(eItems[i]) > 0 &&
                            eItems[i].getPosition() == EquipmentPosition.LEFT_HAND) {
                        for (int j = 0; j < slottedEquipment.length; j++) {
                            if (slottedEquipment[j] != null &&
                                    slottedEquipment[j].getItem() == eItems[i]) {
                                break;
                            }
                        }
                        existEquipment[exist] = eItems[i];
                        exist++;
                    }
                    break;
            }
        }

        if (exist > 0) {
            for (int i = 0; i < exist; i++) {
                /*背景の枠の表示*/
                JLabel itemSlot = new JLabel(iconSlot3);
                itemSlot.setBounds(16 + 80 * (i % 3), 16 + 80 * (i / 3), 64, 64);
                listPane.add(itemSlot);
                listPane.setLayer(itemSlot, 0);

                /*装備の表示の設定*/
                equipIcon[i] = new ImageIcon(existEquipment[i].getAssetPath());
                EquipCompoundButton eqButton = new EquipCompoundButton(isc.scale(equipIcon[i], 2.0), existEquipment[i], false);
                eqButton.setBorderPainted(false);
                eqButton.setContentAreaFilled(false);
                eqButton.setBounds(16 + 80 * (i % 3), 16 + 80 * (i / 3), 64, 64);
                eqButton.addActionListener(this);
                listPane.add(eqButton);
                listPane.setLayer(eqButton, 10);
                equipButtons[i] = eqButton;

                /*装備の数の表示*/
                String equipQuantity = String.valueOf(user.getEquipmentQuantity(existEquipment[i]));
                JLabel numLabel = new JLabel(equipQuantity);
                numLabel.setBounds(64 + 80 * (i % 3), 64 + 80 * (i / 3), 32, 32);
                numLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
                numLabel.setForeground(Color.YELLOW);
                listPane.add(numLabel);
                listPane.setLayer(numLabel, 20);
            }
        } else {
            String txt = "<html>まだ装備を所持していません！<br><br>"
                    + "装備は製造で入手することができます";
            JLabel txtLabel = new JLabel(txt);
            txtLabel.setHorizontalAlignment(JLabel.CENTER);
            txtLabel.setVerticalAlignment(JLabel.CENTER);
            txtLabel.setForeground(Color.yellow);
            txtLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
            txtLabel.setBounds(0, 0, 256, 462);
            listPane.add(txtLabel);
            listPane.setLayer(txtLabel, 10);
        }

        /*リストの背景の表示*/
        int n = ((int) Math.ceil((double) exist / 3.0)) * 80 + 16;
        if (n > 462) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 336, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }
        listPane.setBounds(16, 16, 256, pf);
        listPane.setPreferredSize(new Dimension(256, pf));
    }

    private void getEquipment() {
        slotPane.removeAll();
        slottedEquipment[0] = user.getEquipment(EquipmentPosition.HEAD);
        slottedEquipment[1] = user.getEquipment(EquipmentPosition.BODY);
        slottedEquipment[2] = user.getEquipment(EquipmentPosition.RIGHT_HAND);
        slottedEquipment[3] = user.getEquipment(EquipmentPosition.LEFT_HAND);

        JLabel slotLabel;
        ImageIcon[] slotIcon = new ImageIcon[4];
        EquipCompoundButton[] newButton = new EquipCompoundButton[4];
        for (int i = 0; i < 4; i++) {
            if (slottedEquipment[i] != null) {
                System.out.println("Slot" + i + "is" + slottedEquipment[i].getItem().getAssetPath());
                slotIcon[i] = isc.scale(new ImageIcon(slottedEquipment[i].getItem().getAssetPath()), 2.0);
                newButton[i] = new EquipCompoundButton(isc.scale(slotIcon[i], 2.0),
                        slottedEquipment[i].getItem(), true);
            } else {
                newButton[i] = new EquipCompoundButton(iconSlot1,
                        null, true);
            }
            newButton[i].setBorderPainted(false);
            newButton[i].setContentAreaFilled(false);
            newButton[i].addActionListener(this);

        }
        newButton[0].setBounds(0, 0, 128, 128);
        newButton[1].setBounds(0, 144, 128, 128);
        newButton[2].setBounds(320, 144, 128, 128);
        newButton[3].setBounds(320, 0, 128, 128);
        for (int i = 0; i < 4; i++) {
            slotPane.add(newButton[i]);
            listPane.setLayer(newButton[i], 10);
            slotButtons[i] = newButton[i];
        }

    }

    private void printStatus() {
        infoPane.removeAll();
        /*装備中のアイテムのステ表示*/
        String txt;
        JLabel statusLabelNow = new JLabel();
        if (infoSlot[0].getLevel() < 1) {
            txt = "<html>装備なし";
        } else {
            Status statusNow = infoSlot[0].getStatus();
            txt = "<html>HP:" + statusNow.getHP()
                    + "<br>ATK:" + statusNow.getATK()
                    + "<br>MOV:" + statusNow.getMOV()
                    + "<br>RNG:" + statusNow.getRNG();
            /*装備が存在する場合はアイコンの表示*/
            ImageIcon icon = isc.scale(new ImageIcon(infoSlot[0].getItem().getAssetPath()), 3.0);
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setBounds(0, 32, 96, 96);
            infoPane.add(iconLabel);
            infoPane.setLayer(iconLabel, 10);
        }
        statusLabelNow.setText(txt);
        statusLabelNow.setBounds(96, 32, 80, 96);
        statusLabelNow.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        statusLabelNow.setForeground(Color.BLACK);
        infoPane.add(statusLabelNow);
        infoPane.setLayer(statusLabelNow, 10);

        /*装備予定の方の表示*/
        String txt2;
        JLabel statusLabelNext = new JLabel();
        if (infoSlot[1].getLevel() < 1) {
            txt2 = "<html>装備なし";
        } else {
            Status statusNext = infoSlot[1].getStatus();
            txt2 = "<html>HP:" + statusNext.getHP()
                    + "<br>ATK:" + statusNext.getATK()
                    + "<br>MOV:" + statusNext.getMOV()
                    + "<br>RNG:" + statusNext.getRNG();
            /*装備が存在する場合はアイコンの表示*/
            ImageIcon icon = isc.scale(new ImageIcon(infoSlot[1].getItem().getAssetPath()), 3.0);
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setBounds(176, 32, 96, 96);
            infoPane.add(iconLabel);
            infoPane.setLayer(iconLabel, 10);
        }
        statusLabelNext.setText(txt2);
        statusLabelNext.setBounds(272, 32, 80, 96);
        statusLabelNext.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        statusLabelNext.setForeground(Color.BLACK);
        infoPane.add(statusLabelNext);
        infoPane.setLayer(statusLabelNext, 10);

        base.change(menuPane);
        getList();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (e.getSource() == equipButton) {
            if (infoSlot[1].getLevel() < 1) {
                System.out.println("装備がありません");
                return;
            }
            user.equipItem(infoSlot[1].getItem());

            for (int i = 0; i < slottedEquipment.length; i++) {
                slottedEquipment[i] = dummy[i];
            }
            getEquipment();
            base.change(menuPane);
            printStatus();
            getList();

        }
        if (e.getSource() == bButton.button()) {
            Workshop_MainMenu workshopMainMenu = new Workshop_MainMenu(base, user);
        }

        if (source instanceof EquipCompoundButton) {
            EquipmentItem selected = ((EquipCompoundButton) source).equipment;
            Equipment slotted = new Equipment(selected, user.getEquipmentQuantity(selected));
            if (!((EquipCompoundButton) source).isSlot) {
                infoSlot[1] = slotted;
            } else {
                infoSlot[0] = slotted;
                if (selected != null) {
                    EquipmentPosition p = selected.getPosition();
                    if (p == EquipmentPosition.HEAD) {
                        if (target != 0) {
                            target = 0;
                            infoSlot[1] = dummy[target];
                        }
                    } else if (p == EquipmentPosition.BODY) {
                        if (target != 1) {
                            target = 1;
                            infoSlot[1] = dummy[target];
                        }
                    } else if (p == EquipmentPosition.RIGHT_HAND) {
                        if (target != 2) {
                            target = 2;
                            infoSlot[1] = dummy[target];
                        }
                    } else if (p == EquipmentPosition.LEFT_HAND) {
                        if (target != 3) {
                            target = 3;
                            infoSlot[1] = dummy[target];
                        }
                    } else {
                        System.out.println("Unexpected Error");
                        target = -1;
                    }
                } else {
                    target = -1;
                    for (int i = 0; i < 4; i++) {
                        if (((EquipCompoundButton) source) == slotButtons[i]) {
                            target = i;
                            infoSlot[1] = dummy[target];
                        }
                    }
                }

            }
            printStatus();
            return;
        }
    }

    public static void main(String args[]) {
        User user = new User(114514, "testUser", 45590, 0);
        WindowBase base = new WindowBase("test");

        user.addMaterial(Material.WOOD, 14);
        user.addMaterial(Material.IRON, 6);
        user.addMaterial(Material.DIAMOND, 16);
        user.addMaterial(Material.LEATHER, 4);
        user.addMaterial(Material.BRONZE, 0);
        user.addBlueprint(new Blueprint(EquipmentItem.WOOD_SWORD), 1);
        user.addBlueprint(new Blueprint(EquipmentItem.DIAMOND_SWORD), 3);
        user.addBlueprint(new Blueprint(EquipmentItem.LEATHER_HELMET), 2);

        try {
            user.createEquipment(EquipmentItem.WOOD_SWORD);
            user.createEquipment(EquipmentItem.DIAMOND_SWORD);
            user.createEquipment(EquipmentItem.DIAMOND_SWORD);
            user.createEquipment(EquipmentItem.DIAMOND_SWORD);
            user.createEquipment(EquipmentItem.LEATHER_HELMET);
            user.createEquipment(EquipmentItem.LEATHER_HELMET);
        } catch (Exception e) {
            System.out.println("test");
        }
        user.equipItem(EquipmentItem.DIAMOND_SWORD);


        EquipmentDock test = new EquipmentDock(base, user);
        base.setVisible(true);
    }

}
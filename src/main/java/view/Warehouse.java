package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;
import model.game.Equipment;
import model.util.User;

public class Warehouse extends JFrame implements ActionListener {

    private User user;
    private BackButton bButton = new BackButton();
    private final WindowBase base;
    private final JLayeredPane menuPanel = new JLayeredPane();
    private final JLayeredPane itemInfoPane = new JLayeredPane();
    private final JLayeredPane listPane = new JLayeredPane();
    private final ImageIcon iconBackground = new ImageIcon("./assets/imgs/Equipment_backImg.png");    //画像のディレクトリは調整してもろて
    private final ImageIcon iconList = new ImageIcon("./assets/imgs/ItemList.png");
    private final ImageIcon iconItemInfo = new ImageIcon("./assets/imgs/ItemInfo.png");
    private final ImageIcon iconInfoSlot = new ImageIcon("./assets/imgs/EquipSlot2.png");
    private final ImageIcon iconSlot = new ImageIcon("./assets/imgs/EquipSlot4.png");
    private final ImageIcon iconItem = new ImageIcon("./assets/imgs/TestItemShield.png");
    private final ImageIcon iconBlueprint = new ImageIcon("./assets/imgs/Blueprint.png");

    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(iconBackground);        //画像はlabelで取り込む
    JLabel itemInfoLabel = new JLabel(iconItemInfo);
    JLabel infoSlotLabel = new JLabel(iconInfoSlot);
    JLabel[] textLabels = new JLabel[6];

    JScrollPane scrollPane = new JScrollPane();
    // viewportにscrollPaneの中のコンテンツを格納
    // scrollPane>viewport>listPane>itemButton
    JViewport viewport = scrollPane.getViewport();

    MenuButton[] menuButtons = new MenuButton[3];    //表示する対象を切り替えるボタン
    /*各アイテムの種類ごとのボタン*/
    EquipCompoundButton[] equipButtons = new EquipCompoundButton[EquipmentItem.LENGTH];
    BlueprintCompoundButton[] bpButtons = new BlueprintCompoundButton[EquipmentItem.LENGTH];
    MaterialCompoundButton[] materialButtons = new MaterialCompoundButton[Material.LENGTH];


    private class MenuButton extends JButton {    //表示する対象を切り替えるボタンの構造体
        MenuState state;

        MenuButton(String label, MenuState state) {
            super(label);
            this.state = state;
        }
    }

    private static class EquipCompoundButton extends JButton {   //装備が紐づけられたボタンの構造体
        final EquipmentItem equipment;

        EquipCompoundButton(Icon icon, EquipmentItem equipment) {
            super(icon);
            this.equipment = equipment;
        }
    }

    private static class BlueprintCompoundButton extends JButton {   //原型が紐づけられたボタンの構造体
        final Blueprint blueprint;

        BlueprintCompoundButton(Icon icon, Blueprint blueprint) {
            super(icon);
            this.blueprint = blueprint;
        }
    }

    private static class MaterialCompoundButton extends JButton {    //アイテム情報が紐づけられたボタンの構造体
        final Material material;

        MaterialCompoundButton(Icon icon, Material material) {
            super(icon);
            this.material = material;
        }
    }

    private enum MenuState {
        EQUIPMENT, BLUEPRINT, MATERIAL
    }

    public Warehouse(WindowBase base, User user) {
        this.base = base;
        this.user = user;
        label1.setBounds(0, 0, 832, 512);//背景の描画とレイヤーの設定
        menuPanel.add(label1);
        menuPanel.setLayer(label1, -10);

        for (int i = 0; i < textLabels.length; i++) {
            textLabels[i] = new JLabel();
        }

        start();

        this.base.change(menuPanel);
    }

    public void menu() {          //変化しないパーツのみなさま

        menuPanel.setLayout(null);      //ボタン配置の設定

        bButton.setButtonRight(menuPanel);
        bButton.button().addActionListener(this);

        /*表示する対象を選択するボタンの召喚*/
        menuButtons[0] = new MenuButton("装備", MenuState.EQUIPMENT);
        menuButtons[1] = new MenuButton("原型", MenuState.BLUEPRINT);
        menuButtons[2] = new MenuButton("素材", MenuState.MATERIAL);
        for (int i = 0; i < menuButtons.length; i++) {
            menuButtons[i].setBounds(16 + i * 177, 64, 160, 32);
            menuButtons[i].addActionListener(this);
            menuPanel.add(menuButtons[i]);
            menuPanel.setLayer(menuButtons[i], 0);
        }

        /*アイテム情報表示欄の召喚*/
        itemInfoLabel.setBounds(546, 32, 256, 464);
        menuPanel.add(itemInfoLabel);
        menuPanel.setLayer(itemInfoLabel, -5);
        infoSlotLabel.setBounds(610, 64, 128, 128);
        menuPanel.add(infoSlotLabel);
        menuPanel.setLayer(infoSlotLabel, 0);

        /*アイテム表示蘭の場所とり*/
        itemInfoPane.setBounds(546, 32, 256, 464);
    }

    //ボタンの状態の切り替え
    private void switchMenu(MenuState state) {
        listPane.removeAll();
        switch (state) {
            case EQUIPMENT -> {
                menuButtons[0].setEnabled(false);
                menuButtons[1].setEnabled(true);
                menuButtons[2].setEnabled(true);
                showEquipmentList();
            }
            case BLUEPRINT -> {
                menuButtons[0].setEnabled(true);
                menuButtons[1].setEnabled(false);
                menuButtons[2].setEnabled(true);
                showBlueprintList();
            }
            case MATERIAL -> {
                menuButtons[0].setEnabled(true);
                menuButtons[1].setEnabled(true);
                menuButtons[2].setEnabled(false);
                showMaterialList();
            }
        }
    }

    public void start() {    //始めに画面を呼び出したときに表示する内容の召喚
        menu();

        showEquipmentList();
    }

    public void showEquipmentList() {   //装備一覧の取得と表示
        listPane.removeAll();
        ImageIcon[] equipIcon = new ImageIcon[EquipmentItem.LENGTH];
        EquipmentItem[] eItems = EquipmentItem.values();
        int limit = eItems.length, pf = 366;


        for (int i = 0; i < limit; i++) {
            /*背景の枠の表示*/
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);

            /*装備の表示の設定*/
            equipIcon[i] = new ImageIcon(eItems[i].getAssetPath());
            EquipCompoundButton eqButton = new EquipCompoundButton(isc.scale(equipIcon[i], 2.0), eItems[i]);
            eqButton.setBorderPainted(false);
            eqButton.setContentAreaFilled(false);
            eqButton.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            eqButton.addActionListener(this);
            listPane.add(eqButton);
            listPane.setLayer(eqButton, 10);
            equipButtons[i] = eqButton;

            /*装備の数の表示*/
            String equipQuantity = String.valueOf(user.getEquipmentQuantity(eItems[i]));
            JLabel numLabel = new JLabel(equipQuantity);
            numLabel.setBounds(64 + 80 * (i % 6), 64 + 80 * (i / 6), 32, 32);
            numLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
            numLabel.setForeground(Color.YELLOW);
            listPane.add(numLabel);
            listPane.setLayer(numLabel, 20);
        }

        /*リストの背景の表示*/
        int n = ((int) Math.ceil((double) limit / 6.0)) * 80 + 16;
        if (n > 462) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 336, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }
        listPane.setBounds(0, 0, 496, pf);
        listPane.setPreferredSize(new Dimension(496, pf));
        viewport.setView(listPane);
        scrollPane.setBounds(16, 112, 496 + 18, 366 + 18);
        menuPanel.add(scrollPane);
        menuPanel.setLayer(scrollPane, 10);
    }

    public void showBlueprintList() {
        int pf = 366;

        /*原型情報の格納*/
        ImageIcon[] bpIcon = new ImageIcon[EquipmentItem.LENGTH];
        EquipmentItem[] eItems = EquipmentItem.values();

        for (int i = 0; i < eItems.length; i++) {
            EquipmentItem targetItem = eItems[i];
            /*背景の枠の表示*/
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);
            JLabel bpSlot = new JLabel(isc.scale(iconBlueprint, 2.0));
            bpSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(bpSlot);
            listPane.setLayer(bpSlot, 5);
            /*blueprintCompoundButtonを用いたボタン表示*/
            bpIcon[i] = new ImageIcon(targetItem.getAssetPath());
            BlueprintCompoundButton newButton = new BlueprintCompoundButton(isc.scale(bpIcon[i], 2.0), new Blueprint(targetItem));
            newButton.setBorderPainted(false);
            newButton.setContentAreaFilled(false);
            newButton.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            newButton.addActionListener(this);
            listPane.add(newButton);
            listPane.setLayer(newButton, 10);
            bpButtons[i] = newButton;

            /*アイテム数の表示*/
            String bqQuantity = String.valueOf(user.getBlueprintQuantity(new Blueprint(targetItem)));
            JLabel numLabel = new JLabel(bqQuantity);
            numLabel.setBounds(64 + 80 * (i % 6), 64 + 80 * (i / 6), 32, 32);
            numLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
            numLabel.setForeground(Color.YELLOW);
            listPane.add(numLabel);
            listPane.setLayer(numLabel, 20);
        }
        /*背景の表示*/
        int n = ((int) Math.ceil((double) EquipmentItem.LENGTH / 6.0)) * 80 + 16;
        if (n > 366) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 496, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }
        /*リストを本体に配置*/
        listPane.setBounds(0, 0, 496, 10000);
        listPane.setPreferredSize(new Dimension(496, pf));
        viewport.setView(listPane);
        scrollPane.setBounds(16, 112, 496 + 18, 366 + 18);
        menuPanel.add(scrollPane);
        menuPanel.setLayer(scrollPane, 10);
    }

    public void showMaterialList() {         //素材アイテムをリストとして表示する
        int pf = 366;             //アイテムの数とスクロールの最大値

        /*各アイテムの数量とアイコンを取得する下準備*/
        Material[] materials = Material.values();
        for (int i = 0; i < materials.length; i++) {
            /*背景の枠の表示*/
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);

            /*MaterialCompoundButtonを用いたアイテム用ボタンの表示*/
            ImageIcon materialIcon = new ImageIcon(materials[i].getAssetPath());
            MaterialCompoundButton newButton = new MaterialCompoundButton(isc.scale(materialIcon, 2.0), materials[i]);
            newButton.setBorderPainted(false);
            newButton.setContentAreaFilled(false);
            newButton.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            newButton.addActionListener(this);
            listPane.add(newButton);
            listPane.setLayer(newButton, 10);
            materialButtons[i] = newButton;

            /*アイテムの数の表示*/
            String itemQuantity = String.valueOf(user.getMaterialQuantity(materials[i]));
            JLabel numLabel = new JLabel(itemQuantity);

            numLabel.setBounds(64 + 80 * (i % 6), 64 + 80 * (i / 6), 32, 32);
            numLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
            numLabel.setForeground(Color.YELLOW);
            listPane.add(numLabel);
            listPane.setLayer(numLabel, 20);
        }
        /*背景の表示*/
        int n = ((int) Math.ceil((double) model.Material.LENGTH / 6.0)) * 80 + 16;
        if (n > 366) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 496, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }
        /*リストを画面本体に配置する*/
        listPane.setBounds(0, 0, 496, 10000);
        listPane.setPreferredSize(new Dimension(496, pf));
        viewport.setView(listPane);
        scrollPane.setBounds(16, 112, 496 + 18, 366 + 18);
        menuPanel.add(scrollPane);
        menuPanel.setLayer(scrollPane, 10);
    }

    public void putEquipInfo(EquipmentItem ei) {       //原型の情報を表示するメソッド
        itemInfoPane.removeAll();

        /*アイテム名の表示*/
        JLabel equipName = new JLabel(ei.name);
        equipName.setBounds(0, 0, 256, 32);
        equipName.setHorizontalAlignment(JLabel.CENTER);
        equipName.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        itemInfoPane.add(equipName);
        itemInfoPane.setLayer(equipName, 0);

        /*アイコン表示*/
        JLabel equipIcon = new JLabel(isc.scale(new ImageIcon(ei.getAssetPath()), 4.0));
        equipIcon.setBounds(64, 32, 128, 128);
        itemInfoPane.add(equipIcon);
        itemInfoPane.setLayer(equipIcon, 0);

        /*アイテムの所持数の表示*/
        int lvl = user.getEquipmentQuantity(ei);
        JLabel equipNum = new JLabel();
        if(lvl>0){
            equipNum.setText("レベル：" + lvl);
        }else{
            lvl = 1;
            equipNum.setText("未所持");
        }
        equipNum.setBounds(0, 160, 256, 32);
        equipNum.setHorizontalAlignment(JLabel.CENTER);
        equipNum.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        itemInfoPane.add(equipNum);
        itemInfoPane.setLayer(equipNum, 0);

        /*ステータスの表示*/
        Equipment eq = new Equipment(ei, lvl);
        Status status = eq.getStatus();
        String txt = "<html>&nbsp;&nbsp;&nbsp;&nbsp;HP:" + status.getHP()
                + "<br>攻撃力:" + status.getATK()
                + "<br>機動力:" + status.getMOV()
                + "<br>&nbsp;&nbsp;射程:" + status.getRNG();
        JLabel equipTxt = new JLabel(txt);
        equipTxt.setBounds(32, 192, 208, 256);
        equipTxt.setVerticalAlignment(JLabel.TOP);
        equipTxt.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        itemInfoPane.add(equipTxt);
        itemInfoPane.setLayer(equipTxt, 0);

        menuPanel.add(itemInfoPane);
        menuPanel.setLayer(itemInfoPane, 10);
    }

    public void putBpInfo(Blueprint bp) {       //原型の情報を表示するメソッド
        itemInfoPane.removeAll();

        /*アイテム名の表示*/
        JLabel bpName = new JLabel(bp.baseItem().name);
        bpName.setBounds(0, 0, 256, 32);
        bpName.setHorizontalAlignment(JLabel.CENTER);
        bpName.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        itemInfoPane.add(bpName);
        itemInfoPane.setLayer(bpName, 0);

        /*アイコン表示*/
        JLabel bpIcon = new JLabel(isc.scale(new ImageIcon(bp.baseItem().getAssetPath()), 4.0));
        bpIcon.setBounds(64, 32, 128, 128);
        itemInfoPane.add(bpIcon);
        itemInfoPane.setLayer(bpIcon, 10);
        JLabel bpSlot = new JLabel(isc.scale(iconBlueprint, 4.0));
        bpSlot.setBounds(64, 32, 128, 128);
        itemInfoPane.add(bpSlot);
        itemInfoPane.setLayer(bpSlot, 0);

        /*アイテムの所持数の表示*/
        JLabel bpNum = new JLabel("所持数：" + user.getBlueprintQuantity(bp));
        bpNum.setBounds(0, 160, 256, 32);
        bpNum.setHorizontalAlignment(JLabel.CENTER);
        bpNum.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        itemInfoPane.add(bpNum);
        itemInfoPane.setLayer(bpNum, 0);

        /*説明文の表示*/
        String txt = "<html>" + bp.baseItem().name +
                "の設計図<br>" + "このままでは装備できない<br><br>"
                + "素材を集めることで、装備を製造することができる<br>"
                + "装備を製造すると、設計図は消費される";
        JLabel bpTxt = new JLabel(txt);
        bpTxt.setBounds(32, 192, 208, 256);
        bpTxt.setVerticalAlignment(JLabel.TOP);
        bpTxt.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
        itemInfoPane.add(bpTxt);
        itemInfoPane.setLayer(bpTxt, 0);

        menuPanel.add(itemInfoPane);
        menuPanel.setLayer(itemInfoPane, 10);
    }

    public void putItemInfo(Material material) {       //素材アイテムの情報を表示するメソッド
        itemInfoPane.removeAll();

        /*アイテム名の表示*/
        JLabel itemName = new JLabel(material.getName());
        itemName.setBounds(0, 0, 256, 32);
        itemName.setHorizontalAlignment(JLabel.CENTER);
        itemName.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        itemInfoPane.add(itemName);
        itemInfoPane.setLayer(itemName, 0);

        /*アイコン表示*/
        JLabel itemIcon = new JLabel(isc.scale(new ImageIcon(material.getAssetPath()), 4.0));
        itemIcon.setBounds(64, 32, 128, 128);
        itemInfoPane.add(itemIcon);
        itemInfoPane.setLayer(itemIcon, 0);

        /*アイテムの所持数の表示*/
        JLabel itemNum = new JLabel("所持数：" + user.getMaterialQuantity(material));
        itemNum.setBounds(0, 160, 256, 32);
        itemNum.setHorizontalAlignment(JLabel.CENTER);
        itemNum.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        itemInfoPane.add(itemNum);
        itemInfoPane.setLayer(itemNum, 0);

        /*フレーバーテキストの表示*/
        JLabel itemTxt = new JLabel(material.getTxt());
        itemTxt.setBounds(32, 192, 208, 256);
        itemTxt.setVerticalAlignment(JLabel.TOP);
        itemTxt.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
        itemInfoPane.add(itemTxt);
        itemInfoPane.setLayer(itemTxt, 0);

        menuPanel.add(itemInfoPane);
        menuPanel.setLayer(itemInfoPane, 10);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // MenuButtonが押されたときの処理(切り替えボタン)
        if (source instanceof MenuButton) {
            switchMenu(((MenuButton) source).state);
            return;
        }

        if (source instanceof EquipCompoundButton) {
            putEquipInfo(((EquipCompoundButton) source).equipment);
            return;
        }

        // BlueprintListのボタンが押されたときの処理
        if (source instanceof BlueprintCompoundButton) {
            putBpInfo(((BlueprintCompoundButton) source).blueprint);
            return;
        }

        // MaterialListのボタンが押されたときの処理
        if (source instanceof MaterialCompoundButton) {
            putItemInfo(((MaterialCompoundButton) source).material);
            return;
        }

        if(e.getSource() == bButton.button()){
            Workshop_MainMenu workshopMainMenu = new Workshop_MainMenu(base, user);
        }
    }

    public static void main(String args[]) {
        User user = new User(114514, "testUser", 45590, 3);
        user.addMaterial(Material.WOOD, 20);
        user.addMaterial(Material.IRON, 30);
        user.addMaterial(Material.DIAMOND, 10);
        user.addMaterial(Material.LEATHER, 40);
        user.addMaterial(Material.BRONZE, 30);
        user.addBlueprint(new Blueprint(EquipmentItem.WOOD_SWORD),3);
        user.addBlueprint(new Blueprint(EquipmentItem.LEATHER_HELMET),2);
        user.addBlueprint(new Blueprint(EquipmentItem.DIAMOND_SWORD),1);
        user.addBlueprint(new Blueprint(EquipmentItem.BRONZE_HELMET),4);

        try {
            user.createEquipment(EquipmentItem.WOOD_SWORD);
            user.createEquipment(EquipmentItem.LEATHER_HELMET);
            user.createEquipment(EquipmentItem.LEATHER_HELMET);
        } catch (Exception e) {
            System.out.println("test");
        }

        WindowBase base = new WindowBase("test");
        Warehouse test = new Warehouse(base, user);
        base.setVisible(true);
    }

}
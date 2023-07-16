package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.EquipmentItem;
import model.Material;
import model.util.User;
import model.Blueprint;

public class Warehouse extends JFrame implements ActionListener {

    private User user;
    /*アイテムの種類数を規定する変数*/
    int itemVar = 5, equipVar = 23;
    private final WindowBase base;
    private JLayeredPane menuPanel = new JLayeredPane();
    private JLayeredPane itemInfoPane = new JLayeredPane();
    private JLayeredPane listPane = new JLayeredPane();
    private ImageIcon iconBackground = new ImageIcon("./assets/imgs/ログイン画面.png");    //画像のディレクトリは調整してもろて
    private ImageIcon iconList = new ImageIcon("./assets/imgs/TestItemList.png");
    private ImageIcon iconItemInfo = new ImageIcon("./assets/imgs/TestItemInfo.png");
    private ImageIcon iconInfoSlot = new ImageIcon("./assets/imgs/TestEquipSlot2.png");
    private ImageIcon iconSlot = new ImageIcon("./assets/imgs/TestEquipSlot4.png");
    private ImageIcon iconItem = new ImageIcon("./assets/imgs/TestItemShield.png");

    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");
    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(iconBackground);        //画像はlabelで取り込む
    JLabel itemInfoLabel = new JLabel(iconItemInfo);
    JLabel infoSlotLabel = new JLabel(iconInfoSlot);
    JLabel[] textLabel = new JLabel[6];

    JScrollPane scrollPane = new JScrollPane();
    // viewportにscrollPaneの中のコンテンツを格納
    // scrollPane>viewport>listPane>itemButton
    JViewport viewport = scrollPane.getViewport();
    JButton[] buttonSelector = new JButton[3];    //表示する対象を切り替えるボタン
    /*各アイテムの種類ごとのボタン*/
    itemCompoundButton[] itemButton = new itemCompoundButton[itemVar];
    blueprintCompoundButton[] bpButton = new blueprintCompoundButton[equipVar];

    private class blueprintCompoundButton {   //原型が紐づけられたボタンの構造体
        Blueprint blueprint;
        JButton button;
    }

    private class itemCompoundButton {    //アイテム情報が紐づけられたボタンの構造体
        Material material;
        JButton button;
    }


    public Warehouse(WindowBase base, User user) {

        this.base = base;
        this.user = user;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        menuPanel.add(label1);
        menuPanel.setLayer(label1, -10);

        /*構造体配列の初期化*/
        for (int i1 = 0; i1 < itemVar; i1++) {
            itemButton[i1] = new itemCompoundButton();
        }
        for (int i2 = 0; i2 < equipVar; i2++) {
            bpButton[i2] = new blueprintCompoundButton();
        }

        for (int i = 0; i < textLabel.length; i++) {
            textLabel[i] = new JLabel();
        }

        start();

        this.base.change(menuPanel);
    }

    public void menu() {          //変化しないパーツのみなさま

        menuPanel.setLayout(null);      //ボタン配置の設定

        /*表示する対象を選択するボタンの召喚*/
        buttonSelector[0] = new JButton("装備");
        buttonSelector[1] = new JButton("原型");
        buttonSelector[2] = new JButton("素材");
        for (int i = 0; i < buttonSelector.length; i++) {
            buttonSelector[i].setBounds(16 + i * 177, 64, 160, 32);
            buttonSelector[i].addActionListener(this);
            menuPanel.add(buttonSelector[i]);
            menuPanel.setLayer(buttonSelector[i], 0);
        }

        /*アイテム情報表示欄の召喚*/
        itemInfoLabel.setBounds(546, 32, 256, 464);
        menuPanel.add(itemInfoLabel);
        menuPanel.setLayer(itemInfoLabel, -5);
        infoSlotLabel.setBounds(610, 64, 128, 128);
        menuPanel.add(infoSlotLabel);
        menuPanel.setLayer(infoSlotLabel, 0);
    }

    public void start() {    //始めに画面を呼び出したときに表示する内容の召喚
        menu();

        getEquipmentList();
    }

    public void getEquipmentList() {   //装備一覧の取得と表示
        listPane.removeAll();
        buttonSelector[0].setEnabled(false);   //ボタンの状態の切り替え
        buttonSelector[1].setEnabled(true);
        buttonSelector[2].setEnabled(true);
        int limit = 43, pf = 366;
        for (int i = 0; i < limit; i++) {
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);
            JButton itemButton = new JButton(isc.scale(iconItem, 2.0));
            itemButton.setBorderPainted(false);
            itemButton.setContentAreaFilled(false);
            itemButton.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemButton);
            listPane.setLayer(itemButton, 10);
        }
        int n = ((int) Math.ceil((double) limit / 6.0)) * 80 + 16;
        if (n > 366) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 496, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }
        listPane.setBounds(0, 0, 496, 10000);
        listPane.setPreferredSize(new Dimension(496, pf));
        viewport.setView(listPane);
        scrollPane.setBounds(16, 112, 496 + 18, 366 + 18);
        menuPanel.add(scrollPane);
        menuPanel.setLayer(scrollPane, 10);
    }

    public void getEquipBaseList() {
        listPane.removeAll();
        buttonSelector[0].setEnabled(true);
        buttonSelector[1].setEnabled(false);
        buttonSelector[2].setEnabled(true);
        int pf = 366;

        /*原型情報の格納*/
        int[] bpNum = new int[equipVar];
        ImageIcon[] bpIcon = new ImageIcon[equipVar];
        Blueprint[] bp = new Blueprint[equipVar];
        bp[0] = new Blueprint(EquipmentItem.LEATHER_HELMET);
        bp[1] = new Blueprint(EquipmentItem.COPPER_HELMET);
        bp[2] = new Blueprint(EquipmentItem.IRON_HELMET);
        bp[3] = new Blueprint(EquipmentItem.DIAMOND_HELMET);
        bp[4] = new Blueprint(EquipmentItem.LEATHER_ARMOR);
        bp[5] = new Blueprint(EquipmentItem.COPPER_ARMOR);
        bp[6] = new Blueprint(EquipmentItem.IRON_ARMOR);
        bp[7] = new Blueprint(EquipmentItem.DIAMOND_ARMOR);
        bp[8] = new Blueprint(EquipmentItem.WOOD_SWORD);
        bp[9] = new Blueprint(EquipmentItem.IRON_SWORD);
        bp[10] = new Blueprint(EquipmentItem.DIAMOND_SWORD);
        bp[11] = new Blueprint(EquipmentItem.WOOD_SPEAR);
        bp[12] = new Blueprint(EquipmentItem.IRON_SPEAR);
        bp[13] = new Blueprint(EquipmentItem.DIAMOND_SPEAR);
        bp[14] = new Blueprint(EquipmentItem.WOOD_ARROW);
        bp[15] = new Blueprint(EquipmentItem.IRON_ARROW);
        bp[16] = new Blueprint(EquipmentItem.DIAMOND_ARROW);
        bp[17] = new Blueprint(EquipmentItem.WOOD_SHIELD);
        bp[18] = new Blueprint(EquipmentItem.IRON_SHIELD);
        bp[19] = new Blueprint(EquipmentItem.DIAMOND_SHIELD);
        bp[20] = new Blueprint(EquipmentItem.WOOD_DAGGER);
        bp[21] = new Blueprint(EquipmentItem.IRON_DAGGER);
        bp[22] = new Blueprint(EquipmentItem.DIAMOND_DAGGER);

        for (int i = 0; i < equipVar; i++) {
            /*背景の枠の表示*/
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);
            /*blueprintCompoundButtonを用いたボタン表示*/
            bpButton[i].blueprint = bp[i];
            bpIcon[i] = new ImageIcon(bp[i].baseItem().getAssetPath());
            bpButton[i].button = new JButton(isc.scale(bpIcon[i], 2.0));
            bpButton[i].button.setBorderPainted(false);
            bpButton[i].button.setContentAreaFilled(false);
            bpButton[i].button.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(bpButton[i].button);
            bpButton[i].button.addActionListener(this);
            listPane.setLayer(bpButton[i].button, 10);
            /*アイテム数の表示*/
            bpNum[i] = user.getBlueprintQuantity(bp[i]);
            JLabel num = new JLabel(String.valueOf(bpNum[i]));
            num.setBounds(64 + 80 * (i % 6), 64 + 80 * (i / 6), 32, 32);
            num.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
            listPane.add(num);
            listPane.setLayer(num, 20);
        }
        /*背景の表示*/
        int n = ((int) Math.ceil((double) equipVar / 6.0)) * 80 + 16;
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

    public void getItemList() {         //素材アイテムをリストとして表示する
        listPane.removeAll();
        buttonSelector[0].setEnabled(true);  //押せるボタンの切り替え
        buttonSelector[1].setEnabled(true);
        buttonSelector[2].setEnabled(false);
        int pf = 366;             //アイテムの数とスクロールの最大値

        /*各アイテムの数量とアイコンを取得する下準備*/
        int[] itemNum = new int[itemVar];
        ImageIcon[] itemIcon = new ImageIcon[itemVar];
        Material[] material = new Material[itemVar];
        material[0] = Material.WOOD;
        material[1] = Material.IRON;
        material[2] = Material.DIAMOND;
        material[3] = Material.LEATHER;
        material[4] = Material.BRONZE;

        for (int i = 0; i < itemVar; i++) {
            /*背景の枠の表示*/
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);
            /*itemCompoundButtonを用いたアイテム用ボタンの表示*/
            itemIcon[i] = new ImageIcon(material[i].getAssetPath());
            itemButton[i].material = material[i];
            itemButton[i].button = new JButton(isc.scale(itemIcon[i], 2.0));
            itemButton[i].button.setBorderPainted(false);
            itemButton[i].button.setContentAreaFilled(false);
            itemButton[i].button.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemButton[i].button);
            itemButton[i].button.addActionListener(this);
            listPane.setLayer(itemButton[i].button, 10);
            /*アイテムの数の表示*/
            itemNum[i] = user.getMaterialQuantity(material[i]);
            JLabel num = new JLabel(String.valueOf(itemNum[i]));
            num.setBounds(64 + 80 * (i % 6), 64 + 80 * (i / 6), 32, 32);
            num.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
            listPane.add(num);
            listPane.setLayer(num, 20);
        }
        /*背景の表示*/
        int n = ((int) Math.ceil((double) itemVar / 6.0)) * 80 + 16;
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

    public void putBpInfo(int n) {       //原型の情報を表示するメソッド
        itemInfoPane.removeAll();

        /*アイテム名の表示*/
        JLabel bpName = new JLabel(bpButton[n].blueprint.baseItem().name);
        bpName.setBounds(0, 0, 256, 32);
        bpName.setHorizontalAlignment(JLabel.CENTER);
        bpName.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        itemInfoPane.add(bpName);
        itemInfoPane.setLayer(bpName, 0);

        /*アイコン表示*/
        JLabel bpIcon = new JLabel(isc.scale(new ImageIcon(bpButton[n].blueprint.baseItem().getAssetPath()), 4.0));
        bpIcon.setBounds(64, 32, 128, 128);
        itemInfoPane.add(bpIcon);
        itemInfoPane.setLayer(bpIcon, 0);

        /*アイテムの所持数の表示*/
        JLabel bpNum = new JLabel("所持数：" + user.getBlueprintQuantity(bpButton[n].blueprint));
        bpNum.setBounds(0, 160, 256, 32);
        bpNum.setHorizontalAlignment(JLabel.CENTER);
        bpNum.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        itemInfoPane.add(bpNum);
        itemInfoPane.setLayer(bpNum, 0);

        /*説明文の表示*/
        String txt = "<html>" + bpButton[n].blueprint.baseItem().name +
                "の設計図<br>" + "このままでは装備できない<br><br>"
                + "素材を集めることで、装備を製造することができる<br>"
                + "装備を製造すると、設計図は消費される";
        JLabel bpTxt = new JLabel(txt);
        bpTxt.setBounds(0, 192, 256, 256);
        bpTxt.setVerticalAlignment(JLabel.TOP);
        bpTxt.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
        itemInfoPane.add(bpTxt);
        itemInfoPane.setLayer(bpTxt, 0);

        itemInfoPane.setBounds(546, 32, 256, 464);
        menuPanel.add(itemInfoPane);
        menuPanel.setLayer(itemInfoPane, 10);
    }

    public void putItemInfo(int n) {       //素材アイテムの情報を表示するメソッド
        itemInfoPane.removeAll();

        /*アイテム名の表示*/
        JLabel itemName = new JLabel(itemButton[n].material.getName());
        itemName.setBounds(0, 0, 256, 32);
        itemName.setHorizontalAlignment(JLabel.CENTER);
        itemName.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        itemInfoPane.add(itemName);
        itemInfoPane.setLayer(itemName, 0);

        /*アイコン表示*/
        JLabel itemIcon = new JLabel(isc.scale(new ImageIcon(itemButton[n].material.getAssetPath()), 4.0));
        itemIcon.setBounds(64, 32, 128, 128);
        itemInfoPane.add(itemIcon);
        itemInfoPane.setLayer(itemIcon, 0);

        /*アイテムの所持数の表示*/
        JLabel itemNum = new JLabel("所持数：" + user.getMaterialQuantity(itemButton[n].material));
        itemNum.setBounds(0, 160, 256, 32);
        itemNum.setHorizontalAlignment(JLabel.CENTER);
        itemNum.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
        itemInfoPane.add(itemNum);
        itemInfoPane.setLayer(itemNum, 0);

        /*フレーバーテキストの表示*/
        JLabel itemTxt = new JLabel(itemButton[n].material.getTxt());
        itemTxt.setBounds(0, 192, 256, 256);
        itemTxt.setVerticalAlignment(JLabel.TOP);
        itemTxt.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
        itemInfoPane.add(itemTxt);
        itemInfoPane.setLayer(itemTxt, 0);

        itemInfoPane.setBounds(546, 32, 256, 464);
        menuPanel.add(itemInfoPane);
        menuPanel.setLayer(itemInfoPane, 10);
    }

    public void actionPerformed(ActionEvent e) {
        // Upgradeボタンが押されたときの処理
        if (e.getSource() == buttonSelector[0]) {
            System.out.println("装だよ(便乗)");
            getEquipmentList();
        }
        if (e.getSource() == buttonSelector[1]) {
            System.out.println("原型は製造しないと使えないぜっ！");
            getEquipBaseList();
        }
        if (e.getSource() == buttonSelector[2]) {
            System.out.println("ラストエリクサーなんてないよ");
            getItemList();
        }
        for (int i = 0; i < itemVar; i++) {
            if (e.getSource() == itemButton[i].button) {
                putItemInfo(i);
            }
        }
        for (int i = 0; i < equipVar; i++) {
            if (e.getSource() == bpButton[i].button) {
                putBpInfo(i);
            }
        }

    }

    public static void main(String args[]) {
        User user = new User(114514, "testUser", 45590, 3);
        user.addMaterial(Material.WOOD, 20);
        user.addMaterial(Material.IRON, 30);
        user.addMaterial(Material.DIAMOND, 10);
        user.addMaterial(Material.LEATHER, 40);
        user.addMaterial(Material.BRONZE, 30);

        WindowBase base = new WindowBase("test");
        Warehouse test = new Warehouse(base, user);
        base.setVisible(true);
    }

}

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Material;
import model.util.User;
import model.Blueprint;
import model.EquipmentItem;

import static java.awt.Font.BOLD;

public class Workshop extends JFrame implements ActionListener {

    private User user;
    int itemVar = 5, bpVar = 23;                 //装備原型と素材の種類の数
    private final WindowBase base;
    private JLayeredPane menuPanel = new JLayeredPane();
    private JLayeredPane equipInfoPane = new JLayeredPane();
    private JLayeredPane listPane = new JLayeredPane();
    private JLayeredPane setPane = new JLayeredPane();
    private JLayeredPane upgradePane = new JLayeredPane();
    private ImageIcon iconBackground = new ImageIcon("./assets/imgs/Equipment_backImg.png");    //画像のディレクトリは調整してもろて
    private ImageIcon iconList = new ImageIcon("./assets/imgs/EquipBaseList.png");
    private ImageIcon iconEquipPickArea = new ImageIcon("./assets/imgs/WorkshopText.png");
    private ImageIcon iconUpgradeSelection = new ImageIcon("./assets/imgs/EquipSlot.png");
    private ImageIcon iconSlot = new ImageIcon("./assets/imgs/EquipSlot4.png");
    private ImageIcon iconUpgrade = new ImageIcon("./assets/imgs/TestButton3.png");
    private ImageIcon iconItem = new ImageIcon("./assets/imgs/TestItemShield.png");

    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");
    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(iconBackground);        //画像はlabelで取り込む
    JLabel label2 = new JLabel(iconEquipPickArea);
    JLabel labelUpgradeBefore = new JLabel(iconUpgradeSelection);
    JLabel labelUpgradeAfter = new JLabel(iconUpgradeSelection);
    JLabel textLabel = new JLabel();

    JScrollPane scrollPane = new JScrollPane();
    // viewportにscrollPaneの中のコンテンツを格納
    // scrollPane>viewport>listPane>itemButton
    JViewport viewport = scrollPane.getViewport();
    JButton buttonUpgrade = new JButton(iconUpgrade);
    blueprintCompoundButton[] bpButton = new blueprintCompoundButton[bpVar];
    Blueprint slottedBp;

    private class blueprintCompoundButton {   //原型が紐づけられたボタンの構造体
        Blueprint blueprint;
        JButton button;
    }

    public Workshop(WindowBase base, User user) {

        this.base = base;
        this.user = user;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        menuPanel.add(label1);
        menuPanel.setLayer(label1, -10);

        /*使用する構造体の初期化*/
        for (int i = 0; i < bpButton.length; i++) {
            bpButton[i] = new blueprintCompoundButton();
        }

        start();

        base.change(menuPanel);

    }

    public void menu() {//ボタンのみなさんの召喚

        menuPanel.setLayout(null);      //ボタン配置の設定

        getList();

        label2.setBounds(226, 336, 574, 160);
        menuPanel.add(label2);
        menuPanel.setLayer(label2, 0);

        labelUpgradeBefore.setBounds(242, 72, 192, 192);
        menuPanel.add(labelUpgradeBefore);
        menuPanel.setLayer(labelUpgradeBefore, 0);
        labelUpgradeAfter.setBounds(592, 72, 192, 192);
        menuPanel.add(labelUpgradeAfter);
        menuPanel.setLayer(labelUpgradeAfter, 0);

        buttonUpgrade.setBorderPainted(false);
        buttonUpgrade.setContentAreaFilled(false);
        buttonUpgrade.setBounds(450, 136, 126, 64);
        menuPanel.add(buttonUpgrade);
        buttonUpgrade.addActionListener(this);
        menuPanel.setLayer(buttonUpgrade, 0);
    }

    public void start() {
        menu();
        buttonUpgrade.setEnabled(false);
        equipInfoPane.setBounds(0, 0, 832, 549);
        menuPanel.add(equipInfoPane);
        menuPanel.setLayer(equipInfoPane, 10);

        /*最初にテキストボックスに表示するメッセージ*/
        textLabel.setText("<html>製造する装備の設計図を選択してください<br>"
        + "中央の「製造」ボタンから製造することができます");
        textLabel.setVerticalAlignment(JLabel.TOP);
        textLabel.setFont(new Font("ＭＳ ゴシック", BOLD, 22));
        textLabel.setBounds(234, 344, 558, 144);
        equipInfoPane.add(textLabel);
        equipInfoPane.setLayer(textLabel, 0);
    }

    public void getList() {

        /*全種類の設計図の所持数を確認*/
        int exist = 0;
        Blueprint[] bp = new Blueprint[bpVar];
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
        /*一つ以上所持している設計図のみを表示する*/
        for (int i = 0; i < bpVar; i++) {
            if (user.getBlueprintQuantity(bp[i]) > 0) {
                bpButton[exist].blueprint = bp[i];
                exist = exist + 1;
            }
        }

        /*リストの内容の表示*/
        if (exist > 0) {
            for (int i = 0; i < exist; i++) {
                /*アイテムの枠の表示*/
                JLabel itemSlot = new JLabel(iconSlot);
                itemSlot.setBounds(16 + 80 * (i % 2), 16 + 80 * (i / 2), 64, 64);
                listPane.add(itemSlot);
                listPane.setLayer(itemSlot, 0);
                /*設計図のアイコンの表示*/
                ImageIcon bpIcon = new ImageIcon(bpButton[i].blueprint.baseItem().getAssetPath());
                bpButton[i].button = new JButton(isc.scale(bpIcon, 2.0));
                bpButton[i].button.setBorderPainted(false);
                bpButton[i].button.setContentAreaFilled(false);
                bpButton[i].button.setBounds(16 + 80 * (i % 2), 16 + 80 * (i / 2), 64, 64);
                listPane.add(bpButton[i].button);
                bpButton[i].button.addActionListener(this);
                listPane.setLayer(bpButton[i].button, 10);

                /*設計図の数の表示*/
                int bpNum = user.getBlueprintQuantity(bpButton[i].blueprint);
                JLabel num = new JLabel(String.valueOf(bpNum));
                num.setBounds(64 + 80 * (i % 2), 64 + 80 * (i / 2), 32, 32);
                num.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
                listPane.add(num);
                listPane.setLayer(num, 20);

            }
        } else {
            String txt = "<html>まだ設計図を所持していません！<br><br>"
                    + "設計図はガチャから入手することができます";
            JLabel txtLabel = new JLabel(txt);
            txtLabel.setHorizontalAlignment(JLabel.CENTER);
            txtLabel.setVerticalAlignment(JLabel.CENTER);
            txtLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
            txtLabel.setBounds(0, 0, 176, 462);
            listPane.add(txtLabel);
            listPane.setLayer(txtLabel, 10);
        }

        /*リスト背景の設置*/
        int pf = 462;
        int n = ((int) Math.ceil((double) exist / 2.0)) * 80 + 16;
        if (n > 462) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 176, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }

        listPane.setBounds(16, 16, 176, pf);
        listPane.setPreferredSize(new Dimension(176, pf));
        viewport.setView(listPane);
        scrollPane.setBounds(16, 16, 176 + 18, 462 + 18);
        menuPanel.add(scrollPane);
        menuPanel.setLayer(scrollPane, 10);
    }

    //装備の情報を表示する
    private void printReq(Blueprint bp, boolean warning) {
        equipInfoPane.removeAll();

        int[] req = new int[itemVar];
        int exist = 1;
        Material[] mt = new Material[itemVar];
        JLabel[] reqLabel = new JLabel[itemVar + 1];
        for (int i = 0; i < itemVar + 1; i++) {
            reqLabel[i] = new JLabel();
            reqLabel[i].setText("");
        }

        req[0] = bp.baseItem().req_wood;
        req[1] = bp.baseItem().req_iron;
        req[2] = bp.baseItem().req_diamond;
        req[3] = bp.baseItem().req_leather;
        req[4] = bp.baseItem().req_bronze;
        mt[0] = Material.WOOD;
        mt[1] = Material.IRON;
        mt[2] = Material.DIAMOND;
        mt[3] = Material.LEATHER;
        mt[4] = Material.BRONZE;

        if (warning == true) {
            reqLabel[0].setText("<html>素材が足りません！");
            reqLabel[0].setForeground(Color.RED);
        } else reqLabel[0].setText("<html>[" + bp.baseItem().name + "の設計図]");

        String matTxt;
        for (int i = 0; i < itemVar; i++) {
            if (req[i] > 0) {
                int n = user.getMaterialQuantity(mt[i]);
                matTxt = mt[i].getName() + " " + n + "/" + req[i];
                reqLabel[exist].setText(matTxt);
                if (n < req[i]) reqLabel[exist].setForeground(Color.RED);
                else reqLabel[exist].setForeground(Color.BLACK);
                exist = exist + 1;
            }
        }

        for (int i = 0; i < exist; i++) {
            reqLabel[i].setFont(new Font("ＭＳ ゴシック", BOLD, 22));
            reqLabel[i].setBounds(234, 344 + i * 26, 558, 22);
            equipInfoPane.add(reqLabel[i]);
            equipInfoPane.setLayer(reqLabel[i], 10);
        }

        base.change(menuPanel);

    }

    public void setBlueprint(Blueprint bp){

        setPane.removeAll();
        ImageIcon setBpIcon = new ImageIcon(bp.baseItem().getAssetPath());
        JLabel setBpLabel = new JLabel(isc.scale(setBpIcon,6.0));
        setBpLabel.setBounds(0, 0, 192, 192);
        setPane.add(setBpLabel);
        setPane.setBounds(242, 72, 192, 192);

        upgradePane.removeAll();
        ImageIcon upgradeBpIcon = new ImageIcon(bp.baseItem().getAssetPath());
        JLabel upgradeBpLabel = new JLabel(isc.scale(upgradeBpIcon,6.0));
        upgradeBpLabel.setBounds(0, 0, 192, 192);
        upgradePane.add(upgradeBpLabel);
        upgradePane.setBounds(592, 72, 192, 192);

        buttonUpgrade.setEnabled(true);

        menuPanel.add(setPane);
        menuPanel.setLayer(setPane, 10);
        menuPanel.add(upgradePane);
        menuPanel.setLayer(upgradePane, 10);
    }

    public void actionPerformed(ActionEvent e) {
        // Upgradeボタンが押されたときの処理
        if (e.getSource() == buttonUpgrade) {
            System.out.println("力が…欲しいか…？");
        }
        for (int i = 0; i < bpVar; i++) {
            if (e.getSource() == bpButton[i].button) {
                equipInfoPane.removeAll();
                printReq(bpButton[i].blueprint, false);
                setBlueprint(bpButton[i].blueprint);
            }
        }

    }

    public static void main(String args[]) {
        User user = new User(114514, "testUser", 45590, 3);

        user.addBlueprint(new Blueprint(EquipmentItem.WOOD_SWORD), 3);
        user.addBlueprint(new Blueprint(EquipmentItem.IRON_SWORD), 1);
        user.addBlueprint(new Blueprint(EquipmentItem.DIAMOND_SWORD), 2);

        user.addMaterial(Material.WOOD, 5);
        user.addMaterial(Material.IRON, 2);

        WindowBase base = new WindowBase("test");
        Workshop test = new Workshop(base, user);
        base.setVisible(true);
    }

}
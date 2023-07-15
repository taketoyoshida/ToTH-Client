package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Material;
import model.util.User;

public class Warehouse extends JFrame implements ActionListener {

    private User user;
    int itemVar = 5;
    private final WindowBase base;
    private JLayeredPane menuPanel = new JLayeredPane();
    private JLayeredPane equipInfoPane = new JLayeredPane();
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
    JButton[] buttonSelector = new JButton[3];
    itemCompoundButton[] itemButton = new itemCompoundButton[itemVar];

    private class itemCompoundButton{    //アイテム情報が紐づけられたボタンの構造体
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
        for(int i1 = 0;i1<itemVar;i1++){
            itemButton[i1] = new itemCompoundButton();
        }

        for (int i = 0; i < textLabel.length; i++) {
            textLabel[i] = new JLabel();
        }

        start();

        this.base.change(menuPanel);
    }

    public void menu() {//ボタンのみなさんの召喚

        menuPanel.setLayout(null);      //ボタン配置の設定

        buttonSelector[0] = new JButton("装備");
        buttonSelector[1] = new JButton("原型");
        buttonSelector[2] = new JButton("素材");
        for (int i = 0; i < buttonSelector.length; i++) {
            buttonSelector[i].setBounds(16 + i * 177, 64, 160, 32);
            buttonSelector[i].addActionListener(this);
            menuPanel.add(buttonSelector[i]);
            menuPanel.setLayer(buttonSelector[i], 0);
        }

        itemInfoLabel.setBounds(546, 32, 256, 464);
        menuPanel.add(itemInfoLabel);
        menuPanel.setLayer(itemInfoLabel, -5);
        infoSlotLabel.setBounds(610, 64, 128, 128);
        menuPanel.add(infoSlotLabel);
        menuPanel.setLayer(infoSlotLabel, 0);
    }

    public void start() {
        menu();

        getEquipmentList();
    }

    public void getEquipmentList() {
        listPane.removeAll();
        buttonSelector[0].setEnabled(false);
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
        int limit = 28, pf = 366;
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

    public void getItemList() {         //素材アイテムをリストとして表示する
        listPane.removeAll();
        buttonSelector[0].setEnabled(true);  //押せるボタンの切り替え
        buttonSelector[1].setEnabled(true);
        buttonSelector[2].setEnabled(false);
        int pf = 366;             //アイテムの数とスクロールの最大値

        /*各アイテムの数量とアイコンを取得する*/
        int[] itemNum = new int[itemVar];
        ImageIcon[] itemIcon = new ImageIcon[itemVar];
        itemNum[0] = user.getMaterialQuantity(Material.WOOD);
        itemNum[1] = user.getMaterialQuantity(Material.IRON);
        itemNum[2] = user.getMaterialQuantity(Material.DIAMOND);
        itemNum[3] = user.getMaterialQuantity(Material.LEATHER);
        itemNum[4] = user.getMaterialQuantity(Material.BRONZE);
        itemIcon[0] = new ImageIcon(Material.WOOD.getAssetPath());
        itemIcon[1] = new ImageIcon(Material.IRON.getAssetPath());
        itemIcon[2] = new ImageIcon(Material.DIAMOND.getAssetPath());
        itemIcon[3] = new ImageIcon(Material.LEATHER.getAssetPath());
        itemIcon[4] = new ImageIcon(Material.BRONZE.getAssetPath());

        for (int i = 0; i < itemVar; i++) {
            /*背景の枠の表示*/
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);
            /*itemCompoundButtonを用いたアイテム用ボタンの表示*/
            itemButton[i].button = new JButton(isc.scale(itemIcon[i], 2.0));
            itemButton[i].button.setBorderPainted(false);
            itemButton[i].button.setContentAreaFilled(false);
            itemButton[i].button.setBounds(16 + 80 * (i % 6), 16 + 80 * (i / 6), 64, 64);
            listPane.add(itemButton[i].button);
            itemButton[i].button.addActionListener(this);
            listPane.setLayer(itemButton[i].button, 10);
            /*アイテムの数の表示*/
            JLabel num = new JLabel(String.valueOf(itemNum[i]));
            num.setBounds(64 + 80 * (i % 6), 64 + 80 * (i / 6), 32, 32);
            num.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
            listPane.add(num);
            listPane.setLayer(num, 20);
        }
        int n = ((int) Math.ceil((double) itemVar / 6.0)) * 80 + 16;
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
        if(e.getSource() == itemButton[2].button) {
            System.out.println("テメェ今俺の頭の事なんつったァ!!!");
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

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Warehouse extends JFrame implements ActionListener {

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


    public Warehouse(WindowBase base) {

        this.base = base;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        menuPanel.add(label1);
        menuPanel.setLayer(label1, -10);

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

    public void getItemList() {
        listPane.removeAll();
        buttonSelector[0].setEnabled(true);
        buttonSelector[1].setEnabled(true);
        buttonSelector[2].setEnabled(false);
        int limit = 5, pf = 366;
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

    }

    public static void main(String args[]) {
        WindowBase base = new WindowBase("test");
        Warehouse test = new Warehouse(base);
        base.setVisible(true);
    }

}

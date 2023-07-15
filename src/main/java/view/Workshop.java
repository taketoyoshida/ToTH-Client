package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.util.User;

import static java.awt.Font.BOLD;

public class Workshop extends JFrame implements ActionListener {

    private User user;
    private final WindowBase base;
    private JLayeredPane menuPanel = new JLayeredPane();
    private JLayeredPane equipInfoPane = new JLayeredPane();
    private JLayeredPane listPane = new JLayeredPane();
    private ImageIcon iconBackground = new ImageIcon("./assets/imgs/ログイン画面.png");    //画像のディレクトリは調整してもろて
    private ImageIcon iconList = new ImageIcon("./assets/imgs/TestEquipBaseList.png");
    private ImageIcon iconEquipPickArea = new ImageIcon("./assets/imgs/TestWorkshopText.png");
    private ImageIcon iconUpgradeSelection = new ImageIcon("./assets/imgs/TestEquipSlot.png");
    private ImageIcon iconSlot = new ImageIcon("./assets/imgs/TestEquipSlot4.png");
    private ImageIcon iconUpgrade = new ImageIcon("./assets/imgs/TestButton3.png");
    private ImageIcon iconItem = new ImageIcon("./assets/imgs/TestItemShield.png");

    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");
    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(iconBackground);        //画像はlabelで取り込む
    JLabel label2 = new JLabel(iconEquipPickArea);
    JLabel labelUpgradeBefore = new JLabel(iconUpgradeSelection);
    JLabel labelUpgradeAfter = new JLabel(iconUpgradeSelection);
    JLabel[] textLabel = new JLabel[6];

    JScrollPane scrollPane = new JScrollPane();
    // viewportにscrollPaneの中のコンテンツを格納
    // scrollPane>viewport>listPane>itemButton
    JViewport viewport = scrollPane.getViewport();
    JButton buttonUpgrade = new JButton(iconUpgrade);


    public Workshop(WindowBase base, User user) {

        this.base = base;
        this.user = user;
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

        getList();
        viewport.setView(listPane);
        scrollPane.setBounds(16, 16, 176 + 18, 480);
        menuPanel.add(scrollPane);
        menuPanel.setLayer(scrollPane, 10);

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
        printInfo("装備を選択してください", 0);
        equipInfoPane.setBounds(0, 0, 832, 549);
        menuPanel.add(equipInfoPane);
        menuPanel.setLayer(equipInfoPane, 10);
    }

    public void getList() {
        int limit = 15, pf = 480;
        for (int i = 0; i < limit; i++) {
            JLabel itemSlot = new JLabel(iconSlot);
            itemSlot.setBounds(16 + 80 * (i % 2), 16 + 80 * (i / 2), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);
            JButton itemButton = new JButton(isc.scale(iconItem, 2.0));
            itemButton.setBorderPainted(false);
            itemButton.setContentAreaFilled(false);
            itemButton.setBounds(16 + 80 * (i % 2), 16 + 80 * (i / 2), 64, 64);
            listPane.add(itemButton);
            listPane.setLayer(itemButton, 10);
        }
        int n = ((int) Math.ceil((double) limit / 2.0)) * 80 + 16;
        if (n > 480) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 176, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }
        listPane.setBounds(16, 16, 176, 10000);
        listPane.setPreferredSize(new Dimension(176, pf));
    }

    //装備の情報を表示する
    private void printInfo(String info, int n) {
        System.out.println("test");
        if (n < 0 || n > textLabel.length) return;
        textLabel[n].setText(info);
        textLabel[n].setFont(new Font("ＭＳ ゴシック", BOLD, 22));
        textLabel[n].setBounds(230, 340 + n * 26, 562, 22);
        equipInfoPane.add(textLabel[n]);
        equipInfoPane.setLayer(textLabel[n], 10);
    }

    public void actionPerformed(ActionEvent e) {
        // Upgradeボタンが押されたときの処理
        if (e.getSource() == buttonUpgrade) {
            System.out.println("力が…欲しいか…？");
        }

    }

    public static void main(String args[]) {
        User user = new User(114514, "testUser", 45590, 3);
        WindowBase base = new WindowBase("test");
        Workshop test = new Workshop(base, user);
        base.setVisible(true);
    }

}
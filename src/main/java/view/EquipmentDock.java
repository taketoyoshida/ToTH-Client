package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.util.User;

import static java.awt.Font.BOLD;

public class EquipmentDock extends JFrame implements ActionListener {

    private final WindowBase base;
    private User user;
    private JLayeredPane menuPane = new JLayeredPane();
    private JLayeredPane infoPane = new JLayeredPane();
    private JLayeredPane listPane = new JLayeredPane();
    private ImageIcon iconBackground = new ImageIcon("./assets/imgs/ログイン画面.png");    //画像のディレクトリは調整してもろて
    private ImageIcon iconList = new ImageIcon("./assets/imgs/TestEquipBaseList2.png");
    private ImageIcon iconSlot1 = new ImageIcon("./assets/imgs/TestEquipSlot2.png");
    private ImageIcon iconSector = new ImageIcon("./assets/imgs/TestPlayerSlot.png");
    private ImageIcon iconTextField = new ImageIcon("./assets/imgs/TestDockText.png");
    private ImageIcon iconSlot2 = new ImageIcon("./assets/imgs/TestEquipSlot3.png");
    private ImageIcon iconSlot3 = new ImageIcon("./assets/imgs/TestEquipSlot4.png");
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
    JLabel[] textLabelNow = new JLabel[5];
    JLabel[] textLabelNext = new JLabel[5];

    JScrollPane scrollPane = new JScrollPane();
    JViewport viewport = scrollPane.getViewport();
    JButton b1 = new JButton(iconButton);


    public EquipmentDock(WindowBase base, User user) {

        this.base = base;
        this.user = user;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        menuPane.add(label1);
        menuPane.setLayer(label1, -10);

        for (int i = 0; i < textLabelNow.length; i++) {
            textLabelNow[i] = new JLabel();
        }
        for (int i = 0; i < textLabelNext.length; i++) {
            textLabelNext[i] = new JLabel();
        }

        start();

        base.change(menuPane);

    }

    public void menu() {//ボタンのみなさんの召喚

        menuPane.setLayout(null);      //ボタン配置の設定

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

        printStatus();

        getList();
        viewport.setView(listPane);
        scrollPane.setBounds(500, 16, 256 + 18, 462 + 18);
        menuPane.add(scrollPane);
        menuPane.setLayer(scrollPane, 10);
    }

    public void start() {
        menu();
        infoPane.setBounds(0, 0, 832, 549);
        menuPane.add(infoPane);
        menuPane.setLayer(infoPane, 10);
    }

    public void getList() {
        int limit = 15, pf = 462;
        for (int i = 0; i < limit; i++) {
            JLabel itemSlot = new JLabel(iconSlot3);
            itemSlot.setBounds(16 + 80 * (i % 3), 16 + 80 * (i / 3), 64, 64);
            listPane.add(itemSlot);
            listPane.setLayer(itemSlot, 0);
            JButton itemButton = new JButton(isc.scale(iconItem, 2.0));
            itemButton.setBorderPainted(false);
            itemButton.setContentAreaFilled(false);
            itemButton.setBounds(16 + 80 * (i % 3), 16 + 80 * (i / 3), 64, 64);
            listPane.add(itemButton);
            listPane.setLayer(itemButton, 10);
        }
        int n = ((int) Math.ceil((double) limit / 3.0)) * 80 + 16;
        if (n > 462) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(iconList);
            listLabel.setBounds(0, i * 80 - 64, 336, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, -10);
        }
        listPane.setBounds(16, 16, 256, 10000);
        listPane.setPreferredSize(new Dimension(256, pf));
    }

    private void printStatus() {
        int testHP = 5;
        JLabel testLabel2 = new JLabel(" HP:" + String.format("%2d", testHP));
        testLabel2.setBounds(112, 354, 1000, 27);
        testLabel2.setFont(new Font("MS ゴシック", BOLD, 22));
        menuPane.add(testLabel2);
        menuPane.setLayer(testLabel2, 10);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            System.out.println("力が…欲しいか…？");
        }

    }

    public static void main(String args[]) {
        User user = new User(114514,"testUser",45590,3);
        WindowBase base = new WindowBase("test");
        EquipmentDock test = new EquipmentDock(base,user);
        base.setVisible(true);
    }

}
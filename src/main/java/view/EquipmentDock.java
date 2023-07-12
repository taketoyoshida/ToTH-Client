package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Font.BOLD;

public class EquipmentDock extends JFrame implements ActionListener {

    private final WindowBase base;
    private JLayeredPane p = new JLayeredPane();
    private JLayeredPane p2 = new JLayeredPane();
    private JLayeredPane listPane = new JLayeredPane();
    private ImageIcon iconBackground = new ImageIcon("./assets/imgs/ログイン画面.png");    //画像のディレクトリは調整してもろて
    private ImageIcon listIcon = new ImageIcon("./assets/imgs/TestEquipSlot2.png");
    private ImageIcon iconSlot1 = new ImageIcon("./assets/imgs/TestEquipSlot2.png");
    private ImageIcon iconSector = new ImageIcon("./assets/imgs/TestPlayerSlot.png");
    private ImageIcon iconTextField = new ImageIcon("./assets/imgs/TestDockText.png");
    private ImageIcon iconSlot2 = new ImageIcon("./assets/imgs/TestEquipSlot3.png");
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
    JLabel[] textLabel = new JLabel[6];

    JScrollPane sp = new JScrollPane();
    JViewport vp = sp.getViewport();
    JButton b1 = new JButton(iconButton);


    public EquipmentDock(WindowBase base) {

        this.base = base;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        for (int i = 0; i < textLabel.length; i++) {
            textLabel[i] = new JLabel();
        }

        start();

        base.change(p);


    }

    public void menu() {//ボタンのみなさんの召喚

        p.setLayout(null);      //ボタン配置の設定

        /*部位ごとの現在の装備の表示*/
        labelSlotHead.setBounds(16, 64, 128, 128);
        p.add(labelSlotHead);
        p.setLayer(labelSlotHead, 0);
        labelSlotBody.setBounds(16, 208, 128, 128);
        p.add(labelSlotBody);
        p.setLayer(labelSlotBody, 0);
        labelSlotMainhand.setBounds(336, 64, 128, 128);
        p.add(labelSlotMainhand);
        p.setLayer(labelSlotMainhand, 0);
        labelSlotOffhand.setBounds(336, 208, 128, 128);
        p.add(labelSlotOffhand);
        p.setLayer(labelSlotOffhand, 0);
        labelSector.setBounds(144, 64, 192, 272);
        p.add(labelSector);
        p.setLayer(labelSector, 0);

        /*現在の装備と入れ替え予定の装備の表示*/
        textFieldLabel.setBounds(16, 352, 448, 128);
        p.add(textFieldLabel);
        p.setLayer(textFieldLabel, -5);
        labelSlotNow.setBounds(16, 384, 96, 96);
        p.add(labelSlotNow);
        p.setLayer(labelSlotNow, 0);
        labelSlotNext.setBounds(192, 384, 96, 96);
        p.add(labelSlotNext);
        p.setLayer(labelSlotNext, 0);
        JLabel labelTextNow = new JLabel("[装備中]");
        labelTextNow.setBounds(23, 354, 1000, 27);
        labelTextNow.setFont(new Font("MS ゴシック", BOLD, 22));
        p.add(labelTextNow);
        p.setLayer(labelTextNow, 10);
        JLabel labelTextNext = new JLabel("[装備予定]");
        labelTextNext.setBounds(188, 354, 1000, 27);
        labelTextNext.setFont(new Font("MS ゴシック", BOLD, 22));
        p.add(labelTextNext);
        p.setLayer(labelTextNext, 10);
    }

    public void start() {
        menu();
        //printInfo("装備を選択してください", 0);
        p2.setBounds(0, 0, 832, 549);
        p.add(p2);
        p.setLayer(p2, 10);
    }

    public void getList() {
        int limit = 15, pf = 480;
        for (int i = 0; i < limit; i++) {
            ImageIcon iIcon = new ImageIcon("./assets/imgs/TestItemShield.png");
            JButton iLabel = new JButton(isc.scale(iIcon, 2.0));
            iLabel.setBounds(16 + 80 * (i % 2), 16 + 80 * (i / 2), 64, 64);
            listPane.add(iLabel);
            listPane.setLayer(iLabel, 10);
        }
        int n = ((int) Math.ceil((double) limit / 2.0)) * 80 + 16;
        if (n > 480) pf = n;
        for (int i = 0; i * 80 - 64 <= pf; i++) {
            JLabel listLabel = new JLabel(listIcon);
            listLabel.setBounds(0, i * 80 - 64, 176, 80);
            listPane.add(listLabel);
            listPane.setLayer(listLabel, 0);
        }
        listPane.setBounds(16, 16, 176, 10000);
        listPane.setPreferredSize(new Dimension(176, pf));
    }

    private void printStatus() {
        int testHP = 5;
        JLabel testLabel2 = new JLabel(" HP:" + String.format("%2d", testHP));
        testLabel2.setBounds(112, 354, 1000, 27);
        testLabel2.setFont(new Font("MS ゴシック", BOLD, 22));
        p.add(testLabel2);
        p.setLayer(testLabel2, 10);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            System.out.println("力が…欲しいか…？");
        }

    }

    public static void main(String args[]) {
        WindowBase base = new WindowBase("test");
        EquipmentDock test = new EquipmentDock(base);
        base.setVisible(true);
    }

}
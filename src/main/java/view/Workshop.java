package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Workshop extends JFrame implements ActionListener {

    private final WindowBase base;
    private JLayeredPane p = new JLayeredPane();
    private JLayeredPane p2 = new JLayeredPane();
    private JLayeredPane listPane = new JLayeredPane();
    private ImageIcon icon1 = new ImageIcon("./assets/imgs/ログイン画面.png");    //画像のディレクトリは調整してもろて
    private ImageIcon listIcon = new ImageIcon("./assets/imgs/TestEquipBaseList.png");
    private ImageIcon icon2 = new ImageIcon("./assets/imgs/TestWorkshopText.png");
    private ImageIcon icon3 = new ImageIcon("./assets/imgs/TestEquipSlot.png");
    private ImageIcon bIcon = new ImageIcon("./assets/imgs/TestButton3.png");
    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");
    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    JLabel label2 = new JLabel(icon2);
    JLabel label3 = new JLabel(icon3);
    JLabel label4 = new JLabel(icon3);

    JScrollPane sp = new JScrollPane();
    JViewport vp = sp.getViewport();
    JButton b1 = new JButton(bIcon);


    public Workshop(WindowBase base) {

        this.base = base;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        menu();

        base.change(p);


    }

    public void menu() {//ボタンのみなさんの召喚

        p.setLayout(null);      //ボタン配置の設定

        getList();
        vp.setView(listPane);
        sp.setBounds(16, 16, 176 + 18, 480);
        p.add(sp);
        p.setLayer(sp, 10);

        label2.setBounds(226, 336, 574, 160);
        p.add(label2);
        p.setLayer(label2, 0);

        label3.setBounds(242, 72, 192, 192);
        p.add(label3);
        p.setLayer(label3, 0);
        label4.setBounds(592, 72, 192, 192);
        p.add(label4);
        p.setLayer(label4, 0);

        b1.setBorderPainted(false);
        b1.setContentAreaFilled(false);
        b1.setBounds(450, 136, 126, 64);
        p.add(b1);
        b1.addActionListener(this);
        p.setLayer(b1, 0);
        b1.setEnabled(false);
    }

    public void start() {

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            System.out.println("力が…欲しいか…？");
        }

    }

    public static void main(String args[]) {
        WindowBase base = new WindowBase("test");
        Workshop test = new Workshop(base);
        base.setVisible(true);
    }

}
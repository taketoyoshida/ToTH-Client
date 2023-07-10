package view;

import controller.networking.GachaGateway;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import static java.awt.Font.BOLD;

public class GachaWindow extends JFrame implements MouseListener {

    WindowBase base;
    int coin;
    String[] puText = new String[5];
    JLayeredPane p = new JLayeredPane();             //操作で変化しない部分を表示する
    JLayeredPane p2 = new JLayeredPane();            //操作ごとに変化する部分を格納する
    ImageIcon icon1 = new ImageIcon("./assets/imgs/MainMenuTest.png");    //画像のディレクトリは調整してもろて
    ImageIcon cIcon = new ImageIcon("./assets/imgs/TestCoin.png");
    ImageIcon icon2 = new ImageIcon("./assets/imgs/TestGachaInfo.png");
    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");
    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    JLabel cLabel = new JLabel(cIcon);
    JLabel label2 = new JLabel(icon2);
    JLabel textLabel1 = new JLabel();
    JLabel textLabel2 = new JLabel();
    JLabel[] puLabel = new JLabel[5];

    /*本当は「一回 回す\n<コインの画像>×何枚」みたいにしたい*/
    /*しかしどうやらコンストラクタで画像をはさむことはできないらしい*/
    /*丸ごと画像にしたほうが早いかも… 7/6 */
    JButton b1 = new JButton("１回 回す");
    JButton b2 = new JButton("10回 回す");

    Timer timer = new Timer(false);



    public GachaWindow(WindowBase base) {
        /*実際の運用ではコイン数を管理するクラスからメソッドでコイン数をもらいたい*/
        /*コイン数の増減も同様にメソッドにすべきかも？要検討　7/6 */
        this.coin = 0;
        this.base = base;

        start();

        base.change(p);


    }

    public void menu() {                  //ガチャ画面の基本パーツを召喚する

        p.setLayout(null);                //ボタン配置の設定
        p2.setLayout(null);

        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        cLabel.setBounds(600, 20, 32, 32);  //右上のコイン数の表示
        p.add(cLabel);
        p.setLayer(cLabel, 0);
        textLabel1.setText("×" + coin);
        textLabel1.setFont(new Font("ＭＳ ゴシック", BOLD, 32));
        textLabel1.setBounds(632, 20, 100, 32);
        p.add(textLabel1);
        p.setLayer(textLabel1, 10);

        label2.setBounds(91, 60, 650, 200);  //中央の掲示板の表示
        p.add(label2);
        p.setLayer(label2, 0);

        b1.setBounds(100, 300, 250, 150);
        b1.addMouseListener(this);
        p.add(b1);

        b2.setBounds(482, 300, 250, 150);
        b2.addMouseListener(this);
        p.add(b2);

        addMouseListener(this);
    }

    public void start() {

        p2.removeAll();
        menu();

        textLabel2.setText("[現在ランクで排出される装備]");
        textLabel2.setFont(new Font("ＭＳ ゴシック", BOLD, 24));
        textLabel2.setBounds(0, 0, 630, 24);
        p2.add(textLabel2);
        p2.setLayer(textLabel2, 0);

        puText[0] = "親の孫の顔が見たい発言";                            //PUとして表示する内容
        puText[1] = "結婚したらしい昔の友人";
        puText[2] = "同窓会の事後報告";
        puText[3] = "通学中に見かけるカップル";
        puText[4] = "去っていくビターくん";
        int y = 25, charSize = 28;                                    //計算用変数
        for (int i = 0; i < puLabel.length; i++) {
            puLabel[i] = new JLabel();
            if (i < 2) {
                charSize = 28;
                puLabel[i].setText("☆３　" + puText[i]);
            } else {
                charSize = 24;
                puLabel[i].setText("☆２　" + puText[i]);
            }
            puLabel[i].setFont(new Font("ＭＳ ゴシック", BOLD, charSize));
            puLabel[i].setBounds(0, y, 630, charSize);
            y = y + charSize + 5;
            p2.add(puLabel[i]);
            p2.setLayer(puLabel[i], 0);
        }
        p2.setBounds(98, 70, 650, 200);
        p.add(p2);
        p.setLayer(p2, 10);
    }

    public void gachaWait() {
        p2.removeAll();
        ImageIcon wIcon = new ImageIcon("./assets/imgs/GachaWait.gif");
        JLabel waitLabel = new JLabel(wIcon);
        waitLabel.setBounds(0, 0, 200, 200);  //中央のガチャ情報の表示
        p2.add(waitLabel);
        p2.setLayer(waitLabel, 0);

        p2.setBounds(316, 60, 200, 200);
        p.add(p2);
        p.setLayer(p2, 10);

    }

    public void gachaSingle() {
        TimerTask gachaSingle = new TimerTask() {
            @Override
            public void run() {
                p2.removeAll();
                ImageIcon iIcon = new ImageIcon("./assets/imgs/TestItemShield.png");
                JLabel itemLabel = new JLabel(isc.scale(iIcon, 2.0));
                itemLabel.setBounds(0, 0, 64, 64);  //中央のガチャ情報の表示
                p2.add(itemLabel);
                p2.setLayer(itemLabel, 0);
                p2.setBounds(384, 128, 64, 64);
                p.add(p2);
                p.setLayer(p2, 10);
                base.change(p);

                timer.cancel();
                timer = new Timer(false);
            }
        };
        timer.schedule(gachaSingle, 2000);
    }

    public void gachaTenTimes() {
        TimerTask gachaTenTimes = new TimerTask() {
            @Override
            public void run() {
                p2.removeAll();
                ImageIcon[] iIcon = new ImageIcon[10];
                for (int i = 0; i < 10; i++) {
                    iIcon[i] = new ImageIcon("./assets/imgs/TestItemShield.png");
                    JLabel itemLabel = new JLabel(isc.scale(iIcon[i], 2.0));
                    if (i < 5) itemLabel.setBounds(55 + 119 * i, 24, 64, 64);
                    else itemLabel.setBounds(55 + 119 * (i - 5), 112, 64, 64);
                    p2.add(itemLabel);
                    p2.setLayer(itemLabel, 0);
                }

                p2.setBounds(91, 60, 650, 200);
                p.add(p2);
                p.setLayer(p2, 10);
                base.change(p);

                timer.cancel();
                timer = new Timer(false);
            }
        };
        timer.schedule(gachaTenTimes, 3000);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.println("test");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == b1) {
            System.out.println("単発に願いをこめて");
            gachaWait();
            gachaSingle();
        }
        if (e.getSource() == b2) {
            System.out.println("物量こそ正義");
            gachaWait();
            gachaTenTimes();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }


    public static void main(String args[]) {
        WindowBase base = new WindowBase("test");
        GachaWindow test = new GachaWindow(base);
        base.setVisible(true);
    }

}
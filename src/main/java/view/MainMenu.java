package view;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu extends JFrame implements MouseListener {

    int x = 408, y = 206;//キャラクターの座標
    JLayeredPane p = new JLayeredPane();
    ImageIcon icon1 = new ImageIcon("./assets/imgs/MainMenuTest.png");    //画像のディレクトリは調整してもろて
    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    //JLabel label2 = new JLabel(icon2);
    //Test7とかいう名前は適当に変えること

    JButton b1 = new JButton("装備");
    JButton b2 = new JButton("\n対戦");


    public MainMenu(String title) {

        setTitle(title);
        setBounds(100, 100, 816, 512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        paint();


    }

    public void paint() {//ボタンのみなさんの召喚

        p.setLayout(null);      //配置の初期化


        b1.setBounds(100, -20, 200, 50);
        b1.addMouseListener(this);
        p.add(b1);


        b2.setBounds(100, -70, 200, 200);
        b2.addMouseListener(this);
        p.add(b2);


        setContentPane(p);
        addMouseListener(this);
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
            System.out.println("そんな装備で大丈夫か？");
        }
        if (e.getSource() == b2) {
            System.out.println("大丈夫だ、問題ない");
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
}


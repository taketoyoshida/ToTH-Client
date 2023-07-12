package view;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu extends JFrame implements MouseListener {

    int x = 408, y = 206;//キャラクターの座標
    private final WindowBase base;
    private JLayeredPane p = new JLayeredPane();
    private ImageIcon icon1 = new ImageIcon("./assets/imgs/ログイン画面.png");    //画像のディレクトリは調整してもろて
    private ImageIcon bIcon1 = new ImageIcon("./assets/imgs/TestButton1.png");
    private ImageIcon bIcon2 = new ImageIcon("./assets/imgs/TestButton2.png");
    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");

    private JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    //JLabel label2 = new JLabel(icon2);
    //Test7とかいう名前は適当に変えること

    private JButton b1 = new JButton(bIcon1);
    private JButton b2 = new JButton(bIcon2);


    public MainMenu(WindowBase base) {

        this.base = base;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        paint(p);

        base.change(p);

    }

    public void paint(JLayeredPane p) {//ボタンのみなさんの召喚

        p.setLayout(null);      //配置の初期化


        b1.setBounds(100, 220, 200, 50);
        b1.addMouseListener(this);
        p.add(b1);


        b2.setBounds(100, 280, 200, 150);
        b2.addMouseListener(this);
        p.add(b2);


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
            Workshop_MainMenu wsTest = new Workshop_MainMenu(base);
        }
        if (e.getSource() == b2) {
            System.out.println("大丈夫だ、問題ない");
            Game testGame = new Game(base);
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
        MainMenu test = new MainMenu(base);
        base.setVisible(true);
    }
}


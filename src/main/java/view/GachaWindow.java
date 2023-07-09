package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.Font.BOLD;

public class GachaWindow extends JFrame implements MouseListener {

    WindowBase base;
    int x = 408, y = 206;//キャラクターの座標
    int coin;
    JLayeredPane p = new JLayeredPane();
    ImageIcon icon1 = new ImageIcon("./assets/imgs/MainMenuTest.png");    //画像のディレクトリは調整してもろて
    ImageIcon cIcon = new ImageIcon("./assets/imgs/TestCoin.png");
    ImageIcon icon2 = new ImageIcon("./assets/imgs/TestGachaInfo.png");
    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    JLabel cLabel = new JLabel(cIcon);
    JLabel label2 = new JLabel(icon2);
    JLabel textLabel1 = new JLabel();
    JLabel textLabel2 = new JLabel();

    /*本当は「一回 回す\n<コインの画像>×何枚」みたいにしたい*/
    /*しかしどうやらコンストラクタで画像をはさむことはできないらしい*/
    /*丸ごと画像にしたほうが早いかも… 7/6 */
    JButton b1 = new JButton("１回 回す");
    JButton b2 = new JButton("10回 回す");


    public GachaWindow(WindowBase base) {
        /*実際の運用ではコイン数を管理するクラスからメソッドでコイン数をもらいたい*/
        /*コイン数の増減も同様にメソッドにすべきかも？要検討　7/6 */
        this.coin = 0;
        this.base = base;

        menu();

        base.change(p);


    }

    public void menu() {//ボタンのみなさんの召喚

        p.setLayout(null);      //ボタン配置の設定

        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        cLabel.setBounds(600, 20, 32, 32);  //右上のコイン数の表示
        p.add(cLabel);
        p.setLayer(cLabel, 0);
        textLabel1.setText("×"+coin);
        textLabel1.setFont(new Font("ＭＳ ゴシック",BOLD,32));
        textLabel1.setBounds(632, 20, 100, 32);
        p.add(textLabel1);
        p.setLayer(textLabel1, 10);

        label2.setBounds(83, 60, 650, 200);  //中央のガチャ情報の表示
        p.add(label2);
        p.setLayer(label2, 20);
        textLabel1.setText("test");
        textLabel1.setFont(new Font("ＭＳ ゴシック",BOLD,32));
        textLabel1.setBounds(632, 20, 100, 32);
        p.add(textLabel1);
        p.setLayer(textLabel1, 10);


        b1.setBounds(100, 300, 250, 150);
        b1.addMouseListener(this);
        p.add(b1);

        b2.setBounds(466, 300, 250, 150);
        b2.addMouseListener(this);
        p.add(b2);


        //setContentPane(p);
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
            System.out.println("単発に願いをこめて");
        }
        if (e.getSource() == b2) {
            System.out.println("物量こそ正義");
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
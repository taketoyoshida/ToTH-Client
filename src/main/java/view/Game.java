package view;

import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Game extends JFrame implements KeyListener {

    WindowBase base;
    int x = 408, y = 206;//キャラクターの座標
    boolean keyFlag = false;//キーが無限に押されないようにするための変数
    JLayeredPane p = new JLayeredPane();
    ImageIcon icon1 = new ImageIcon("./assets/imgs/イラスト7.jpg");    //画像のディレクトリは調整してもろて
    ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    JLabel label2 = new JLabel(icon2);
    //Test7とかいう名前は適当に変えること

    public Game(WindowBase base) {

        this.base = base;
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, 10);

        //paint();
        label2.setBounds(x, y, 32, 32);
        p.add(label2);//数字がでかいほど手前に来る
        p.setLayer(label2, 20);

        //setContentPane(p);
        base.addKeyListener(this);

        base.change(p);


    }

    public void paint() {//キャラクターの描画とレイヤーの設定
        label2.setBounds(x, y, 32, 32);

        //JLabel label2 = new JLabel();
        //label2.setIcon(icon2);

        //JPanel p = new JPanel();
        //JLayeredPaneを使うとレイヤー設定ができる


        p.add(label2);//数字がでかいほど手前に来る
        p.setLayer(label2, 20);

        //setContentPane(p);
        //addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) { //Keyを押したときの動作
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                if (!keyFlag) {
                    moveCharacter(0, -32);
                    keyFlag = true;
                }

                break;
            case KeyEvent.VK_S:
                if (!keyFlag) {
                    moveCharacter(0, 32);
                    keyFlag = true;
                }
                break;
            case KeyEvent.VK_A:
                if (!keyFlag) {
                    moveCharacter(-32, 0);
                    keyFlag = true;
                }
                break;
            case KeyEvent.VK_D:
                if (!keyFlag) {
                    moveCharacter(32, 0);
                    keyFlag = true;
                }
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {//Keyboardを離したときの動作
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keyFlag = false;

                break;
            case KeyEvent.VK_D:
                keyFlag = false;

                break;
            case KeyEvent.VK_S:
                keyFlag = false;
                break;
            case KeyEvent.VK_A:
                keyFlag = false;

                break;
        }

    }

    public void moveCharacter(int X, int Y) {//キャラクターを動かす。
        if (Y < 0 && X == 0) {
            for (int i = 0; i < -1 * Y; i++) {
                y--;
                paint();
            }
            System.out.println("Wが押されました");
        } else if (y > 0 && X == 0) {
            for (int i = 0; i < Y; i++) {
                y++;
                paint();
            }
            System.out.println("Sが押されました");

        } else if (X < 0 && Y == 0) {
            for (int i = 0; i < -1 * X; i++) {
                x--;
                paint();
            }
            System.out.println("Aが押されました");
        } else if (X > 0 && Y == 0) {
            for (int i = 0; i < X; i++) {
                x++;
                paint();
            }
            System.out.println("dが押されました");
        }
    }


    public static void main(String args[]) {
        WindowBase base = new WindowBase("test");
        Game test = new Game(base);
        base.setVisible(true);
    }


}

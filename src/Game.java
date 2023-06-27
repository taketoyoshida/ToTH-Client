import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Game extends JFrame implements KeyListener{
    int turn;   //手番
    int[][] board = new int[8][12];
    int[] remainAction = new int[2];
    boolean[] isDead = new boolean[2];

    int x=408,y=206;//キャラクターの座標
    boolean Keyflag=false;//キーが無限に押されないようにするための変数
    JLayeredPane p = new JLayeredPane();
    ImageIcon icon1 = new ImageIcon("./assets/imgs/イラスト7.jpg");    //画像のディレクトリは調整してもろて
    ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    JLabel label2 = new JLabel(icon2);
    //Test7とかいう名前は適当に変えること


    public void keyTyped(KeyEvent e){


    }
    public void keyPressed(KeyEvent e) { //Keyを押したときの動作
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                if(!Keyflag){
                    moveCharactar(0,-32);
                    Keyflag=true;
                }

                break;
            case KeyEvent.VK_S:
                if(!Keyflag){
                    moveCharactar(0,32);
                    Keyflag=true;
                }
                break;
            case KeyEvent.VK_A:
                if(!Keyflag){
                    moveCharactar(-32,0);
                    Keyflag=true;
                }
                break;
            case KeyEvent.VK_D:
                if(!Keyflag){
                    moveCharactar(32,0);
                    Keyflag=true;
                }
                break;
        }

    }
    public void keyReleased(KeyEvent e){//Keyboardを離したときの動作
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                Keyflag = false;

                break;
            case KeyEvent.VK_D:
                Keyflag = false;

                break;
            case KeyEvent.VK_S:
                Keyflag = false;
                break;
            case KeyEvent.VK_A:
                Keyflag = false;

                break;
        }

    }
    public void moveCharactar(int X,int Y){//キャラクターを動かす。
        if(Y<0&&X==0) {
            for (int i = 0; i < -1 * Y; i++) {
                y--;
                paint();
            }
            System.out.println("Wが押されました");
        }else if(y>0&&X==0) {
            for (int i = 0; i < Y; i++) {
                y++;
                paint();
            }
            System.out.println("Sが押されました");

        }else if(X<0&&Y==0) {
            for (int i = 0; i < -1 * X; i++) {
                x--;
                paint();
            }
            System.out.println("Aが押されました");
        }else if(X>0&&Y==0) {
            for (int i = 0; i < X; i++) {
                x++;
                paint();
            }
            System.out.println("dが押されました");
        }
    }


    public Game(String title){

        setTitle(title);
        setBounds(100, 100, 816, 512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, 10);

        paint();


    }

    public void paint(){//キャラクターの描画とレイヤーの設定
        label2.setBounds(x,y,32,32);

        //JLabel label2 = new JLabel();
        //label2.setIcon(icon2);

        //JPanel p = new JPanel();
        //JLayerdPaneを使うとレイヤー設定ができる


        p.add(label2);//数字がでかいほど手前に来る
        p.setLayer(label2, 20);

        setContentPane(p);
        addKeyListener(this);
    }
}
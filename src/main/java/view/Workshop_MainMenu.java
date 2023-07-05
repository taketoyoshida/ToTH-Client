package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Workshop_MainMenu extends JFrame implements MouseListener {

    int x = 408, y = 206;//キャラクターの座標
    JLayeredPane p = new JLayeredPane();
    ImageIcon icon1 = new ImageIcon("./assets/imgs/MainMenuTest.png");    //画像のディレクトリは調整してもろて
    //ImageIcon bIcon1 = new ImageIcon("./assets/imgs/TestButton1.png");
    //ImageIcon bIcon2 = new ImageIcon("./assets/imgs/TestButton2.png");
    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    //JLabel label2 = new JLabel(icon2);
    //Test7とかいう名前は適当に変えること

    JButton b1 = new JButton("ガチャ");
    JButton b2 = new JButton("製造");
    JButton b3 = new JButton("強化");
    JButton b4 = new JButton("装備する");


    public Workshop_MainMenu(String title) {

        setTitle(title);
        setBounds(100, 100, 816, 512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label1.setBounds(0, 0, 816, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        paint();


    }

    public void paint() {//ボタンのみなさんの召喚

        p.setLayout(null);      //ボタン配置の設定

        b1.setBounds(100, 100, 200, 100);
        b1.addMouseListener(this);
        p.add(b1);

        b2.setBounds(500, 100, 200, 100);
        b2.addMouseListener(this);
        p.add(b2);

        b3.setBounds(100, 300, 200, 100);
        b3.addMouseListener(this);
        p.add(b3);

        b4.setBounds(500, 300, 200, 100);
        b4.addMouseListener(this);
        p.add(b4);


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
                System.out.println("ガチャは悪い文明…！破壊する…！");
        }
        if (e.getSource() == b2) {
            System.out.println("2-4-11");
        }
        if (e.getSource() == b3) {
            System.out.println("素晴らしく運がないな君は");
        }
        if (e.getSource() == b4) {
            System.out.println("ここで装備していくかい？");
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
        Workshop_MainMenu frame = new Workshop_MainMenu("MyTitle");
        frame.setVisible(true);


        /*フルスクリーンに出来るが、画像と画面の大きさと合わない
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        gd.setFullScreenWindow(frame);
        DisplayMode[] modelist = gd.getDisplayModes();
        DisplayMode activeMode = null;
        for(DisplayMode mode : modelist){
            System.out.println(mode);
            if(mode.getWidth()==800&& mode.getHeight()==600 &&
                    ((activeMode == null)
                    || activeMode.getBitDepth()<mode.getBitDepth()
                    ||activeMode.getBitDepth()==mode.getBitDepth() && activeMode.getRefreshRate()<=mode.getRefreshRate())) {
                activeMode = mode;
            }
        }

        if(activeMode!=null){
            gd.setDisplayMode(activeMode);
        }else{
            System.out.println("解像度変更失敗");
        }*/
    }

}


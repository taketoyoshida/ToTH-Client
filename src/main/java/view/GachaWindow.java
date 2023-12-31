package view;

import controller.home.GachaMock;
import controller.networking.GachaGateway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import model.Blueprint;
import model.Material;
import model.util.User;

import static java.awt.Font.BOLD;

public class GachaWindow extends JFrame implements ActionListener {

    private User user;
    WindowBase base;
    private BackButton bButton = new BackButton();
    String[] puText = new String[5];           //新規装備の名前を５つ格納する配列
    JLayeredPane p = new JLayeredPane();             //操作で変化しない部分を表示する
    JLayeredPane p2 = new JLayeredPane();            //操作ごとに変化する部分を格納する

    ImageIcon icon1 = new ImageIcon("./assets/imgs/Equipment_backImg.png");    //画像のディレクトリは調整してもろて
    ImageIcon cIcon = new ImageIcon("./assets/imgs/Coin.png");
    ImageIcon icon2 = new ImageIcon("./assets/imgs/Gachaikei.png");
    private final ImageIcon gachaInfo = new ImageIcon("./assets/imgs/GachaInfo.png");
    private final ImageIcon gachaIcon = new ImageIcon("./assets/imgs/GachaButton.png");
    //ImageIcon icon2 = new ImageIcon("./assets/imgs/エルフ.jpg");
    ImageScaling isc = new ImageScaling();

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    JLabel cLabel = new JLabel(cIcon);
    JLabel label2 = new JLabel(icon2);
    JLabel textLabel1 = new JLabel();

    /*本当は「一回 回す\n<コインの画像>×何枚」みたいにしたい*/
    /*しかしどうやらコンストラクタで画像をはさむことはできないらしい*/
    /*丸ごと画像にしたほうが早いかも… 7/6 */
    JButton b1 = new JButton(gachaIcon);
    JButton b2 = new JButton(gachaIcon);

    Timer timer = new Timer(false);//待機画面表示用のタイマー
    private GachaGateway.IGachaGateway gateway;


    public GachaWindow(WindowBase base, User user) {
        /*実際の運用ではコイン数を管理するクラスからメソッドでコイン数をもらいたい*/
        /*コイン数の増減も同様にメソッドにすべきかも？要検討　7/6 */
        this.base = base;
        this.user = user;

        start();

        base.change(p);
        gateway = new GachaGateway.MockGachaGateway();
    }

    public static void main(String args[]) {
        User user = new User(114514, "testUser", 7974, 3);
        WindowBase base = new WindowBase("test");
        GachaWindow test = new GachaWindow(base, user);
        base.setVisible(true);

        user.addMaterial(Material.WOOD, 300);
        user.addMaterial(Material.IRON, 200);
        user.addMaterial(Material.DIAMOND, 100);
        user.addMaterial(Material.LEATHER, 300);
        user.addMaterial(Material.BRONZE, 200);

    }

    public void menu() {                  //ガチャ画面の基本パーツを召喚する

        p.setLayout(null);                //ボタン配置の設定
        p2.setLayout(null);

        label1.setBounds(0, 0, 832, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        cLabel.setBounds(600, 20, 32, 32);  //右上のコイン数の表示
        p.add(cLabel);
        p.setLayer(cLabel, 0);
        getUserCoin();                  //コイン枚数も更新して表示する
        textLabel1.setFont(new Font("ＭＳ ゴシック", BOLD, 32));
        textLabel1.setBounds(632, 20, 500, 32);
        p.add(textLabel1);
        p.setLayer(textLabel1, 10);

        label2.setBounds(91, 60, 650, 200);  //中央の掲示板の表示
        p.add(label2);
        p.setLayer(label2, 0);

        /*ガチャボタンの表示*/
        b1.setBounds(91, 300, 256, 160);
        b1.setBorderPainted(false);
        b1.setContentAreaFilled(false);
        b1.addActionListener(this);
        p.add(b1);
        p.setLayer(b1, 0);
        JLabel b1Label = new JLabel("１回回す");
        b1Label.setFont(new Font("PixelMplus10", Font.BOLD, 56));
        b1Label.setHorizontalAlignment(JLabel.CENTER);
        b1Label.setVerticalAlignment(JLabel.CENTER);
        b1Label.setBounds(91, 300, 256, 160);
        p.add(b1Label);
        p.setLayer(b1Label, 10);

        b2.setBounds(485, 300, 256, 160);
        b2.setBorderPainted(false);
        b2.setContentAreaFilled(false);
        b2.addActionListener(this);
        p.add(b2);
        JLabel b2Label = new JLabel("10回回す");
        b2Label.setFont(new Font("PixelMplus10", Font.BOLD, 56));
        b2Label.setHorizontalAlignment(JLabel.CENTER);
        b2Label.setVerticalAlignment(JLabel.CENTER);
        b2Label.setBounds(485, 300, 256, 160);
        p.add(b2Label);
        p.setLayer(b2Label, 10);

        bButton.setButtonRight(p);
        bButton.button().addActionListener(this);
    }

    public void start() {         //ガチャ画面の最初の表示を行うメソッド

        p2.removeAll();
        menu();                   //ここで基本パーツを召喚する

        /*
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
        */

        JLabel gachaInfoLabel = new JLabel(gachaInfo);
        gachaInfoLabel.setBounds(0,0,650,200);
        p2.add(gachaInfoLabel);
        p2.setLayer(gachaInfoLabel, 0);
        p2.setBounds(91, 60, 650, 200);
        p.add(p2);
        p.setLayer(p2, 10);
    }

    public void getUserCoin() {      //ユーザのコイン数を取得する
        int coin = user.getBalance();    //まず表示する
        textLabel1.setHorizontalAlignment(JLabel.LEFT);
        if (coin > 9999) {
            textLabel1.setText("×9999+");
            textLabel1.setForeground(Color.RED);
        } else {
            textLabel1.setText("×" + String.format("%4d", coin));
            textLabel1.setForeground(Color.BLACK);
        }
        base.change(p);

        if (coin < 100) {               //コイン枚数からガチャボタンを無効化する
            b1.setEnabled(false);
        }
        if (coin < 1000) {
            b2.setEnabled(false);
        }
    }

    public void gachaWait() {       //ガチャの待機演出を表示するメソッド
        p2.removeAll();
        ImageIcon wIcon = new ImageIcon("./assets/imgs/GachaWait.gif");
        JLabel waitLabel = new JLabel(wIcon);
        waitLabel.setBounds(0, 0, 200, 200);  //中央のガチャ情報の表示
        p2.add(waitLabel);
        p2.setLayer(waitLabel, 0);
        b1.setEnabled(false);      //待機演出中はガチャボタンは無効化する
        b2.setEnabled(false);
        bButton.button().setEnabled(false);

        p2.setBounds(316, 60, 200, 200);
        p.add(p2);
        p.setLayer(p2, 10);
    }

    public void gachaSingle() {    //単発ガチャを回すメソッド
        /*このメソッドでは、少し待機して処理させる内容をTimerTaskのインスタンスにしている*/
        /*Timerクラスを用いてTimerTaskを遅れて呼び出すことで、TimerTask内の操作を時間差で実行している*/
        /*Timerクラスは使い捨てなのでTimerTask内で定義し直す操作も行っている*/
        /*TimerTaskクラスも使い捨てだが、このメソッドを実行する際にインスタンスを新規生成して使っているので問題ない*/
        GachaMock.GachaResult result;
        double rand = Math.random();
        try {
            result = gateway.play(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ガチャの結果が取得できませんでした");
            return;
        }
        gachaWait();
        TimerTask gachaSingle = new TimerTask() {
            @Override
            public void run() {
                p2.removeAll();
                ImageIcon iIcon = new ImageIcon(result.bp.baseItem().getAssetPath());
                if (iIcon.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) {
                    iIcon = new ImageIcon("./assets/imgs/TestItemShield.png");
                    System.out.println(iIcon.getImageLoadStatus());
                    System.out.println("アイテムの画像が見つかりませんでした: " + result.bp.baseItem().getAssetPath());
                }

                JLabel itemLabel = new JLabel(isc.scale(iIcon, 2.0));
                itemLabel.setBounds(0, 0, 64, 64);  //中央のガチャ情報の表示
                p2.add(itemLabel);
                p2.setLayer(itemLabel, 0);
                p2.setBounds(384, 128, 64, 64);
                p.add(p2);
                p.setLayer(p2, 10);
                b1.setEnabled(true);    //忘れずにボタンを有効化する
                b2.setEnabled(true);
                bButton.button().setEnabled(true);
                getUserCoin();  //コイン数を更新する
                base.change(p);

                timer.cancel();
                timer = new Timer(false);
            }
        };
        long delay = (long)(2000*(rand*0.4+0.8));
        timer.schedule(gachaSingle, delay);
    }

    public void gachaTenTimes() {
        /*やってることはgachaSingleと同じ*/
        /*TimerTask内に10回分繰り返しがあるくらい*/
        GachaMock.GachaResult[] results;
        double rand = Math.random();
        try {
            results = this.gateway.play10(user);
        } catch (Exception e) {
            System.out.println("ガチャに失敗しました。");
            return;
        }
        gachaWait();
        TimerTask gachaTenTimes = new TimerTask() {
            @Override
            public void run() {
                p2.removeAll();
                ImageIcon[] iIcon = new ImageIcon[10];
                for (int i = 0; i < 10; i++) {
                    iIcon[i] = new ImageIcon(results[i].bp.baseItem().getAssetPath());
                    if (iIcon[i].getImageLoadStatus() != MediaTracker.COMPLETE) {
                        iIcon[i] = new ImageIcon("./assets/imgs/TestItemShield.png");
                        System.out.println("アイテムの画像が見つかりませんでした: " + results[i].bp.baseItem().getAssetPath());
                    }
                    getUserCoin();  //コイン数を更新する
                    JLabel itemLabel = new JLabel(isc.scale(iIcon[i], 2.0));
                    if (i < 5) itemLabel.setBounds(55 + 119 * i, 24, 64, 64);
                    else itemLabel.setBounds(55 + 119 * (i - 5), 112, 64, 64);
                    p2.add(itemLabel);
                    p2.setLayer(itemLabel, 0);
                }

                p2.setBounds(91, 60, 650, 200);
                p.add(p2);
                p.setLayer(p2, 10);
                b1.setEnabled(true);    //忘れずにボタンを有効化する
                b2.setEnabled(true);
                bButton.button().setEnabled(true);
                getUserCoin();  //コイン数を更新する
                base.change(p);

                timer.cancel();
                timer = new Timer(false);
            }
        };
        long delay = (long)(3000*(rand*0.8+0.6));
        timer.schedule(gachaTenTimes, delay);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            System.out.println("単発に願いをこめて");
            gachaSingle();
        }
        if (e.getSource() == b2) {
            System.out.println("物量こそ正義");
            gachaTenTimes();
        }

        if (e.getSource() == bButton.button()) {
            Workshop_MainMenu workshopMainMenu = new Workshop_MainMenu(base, user);
        }
    }
}
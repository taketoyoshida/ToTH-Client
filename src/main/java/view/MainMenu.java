package view;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.EquipmentItem;
import model.Material;
import model.Player;
import model.Status;
import model.util.User;

public class MainMenu extends JFrame implements MouseListener {

    User user;
    BackButton bButton = new BackButton();
    private final WindowBase base;
    private JLayeredPane p = new JLayeredPane();
    private JLayeredPane infoPane = new JLayeredPane();
    private ImageIcon icon1 = new ImageIcon("./assets/imgs/home_backImg.png");    //画像のディレクトリは調整してもろて
    private ImageIcon bIcon1 = new ImageIcon("./assets/imgs/Button1.png");
    private ImageIcon bIcon2 = new ImageIcon("./assets/imgs/Button2.png");
    private ImageIcon infoIcon = new ImageIcon("./assets/imgs/MainMenuInfo.png");
    private ImageIcon cIcon = new ImageIcon("./assets/imgs/Coin.png");

    private ImageScaling isc = new ImageScaling();

    private JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    //JLabel label2 = new JLabel(icon2);
    //Test7とかいう名前は適当に変えること

    private JButton b1 = new JButton(bIcon1);
    private JButton b2 = new JButton(bIcon2);


    public MainMenu(WindowBase base, User user) {

        this.base = base;
        this.user = user;
        label1.setBounds(0, 0, 832, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        bButton.setButtonRight(p);
        bButton.button().addMouseListener(this);

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

        /*プレイヤ情報の表示*/
        infoPane.setBounds(384, 220, 384, 256);
        p.add(infoPane);
        p.setLayer(infoPane, 0);
        JLabel infoBox = new JLabel(infoIcon);
        infoBox.setBounds(0, 0, 384, 256);
        infoPane.add(infoBox);
        infoPane.setLayer(infoBox, 10);
        /*ユーザ名の表示*/
        String name = "<html>" + user.getUsername() + "さん";
        JLabel nameLabel = new JLabel(name);
        nameLabel.setBounds(16, 16, 384, 35);
        nameLabel.setFont(new java.awt.Font("ＭＳ ゴシック", java.awt.Font.BOLD, 40));
        infoPane.add(nameLabel);
        infoPane.setLayer(nameLabel, 20);
        /*所持金の表示*/
        JLabel coinIconLabel = new JLabel(isc.scale(cIcon, 1.0));
        coinIconLabel.setBounds(16, 67, 32, 32);
        infoPane.add(coinIconLabel);
        infoPane.setLayer(coinIconLabel, 20);
        String balance = "×" + user.getBalance();
        JLabel balanceLabel = new JLabel(balance);
        balanceLabel.setBounds(48, 67, 384, 32);
        balanceLabel.setFont(new java.awt.Font("ＭＳ ゴシック", java.awt.Font.BOLD, 32));
        infoPane.add(balanceLabel);
        infoPane.setLayer(balanceLabel, 20);
        /*所持装備の数の表示*/
        EquipmentItem[] eItems = EquipmentItem.values();
        int count = 0;
        for (int i = 0; i < eItems.length; i++) {
            if (user.getEquipmentQuantity(eItems[i]) > 0) {
                count++;
            }
        }
        JLabel equipmentLabel = new JLabel("<html>作った装備：" + count + "種類");
        equipmentLabel.setBounds(16, 115, 384, 32);
        equipmentLabel.setFont(new java.awt.Font("ＭＳ ゴシック", java.awt.Font.BOLD, 32));
        infoPane.add(equipmentLabel);
        infoPane.setLayer(equipmentLabel, 20);
        /*ランクの表示*/
        JLabel rankLabel = new JLabel("<html>現在のランク：" + user.getRank());
        rankLabel.setBounds(16, 163, 384, 32);
        rankLabel.setFont(new java.awt.Font("ＭＳ ゴシック", java.awt.Font.BOLD, 32));
        infoPane.add(rankLabel);
        infoPane.setLayer(rankLabel, 20);
        /*次のランクまでのポイントの表示*/
        int nextRankPoint = user.getRankPoint();
        String rankTxt;
        if (nextRankPoint < 0) rankTxt = "<html>次のランクまで：---ポイント";
        else rankTxt = "<html>次のランクまで：" + nextRankPoint + "ポイント";
        JLabel nextRankLabel = new JLabel(rankTxt);
        nextRankLabel.setBounds(16, 208, 384, 32);
        nextRankLabel.setFont(new java.awt.Font("ＭＳ ゴシック", java.awt.Font.BOLD, 20));
        infoPane.add(nextRankLabel);
        infoPane.setLayer(nextRankLabel, 20);


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
            Workshop_MainMenu wsTest = new Workshop_MainMenu(base, user);
        }
        if (e.getSource() == b2) {
            System.out.println("大丈夫だ、問題ない");
            //Status status = new Status(20, 3, 5, 2);
            //Player player = new Player("testUser", status);
            //Game testGame = new Game(base, player);
            //base.setVisible(true);
        }

        if (e.getSource() == bButton.button()) {
            Login login = new Login(base, user);
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
        User user = new User(114514, "testUser", 7974, 3);
        WindowBase base = new WindowBase("test");
        MainMenu test = new MainMenu(base, user);
        base.setVisible(true);

        user.addMaterial(Material.WOOD, 300);
        user.addMaterial(Material.IRON, 200);
        user.addMaterial(Material.DIAMOND, 100);
        user.addMaterial(Material.LEATHER, 300);
        user.addMaterial(Material.BRONZE, 200);
    }
}


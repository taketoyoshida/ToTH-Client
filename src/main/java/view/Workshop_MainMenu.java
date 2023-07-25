package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.util.User;

public class Workshop_MainMenu extends JFrame implements MouseListener {

    private User user;
    private BackButton bButton = new BackButton();
    private final WindowBase base;
    private JLayeredPane p = new JLayeredPane();
    private ImageIcon icon1 = new ImageIcon("./assets/imgs/Equipment_backImg.png");    //画像のディレクトリは調整してもろて
    private final ImageIcon buttonIcon = new ImageIcon("./assets/imgs/WorkshopMainmenuButton.png");

    JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    JLabel[] txtLabel = new JLabel[4];

    JButton[] button = new JButton[4];


    public Workshop_MainMenu(WindowBase base, User user) {

        this.base = base;
        this.user = user;
        label1.setBounds(0, 0, 832, 512);//背景の描画とレイヤーの設定
        p.add(label1);
        p.setLayer(label1, -10);

        bButton.setButtonRight(p);
        bButton.button().addMouseListener(this);

        for(int i=0;i<txtLabel.length;i++){
            txtLabel[i] = new JLabel();
            txtLabel[i].setFont(new Font("PixelMplus10", Font.BOLD, 56));
            txtLabel[i].setForeground(Color.DARK_GRAY);
            txtLabel[i].setHorizontalAlignment(JLabel.CENTER);
            txtLabel[i].setVerticalAlignment(JLabel.CENTER);
            p.setLayer(txtLabel[i], 10);
        }
        for(int i=0;i<button.length;i++){
            button[i] = new JButton(buttonIcon);
            button[i].setBorderPainted(false);
            button[i].setContentAreaFilled(false);
            button[i].addMouseListener(this);
            p.setLayer(button[i], 0);
        }

        paint();

        base.change(p);


    }

    public void paint() {//ボタンのみなさんの召喚

        p.setLayout(null);      //ボタン配置の設定

        button[0].setBounds(64, 64, 256, 128);
        button[0].addMouseListener(this);
        p.add(button[0]);
        txtLabel[0].setText("ガチャ");
        txtLabel[0].setBounds(64, 64, 256, 128);
        p.add(txtLabel[0]);

        button[1].setBounds(512, 64, 256, 128);
        button[1].addMouseListener(this);
        p.add(button[1]);
        txtLabel[1].setText("製造");
        txtLabel[1].setBounds(512, 64, 256, 128);
        p.add(txtLabel[1]);

        button[2].setBounds(64, 320, 256, 128);
        button[2].addMouseListener(this);
        p.add(button[2]);
        txtLabel[2].setText("装備");
        txtLabel[2].setBounds(64, 320, 256, 128);
        p.add(txtLabel[2]);

        button[3].setBounds(512, 320, 256, 128);
        button[3].addMouseListener(this);
        p.add(button[3]);
        txtLabel[3].setText("アイテム");
        txtLabel[3].setBounds(512, 320, 256, 128);
        p.add(txtLabel[3]);


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
        if (e.getSource() == button[0]) {
            System.out.println("ガチャは悪い文明…！破壊する…！");
            GachaWindow gachaTest = new GachaWindow(base, user);
        }
        if (e.getSource() == button[1]) {
            System.out.println("2-4-11");
            Workshop workshopTest = new Workshop(base, user);
        }
        if (e.getSource() == button[2]) {
            System.out.println("ここで装備していくかい？");
            EquipmentDock dockTest = new EquipmentDock(base, user);
        }
        if (e.getSource() == button[3]) {
            System.out.println("宝物庫の鍵を開けてやろう");
            Warehouse warehouseTest = new Warehouse(base, user);
        }

        if (e.getSource() == bButton.button()) {
            MainMenu mainMenu = new MainMenu(base, user);
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
        User user = new User(114514, "testUser", 45590, 3);
        WindowBase base = new WindowBase("test");
        Workshop_MainMenu test = new Workshop_MainMenu(base, user);
        base.setVisible(true);
    }

}



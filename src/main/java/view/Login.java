package view;

import view.Game;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Login extends JFrame implements MouseListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    int signup_or_login;

    JLabel label1 = new JLabel("SignUp");
    JLabel label2 = new JLabel("Menual");
    JTextField ID = new JTextField(16);
    JTextField PW = new JTextField(16);


    JPanel p = (JPanel) getContentPane();
    JButton button = new JButton("決定");
    JButton button2 = new JButton("ログインへ");


    public Login(String title, int signup_or_login) {
        super(title);
        this.signup_or_login = signup_or_login;
        button.addMouseListener(this);
        button2.addMouseListener(this);

        if (signup_or_login == 0) {
            label1.setText("新規登録");
            button2.setText("ログインへ");

        } else if (signup_or_login == 1) {
            label1.setText("ログイン");
            button2.setText("新規登録へ");

        }
        label2.setText("Please Input ID and PW");


        p.setLayout(new FlowLayout());
        p.add(label1);
        p.add(label2);
        p.add(ID);
        p.add(PW);
        p.add(button);
        p.add(button2);

        setSize(200, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
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
        if (e.getSource() == button) {
            if (signup_or_login == 0) {
                //PlayerSample1 player = new PlayerSample1();
                //player.setInfo(ID.getText(), PW.getText());
                //System.out.println("ID: " + ID.getText() + "PW: " + PW.getText() + " confirm!");
                //send("1", PW.getText());
                System.out.println("sol=0");
                dispose();
            } else if (signup_or_login == 1) {
                //PlayerSample1 player = new PlayerSample1();
                //player.confirmInfo(ID.getText(), PW.getText());
                //send("0", PW.getText());
                //System.out.println("confirm!");
                System.out.println("sol=1");
                setVisible(false);
                Game testGame = new Game("MyTitle");
                testGame.setVisible(true);
                dispose();
            }
        }
        if (e.getSource() == button2) {
            System.out.println("ボタン");
            if (signup_or_login == 0) {
                signup_or_login = 1;
                label1.setText("ログイン");
                button2.setText("新規登録へ");
            } else if (signup_or_login == 1) {
                signup_or_login = 0;
                label1.setText("新規登録");
                button2.setText("ログインへ");
            }
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
        Login frame = new Login("MyTitle",0);
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


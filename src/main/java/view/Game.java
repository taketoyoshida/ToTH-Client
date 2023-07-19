package view;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Player;
import model.Status;

public class Game extends JFrame implements KeyListener {

    private final Player player;
    private final WindowBase base;
    private int charPosX, charPosY, limX = 12, limY = 8;          //キャラクターの座標とマスの縦横サイズ
    private int cursorPosX, cursorPosY;                    //カーソルの座標
    private boolean keyFlag = false, allowCursor = false;  //キーとカーソル用の変数
    private boolean isAttack = false;       //カーソルの操作指定用変数
    private JLayeredPane bgPanel = new JLayeredPane();
    private JLayeredPane gamePanel = new JLayeredPane();
    private JLayeredPane cursorGuidePanel = new JLayeredPane();
    private ImageIcon icon1 = new ImageIcon("./assets/imgs/ログイン画面.png");    //画像のディレクトリは調整してもろて
    private ImageIcon charIcon = new ImageIcon("./assets/imgs/エルフ.jpg");
    private ImageIcon bgIcon = new ImageIcon("./assets/imgs/TestGameBG.png");
    private ImageIcon cursorIcon = new ImageIcon("./assets/imgs/TestCursor.png");
    private ImageIcon moveGuideIcon = new ImageIcon("./assets/imgs/TestGuide1.png");

    private JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    private JLabel charLabel = new JLabel(charIcon);
    private JLabel cursorLabel = new JLabel(cursorIcon);

    public Game(WindowBase base, Player player) {

        this.player = player;
        this.base = base;

        label1.setBounds(0, 0, 832, 512);//背景の描画とレイヤーの設定
        bgPanel.add(label1);
        bgPanel.setLayer(label1, -10);

        gamePanel.setBounds(64, 32, 384, 256);
        bgPanel.add(gamePanel);
        bgPanel.setLayer(gamePanel, 0);
        cursorGuidePanel.setBounds(0, 0, 384, 256);
        gamePanel.add(cursorGuidePanel);
        gamePanel.setLayer(cursorGuidePanel, 10);

        start();

        base.addKeyListener(this);

        base.change(bgPanel);


    }

    public void start() {
        setGameBG();
        setChar(4, 4);
    }

    public void setChar(int x, int y) {
        charPosX = x;
        charPosY = y;
        gamePanel.add(charLabel);
        charLabel.setBounds(charPosX * 32, charPosY * 32, 32, 32);
        gamePanel.setLayer(charLabel, 10);
        cursorGuidePanel.removeAll();
        base.change(bgPanel);
    }

    public void setCursor(int x, int y) {
        cursorPosX = x;
        cursorPosY = y;
        cursorLabel.setBounds(cursorPosX * 32, cursorPosY * 32, 32, 32);
        gamePanel.add(cursorLabel);
        gamePanel.setLayer(cursorLabel, 20);
        allowCursor = true;
    }

    public void setGuide(int MOV) {                 //カーソルの可動域を表示するメソッド
        int posX, posY, movX, movY;
        for (posX = 0; posX < limX; posX++)
            for (posY = 0; posY < limY; posY++) {
                movX = Math.abs(posX - charPosX);
                movY = Math.abs(posY - charPosY);
                if (movX + movY < MOV && (posX != cursorPosX || posY != cursorPosY)) {
                    JLabel guideLabel = new JLabel(moveGuideIcon);
                    guideLabel.setBounds(posX * 32, posY * 32, 32, 32);
                    cursorGuidePanel.add(guideLabel);
                    cursorGuidePanel.setLayer(guideLabel, 0);
                }
            }
        //gamePanel.add(cursorGuidePanel);
        //gamePanel.setLayer(cursorGuidePanel, 10);
    }


    public void moveCursor(int x, int y, int MOV) {   //カーソルを動かすメソッド
        /*始めにカーソルが枠外に出ないか判定する*/
        int posX = cursorPosX + x;
        int posY = cursorPosY + y;
        if (posX < 0 || posX > limX - 1) return;
        if (posY < 0 || posY > limY - 1) return;
        /*枠内なら、行動指定可能範囲内か判定する*/
        int movX = Math.abs(posX - charPosX);
        int movY = Math.abs(posY - charPosY);
        if (movX + movY < MOV) {
            setCursor(cursorPosX + x, cursorPosY + y);
        }
        setGuide(MOV);
    }

    public void removeCursor() {
        gamePanel.remove(gamePanel.getIndexOf(cursorLabel));
        allowCursor = false;
    }

    public void getCursorAction(int x, int y, boolean isAttack) {
        if (isAttack) {   //攻撃処理
            removeCursor();
        } else {   //移動処理
            setChar(x, y);
            removeCursor();
        }
    }

    public void setGameBG() {
        for (int x = 0; x < 12; x++)
            for (int y = 0; y < 8; y++) {
                JLabel label = new JLabel();
                label.setIcon(bgIcon);
                label.setBounds(x * 32, y * 32, 32, 32);
                gamePanel.add(label);
                gamePanel.setLayer(label, -10);
            }
    }

    public void paint() {//キャラクターの描画とレイヤーの設定
        //charLabel.setBounds(posX*32, posY*32, 32, 32);

        //JLabel label2 = new JLabel();
        //label2.setIcon(icon2);

        //JPanel p = new JPanel();
        //JLayeredPaneを使うとレイヤー設定ができる

        //setContentPane(p);
        //addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) { //Keyを押したときの動作
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
                if (allowCursor == false) {
                    allowCursor = true;
                    setCursor(charPosX, charPosY);
                    setGuide(player.getStatus().getMOV());
                }

                break;
        }
        if (allowCursor == true) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (!keyFlag) {
                        moveCursor(0, -1, player.getStatus().getMOV());
                        keyFlag = true;
                    }

                    break;
                case KeyEvent.VK_S:
                    if (!keyFlag) {
                        moveCursor(0, 1, player.getStatus().getMOV());
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_A:
                    if (!keyFlag) {
                        moveCursor(-1, 0, player.getStatus().getMOV());
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_D:
                    if (!keyFlag) {
                        moveCursor(1, 0, player.getStatus().getMOV());
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_F:
                    if (!keyFlag) {
                        getCursorAction(cursorPosX, cursorPosY, false);
                        keyFlag = true;
                    }
            }
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
        if (Y == -1 && X == 0 && charPosY > 0) {
            setChar(charPosX, charPosY - 1);
            System.out.println("Wが押されました");
        } else if (Y == 1 && X == 0 && charPosY < limY - 1) {
            setChar(charPosX, charPosY + 1);
            System.out.println("Sが押されました");

        } else if (X == -1 && Y == 0 && charPosX > 0) {
            setChar(charPosX - 1, charPosY);
            System.out.println("Aが押されました");
        } else if (X == 1 && Y == 0 && charPosX < limX - 1) {
            setChar(charPosX + 1, charPosY);
            System.out.println("dが押されました");
        }
    }


    public static void main(String args[]) {
        Status status = new Status(20, 1, 4, 2);
        Player player = new Player("testUser", status);
        WindowBase base = new WindowBase("test");
        Game test = new Game(base, player);
        base.setVisible(true);
    }


}

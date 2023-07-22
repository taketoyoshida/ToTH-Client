package view;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import model.Player;
import model.Status;

public class Game extends JFrame implements KeyListener {

    private final Player player;
    private final WindowBase base;
    private int charPosX, charPosY;          //キャラクターの座標とマスの縦横サイズ
    private final int limX = 12, limY = 8;
    private int cursorPosX, cursorPosY;                    //カーソルの座標
    private boolean keyFlag = false, allowCursor = false;  //キーとカーソル用の変数
    private boolean isAttack = false;       //カーソルの操作指定用変数
    private Timer timer = new Timer(false);
    private JLayeredPane bgPanel = new JLayeredPane();
    private JLayeredPane gamePanel = new JLayeredPane();
    private JLayeredPane cursorGuidePanel = new JLayeredPane();
    private ImageIcon icon1 = new ImageIcon("./assets/imgs/game.png");    //画像のディレクトリは調整してもろて
    private ImageIcon charIcon = new ImageIcon("./assets/imgs/エルフGreen.png");
    private ImageIcon bgIcon = new ImageIcon("./assets/imgs/TestGameBG.png");
    private ImageIcon attackIcon = new ImageIcon("./assets/imgs/TestAttack.gif");
    private ImageIcon moveCursorIcon = new ImageIcon("./assets/imgs/TestCursor1.png");
    private ImageIcon attackCursorIcon = new ImageIcon("./assets/imgs/TestCursor2.png");
    private ImageIcon moveGuideIcon = new ImageIcon("./assets/imgs/TestGuide1.png");
    private ImageIcon attackGuideIcon = new ImageIcon("./assets/imgs/TestGuide2.png");

    private JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    private JLabel charLabel = new JLabel(charIcon);
    private JLabel cursorLabel = new JLabel();

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
    }

    public void setCursor(int x, int y) {
        /*攻撃か移動かでカーソルを変更する*/
        if (isAttack == false) cursorLabel.setIcon(moveCursorIcon);
        else cursorLabel.setIcon(attackCursorIcon);
        /*引数の場所にカーソルを設置する*/
        cursorPosX = x;
        cursorPosY = y;
        cursorLabel.setBounds(cursorPosX * 32, cursorPosY * 32, 32, 32);
        gamePanel.add(cursorLabel);
        gamePanel.setLayer(cursorLabel, 20);
        allowCursor = true;
    }

    public void showAttackParticle(int x, int y) {
        /*表示されているカーソルを消去する*/
        removeCursor();
        /*攻撃の演出を表示する*/
        JLabel attackLabel = new JLabel(attackIcon);
        attackLabel.setBounds(x * 32, y * 32, 32, 32);
        gamePanel.add(attackLabel);
        gamePanel.setLayer(attackLabel, 30);
        /*遅れて攻撃演出の消去を設定する*/
        TimerTask attack = new TimerTask() {
            @Override
            public void run() {
                gamePanel.remove(gamePanel.getIndexOf(attackLabel));
                timer.cancel();
                timer = new Timer(false);
                base.change(bgPanel);
            }
        };
        timer.schedule(attack, 2000);
    }

    public void setGuide(Status status) {                 //カーソルの可動域を表示するメソッド
        int posX, posY, movX, movY, LIM;
        /*移動と攻撃の処理のどちらかを判別する*/
        if (isAttack == false) LIM = status.getMOV();
        else LIM = status.getATK();
        for (posX = 0; posX < limX; posX++)
            for (posY = 0; posY < limY; posY++) {
                movX = Math.abs(posX - charPosX);
                movY = Math.abs(posY - charPosY);
                if (movX + movY < LIM && (posX != cursorPosX || posY != cursorPosY)) {
                    JLabel guideLabel;
                    if (isAttack == false) guideLabel = new JLabel(moveGuideIcon);
                    else guideLabel = new JLabel(attackGuideIcon);
                    guideLabel.setBounds(posX * 32, posY * 32, 32, 32);
                    cursorGuidePanel.add(guideLabel);
                    cursorGuidePanel.setLayer(guideLabel, 0);
                }
            }
        //gamePanel.add(cursorGuidePanel);
        //gamePanel.setLayer(cursorGuidePanel, 10);
    }


    public void moveCursor(int x, int y, Status status) {   //カーソルを動かすメソッド
        /*始めに攻撃と移動どちらの処理か決定する*/
        int LIM;
        if (isAttack == false) LIM = status.getMOV();
        else LIM = status.getATK();
        /*次にカーソルが枠外に出ないか判定する*/
        int posX = cursorPosX + x;
        int posY = cursorPosY + y;
        if (posX < 0 || posX > limX - 1) return;
        if (posY < 0 || posY > limY - 1) return;
        /*枠内なら、行動指定可能範囲内か判定する*/
        int movX = Math.abs(posX - charPosX);
        int movY = Math.abs(posY - charPosY);
        if (movX + movY < LIM) {
            setCursor(cursorPosX + x, cursorPosY + y);
        }
        setGuide(status);
    }

    public void removeCursor() {
        gamePanel.remove(gamePanel.getIndexOf(cursorLabel));
        allowCursor = false;
        cursorGuidePanel.removeAll();
        base.change(bgPanel);
    }

    public void getCursorAction(int x, int y) {
        if (isAttack) {   //攻撃処理
            showAttackParticle(x, y);
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
                    setGuide(player.getStatus());
                }

                break;
            case KeyEvent.VK_2:
                if(allowCursor== false){
                    if (isAttack == false) {
                        isAttack = true;
                    } else isAttack = false;
                }

                break;
        }
        if (allowCursor == true) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (!keyFlag) {
                        moveCursor(0, -1, player.getStatus());
                        keyFlag = true;
                    }

                    break;
                case KeyEvent.VK_S:
                    if (!keyFlag) {
                        moveCursor(0, 1, player.getStatus());
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_A:
                    if (!keyFlag) {
                        moveCursor(-1, 0, player.getStatus());
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_D:
                    if (!keyFlag) {
                        moveCursor(1, 0, player.getStatus());
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_F:
                    if (!keyFlag) {
                        getCursorAction(cursorPosX, cursorPosY);
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
        Status status = new Status(20, 3, 5, 2);
        Player player = new Player("testUser", status);
        WindowBase base = new WindowBase("test");
        Game test = new Game(base, player);
        base.setVisible(true);
    }


}

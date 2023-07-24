package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import controller.game.GameController;
import model.*;
import model.util.User;

import static java.awt.Font.BOLD;
import static model.GameModel.BOARD_COL;
import static model.GameModel.BOARD_ROW;


public class Game extends JFrame implements KeyListener, MouseListener {

    private final Player player;
    private final WindowBase base;
    private int charPosX=0, charPosY=7;          //キャラクターの座標とマスの縦横サイズ
    private final int limX = 12, limY = 8;
    private int cursorPosX, cursorPosY;                    //カーソルの座標
    private boolean keyFlag = false, allowCursor = false;  //キーとカーソル用の変数
    private boolean isAttack = false;       //カーソルの操作指定用変数
    public static User testUser1 = new User(100, "test1", 100000, 5);
    public static User testUser2 = new User(200, "test2", 100000, 5);
    private GameModel GM;
    private GameController GC = new GameController(testUser1,testUser2);
    JLabel[] textLabel2 = new JLabel[24];
    JLabel[] textLabel1 = new JLabel[8];

    private Timer timer = new Timer(false);
    private JLayeredPane bgPanel = new JLayeredPane();
    private JLayeredPane gamePanel = new JLayeredPane();
    private JLayeredPane cursorGuidePanel = new JLayeredPane();

    private ImageIcon icon1 = new ImageIcon("./assets/imgs/game.png");    //画像のディレクトリは調整してもろて
    private ImageIcon[] Icon = new ImageIcon[16];


    private ImageIcon bgIcon = new ImageIcon("./assets/imgs/W.png");
    private ImageIcon attackIcon = new ImageIcon("./assets/imgs/TestAttack.gif");
    private ImageIcon moveCursorIcon = new ImageIcon("./assets/imgs/TestCursor1.png");
    private ImageIcon attackCursorIcon = new ImageIcon("./assets/imgs/TestCursor2.png");
    private ImageIcon moveGuideIcon = new ImageIcon("./assets/imgs/TestGuide1.png");
    private ImageIcon attackGuideIcon = new ImageIcon("./assets/imgs/TestGuide2.png");

    private JLabel label1 = new JLabel(icon1);        //画像はlabelで取り込む
    private JLabel charLabel = new JLabel(Icon[1]);
    private JLabel cursorLabel = new JLabel();



    public Game(WindowBase base, Player player) {

        GM=GC.ReturnGameModel();

        this.player = GC.Player1;
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


        for(int i=0;i<24;i++) {
            textLabel2[i]=new JLabel();
            textLabel2[i].setFont(new Font("ＭＳ ゴシック", BOLD, 15));
            textLabel2[i].setForeground(Color.WHITE);
            textLabel2[i].setBounds(560, 16*i, 256, 250);
            bgPanel.add(textLabel2[i]);
            bgPanel.setLayer(textLabel2[i], 10);
        }

        for(int i=0;i<8;i++) {
            textLabel1[i]=new JLabel();
            textLabel1[i].setFont(new Font("ＭＳ ゴシック", BOLD, 15));
            textLabel1[i].setForeground(Color.WHITE);
            textLabel1[i].setBounds(64, 16*i,326 , 600);
            bgPanel.add(textLabel2[i]);
            bgPanel.setLayer(textLabel2[i], 10);
        }

        start();

        gamePanel.addKeyListener(this);
        base.addKeyListener(this);

        gamePanel.addMouseListener(this);
        base.addMouseListener(this);


        base.change(bgPanel);

    }

    public void start() {
        setGameBG();
        printBoard();
        setText2();
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
    public  void setText1(){

    }
    public void setText2(){//各キャラクタと敵のHPが出る
        textLabel2[0].setText("┌------------------┐");
        textLabel2[1].setText("|     Turn: "+GC.turnReturn()+"        |\n");
        textLabel2[2].setText("└------------------┘");

        textLabel2[3].setText("先手: " + GC.Player1.getName());
        textLabel2[4].setText(" HP | ATK | MOV | RNG");
        textLabel2[5].setText(" "+GC.Player1.getHP()+"| "+GC.Player1.getATK()+"| "+GC.Player1.getMOV()+"| "+GC.Player1.getRNG());
        textLabel2[6].setText("SCORE : " + GC.Player1.getScore());

        textLabel2[7].setText("後手: " + GC.Player2.getName());
        textLabel2[8].setText(" HP | ATK | MOV | RNG");
        textLabel2[9].setText(" "+GC.Player2.getHP()+"| "+GC.Player2.getATK()+"| "+GC.Player2.getMOV()+"| "+GC.Player2.getRNG());
        textLabel2[10].setText("SCORE : " + GC.Player2.getScore());

        System.out.println();
        // 敵の情報の表示
        int count=11;
        for (Enemy enemy : GC.enemylist()) {

            textLabel2[count].setText("ENEMY: " + enemy.getName() + " " + enemy.getPosition());
            textLabel2[count+1].setText(" HP | ATK | MOV | RNG");
            textLabel2[count+2].setText(" "+enemy.getHp()+"| "+enemy.getAtk()+"| "+enemy.getMov()+"| "+enemy.getRng());
            count=count+3;
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
    public void mouseClicked(MouseEvent e) {

        // マウスクリック時の座標を取得してGameControllerに渡す
        int x = e.getX() / 32; // マスのサイズ32に合わせて割る
        int y = e.getY() / 32;

        GC.processClick(x, y); // GameControllerにクリックした座標を渡す
        printBoard();
    }
    @Override
    public  void mousePressed(MouseEvent e){};
    @Override
    public  void mouseReleased(MouseEvent e){};
    @Override
    public  void mouseEntered(MouseEvent e){};
    @Override
    public  void mouseExited(MouseEvent e){};

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) { //Keyを押したときの動作
            switch (e.getKeyCode()) {
                case KeyEvent.VK_M:
                    if (!keyFlag) {
                        moveCharacter(100,100);
                        keyFlag = true;
                    }
                case KeyEvent.VK_W:
                    if (!keyFlag) {
                        moveCharacter(0,-1);
                        keyFlag = true;
                    }

                    break;
                case KeyEvent.VK_S:
                    if (!keyFlag) {

                        moveCharacter(0,1);
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_A:
                    if (!keyFlag) {

                        moveCharacter(-1,0);
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_D:
                    if (!keyFlag) {
                        moveCharacter(1,0);
                        keyFlag = true;
                    }
                    break;
                case KeyEvent.VK_F:
                    if (!keyFlag) {

                        keyFlag = true;
                    }
            }


    }


    @Override
    public void keyReleased(KeyEvent e) {//Keyboardを離したときの動作
        switch (e.getKeyCode()) {
            case  KeyEvent.VK_M:
                keyFlag=false;
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
        if(Y == 100 && X == 100){
            GC.setPosition(100,100);//移動終了
        }else if (Y == -1 && X == 0 && charPosY > 0) {
            charPosY=charPosY-1;
            GC.setPosition(charPosX,charPosY);
            setText2();
            printBoard();
        } else if (Y == 1 && X == 0 && charPosY < limY - 1) {
            charPosY=charPosY+1;
            GC.setPosition(charPosX,charPosY);
            setText2();
            printBoard();


        } else if (X == -1 && Y == 0 && charPosX > 0) {
            charPosX=charPosX-1;
            GC.setPosition(charPosX,charPosY);
            setText2();
            printBoard();

        } else if (X == 1 && Y == 0 && charPosX < limX - 1) {
            charPosX=charPosX+1;
            GC.setPosition(charPosX,charPosY);
            setText2();
            printBoard();

        }
    }

    public void printBoard() {
        Icon[0]=new ImageIcon("./assets/imgs/エルフGreen.png");
        Icon[1]=new ImageIcon("./assets/imgs/エルフGreen.png");
        Icon[2]=new ImageIcon("./assets/imgs/enemy/Enemy_ID1.png");
        Icon[3]=new ImageIcon("./assets/imgs/enemy/Enemy_ID2.png");
        Icon[4]=new ImageIcon("./assets/imgs/enemy/Enemy_ID3.png");
        Icon[5]=new ImageIcon("./assets/imgs/enemy/Enemy_ID4.png");
        Icon[6]=new ImageIcon("./assets/imgs/enemy/Enemy_ID5.png");
        Icon[7]=new ImageIcon("./assets/imgs/enemy/enemy_ID6.png");
        Icon[8]=new ImageIcon("./assets/imgs/enemy/Enemy_ID7.png");
        Icon[9]=new ImageIcon("./assets/imgs/enemy/Enemy_ID8.png");
        Icon[10]=new ImageIcon("./assets/imgs/enemy/Enemy_ID9.png");
        Icon[11]=new ImageIcon("./assets/imgs/enemy/Enemy_ID10.png");
        Icon[12]=new ImageIcon("./assets/imgs/enemy/Enemy_ID11.png");
        Icon[13]=new ImageIcon("./assets/imgs/enemy/Enemy_ID12.png");
        Icon[14]=new ImageIcon("./assets/imgs/Forest.png");
        Icon[15]=new ImageIcon("./assets/imgs/W.png");

        for (int i = 0; i < BOARD_ROW; i++) {
            for (int j = 0; j < BOARD_COL; j++) {
                Piece piece = GM.getPiece(i, j);
                int symbol = (piece != null) ? piece.toIntger() : null;
                JLabel charLabel = new JLabel(Icon[symbol]);
                gamePanel.add(charLabel);
                charLabel.setBounds(j * 32, i * 32, 32, 32);
                gamePanel.setLayer(charLabel, 10);


            }

        }
    }



    public static void main(String args[]) {
        Status status = new Status(20, 3, 5, 2);
        Player player = new Player(Player.Teban.SENTE, "testName", status, 1, new Position(1, 1));
        WindowBase base = new WindowBase("test");
        Game test = new Game(base, player);
        base.setVisible(true);
        test.GC.playGame();


    }


}

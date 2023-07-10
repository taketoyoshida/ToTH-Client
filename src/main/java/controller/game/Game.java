package controller.game;

import model.EnemyStatus;
import model.Player;
import model.Status;
import model.Position;

import java.util.Random;

public class Game {
    private Random random;
    private static final int GAME_TURN = 15;
    private static final int BOARD_ROW = 8;
    private static final int BOARD_COL = 12;
    private int turnNum;
    private int[][] board;
    private int[] enemy;
    Player Player1 = new Player("Player1", new Status(1, 1, 1, 1));
    Player Player2 = new Player("Player2", new Status(1, 1, 1, 1));


    //ゲームのメインループ
    public void playGame() {

        while (!isGameOver()) {
            if (isValidMove()) {
                movePlayer();
            }
            refScore();
            refBoard();
            if (isValidAtk()) {
                atkPlayer();
            }

            /* 敵の行動 */

            refBoard();
            refStatus();

            if (Player1.isDead()) {

            }
            if (Player2.isDead()) {

            }
        }

        if (Player1.getScore() == Player2.getScore()) {
            /* 引き分け */
        } else if (Player1.getScore() > Player2.getScore()) {
            /* プレイヤ－1の勝ち */
        } else if (Player1.getScore() < Player2.getScore()) {
            /* プレイヤー2の勝ち */
        }

        /* 獲得ゴールドの計算 */
    }


    //ゲームの初期化
    public void initialize() {
        board = new int[BOARD_ROW][BOARD_COL];
        setObstacle();
        board[7][0] = 1;  //プレイヤー初期位置
        board[0][11] = 2; //プレイヤー初期位置
        setEnemy();
        turnNum = 0;
    }

    //盤面を更新
    public void refBoard() {

    }

    //スコアを更新
    public void refScore() {

    }

    //ステータスを更新
    public void refStatus() {

    }

    //プレイヤーが攻撃
    public void atkPlayer() {

    }

    //攻撃が妥当か判定
    public boolean isValidAtk() {
        if (true) {
            return true;
        } else {
            return false;
        }
    }

    //プレイヤーを移動
    public void movePlayer() {

    }

    //妥当な移動か（移動できるか）を判定
    public boolean isValidMove() {
        if (true) {
            return true;
        } else {
            return false;
        }

    }

    public void printBoard() {

    }

    //敵をセット
    public void setEnemy() {
        int rank;
        if (Player1.getRank() < Player2.getRank()) {
            rank = Player1.getRank();
        } else {
            rank = Player2.getRank();
        }
        enemy = new int[4];
        int i;
        for(i=1;i<=4;i++) {
            enemy[i] = rank * 2 - i;    // とりあえずIDを入れる
            if (rank * 2 - i <= 0) break;// rank1のみ敵は２種類
        }
        board[0][0] = enemy[i-2];   // rank1以外は１つ前のrankで初登場した敵が対角に初期配置
        board[7][11] = enemy[i-1];  // rank1ではrank1の敵２種が配置
    }

    // 敵を特定のマスに置く
    public void putEnemy() {
        int ROW, COL;
        do {
            ROW = RandomNum(0, BOARD_ROW - 1);// ランダムで座標を生成
            COL = RandomNum(0, BOARD_COL - 1);
            if(board[ROW][COL]!=0) continue;// 当該マスに障害物含む何かがあったら再生成
            if (ObjectCheck(ROW, COL, 3)) {
                board[ROW][COL] = enemy[RandomNum(0, 3)];// 周囲1マスに他のプレイヤーやモンスターが無ければランダムで敵をセット
                break;
            }
        }while(true);// 敵がセットされるまで続く
    }

    //障害物を最大10個セット
    public void setObstacle() {
        int ROW,COL;
        for(int i=0;i<10;i++) {
            ROW = RandomNum(0, BOARD_ROW - 1);// ランダムで座標を生成
            COL = RandomNum(0, BOARD_COL - 1);
            if(ObjectCheck(ROW,COL,0)) board[ROW][COL] = 3;// 周囲1マスに他の障害物が無ければセット
        }
    }

    public boolean isGameOver() {
        if (turnNum == GAME_TURN) {
            return true;
        } else {
            return false;
        }
    }

    public int RandomNum(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    // 周囲にオブジェクトがあるかチェック
    public boolean ObjectCheck(int ROW,int COL,int without) {// withoutに指定したオブジェクトは無視
        int X=0;
        for(int j=-1;j<=1;j++){
            for(int k=-1;k<=1;k++){
                if(board[ROW+j][COL+k]!=0 && board[ROW+j][COL+k]!=without) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.printBoard();
        game.playGame();
    }
}
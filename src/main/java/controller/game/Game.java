package controller.game;

import controller.networking.GameGateway;
import model.*;

import java.util.Random;

import static model.Game.BOARD_COL;
import static model.Game.BOARD_ROW;

public class Game {
    private Random random;
    private static final int GAME_TURN = 15;
    private int turnNum;
    private final model.Game game;
    private EnemyInfo[] enemy;
    private final GameGateway.IGameGateway gateway;
    private final Player me = new Player("Player1", new Status(1, 1, 1, 1));
    private final Player opponent = new Player("Player2", new Status(1, 1, 1, 1));


    //ゲームの初期化
    public Game() {
        game = new model.Game();
        setObstacle();
        setEnemy();
        turnNum = 0;
        random = new Random();
        // TODO: Gateway should be properly initialized
        this.gateway = new GameGateway.IGameGateway() {
        };
    }

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

            if (me.isDead()) {

            }
            if (opponent.isDead()) {

            }
        }

        if (me.getScore() == opponent.getScore()) {
            /* 引き分け */
        } else if (me.getScore() > opponent.getScore()) {
            /* プレイヤ－1の勝ち */
        } else if (me.getScore() < opponent.getScore()) {
            /* プレイヤー2の勝ち */
        }

        /* 獲得ゴールドの計算 */
    }


    //盤面を更新
    public void refBoard() {
        updateBoard();
        game.printBoard();
    }

    //スコアを更新
    public void refScore() {
        getScore();
    }

    //ステータスを更新
    public void refStatus() {
        getStatus();
    }

    //プレイヤーが攻撃
    public void atkPlayer() {
        EnemyStatus.hp -= PlayerStatus.atk;
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
        Player.moveCharacter();
    }

    //妥当な移動か（移動できるか）を判定
    public boolean isValidMove() {
        if (getPiece() == null) {   // 移動先にコマがない(?)
            return true;
        } else {
            return false;
        }

    }

    public void printBoard() {
// viewクラスで定義？
    }

    //敵をセット
    public void setEnemy() {
        int rank;
        if (me.getRank() < opponent.getRank()) {
            rank = me.getRank();
        } else {
            rank = opponent.getRank();
        }
        enemy = new EnemyInfo[4];
        int i,j=0;
        for (i = rank*2-1; i >= rank*2-4; i--) {
            enemy[j] = EnemyInfo.valueOf("ENEMY_"+ i);    // rank1の敵はENEMY_1, ENEMY_2、2以上では4種類
            if (i <= 0) break;// rank1のみ敵は２種類
            j++;
        }
        // TODO: Implement this using Piece class
        game.setPiece(new Position(0, 0), new Piece()); // rank1以外は１つ前のrankで初登場した敵が対角に初期配置
        game.setPiece(new Position(7, 11), new Piece()); // rank1ではrank1の敵２種が配置とやるにはどうすれば良い？？？

//        game.board[0][0] = enemy[j];   // rank1以外は１つ前のrankで初登場した敵が対角に初期配置
//        game.board[7][11] = enemy[j - 1];  // rank1ではrank1の敵２種が配置
    }

    // 敵を特定のマスに置く
    public void putEnemy() {
        do {
            // 周囲1マスに他のプレイヤーやモンスターが無ければランダムで敵をセット
            int ROW = getRandomNum(0, BOARD_ROW - 1);// ランダムで座標を生成
            int COL = getRandomNum(0, BOARD_COL - 1);
            if (game.getPiece(ROW, COL) != null
                    || game.getPiecesAround(new Position(ROW, COL)).size() == 0) continue;// 当該マスに障害物含む何かがあったら再生成
            // TODO: Implement Piece
            game.setPiece(new Position(ROW, COL), new Piece());
            break;
        } while (true);// 敵がセットされるまで続く
    }

    //障害物を10個セット
    public void setObstacle() {
        int ROW, COL;
        for (int i = 0; i < 10; i++) {
            do {
                ROW = random.nextInt(0, BOARD_ROW);// ランダムで座標を生成
                COL = random.nextInt(0, BOARD_COL);
                // TODO: Implement Piece
                if (game.getPiecesAround(new Position(ROW, COL)).size() == 0) {
                    game.setPiece(new Position(ROW, COL), new Piece());// 周囲1マスに他の障害物が無ければセット
                    break;
                }
            } while (true);// 障害物がセットされるまで続く
        }
    }

    public boolean isGameOver() {
        return turnNum == GAME_TURN;
//        if (turnNum == GAME_TURN) {
//            return true;
//        } else {
//            return false;
//        }
    }

    public int getRandomNum(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.printBoard();
        game.playGame();
    }
}
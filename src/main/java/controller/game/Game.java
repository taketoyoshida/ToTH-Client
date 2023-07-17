package controller.game;

import controller.networking.GameGateway;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Game.BOARD_COL;
import static model.Game.BOARD_ROW;

import model.util.User;

public class Game {
    private Random random;
    private static final int GAME_TURN = 15;
    private int turnNum;
    private final model.Game game;
    private List<Integer> enemies;
    private final GameGateway.IGameGateway gateway;
//    private final Player me = new Player("Player1", new Status(1, 1, 1, 1));
//    private final Player opponent = new Player("Player2", new Status(1, 1, 1, 1));

    public static User testUser1 = new User(100, "test1", 100000, 1);
    public static User testUser2 = new User(200, "test2", 100000, 1);
    public static Player Player1;
    public static Player Player2;

    public Game(User user1, User user2) {
        Status player1Status = user1.getStatus();
        Player1 = new Player(user1.getUsername(), player1Status, user1.getRank());
        Status player2Status = user2.getStatus();
        Player2 = new Player(user2.getUsername(), player2Status, user2.getRank());

        game = new model.Game();
        setObstacle();
        setEnemy();
        turnNum = 0;
        this.random = new Random();
        // TODO: Gateway should be properly initialized
        this.gateway = new GameGateway.IGameGateway() {
        };
    }

    //ゲームのメインループ
    public void playGame() {

        while (!isGameOver()) {
            System.out.println("Turn: " + turnNum);
            System.out.println("Player1: " + Player1.getScore());
            System.out.println("Player2: " + Player2.getScore());
            System.out.println("プレイヤーのターンです");
            System.out.println("移動先を選択してください");
            // TODO: Player should be properly initialized
            if (isValidMove()) {
                movePlayer();
            }
            // 2人の行動が終わったら
            refBoard(); //敵プレイやの動きを加味してボードの書き換え

            System.out.println("攻撃するコマを選択してください");
            if (isValidAtk()) {
                atkPlayer();
            }

            refScore();
            refBoard();

            /* 敵の行動 */
            // TODO: 敵の行動を処理するプログラムの追加
            while (enemies.size() < 3) {
                setEnemy();
            }
            for (int enemyId : enemies) {
                EnemyInfo enemyInfo = null;
                for (EnemyInfo info : EnemyInfo.values()) {
                    if (info.getID() == enemyId) {
                        enemyInfo = info;
                        break;
                    }
                }
                if (enemyInfo == null) {
                    System.out.println("敵の情報が見つかりません: " + enemyId);
                    continue;
                }
                moveEnemy();
                atkEnemy();
            }

            refBoard();
            refStatus();

            if (Player1.isDead()) {
                System.out.println(Player1.getName() + "は死にました");
                Player1.setScore(Player1.getScore() - 500);
                Player1.increaseDeadCount();
            }
            if (Player2.isDead()) {
                System.out.println(Player2.getName() + "は死にました");
                Player2.setScore(Player2.getScore() - 500);
                Player1.increaseDeadCount();
            }
            turnNum++;
        }

        System.out.println("ゲーム終了です");
        /* 得点計算処理 */
        if (Player1.getScore() == Player2.getScore()) {
            /* 引き分け */
            System.out.println("引き分けです");
            Player1.setReward(Player1.getScore() / 20);
            Player2.setReward(Player2.getScore() / 20);
        } else if (Player1.getScore() > Player2.getScore()) {
            /* プレイヤ－1の勝ち */
            System.out.println(Player1.getName() + "の勝ちです");
            Player1.setReward(Player1.getScore() / 10);
            Player2.setReward(Player2.getScore() / 40);
        } else if (Player1.getScore() < Player2.getScore()) {
            /* プレイヤー2の勝ち */
            System.out.println(Player2.getName() + "の勝ちです");
            Player1.setReward(Player1.getScore() / 40);
            Player2.setReward(Player2.getScore() / 10);
        }


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

    public void moveEnemy() {

    }

    public void atkEnemy() {

    }

    public void printBoard() {
        //これはviewクラスで定義するのか、、？
    }

    //敵をセット
    public void setEnemy() {
        enemies = new ArrayList<>();
        int rank = Math.min(Player1.getRank(), Player2.getRank());
        for (int i = 0; i < 3; i++) {
            int enemyId = 0;
            if (rank == 1) {
                enemyId = getRandomNum(1, rank * 2);
            } else if (1 < rank && rank <= 6) {
                enemyId = getRandomNum(rank * 2 - 3, rank * 2);
            } else {
                System.out.println("rankが不正です");
            }
            enemies.add(enemyId);
            putEnemy(enemyId);
        }
    }

    //        int rank;
//        if (Player1.getRank() < Player2.getRank()) {
//            rank = Player1.getRank();
//        } else {
//            rank = Player2.getRank();
//        }
//        enemy = new int[4];
//        int i;
//        for (i = 1; i <= 4; i++) {
//            enemy[i] = rank * 2 - i;    // とりあえずIDを入れる
//            if (rank * 2 - i <= 0) break;// rank1のみ敵は２種類
//        }
    // TODO: Implement this using Piece class
//        game.board[0][0] = enemy[i - 2];   // rank1以外は１つ前のrankで初登場した敵が対角に初期配置
//        game.board[7][11] = enemy[i - 1];  // rank1ではrank1の敵２種が配置
//    }
    // 敵を特定のマスに置く
    public void putEnemy(int enemyId) {
        EnemyInfo enemyInfo = null;
        // 指定された敵IDに対応するEnemyInfoを取得
        for (EnemyInfo info : EnemyInfo.values()) {
            if (info.getID() == enemyId) {
                enemyInfo = info;
                break;
            }
        }
        if (enemyInfo == null) {
            System.out.println("指定された敵IDに対応する敵が存在しません。");
            return;
        }
        do {
            // 周囲1マスに他のプレイヤーやモンスターが無ければランダムで敵をセット
            int ROW = getRandomNum(0, BOARD_ROW - 1);// ランダムで座標を生成
            int COL = getRandomNum(0, BOARD_COL - 1);
            if (game.getPiece(ROW, COL) != null
                    || game.getPiecesAround(new Position(ROW, COL)).size() == 0) continue;// 当該マスに障害物含む何かがあったら再生成
            // TODO: Implement Piece
            // enemyInfoに対応する敵をセット
            game.setPiece(new Position(ROW, COL), new Piece());
            break;
        } while (true);// 敵がセットされるまで続く
    }

    //障害物を最大10個セット
    public void setObstacle() {
        int ROW, COL;
        random = new Random();
        for (int i = 0; i < 10; i++) {
            ROW = random.nextInt(BOARD_ROW);// ランダムで座標を生成
            COL = random.nextInt(BOARD_COL);
            // TODO: Implement Piece
            if (game.getPiecesAround(new Position(ROW, COL)).size() == 0)
                game.setPiece(new Position(ROW, COL), new Piece());// 周囲1マスに他の障害物が無ければセット
        }
    }

    public boolean isGameOver() {
        return turnNum == GAME_TURN;
    }

    public int getRandomNum(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) {
        Game game = new Game(testUser1, testUser2);
        game.printBoard();
        game.playGame();
    }
}
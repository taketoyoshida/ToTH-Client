package controller.game;

import controller.networking.GameGateway;
import model.*;

import java.util.*;

import static model.GameModel.BOARD_COL;
import static model.GameModel.BOARD_ROW;


import model.util.User;

public class GameController {
    private Random random;
    private static final int GAME_TURN = 1;
    private int turnNum;
    private GameModel gameModel;
    private List<Integer> enemies;
    private final GameGateway.IGameGateway gateway;

    public static User testUser1 = new User(100, "test1", 100000, 1);
    public static User testUser2 = new User(200, "test2", 100000, 1);
    public static Player Player1;
    public static Player Player2;

    public GameController(User user1, User user2) {
        this.random = new Random();
        // TODO: Gateway should be properly initialized
        this.gateway = new GameGateway.IGameGateway() {};

        // gameModelインスタンスを1回だけ作成し、それを使用する
        gameModel = new GameModel();
        Status player1Status = user1.getStatus();
        Player1 = new Player(user1.getUsername(), player1Status, user1.getRank(), new Position(0, 0));
        gameModel.setPiece(new Position(0, 0), new Piece(Piece.PieceType.PLAYER));

        Status player2Status = user2.getStatus();
        Player2 = new Player(user2.getUsername(), player2Status, user2.getRank(), new Position(BOARD_ROW - 1, BOARD_COL - 1));
        gameModel.setPiece(new Position(BOARD_ROW - 1, BOARD_COL - 1), new Piece(Piece.PieceType.PLAYER));

        setObstacle();
        gameModel.setPiece(new Position(1, 1), new Piece(Piece.PieceType.ENEMY));

        turnNum = 1;
    }


    //ゲームのメインループ
    public void playGame() {
        System.out.println("ゲームを開始します");
        System.out.println("Player1: " + Player1.getName());
        Player1.printStatus();
        System.out.println("Player2: " + Player2.getName());
        Player2.printStatus();
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            System.out.println("Turn: " + turnNum);
            System.out.println("Player1_Score: " + Player1.getScore());
            System.out.println("Player2_Score: " + Player2.getScore());
            System.out.println("プレイヤー1のターンです");
            System.out.println("移動先を選択してください");
            // TODO: Player should be properly initialized
            // Player1の行動
            // Player2の行動
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

            refBoard();
            refStatus();

            Player1.increaseAliveTurn();
            Player2.increaseAliveTurn();

            if (Player1.isDead()) {
                System.out.println(Player1.getName() + "は死にました");
                Player1.setScore(Player1.getScore() - 500);
                Player1.increaseDeadCount();
                Player1.setAliveTurn(0);
            }
            if (Player2.isDead()) {
                System.out.println(Player2.getName() + "は死にました");
                Player2.setScore(Player2.getScore() - 500);
                Player2.increaseDeadCount();
                Player2.setAliveTurn(0);
            }

            //生存ボーナス
            System.out.println("生存ボーナスとして" + Player1.getName() + "は" + 10 * Player1.getAliveTurn() + "点を獲得しました");
            Player1.increaseScore(10 * Player1.getAliveTurn());
            System.out.println("生存ボーナスとして" + Player2.getName() + "は" + 10 * Player2.getAliveTurn() + "点を獲得しました");
            Player2.increaseScore(10 * Player2.getAliveTurn());

            printBoard();
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

//        System.out.println("Player1の得点: " + Player1.getScore());
//        System.out.println("Player2の得点: " + Player2.getScore());
//        System.out.println("Player1の報酬: " + Player1.getReward());
//        System.out.println("Player2の報酬: " + Player2.getReward());


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
        return true;
    }

    //プレイヤーを移動
    public void movePlayer() {

    }

    //妥当な移動か（移動できるか）を判定
    public boolean isValidMove() {
        return true;

    }

    public void moveEnemy() {

    }

    public void atkEnemy() {

    }

    public void printBoard() {
        System.out.println("盤面表示");
        for (int i = 0; i < BOARD_ROW; i++) {
            System.out.print(" ");
            for (int j = 0; j < BOARD_COL; j++) {
                Piece piece = gameModel.getPiece(i, j); // gameModelからPieceを取得するように修正
                String symbol = (piece != null) ? piece.toString() : "-";
                System.out.print(symbol);
            }
            System.out.println();
        }
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
            if (gameModel.getPiece(ROW, COL) != null
                    || gameModel.getPiecesAround(new Position(ROW, COL)).size() == 0) continue;// 当該マスに障害物含む何かがあったら再生成
            // TODO: Implement Piece
            // enemyInfoに対応する敵をセット
            gameModel.setPiece(new Position(ROW, COL), new Piece(Piece.PieceType.ENEMY));
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
            gameModel.setPiece(new Position(ROW, COL), new Piece(Piece.PieceType.OBSTACLE));
            // TODO: Implement Piece
//            if (game.getPiecesAround(new Position(ROW, COL)).size() == 0)
//                game.setPiece(new Position(ROW, COL), new Piece(Piece.PieceType.OBSTACLE));// 周囲1マスに他の障害物が無ければセット
        }
    }

    public boolean isGameOver() {
        if (turnNum > GAME_TURN) return true;
        else return false;
    }

    public int getRandomNum(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) {
        GameModel gameModel = new GameModel(); // GameModelのインスタンスをここで作成
        GameController gameController = new GameController(testUser1, testUser2); // GameModelのインスタンスをコンストラクタに渡す
        gameController.printBoard();
        gameController.playGame();
    }
}
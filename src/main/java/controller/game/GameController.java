package controller.game;

import controller.networking.GameGateway;
import model.*;

import java.util.*;

import static model.GameModel.*;
import static model.Player.Teban.*;


import model.util.User;

public class GameController {
    private Random random;
    private static final int GAME_TURN = 3;
    private int turnNum;
    private GameModel gameModel;
    private List<Enemy> enemyList;
    private final GameGateway.IGameGateway gateway;
    private int gameRank;

    public static User testUser1 = new User(100, "test1", 100000, 5);
    public static User testUser2 = new User(200, "test2", 100000, 5);
    public static Player Player1;
    public static Player Player2;

    public GameController(User user1, User user2) {
        this.random = new Random();
        enemyList = new ArrayList<>();
        // TODO: Gateway should be properly initialized
        this.gateway = new GameGateway.IGameGateway() {
        };

        gameModel = new GameModel();
        Status player1Status = user1.getStatus();
        Player1 = new Player(SENTE, user1.getUsername(), player1Status, user1.getRank(), new Position(BOARD_ROW - 1, 0));
        gameModel.setPiece(new Position(BOARD_ROW - 1, 0), new Piece(Piece.PieceType.PLAYER1));

        Status player2Status = user2.getStatus();
        Player2 = new Player(GOTE, user2.getUsername(), player2Status, user2.getRank(), new Position(0, BOARD_COL - 1));
        gameModel.setPiece(new Position(0, BOARD_COL - 1), new Piece(Piece.PieceType.PLAYER2));

        gameRank = Math.min(Player1.getRank(), Player2.getRank());

        setObstacle();
        setEnemy(gameRank);
        turnNum = 1;
    }


    //ゲームのメインループ
    public void playGame() {
        System.out.println("----------ゲームを開始します----------");

        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            System.out.println();
            System.out.println("┌------------------┐");
            System.out.printf ("|     Turn: %2d     |", turnNum);
            System.out.println("└------------------┘");

            System.out.println("先手: " + Player1.getName());
            Player1.printStatus();
            System.out.println("SCORE : " + Player1.getScore());

            System.out.println("後手: " + Player2.getName());
            Player2.printStatus();
            System.out.println("SCORE : " + Player2.getScore());

            System.out.println();
            // 敵の情報の表示
            for (Enemy enemy : enemyList) {
                System.out.println("ENEMY: " + enemy.getName() + " " + enemy.getPosition());
                enemy.printEnemyStatus();
            }

            printBoard();
            // 先手
            System.out.println();
            System.out.println("-----プレイヤー1のターンです-----");
            actionPlayer(Player1, gameModel);
            // 後手
            System.out.println("-----プレイヤー2のターンです-----");
            actionPlayer(Player2, gameModel);

            /* 敵の行動 */
            // TODO: 敵の行動を処理するプログラムの追加
            System.out.println();
            System.out.println("-----敵のターンです-----");
            for (Enemy enemy : enemyList) {
                actionEnemy(enemy);
            }

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
            System.out.println();
            System.out.println("生存ボーナスとして" + Player1.getName() + "は" + 10 * Player1.getAliveTurn() + "点を獲得しました");
            Player1.increaseScore(10 * Player1.getAliveTurn());
            System.out.println("生存ボーナスとして" + Player2.getName() + "は" + 10 * Player2.getAliveTurn() + "点を獲得しました");
            Player2.increaseScore(10 * Player2.getAliveTurn());

            turnNum++;
        }
        System.out.println();
        System.out.println("----------ゲーム終了です----------");
        /* 得点計算処理 */
        System.out.println("Player1の得点: " + Player1.getScore());
        System.out.println("Player2の得点: " + Player2.getScore());
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

    private void actionPlayer(Player player, GameModel gameModel) {
        GameActions gameActions = new GameActions();
        gameActions.actionPlayer(player, gameModel, enemyList);
    }

    public void printBoard() {
        // 列番号を表示
        System.out.print("   ");
        for (int j = 0; j < BOARD_COL; j++) {
            System.out.printf(" %3d ", j);
        }
        System.out.println();

        for (int i = 0; i < BOARD_ROW; i++) {
            // 行番号を表示
            System.out.printf("%2d ", i);
            for (int j = 0; j < BOARD_COL; j++) {
                Piece piece = gameModel.getPiece(i, j);
                String symbol = (piece != null) ? piece.toString() : "nul";
                System.out.printf("%4s", symbol);
            }
            System.out.println();
        }
    }


    //敵をセット
    // setEnemyメソッド：敵を初期配置する
    public void setEnemy(int gameRank) {
        // 最初に敵を初期配置する
        for (int i = 0; i < 3; i++) {
            int enemyId = 0;
            if (gameRank == 1) {
                enemyId = getRandomNum(1, 2);
            } else if (2 <= gameRank && gameRank <= 6) {
                enemyId = getRandomNum(gameRank * 2 - 3, gameRank * 2);
            }
            putEnemy(enemyId);

        }
    }

    // putEnemyメソッド：敵を盤面にセットし、ゲーム中のENEMYの情報を格納するリストに追加する
    public void putEnemy(int enemyId) {
        EnemyInfo enemyInfo = getEnemyInfoById(enemyId);
        if (enemyInfo == null) {
            System.out.println("指定された敵IDに対応する敵が存在しません。");
            return;
        }

        do {
            int row = getRandomNum(0, GameModel.BOARD_ROW - 1); // ランダムで座標を生成
            int col = getRandomNum(0, GameModel.BOARD_COL - 1);

            Piece piece = gameModel.getPiece(row, col);
            if (isEmpty(new Position(row, col), gameModel.getBoard()) && areAdjacentCellsEmpty(new Position(row, col), gameModel.getBoard())) {
                switch (enemyId) {
                    case 1:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY01));
                        break;
                    case 2:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY02));
                        break;
                    case 3:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY03));
                        break;
                    case 4:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY04));
                        break;
                    case 5:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY05));
                        break;
                    case 6:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY06));
                        break;
                    case 7:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY07));
                        break;
                    case 8:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY08));
                        break;
                    case 9:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY09));
                        break;
                    case 10:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY10));
                        break;
                    case 11:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY11));
                        break;
                    case 12:
                        gameModel.setPiece(new Position(row, col), new Piece(Piece.PieceType.ENEMY12));
                        break;
                    default:
                        break;
                }
                Enemy enemy = new Enemy(enemyId, enemyInfo.name, enemyInfo.HP, enemyInfo.ATK, enemyInfo.MOV, enemyInfo.RNG, new Position(row, col));
                enemyList.add(enemy); // enemyListに敵を追加

                break;
            }
        } while (true);
    }

    // actionEnemyメソッド：敵の行動を実行する
    public void actionEnemy(Enemy enemy) {
        Random random = new Random();
        int movementRange = enemy.getMov();
        Position initialPosition = enemy.getPosition();
        Position currentPosition = enemy.getPosition();
        Position newPosition = null;

        // 敵の移動処理を実装
        for (int i = 0; i < movementRange; i++) {
            int randomDirection = getRandomNum(0, 3); // ランダムに上下左右の方向を選択

            // 敵の新しい位置を計算
            newPosition = new Position(currentPosition.getRow(), currentPosition.getCol());
            switch (randomDirection) {
                case 0: // 上に移動
                    if (newPosition.getRow() > 0) {
                        newPosition.setRow(newPosition.getRow() - 1);
                    }
                    break;
                case 1: // 下に移動
                    if (newPosition.getRow() < GameModel.BOARD_ROW - 1) {
                        newPosition.setRow(newPosition.getRow() + 1);
                    }
                    break;
                case 2: // 左に移動
                    if (newPosition.getCol() > 0) {
                        newPosition.setCol(newPosition.getCol() - 1);
                    }
                    break;
                case 3: // 右に移動
                    if (newPosition.getCol() < GameModel.BOARD_COL - 1) {
                        newPosition.setCol(newPosition.getCol() + 1);
                    }
                    break;
                default:
                    break;
            }

            // 移動先が空の場合のみ移動を実行
            if (isEmpty(newPosition, gameModel.getBoard())) {
                gameModel.setPiece(currentPosition, new Piece(Piece.PieceType.EMPTY));
                switch (enemy.getId()) {
                    case 1:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY01));
                        break;
                    case 2:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY02));
                        break;
                    case 3:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY03));
                        break;
                    case 4:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY04));
                        break;
                    case 5:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY05));
                        break;
                    case 6:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY06));
                        break;
                    case 7:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY07));
                        break;
                    case 8:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY08));
                        break;
                    case 9:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY09));
                        break;
                    case 10:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY10));
                        break;
                    case 11:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY11));
                        break;
                    case 12:
                        gameModel.setPiece(newPosition, new Piece(Piece.PieceType.ENEMY12));
                        break;
                    default:
                        break;
                }
                enemy.setPosition(newPosition); // 移動後の座標を設定
                currentPosition = newPosition; // 現在の位置を更新
            }
        }
        System.out.println(enemy.getName() + " moved from " + initialPosition + " to " + newPosition);

    }

    // 敵の情報をIDに基づいて取得するメソッド
    private EnemyInfo getEnemyInfoById(int enemyId) {
        for (EnemyInfo enemyInfo : EnemyInfo.values()) {
            if (enemyInfo.getID() == enemyId) {
                return enemyInfo;
            }
        }
        return null;
    }

    //障害物を最大10個セット
    public void setObstacle() {
        int ROW, COL;
        random = new Random();
        for (int i = 0; i < 10; i++) {
            ROW = random.nextInt(BOARD_ROW);// ランダムで座標を生成
            COL = random.nextInt(BOARD_COL);
            if (isEmpty(new Position(ROW, COL), gameModel.getBoard())) {
                gameModel.setPiece(new Position(ROW, COL), new Piece(Piece.PieceType.OBSTACLE));
            }
            // TODO: プレイヤーとか敵が締め出される可能性があるので、その場合は再度セットし直す
        }
    }

    public boolean areAdjacentCellsEmpty(Position pos, GameBoard gameBoard) {
        int x = pos.getRow();
        int y = pos.getCol();

        // 上下左右の座標を表す配列
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // 隣接するマスをチェック
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            // 新しい座標が盤面内かをチェック
            if (newX >= 0 && newX < BOARD_ROW && newY >= 0 && newY < BOARD_COL) {
                // 新しい座標のマスがEMPTYでない場合、falseを返す
                if (gameBoard.getPiece(newX, newY).getType() != Piece.PieceType.EMPTY) {
                    return false;
                }
            }
        }

        // 全ての方向の隣接するマスがEMPTYだった場合、trueを返す
        return true;
    }

    public boolean isEmpty(Position pos, GameBoard gameBoard) {
        Piece piece = gameBoard.getPiece(pos);
        return piece != null && piece.getType() == Piece.PieceType.EMPTY;
    }

    public boolean isGameOver() {
        if (turnNum > GAME_TURN) return true;
        else return false;
    }

    public int getRandomNum(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) {
        GameController gameController = new GameController(testUser1, testUser2); // GameModelのインスタンスをコンストラクタに渡す
        gameController.playGame();
    }
}
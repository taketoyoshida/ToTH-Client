package model;

import java.util.List;
import java.util.Scanner;

import model.Enemy;

import static model.GameModel.DEFEAT_BONUS;
import java.io.*;

public class GameActions {
    private Scanner scanner;
    //private PrintWriter logWriter;

    public GameActions() {
        scanner = new Scanner(System.in);
//        try {
//            logWriter = new PrintWriter(new FileWriter("src/main/java/controller/game/GameLog.txt", true));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    private void writeToLog(PrintWriter logWriter, String log) {
        logWriter.println(log);
        logWriter.flush();
    }

    // プレイヤーの行動を実行するメソッド
    public void actionPlayer(Player player, GameModel gameModel, List<Enemy> enemyList,PrintWriter logWriter) {
        // 1. playerのMOVを取得
        int movementRange = player.getMOV();

        // 2. プレイヤーの移動を行う
        System.out.println();
        System.out.println("----------移動フェイズ----------");
        writeToLog(logWriter,"----------移動フェイズ----------");

        int currentRow = player.getRow();
        int currentCol = player.getCol();

        boolean isMoving = true;
        while (isMoving && movementRange > 0) {
            System.out.println("現在位置: (" + currentRow + ", " + currentCol + ")");
            System.out.println("残り移動範囲: " + movementRange);
            System.out.println("移動したい座標を入力してください。移動フェイズを終了する場合は\"100\"を入力");

            writeToLog(logWriter,"現在位置: (" + currentRow + ", " + currentCol + ")");
            writeToLog(logWriter,"残り移動範囲: " + movementRange);
            writeToLog(logWriter,"移動したい座標を入力してください。移動フェイズを終了する場合は\"100\"を入力");

            String input = scanner.nextLine();

            if (input.equals("100")) {
                isMoving = false;
                System.out.println("移動フェイズを終了します。");
                writeToLog(logWriter,"移動フェイズを終了します。");
                break;
            }

            String[] coordinates = input.split(" ");

            if (coordinates.length != 2) {
                System.out.println("無効な入力です。再度入力してください。");
                continue;
            }

            try {
                int targetRow = Integer.parseInt(coordinates[0]);
                int targetCol = Integer.parseInt(coordinates[1]);

                if (targetRow < 0 || targetRow >= GameModel.BOARD_ROW || targetCol < 0 || targetCol >= GameModel.BOARD_COL) {
                    System.out.println("無効な座標です。再度入力してください。");
                    writeToLog(logWriter,"無効な座標です。再度入力してください。");
                    continue;
                }

                // 移動範囲内かどうかの判定
                int distance = Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
                if (distance > movementRange) {
                    System.out.println("指定された座標は移動範囲外です。再度入力してください。");
                    writeToLog(logWriter,"指定された座標は移動範囲外です。再度入力してください。");
                    continue;
                }

                Piece targetPiece = gameModel.getPiece(targetRow, targetCol);
                if (targetPiece.getType() != Piece.PieceType.EMPTY) {
                    System.out.println("指定された座標にはすでに何かが配置されています。再度入力してください。");
                    writeToLog(logWriter,"指定された座標にはすでに何かが配置されています。再度入力してください。");
                    continue;
                }

                // 移動先の座標にプレイヤーを移動
                gameModel.setPiece(currentRow, currentCol, new Piece(Piece.PieceType.EMPTY));
                switch (player.getTeban()) {
                    case SENTE:
                        gameModel.setPiece(targetRow, targetCol, new Piece(Piece.PieceType.PLAYER1));
                        break;
                    case GOTE:
                        gameModel.setPiece(targetRow, targetCol, new Piece(Piece.PieceType.PLAYER2));
                        break;
                    default:
                        break;
                }
                System.out.println("(" + currentRow + ", " + currentCol + ") -> (" + targetRow + ", " + targetCol + ")");
                writeToLog(logWriter,"(" + currentRow + ", " + currentCol + ") -> (" + targetRow + ", " + targetCol + ")");

                currentRow = targetRow;
                currentCol = targetCol;
                movementRange = movementRange - distance;
                player.setRow(currentRow);
                player.setCol(currentCol);

                // 盤面を出力
                gameModel.printBoard();


            } catch (NumberFormatException e) {
                System.out.println("無効な入力です。再度入力してください。");
                writeToLog(logWriter,"無効な入力です。再度入力してください。");
            }
        }

        Position currentPlayerPosition = player.getPosition();
        boolean isEnemyInRange = false;
        for (Enemy enemy : enemyList) {
            // 各敵とのマンハッタン距離を計算
            int manhattanDistance = Math.abs(enemy.getPosition().getRow() - currentPlayerPosition.getRow()) +
                    Math.abs(enemy.getPosition().getCol() - currentPlayerPosition.getCol());

            if (manhattanDistance <= player.getRNG()) {
                // 敵が攻撃範囲内に存在する場合
                isEnemyInRange = true;
                break;
            }
        }
        if (!isEnemyInRange) {
            System.out.println("攻撃対象がプレイヤーの攻撃範囲外です。攻撃フェイズをスキップします。");
            writeToLog(logWriter,"攻撃対象がプレイヤーの攻撃範囲外です。攻撃フェイズをスキップします。");
        }else {
            //攻撃フェイズ
            System.out.println();
            System.out.println("----------攻撃フェイズ----------");
            writeToLog(logWriter,"----------攻撃フェイズ----------");
            System.out.println("攻撃したい座標を入力してください。攻撃フェイズを終了する場合は\"100\"を入力");
            writeToLog(logWriter,"攻撃したい座標を入力してください。攻撃フェイズを終了する場合は\"100\"を入力");
            while (true) {
                String attackInput = scanner.nextLine();
                // 攻撃フェイズを終了する
                if (attackInput.equals("100")) {
                    System.out.println("攻撃フェイズを終了します。");
                    writeToLog(logWriter,"攻撃フェイズを終了します。");
                    break;
                }
                String[] attackCoordinates = attackInput.split(" ");

                if (attackCoordinates.length != 2) {
                    System.out.println("無効な入力です。再度入力してください。");
                    writeToLog(logWriter,"無効な入力です。再度入力してください。");
                    continue;
                }

                try {
                    int attackRow = Integer.parseInt(attackCoordinates[0]);
                    int attackCol = Integer.parseInt(attackCoordinates[1]);

                    if (attackRow < 0 || attackRow >= GameModel.BOARD_ROW || attackCol < 0 || attackCol >= GameModel.BOARD_COL) {
                        System.out.println("無効な座標です。再度入力してください。");
                        writeToLog(logWriter,"無効な座標です。再度入力してください。");
                        continue;
                    }
                    int manhattanDistance = Math.abs(attackRow - currentRow) + Math.abs(attackCol - currentCol);
                    if (manhattanDistance > player.getRNG()) {
                        System.out.println("攻撃対象がプレイヤーの攻撃範囲外です。再度入力してください。");
                        writeToLog(logWriter,"攻撃対象がプレイヤーの攻撃範囲外です。再度入力してください。");
                        continue;
                    }

                    Piece targetPiece = gameModel.getPiece(attackRow, attackCol);
                    boolean isEnemy = false;
                    for (int i = 1; i <= 12; i++) {
                        if (targetPiece.getType() == Piece.PieceType.valueOf("ENEMY" + String.format("%02d", i))) {
                            isEnemy = true;
                            break;
                        }
                    }
                    if (isEnemy) {
                        if (attackRow != currentRow || attackCol != currentCol) {
                            // ENEMY を攻撃
                            Position pos = new Position(attackRow, attackCol);
                            for (Enemy enemy : enemyList) {
                                if (enemy.getPosition().equals(pos)) {
                                    System.out.println();
                                    System.out.println("----------Attack Log----------");
                                    writeToLog(logWriter,"----------Attack Log----------");
                                    System.out.println(pos + "にいる" + enemy.getName() + "を攻撃します。");
                                    writeToLog(logWriter,pos + "にいる" + enemy.getName() + "を攻撃します。");
                                    enemy.setHp(enemy.getHp() - player.getATK());
                                    System.out.println(enemy.getName() + "に" + player.getATK() + "のダメージを与えました。");
                                    writeToLog(logWriter,enemy.getName() + "に" + player.getATK() + "のダメージを与えました。");
                                    System.out.println(enemy.getName() + "の残りHPは" + enemy.getHp() + "です。");
                                    writeToLog(logWriter,enemy.getName() + "の残りHPは" + enemy.getHp() + "です。");
                                    if (enemy.getHp() <= 0) {
                                        System.out.println("敵を倒しました。");
                                        writeToLog(logWriter,"敵を倒しました。");
                                        System.out.println("撃破ボーナスとして1000点が加算されます");
                                        writeToLog(logWriter,"撃破ボーナスとして1000点が加算されます");
                                        System.out.println("現在のスコア :" + player.getScore());
                                        writeToLog(logWriter,"現在のスコア :" + player.getScore());
                                        enemyList.remove(enemy);
                                        gameModel.setPiece(attackRow, attackCol, new Piece(Piece.PieceType.EMPTY));
                                        player.increaseScore(DEFEAT_BONUS);
                                    }
                                    System.out.println("------------------------------");
                                    writeToLog(logWriter,"------------------------------");
                                    System.out.println();
                                    break;
                                }
                            }

                        } else {
                            System.out.println("自分自身を攻撃することはできません。再度入力してください。");
                            writeToLog(logWriter,"自分自身を攻撃することはできません。再度入力してください。");
                            continue;
                        }
                    } else {
                        System.out.println("攻撃対象の座標に敵がいません。再度入力してください。");
                        writeToLog(logWriter,"攻撃対象の座標に敵がいません。再度入力してください。");
                        continue;
                    }

                    // 攻撃したら攻撃フェイズは終了する
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("無効な入力です。再度入力してください。");
                    writeToLog(logWriter,"無効な入力です。再度入力してください。");
                }
            }
        }

    }
    public String movePos(int x,int y){
        String X= String.valueOf(x);
        String Y= String.valueOf(y);
        return X+" "+Y;
    }
}

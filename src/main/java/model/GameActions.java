package model;

import java.util.Scanner;

public class GameActions {
    private Scanner scanner;

    public GameActions() {
        scanner = new Scanner(System.in);
    }

    // プレイヤーの行動を実行するメソッド
    public void actionPlayer(Player player, GameModel gameModel) {
        // 1. playerのMOVを取得
        int movementRange = player.getMOV();

        // 2. プレイヤーの移動を行う
        System.out.println("プレイヤーの移動を行います。wasdキーで上下左右に移動します。");
        System.out.println("MOVの範囲内で移動可能です。途中でESCキーを押すと移動を終了します。");
        System.out.println("現在位置: (" + player.getRow() + ", " + player.getCol() + ")");

        int currentRow = player.getRow();
        int currentCol = player.getCol();

        boolean isMoving = true;
        while (isMoving && movementRange > 0) {
            System.out.print("移動先を選択してください (w:上, a:左, s:下, d:右, esc:移動終了): ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("w")) {
                if (currentRow > 0) {
                    currentRow--;
                }
            } else if (input.equals("a")) {
                if (currentCol > 0) {
                    currentCol--;
                }
            } else if (input.equals("s")) {
                if (currentRow < GameModel.BOARD_ROW - 1) {
                    currentRow++;
                }
            } else if (input.equals("d")) {
                if (currentCol < GameModel.BOARD_COL - 1) {
                    currentCol++;
                }
            } else if (input.equals("esc")) {
                isMoving = false;
            }

            System.out.println("現在位置: (" + currentRow + ", " + currentCol + ")");
            movementRange--;
        }
        gameModel.setPiece(player.getRow(), player.getCol(), new Piece(Piece.PieceType.EMPTY));
        switch (player.getName()) {
            case "player1":
                gameModel.setPiece(currentRow, currentCol, new Piece(Piece.PieceType.PLAYER1));
                break;
            case "player2":
                gameModel.setPiece(currentRow, currentCol, new Piece(Piece.PieceType.PLAYER2));
                break;
            default:
                break;

        }


//        // 3. 攻撃ターンに移る
//        if (!isMoving) {
//            System.out.println("移動を終了しました。攻撃ターンに移ります。");
//        } else {
//            System.out.println("MOVの範囲内で移動を終了しました。攻撃ターンに移ります。");
//        }
//
//        // 4. playerのRNGを取得
//        int attackRange = player.getRNG();
//        System.out.println("攻撃範囲: " + attackRange);

//        // 5. 攻撃を行う
//        System.out.println("攻撃を行います。行き先の位置 (row, col) を入力してください。途中でESCキーを押すと攻撃をスキップします。");
//
//        boolean isAttacking = true;
//        while (isAttacking && attackRange > 0) {
//            System.out.print("攻撃先の位置 (row, col) を入力してください (例: 2 3) もしくは esc:攻撃終了: ");
//            String input = scanner.nextLine();
//            if (input.equalsIgnoreCase("esc")) {
//                isAttacking = false;
//            } else {
//                String[] coordinates = input.split(" ");
//                if (coordinates.length != 2) {
//                    System.out.println("無効な入力です。再度入力してください。");
//                    continue;
//                }
//
//                try {
//                    int targetRow = Integer.parseInt(coordinates[0]);
//                    int targetCol = Integer.parseInt(coordinates[1]);
//
//                    if (targetRow < 0 || targetRow >= GameModel.BOARD_ROW || targetCol < 0 || targetCol >= GameModel.BOARD_COL) {
//                        System.out.println("無効な位置です。再度入力してください。");
//                        continue;
//                    }
//
//                    // 6. 指定した位置に対して攻撃を行う
//                    Piece targetPiece = gameModel.getPiece(targetRow, targetCol);
//                    if (targetPiece != null) {
//                        if (targetPiece.getType() == Piece.PieceType.ENEMY || targetPiece.getType() == Piece.PieceType.PLAYER) {
//                            // 7. 攻撃力はplayerのATKの値であり、攻撃された対象はHPが攻撃者のATKの値分引かれる
//                            int attackDamage = player.getATK();
//                            targetPiece.decreaseHP(attackDamage);
//
//                            if (targetPiece.getHP() <= 0) {
//                                gameModel.setPiece(targetRow, targetCol, new Piece(Piece.PieceType.EMPTY));
//                            }
//
//                            attackRange--;
//                        } else {
//                            System.out.println("攻撃先に敵またはプレイヤーがいません。再度入力してください。");
//                            continue;
//                        }
//                    } else {
//                        System.out.println("攻撃先に敵またはプレイヤーがいません。再度入力してください。");
//                        continue;
//                    }
//
//                } catch (NumberFormatException e) {
//                    System.out.println("無効な入力です。再度入力してください。");
//                }
//            }
//        }
//
//        if (!isAttacking) {
//            System.out.println("攻撃をスキップしました。ターンを終了します。");
//        } else {
//            System.out.println("攻撃範囲内の攻撃を終了しました。ターンを終了します。");
//        }
    }
}

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
        System.out.println("プレイヤーの移動を行います。移動したい座標を入力してください。");
        System.out.println("例: 2 3 (2行目3列目に移動)");

        int currentRow = player.getRow();
        int currentCol = player.getCol();

        System.out.println("現在位置: (" + currentRow + ", " + currentCol + ")");
        System.out.println("残り移動範囲: " + movementRange);

        boolean isMoving = true;
        while (isMoving && movementRange > 0) {
            System.out.print("移動先の座標を入力してください (行 列): ");
            String input = scanner.nextLine();
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
                    continue;
                }

                // 移動範囲内かどうかの判定
                int distance = Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
                if (distance > movementRange) {
                    System.out.println("指定された座標は移動範囲外です。再度入力してください。");
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

                currentRow = targetRow;
                currentCol = targetCol;
                movementRange--;
                player.setRow(currentRow);
                player.setCol(currentCol);

                // 盤面を出力
                gameModel.printBoard();


            } catch (NumberFormatException e) {
                System.out.println("無効な入力です。再度入力してください。");
            }
        }
    }

}

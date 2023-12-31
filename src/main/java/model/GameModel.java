package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameModel {
    public static final int BOARD_ROW = 8;
    public static final int BOARD_COL = 12;
    public int turn;
    private GameBoard board;
    int[] remainAction = new int[2];
    public final boolean[] isDead;
    public static final int DEFEAT_BONUS = 1000;

    public GameModel() {
        turn = 0;
        remainAction[0] = 2;
        remainAction[1] = 2;
        isDead = new boolean[]{false, false};
        board = new GameBoard();
        initBoard();
    }

    // 新しいGameBoardクラスのメソッドを使用するように修正
    public void setPiece(Position pos, Piece piece) {
        board.setPiece(pos, piece);
    }

    public void setPiece(int x, int y, Piece piece) {
        board.setPiece(x, y, piece);
    }

    public Piece getPiece(Position pos) {
        return board.getPiece(pos);
    }

    public Piece getPiece(int x, int y) {
        return board.getPiece(x, y);
    }

    public GameBoard getBoard() {
        return board;
    }

    // 対象のマスの周囲のコマを返す
    public Set<Map.Entry<Position, Piece>> getPiecesAround(Position pos) {
        return board.getPiecesAround(pos);
    }

    private void initBoard() {
        board.initBoard(BOARD_ROW, BOARD_COL);
    }

    public void printPiece(int x, int y) {
        Piece piece = getPiece(x, y);
        System.out.println("Position (" + x + ", " + y + "), Piece: " + piece);
        String symbol = (piece != null) ? piece.toString() : "-";
        System.out.print(symbol);
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
                Piece piece = getPiece(i, j);
                String symbol = (piece != null) ? piece.toString() : "nul";
                System.out.printf("%4s", symbol);
            }
            System.out.println();
        }
    }
}


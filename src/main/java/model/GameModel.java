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

    public Piece getPiece(Position pos) {
        return board.getPiece(pos);
    }

    public Piece getPiece(int x, int y) {
        return board.getPiece(x, y);
    }

    // 対象のマスの周囲のコマを返す
    public Set<Map.Entry<Position, Piece>> getPiecesAround(Position pos) {
        return board.getPiecesAround(pos);
    }

    private void initBoard() {
        board.initBoard(BOARD_ROW, BOARD_COL);
    }
}


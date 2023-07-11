package model;

import java.util.HashMap;

public class Game {
    public static final int BOARD_ROW = 8;
    public static final int BOARD_COL = 12;
    public int turn;   //手番
    private final HashMap<Position, Piece> board;
    int[] remainAction = new int[2];
    public final boolean[] isDead;

    public Game() {
        turn = 0;
        remainAction[0] = 2;
        remainAction[1] = 2;
        isDead = new boolean[]{false, false};
        board = new HashMap<>();
    }

    public void setPiece(Position pos, Piece piece) {
        board.put(pos, piece);
    }

    public Piece getPiece(Position pos) {
        return board.get(pos);
    }

    public Piece getPiece(int x, int y) {
        return board.get(new Position(x, y));
    }
}
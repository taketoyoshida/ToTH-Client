package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public void printPiece(Position pos) {
        if (getPiece(pos) == null) {
            System.out.print("空");
        }
    }

    // 対象のマスの周囲のコマを返す
    public Set<Map.Entry<Position, Piece>> getPiecesAround(Position pos) {
        Set<Map.Entry<Position, Piece>> results = new HashSet<>();
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            if (entry.getKey().getY() == pos.getY() &&
                    (entry.getKey().getX() == pos.getX() - 1 || entry.getKey().getX() == pos.getX() + 1)) {
                results.add(entry);
            } else if (entry.getKey().getX() == pos.getX() &&
                    (entry.getKey().getY() == pos.getY() - 1 || entry.getKey().getY() == pos.getY() + 1)) {
                results.add(entry);
            }
        }
        return results;
    }
}
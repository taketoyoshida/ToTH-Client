package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameBoard {
    private final Map<Position, Piece> board;

    public GameBoard() {
        board = new HashMap<>();
    }

    public void setPiece(Position pos, Piece piece) {
        board.put(pos, piece);
    }

    public void setPiece(int x, int y, Piece piece) {
        Position pos = new Position(x, y);
        board.put(pos, piece);
    }

    public Piece getPiece(Position pos) {
        return board.get(pos);
    }

    public Piece getPiece(int x, int y) {
        return board.get(new Position(x, y));
    }

    // 対象のマスの周囲のコマを返す
    public Set<Map.Entry<Position, Piece>> getPiecesAround(Position pos) {
        Set<Map.Entry<Position, Piece>> results = new HashSet<>();
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            int x = entry.getKey().getRow();
            int y = entry.getKey().getCol();

            if ((x == pos.getRow() - 1 || x == pos.getRow() + 1) && y == pos.getCol()) {
                results.add(entry);
            } else if ((y == pos.getCol() - 1 || y == pos.getCol() + 1) && x == pos.getRow()) {
                results.add(entry);
            }
        }
        return results;
    }

    // 盤面を初期化
    public void initBoard(int rows, int cols) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                board.put(new Position(x, y), new Piece(Piece.PieceType.EMPTY));
            }
        }
    }

}

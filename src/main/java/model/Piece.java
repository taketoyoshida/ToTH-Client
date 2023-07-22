package model;


import java.io.Serial;
import java.io.Serializable;

// Board上のコマを表すクラス。Player, Enemy, Obstacleの親クラス
public class Piece implements Serializable {
    @Serial
    private static final long serialVersionUID = 12034L;

    public enum PieceType {
        PLAYER, ENEMY, OBSTACLE, EMPTY
    }

    private PieceType type;

    public Piece(PieceType type) {
        this.type = type;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        switch (type) {
            case EMPTY:
                return "-";
            case PLAYER:
                return "P";
            case ENEMY:
                return "E";
            case OBSTACLE:
                return "O";
            default:
                return "^";
        }
    }

}

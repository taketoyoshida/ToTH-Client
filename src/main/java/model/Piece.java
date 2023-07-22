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
        if (this.type == PieceType.PLAYER) {
            return "P";
        } else if (this.type == PieceType.ENEMY) {
            return "E";
        } else if (this.type == PieceType.OBSTACLE) {
            return "O";
        } else {
            return "-";
        }
    }


}

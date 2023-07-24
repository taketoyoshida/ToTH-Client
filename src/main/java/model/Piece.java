package model;


import java.io.Serial;
import java.io.Serializable;

// Board上のコマを表すクラス。Player, Enemy, Obstacleの親クラス
public class Piece implements Serializable {
    @Serial
    private static final long serialVersionUID = 12034L;

    public enum PieceType {
        PLAYER1, PLAYER2, ENEMY01, ENEMY02, ENEMY03, ENEMY04, ENEMY05, ENEMY06, ENEMY07, ENEMY08, ENEMY09, ENEMY10, ENEMY11, ENEMY12, OBSTACLE, EMPTY
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
            case PLAYER1:
                return " PL1 ";
            case PLAYER2:
                return " PL2 ";
            case ENEMY01:
                return " E01 ";
            case ENEMY02:
                return " E02 ";
            case ENEMY03:
                return " E03 ";
            case ENEMY04:
                return " E04 ";
            case ENEMY05:
                return " E05 ";
            case ENEMY06:
                return " E06 ";
            case ENEMY07:
                return " E07 ";
            case ENEMY08:
                return " E08 ";
            case ENEMY09:
                return " E09 ";
            case ENEMY10:
                return " E10 ";
            case ENEMY11:
                return " E11 ";
            case ENEMY12:
                return " E12 ";
            case OBSTACLE:
                return " *** ";
            case EMPTY:
                return " --- ";
            default:
                return " ^^^ ";
        }
    }

    public int toIntger() {
        switch (type) {
            case PLAYER1, PLAYER2:
                return 1;
            case ENEMY01:
                return 2;
            case ENEMY02:
                return 3;
            case ENEMY03:
                return 4;
            case ENEMY04:
                return 5;
            case ENEMY05:
                return 6;
            case ENEMY06:
                return 7;
            case ENEMY07:
                return 8;
            case ENEMY08:
                return 9;
            case ENEMY09:
                return 10;
            case ENEMY10:
                return 11;
            case ENEMY11:
                return 12;
            case ENEMY12:
                return 13;
            case OBSTACLE:
                return 14;
            case EMPTY:
                return 15;
            default:
                return 16;
        }
    }


}

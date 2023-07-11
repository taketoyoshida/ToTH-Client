package model;

import java.io.Serial;
import java.io.Serializable;

public class Position implements Serializable {
    @Serial
    private static final long serialVersionUID = 242876L;

    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

package com.jsaop.conways.game;

/**
 * Created by jsaop on 4/11/17.
 */
public class OutOfGameBoundsException extends RuntimeException {
    private final int x;
    private final int y;
    private final String message;

    OutOfGameBoundsException(int x, int y) {
        this.x = x;
        this.y = y;
        message = "out of bounds";

    }

    @Override
    public String getMessage() {
        return String.format("%d, %d %s", x, y, message);
    }
}

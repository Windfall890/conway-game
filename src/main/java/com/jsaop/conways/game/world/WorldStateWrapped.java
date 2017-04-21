package com.jsaop.conways.game.world;

/**
 * Created by jsaop on 4/20/17.
 */
public class WorldStateWrapped extends WorldState {
    public WorldStateWrapped() {
        super(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    public WorldStateWrapped(int width, int height) {
        super(width, height);
    }

    @Override
    public int countNeighbors(int x, int y) {
        int count = 0;

        for (int i = y - 1; i <= y + 1; i++)
            for (int j = x - 1; j <= x + 1; j++)
                if (isAlive(j, i))
                    ++count;

        if (isAlive(x, y))
            count--;

        return count;
    }

    @Override
    public boolean isAlive(int x, int y) {
        x = x % getWidth();
        y = y % getHeight();

        while (x < 0) x = x+getWidth();
        while (y < 0) y = y+getHeight();


        return getCells()[y][x];
    }

    @Override

    public WorldState copy() {
        WorldState copy = new WorldStateWrapped(getWidth(), getHeight());
        copy.setCells(copyCells());
        return copy;
    }


}

package com.jsaop.conways.game.world;

public class WorldStateBounded extends WorldState {

    public WorldStateBounded() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public WorldStateBounded(int width, int height) {
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
}


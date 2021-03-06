package com.jsaop.conways.game.world;

public abstract class WorldState {

    public static final int DEFAULT_WIDTH = 5;
    public static final int DEFAULT_HEIGHT = 5;

    private final int width;
    private final int height;
    private boolean[][] cells;

    public WorldState() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public WorldState(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new boolean[height][width];
    }

    public boolean[][] getCells() {
        return cells;
    }

    public void setCells(boolean[][] cells) {
        this.cells = cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumberCells() {
        return width * height;
    }

    public WorldState copy() {
        WorldState copy = new WorldStateBounded(width, height);
        copy.setCells(copyCells());
        return copy;
    }

    boolean[][] copyCells() {
        boolean[][] copy = new boolean[height][width];

        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                copy[y][x] = this.cells[y][x];

        return copy;
    }

    public String getStateDiagram() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < getHeight(); y++) {
            sb.append(makeFancyRow(cells[y]));
            sb.append('\n');
        }

        return sb.toString();
    }

    private String makeFancyRow(boolean[] row) {
        StringBuilder sb = new StringBuilder();

        sb.append("[ ");
        for (boolean b : row) {
            sb.append(b ? '*' : '.');
            sb.append(" ");
        }
        sb.append(']');
        return sb.toString();
    }

    public abstract int countNeighbors(int x, int y);

    public void spawn(int x, int y) {
        try {
            getCells()[y][x] = true;
        } catch (IndexOutOfBoundsException ex) {
            throw new OutOfGameBoundsException(x, y);
        }
    }

    public void kill(int x, int y) {

        try {
            getCells()[y][x] = false;
        } catch (IndexOutOfBoundsException ex) {
            throw new OutOfGameBoundsException(x,y);
        }
    }

    public boolean isAlive(int x, int y) {
        try {
            return getCells()[y][x];
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }
}

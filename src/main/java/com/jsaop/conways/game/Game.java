package com.jsaop.conways.game;

public class Game {
    public static final int DEFAULT_WIDTH = 3;
    public static final int DEFAULT_HEIGHT = 3;

    private WorldState currentGen;
    private WorldState lastGen;
    private int generation;


    public Game() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Game(int width, int height) {
        this.currentGen = new WorldState(width, height);
        copyWorldStateToLastGen();
        this.generation = 0;
    }

    public void spawn(int x, int y) {
        currentGen.spawn(x, y);
    }

    public void kill(int x, int y) {
        currentGen.kill(x, y);
    }

    public boolean isAlive(int x, int y) {
        return currentGen.isAlive(x, y);
    }

    public void step() {
        copyWorldStateToLastGen();
        determineCellFates();
        generation++;
    }

    public void spawnRPentomino(int x, int y) {
        x = x - 2;
        y = y - 2;
        spawn(x + 2, y + 1);
        spawn(x + 3, y + 1);
        spawn(x + 1, y + 2);
        spawn(x + 2, y + 2);
        spawn(x + 2, y + 3);
    }

    public void spawnBlockLayingSwitch(int x, int y) throws OutOfGameBoundsException{
        x = x - 5;
        y = y - 5;
        spawn(x + 8, y + 2);
        spawn(x + 8, y + 3);
        spawn(x + 8, y + 4);
        spawn(x + 9, y + 3);
        spawn(x + 6, y + 3);
        spawn(x + 6, y + 4);
        spawn(x + 6, y + 5);
        spawn(x + 4, y + 6);
        spawn(x + 4, y + 7);
        spawn(x + 2, y + 7);
    }

    public void spawnGlider(int x, int y) throws OutOfGameBoundsException{
        x = x -2;
        y = y -2;
        spawn(x + 0, y + 3);
        spawn(x + 1, y + 3);
        spawn(x + 2, y + 3);
        spawn(x + 2, y + 2);
        spawn(x + 1, y + 1);
    }


    public void spawnBlinker(int x, int y) throws OutOfGameBoundsException {
        spawn(x - 1, y);
        spawn(x, y);
        spawn(x + 1, y);
    }

    public int getWidth() {
        return currentGen.getWidth();
    }

    public int getHeight() {
        return currentGen.getHeight();
    }

    public int getNumberCells() {
        return currentGen.getNumberCells();
    }

    public boolean[][] getState() {
        return currentGen.getCells();
    }

    public String getStateDiagram() {
        return currentGen.getStateDiagram();
    }

    public int getGeneration() {
        return generation;
    }

    public int getPopulation() {
        return currentGen.getPopulation();
    }

    private void determineCellFates() {
        for (int y = 0; y < lastGen.getHeight(); y++)
            for (int x = 0; x < lastGen.getWidth(); x++)
                determineFate(y, x);
    }

    private void determineFate(int y, int x) {
        int neighbors = lastGen.countNeighbors(x, y);
        if (shouldDie(neighbors)) {
            kill(x, y);
        } else if (shouldLive(y, x, neighbors)) {
            spawn(x, y);
        }
    }

    private boolean shouldLive(int y, int x, int neighbors) {
        return !lastGen.isAlive(x, y) && neighbors == 3;
    }

    private boolean shouldDie(int neighbors) {
        return neighbors < 2 || neighbors > 3;
    }


    private void copyWorldStateToLastGen() {
        lastGen = currentGen.copy();
    }


}

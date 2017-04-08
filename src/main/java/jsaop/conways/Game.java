package jsaop.conways;

public class Game {
    public static final int DEFAULT_WIDTH = 3;
    public static final int DEFAULT_HEIGHT = 3;

    private WorldState currentGen;
    private WorldState lastGen;


    public Game() {
        this.currentGen = new WorldState(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        copyWorldState();
    }

    public Game(int width, int height) {
        this.currentGen = new WorldState(width, height);
        this.lastGen = new WorldState(width, height);
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
        copyWorldState();
        determineCellFates();
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

    private void copyWorldState() {
        lastGen = currentGen.copy();
    }

}

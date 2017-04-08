package jsaop.conways;

/**
 * Created by jsaop on 4/7/17.
 */
public class WorldState {

    private final int width;
    private final int height;
    private boolean[][] cells;

    public WorldState(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new boolean[width][height];
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


    public void spawn(int x, int y) {
        cells[y][x] = true;
    }

    public void kill(int x, int y) {
        cells[y][x] = false;
    }

    public boolean isAlive(int x, int y) {
        try {
            return cells[y][x];
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    public int countNeighbors(int x, int y) {
        int count = 0;

        for (int i = y - 1; i <= y + 1; i++)
            for (int j = x - 1; j <= x + 1; j++)
                if (isAlive(j, i))
                    ++count;

        if(isAlive(x,y))
            count--;

        return count;

    }

    public WorldState copy() {
        WorldState copy = new WorldState(width, height);
        copy.setCells(copyCells());
        return copy;
    }

    private boolean[][] copyCells() {
        boolean[][] copy = new boolean[width][height];

        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                copy[x][y] = this.cells[x][y];

        return copy;
    }
}

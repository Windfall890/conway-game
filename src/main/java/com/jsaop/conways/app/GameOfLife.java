package com.jsaop.conways.app;

import com.jsaop.conways.game.Game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOfLife extends Application {
    public static final Color CELL_FILL_COLOR = Color.AQUA;
    public static int CELLS_WIDE = 250;
    public static int CELLS_HIGH = 250;
    public static int CELL_DIMENSION = 2;
    public static long FRAME_DELAY = 150;

    private Game game;
    private GridPane grid;
    private Text generation;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game(CELLS_WIDE, CELLS_HIGH);
//        spawnGlider();
//        spawnBlockLayingSwitch();
        game.spawnRPentomino(CELLS_WIDE/2, CELLS_HIGH/2);
        game.spawnBlinker(1,1);
        game.spawnBlinker(CELLS_WIDE-2, CELLS_HIGH-2);
        System.out.println(game.getStateDiagram());

        primaryStage.setTitle("Conway's Game of Life");

        grid = new GridPane();
        grid.setGridLinesVisible(false);
        grid.setPrefSize((CELL_DIMENSION * CELLS_WIDE) + CELLS_WIDE, (CELL_DIMENSION * CELLS_HIGH) + CELLS_HIGH);
        for (int row = 0; row < CELLS_WIDE; row++) {
            for (int col = 0; col < CELLS_HIGH; col++) {
                createGridCell(row, col);
            }
        }

        generation = new Text();

        BorderPane root = new BorderPane();
        root.setBackground(Background.EMPTY);
        root.setTop(generation);
        root.setCenter(grid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        startAnimation();

    }

    private void createGridCell(int row, int col) {
        Rectangle r = new Rectangle();
        r.setWidth(CELL_DIMENSION);
        r.setHeight(CELL_DIMENSION);
        if (game.isAlive(row, col)) {
            r.setFill(CELL_FILL_COLOR);
            r.setStroke(CELL_FILL_COLOR);
        }

        GridPane.setConstraints(r, col, row);
        grid.getChildren().add(r);
    }

    private void spawnBlockLayingSwitch() {
        int x = (CELLS_WIDE / 2) - 5;
        int y = (CELLS_HIGH / 2) - 5;
        game.spawn(x + 8, y + 2);
        game.spawn(x + 8, y + 3);
        game.spawn(x + 8, y + 4);
        game.spawn(x + 9, y + 3);
        game.spawn(x + 6, y + 3);
        game.spawn(x + 6, y + 4);
        game.spawn(x + 6, y + 5);
        game.spawn(x + 4, y + 6);
        game.spawn(x + 4, y + 7);
        game.spawn(x + 2, y + 7);
    }

    private void spawnGliderAndblinker() {
        game.spawn(0, 3);
        game.spawn(1, 3);
        game.spawn(2, 3);
        game.spawn(2, 2);
        game.spawn(1, 1);
        game.spawn(7, 8);
        game.spawn(8, 8);
        game.spawn(9, 8);
    }

    private void startAnimation() {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0) {
                    updateAnimation();
                }
                lastUpdateTime.set(timestamp);
            }
        };
        timer.start();
    }

    private void updateAnimation() {
        updateGrid();
        updateGeneration();
        game.step();
        delayFrame();
    }

    private void delayFrame() {
        try {
            Thread.sleep(FRAME_DELAY);
        } catch (InterruptedException e) {
            //silent
        }
    }

    private void updateGrid() {
        for (int row = 0; row < CELLS_WIDE; row++)
            for (int col = 0; col < CELLS_HIGH; col++)
                updateCell(row, col);
    }

    private void updateGeneration() {
        generation.setText("Generation: " + game.getGeneration());
    }

    private void updateCell(int row, int col) {
        Rectangle r = getRectangle(row, col);
        if (game.isAlive(row, col)) {
            r.setFill(CELL_FILL_COLOR);
            r.setStroke(CELL_FILL_COLOR);
        } else {
            r.setFill(Color.BLACK);
            r.setStroke(Color.BLACK);
        }
    }

    private Rectangle getRectangle(int row, int col) {
        return (Rectangle) grid.getChildren().get(getIndex(row, col));
    }

    private int getIndex(int row, int col) {
        return row * CELLS_WIDE + col;
    }
}



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
//        spawnGliderAndblinker();
        spawnBlockLayingSwitch();
//        spawnRPentomino();
        System.out.println(game.getStateDiagram());

        primaryStage.setTitle("Conway's Game of Life");

        grid = new GridPane();
        grid.setGridLinesVisible(false);
        for (int row = 0; row < CELLS_WIDE; row++) {
            for (int col = 0; col < CELLS_HIGH; col++) {
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

    private void spawnRPentomino() {
        int x = (CELLS_WIDE / 2) - 2;
        int y = (CELLS_HIGH / 2) - 2;
        game.spawn(x + 2, y + 1);
        game.spawn(x + 3, y + 1);
        game.spawn(x + 1, y + 2);
        game.spawn(x + 2, y + 2);
        game.spawn(x + 2, y + 3);
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
                    updateGrid();
                    game.step();
//                    try {
//                        Thread.sleep(FRAME_DELAY);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                lastUpdateTime.set(timestamp);
            }
        };
        timer.start();
    }

    private void updateGrid() {
        for (int row = 0; row < CELLS_WIDE; row++) {
            for (int col = 0; col < CELLS_HIGH; col++) {
                Rectangle r = ((Rectangle) grid.getChildren().get(row * CELLS_WIDE + col));
                if (game.isAlive(row, col)) {
                    r.setFill(CELL_FILL_COLOR);
                    r.setStroke(CELL_FILL_COLOR);
                } else {
                    r.setFill(Color.BLACK);
                    r.setStroke(Color.BLACK);
                }

            }
        }
        generation.setText("Generation: " + game.getGeneration());
    }
}



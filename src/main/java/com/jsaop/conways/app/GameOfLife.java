package com.jsaop.conways.app;

import com.jsaop.conways.game.Game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameOfLife extends Application {
    public static int CELLS_WIDE = 10;
    public static int CELLS_HIGH = 10;
    public static int CELL_DIMENSION = 20;
    public static long FRAME_DELAY = 300;

    Game game;
    GridPane grid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game(CELLS_WIDE, CELLS_HIGH);
        game.spawn(0, 3);
        game.spawn(1, 3);
        game.spawn(2, 3);
        game.spawn(2, 2);
        game.spawn(1, 1);
        game.spawn(7, 8);
        game.spawn(8, 8);
        game.spawn(9, 8);

        primaryStage.setTitle("Conway's Game of Life");

        grid = new GridPane();
        for (int row = 0; row < CELLS_WIDE; row++) {
            for (int col = 0; col < CELLS_HIGH; col++) {
                Rectangle r = new Rectangle();
                r.setWidth(CELL_DIMENSION);
                r.setHeight(CELL_DIMENSION);
                if (game.isAlive(row, col)) {
                    r.setFill(Color.AQUA);
                }
                r.setStroke(Color.WHITE);

                GridPane.setConstraints(r, col, row);
                grid.getChildren().add(r);
            }
        }

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

        startAnimation();

    }

    private void startAnimation() {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0) {
                    updateGrid();
                    game.step();
                    try {
                        Thread.sleep(FRAME_DELAY);
                    } catch (InterruptedException e) {

                    }
                }
                lastUpdateTime.set(timestamp);
            }
        };
        System.out.print(".");
        timer.start();
    }

    private void updateGrid() {
        for (int row = 0; row < CELLS_WIDE; row++) {
            for (int col = 0; col < CELLS_HIGH; col++) {
                Rectangle r = ((Rectangle) grid.getChildren().get(row * CELLS_WIDE + col));
                if (game.isAlive(row, col)) {
                    r.setFill(Color.AQUA);
                } else {
                    r.setFill(Color.BLACK);
                }

            }
        }
    }
}



package com.jsaop.conways.app;

import com.jsaop.conways.game.ConwayStructureGenerator;
import com.jsaop.conways.game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameOfLife extends Application {
    public static final int CELLS_WIDE = 150;
    public static final int CELLS_HIGH = 100;
    public static final int CELL_DIMENSION = 6;
    public static final long DEFAULT_FRAME_DELAY = 50;

    private Game game;
    private GridPane grid;
    private Text generation;
    private Timeline timeline;

    private boolean paused = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game(CELLS_WIDE, CELLS_HIGH);
        System.out.println(game.getStateDiagram());


        ConwayStructureGenerator generator = new ConwayStructureGenerator(game);
        generator.spawnFromStringAtOffset(ConwayStructureGenerator.GLIDER_GUN, 2, 12);
//        generator.spawnFromStringAtOffset(ConwayStructureGenerator.R_PENTOMINO, CELLS_WIDE/2, CELLS_HIGH/2);
//        generator.spawnFromString(".*\n.*\n.*");
//        generator.spawnFromStringAtOffset(ConwayStructureGenerator.BLOCK_LAYING_SWITCH, CELLS_WIDE-30, CELLS_HIGH-30);
        System.out.println(game.getStateDiagram());


        primaryStage.setTitle("Conway's Game of Life");

        grid = new GridPane();
        grid.setGridLinesVisible(false);
        grid.setPrefSize((CELL_DIMENSION * CELLS_WIDE) + CELLS_WIDE, (CELL_DIMENSION * CELLS_HIGH) + CELLS_HIGH);
        for (int y = 0; y < CELLS_HIGH; y++) {
            for (int x = 0; x < CELLS_WIDE; x++) {
                createGridCell(x, y);
            }
        }

        generation = new Text();

        Pane infoPane = new FlowPane();
        infoPane.getChildren().addAll(generation);
        infoPane.setPrefSize((CELL_DIMENSION * CELLS_WIDE) + CELLS_WIDE,generation.getScaleY());

        BorderPane root = new BorderPane();
        root.setBackground(Background.EMPTY);
        root.setTop(infoPane);
        root.setCenter(grid);

        Scene scene = new Scene(root);
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE)
                handleKeyCodeSpace();
        });


        primaryStage.setScene(scene);
        primaryStage.show();
        startAnimation();
    }

    private void handleKeyCodeSpace() {
        if (paused) {
            timeline.play();
        } else {
            timeline.pause();
        }
        paused = !paused;
    }

    private void createGridCell(int x, int y) {
        Rectangle r = new Rectangle();
        r.setOnMouseClicked(event -> {
            game.spawn(x, y);
            setColor(r, Color.BLUEVIOLET);
        });
        r.setWidth(CELL_DIMENSION);
        r.setHeight(CELL_DIMENSION);
        if (game.isAlive(x, y)) {
            setColor(r, Color.AQUA);
        }

        GridPane.setConstraints(r, x, y);
        grid.getChildren().add(r);
    }

    private static void setColor(Rectangle r, Color blueviolet) {
        r.setFill(blueviolet);
        r.setStroke(blueviolet);
    }

    private void startAnimation() {
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> updateAnimation()),
                new KeyFrame(Duration.millis(DEFAULT_FRAME_DELAY))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }

    private void updateAnimation() {
        updateGrid();
        generation.setText("Generation: " + game.getGeneration());
        game.step();
    }

    private void updateGrid() {
        for (int y = 0; y < CELLS_HIGH; y++)
            for (int x = 0; x < CELLS_WIDE; x++)
                updateCell(x, y);
    }

    private void updateCell(int x, int y) {
        Rectangle r = getRectangle(x, y);
        if (game.isAlive(x, y)) {
            if(r.getFill() == Color.BLACK){
                setColor(r, Color.AZURE);
            }else {
                setColor(r, Color.AQUA);
            }
        } else {
            if(r.getFill() == Color.AQUA){
                setColor(r, Color.GRAY);
            }else if (r.getFill() == Color.GRAY) {
                setColor(r, Color.DIMGRAY);
            } else {
                setColor(r, Color.BLACK);
            }
        }
    }

    private Rectangle getRectangle(int x, int y) {
        return (Rectangle) grid.getChildren().get(getIndex(x, y));
    }

    private int getIndex(int x, int y) {
        return y * CELLS_WIDE + x;
    }
}



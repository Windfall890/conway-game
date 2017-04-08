package com.jsaop.conways.game;

public class Simulation {
    private int generation;
    private Game game;

    public Simulation(Game game) {
        this.generation = 0;
        this.game = game;
    }

    public void stepOnce(){
        generation++;
        game.step();
    }

    public int getGeneration() {
        return generation;
    }

    public String getState() {
        StringBuilder sb = new StringBuilder();
        boolean[][] state = game.getState();

        for(int y = 0; y < game.getHeight(); y++){
            sb.append(makeFancyRow(state[y]));
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

    public static void main(String[] args) {

        Game game = new Game(10,10);
        game.spawn(0, 3);
        game.spawn(1, 3);
        game.spawn(2, 3);
        game.spawn(2, 2);
        game.spawn(1, 1);
        game.spawn(7,8);
        game.spawn(8,8);
        game.spawn(9,8);

        Simulation sim = new Simulation(game);
        System.out.println(sim.getState());

        for(int i =0; i < 50; i++) {
            sim.stepOnce();
            System.out.println(sim.getState());

            try {
                Thread.sleep(600);
            } catch (Exception ex){
                //foo
            }
        }
    }
}

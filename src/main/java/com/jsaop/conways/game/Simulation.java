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
        return game.getStateDiagram();
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

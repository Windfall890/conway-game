package com.jsaop.conways.game

import spock.lang.Specification

import static Game.DEFAULT_HEIGHT
import static Game.DEFAULT_WIDTH

class GameSpec extends Specification {

    Game game

    void setup() {
        game = new Game()
    }

    def "Game provides x, y size"() {
        expect:
            game.getWidth() == DEFAULT_WIDTH
            game.getHeight() == DEFAULT_HEIGHT
    }

    def "Game provides cell number"() {
        expect:
            game.getNumberCells() == (DEFAULT_WIDTH * DEFAULT_HEIGHT)
    }

    def "game can be created with of optional size"() {
        when:
            game = new Game(5, 5)
        then:
            game.width == 5
            game.height == 5
    }

    def "Game has current generation state"() {
        expect:
            game.state instanceof boolean[][]
            game.state.length == DEFAULT_HEIGHT
            game.state[0].length == DEFAULT_WIDTH
    }


    def "step() kills cell with no neighbors"() {
        given:
            game.spawn(1, 1)
        when:
            game.step()
        then:
            !game.isAlive(1, 1)
    }

    def "step() kills cell with one neighbor"() {
        given:
            game.spawn(1, 1)
            game.spawn(1, 2)
        when:
            game.step()
        then:
            !game.isAlive(1, 1)
    }

    def "step() doesn't kill cell with two neighbors"() {
        given:
            game.spawn(1, 1)
            game.spawn(0, 1)
            game.spawn(2, 1)
        when:
            game.step()
        then:
            game.isAlive(1, 1)
    }

    def "step() kills cell with >3 neighbors"() {
        given:
            game.spawn(0, 0)
            game.spawn(1, 0)
            game.spawn(2, 0)
            game.spawn(1, 1)
            game.spawn(1, 2)
        when:
            game.step()
        then:
            !game.isAlive(1, 1)
    }

    def "Step() respawns dead cell with exactly 3 neighbors "() {
        given:
            game.spawn(0, 0)
            game.spawn(1, 0)
            game.spawn(2, 0)
            game.kill(1, 1)
        when:
            game.step()
        then:
            game.isAlive(1, 1)

    }
}
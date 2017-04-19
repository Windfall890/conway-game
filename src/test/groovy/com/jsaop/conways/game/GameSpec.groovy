package com.jsaop.conways.game

import com.jsaop.conways.game.world.WorldStateBounded
import spock.lang.Specification


class GameSpec extends Specification {

    static WIDTH = 5
    static HEIGHT = 5

    Game game
    WorldStateBounded state = Mock(WorldStateBounded)

    void setup() {
        game = new Game()
    }


    def "Game provides default x, y size of 5x5"() {
            state.getWidth() >> 5
            state.getHeight() >> 5
        expect:
            game.getWidth() == WIDTH
            game.getHeight() == HEIGHT
    }

    def "game can be created with of optional size"() {
        when:
            game = new Game(5, 5)
        then:
            game.width == 5
            game.height == 5
    }

    def "Game provides cell count"() {
        expect:
            game.getNumberCells() == (WIDTH * HEIGHT)
    }

    def "Game has current generation state"() {
        expect:
            game.state instanceof boolean[][]
            game.state.length == HEIGHT
            game.state[0].length == WIDTH
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
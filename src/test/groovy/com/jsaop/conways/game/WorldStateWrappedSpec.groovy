package com.jsaop.conways.game

import com.jsaop.conways.game.world.WorldState
import com.jsaop.conways.game.world.WorldStateWrapped
import spock.lang.Specification


class WorldStateWrappedSpec extends Specification {

    static int DEFAULT_X = 5
    static int DEFAULT_Y = 5
    
    WorldState state

    void setup() {
        state = new WorldStateWrapped(DEFAULT_X, DEFAULT_Y)
    }

    def "isALive() returns true for (5,1) on a 5x5 world with (0,1) spawned"() {
        given:
            state.spawn(0,1)
            println state.stateDiagram
        expect:
            state.isAlive(5,1) == true

    }

    def "counts cells off to the right and left of horizontal edge of Cell as neighbor"() {

        given:
            state.spawn(0,1)
            state.spawn(0,2)
            state.spawn(4,1)
            println state.stateDiagram
        expect:
            state.countNeighbors(4,1) == 2
            state.countNeighbors(0,1) == 2

    }

    def "counts cell on other vertical edge of Cells as neighbor"() {

        given:
            state.spawn(1,0)
            state.spawn(1,4)
            println state.stateDiagram
        expect:
            state.countNeighbors(1,0) == 1
            state.countNeighbors(1,4) == 1
    }
}
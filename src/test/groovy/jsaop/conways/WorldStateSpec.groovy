package jsaop.conways

import spock.lang.Specification

class WorldStateSpec extends Specification {


    WorldState state
    static int DEFAULT_X = 3
    static int DEFAULT_Y = 3


    void setup() {
        state = new WorldState(DEFAULT_X, DEFAULT_Y)
    }


    def "provides x, y size"() {
        expect:
            state.width == DEFAULT_X
            state.height == DEFAULT_Y
    }

    def "provides cell number"() {
        expect:
            state.getNumberCells() == (DEFAULT_X * DEFAULT_Y)
    }

    def "state can be created with of optional size"() {
        given:
            state = new WorldState(5, 5)
        expect:
            state.width == 5
            state.height == 5
    }

    def "WorldState has a 2D field for Cells"() {
        expect:
            state.cells instanceof boolean[][]
    }

    def "cell can be spawned"() {
        when:
            state.spawn(0, 0)
        then:
            state.isAlive(0, 0) == true
    }

    def "cell can be killed"() {
        given:
            state.spawn(0, 0)
        when:
            state.kill(0, 0)
        then:
            state.isAlive(0, 0) == false
    }

    def "copy cells works"() {
        when:
            WorldState state2 = state.copy()
        then:
            state2.width == state.width
            state2.height == state.height
            state2.cells == state.cells
    }

    def "out of bounds calls to isAlive() are ignored"() {
        expect:
            !state.isAlive(-12, 500)

    }

    def "countNeighbors counts 0 neighbors when alone and cell alive"() {
        when:
            state.spawn(1, 1)
        then:
            state.countNeighbors(1, 1) == 0
    }

    def "countNeighbors counts 0 neighbors when alone and cell dead"() {
        expect:
            state.countNeighbors(1,1) == 0
    }

    def "CountNeighbors counts 2 neighbors"() {
        when:
            state.spawn(1, 0) // . * .
            state.spawn(1, 1) // . * .
            state.spawn(2, 2) // . . *
        then:
            state.countNeighbors(1, 1) == 2

    }

    def "CountNeighbors counts the max of 8 neighbors"() {
    when:
        state.spawn(0,0)
        state.spawn(0,1)
        state.spawn(0,2)
        state.spawn(1,0)
//        state.spawn(1,1) //center
        state.spawn(1,2)
        state.spawn(2,0)
        state.spawn(2,1)
        state.spawn(2,2)
    then:
        state.countNeighbors(1,1) == 8
    }
}
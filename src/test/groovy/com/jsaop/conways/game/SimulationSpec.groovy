package com.jsaop.conways.game

import spock.lang.Specification


class SimulationSpec extends Specification {

    def mockGame = Mock(Game)
    def sim

    void setup() {
        sim = new Simulation(mockGame)
    }

    def "Simulation can step the game one generation"() {
        when:
            sim.stepOnce()
        then:
            sim.generation == 1
            1 * mockGame.step()
    }

    def "Simulation can get a world state from the game"() {
        mockGame.getState() >> new boolean[3][3]
        mockGame.getHeight() >> 3
        when:
            def state = sim.getState()
        then:
            state.contains("[ . . . ]\n[ . . . ]\n[ . . . ]")
    }
}
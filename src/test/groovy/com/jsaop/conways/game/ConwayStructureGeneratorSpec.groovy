package com.jsaop.conways.game

import spock.lang.Specification

/**
 * Created by jsaop on 4/11/17.
 */
class ConwayStructureGeneratorSpec extends Specification {

    def mockGame = Mock(Game)

    ConwayStructureGenerator generator
    void setup() {
        generator = new ConwayStructureGenerator(mockGame)
    }

    def "when a string containing '.'s is passed nothing happens"() {
        when:
            generator.spawnFromString(".....\n.....")
        then:
            //noinspection GroovyAssignabilityCheck
            0 * mockGame.spawn(*_)

    }

    def " string containing '.' and '*' spawns cells in game"() {
        when:
            generator.spawnFromString("..*\n..*")
        then:
            1 * mockGame.spawn(2, 0)
            1 * mockGame.spawn(2, 1)
    }

    def "string of '..*' at offset 1,1 spawns correct cell"() {
        when:
            generator.spawnFromStringAtOffset("..*", 1, 1)
        then:
            1* mockGame.spawn(3,1)

    }
}

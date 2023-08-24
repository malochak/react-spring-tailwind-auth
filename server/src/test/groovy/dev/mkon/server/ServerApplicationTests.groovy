package dev.mkon.server

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ServerApplicationTests extends Specification {

    def "contextLoads"() {
        expect:
            true
    }
}

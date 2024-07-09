package ru.csdeep

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.csdeep.config.ConfigTest

@SpringBootTest(classes = [ConfigTest::class])
@ExtendWith(SpringExtension::class)
class CsdeepApplicationTests {

    @Test
    fun contextLoads() {
        assert(true).equals(true)
    }
}

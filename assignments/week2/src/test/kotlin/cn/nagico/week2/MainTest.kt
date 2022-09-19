package cn.nagico.week2

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class MainTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            Loader.objectList.clear()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            Loader.objectList.clear()
        }
    }

    @Test
    fun mainSuccess() {
        assertDoesNotThrow { main(arrayOf("test.properties", "bootstrapClass")) }
    }

    @Test
    fun mainFailed() {
        assertDoesNotThrow {
            main(arrayOf("test2.properties", "bootstrapClass"))
            main(arrayOf("test.properties", "bootstrapClass2"))
            main(arrayOf("testNoClass.properties", "bootstrapClass"))
            main(arrayOf("testClassFormatError.properties", "bootstrapClass"))
        }
    }
}
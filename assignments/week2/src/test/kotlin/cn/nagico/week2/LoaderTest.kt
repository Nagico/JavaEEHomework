package cn.nagico.week2

import cn.nagico.week2.pojo.Student
import cn.nagico.week2.pojo.Teacher
import org.junit.jupiter.api.*

class LoaderTest {
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
    fun loadPropertiesSuccess() {
        assertDoesNotThrow { Loader() }
        val prop = Loader.loadProperties("test.properties")
        assert(prop.getProperty("test") == "test")
    }

    @Test
    fun loadPropertiesFileNotFound() {
        assertThrows<NoSuchFileException> {
            Loader.loadProperties("test1.properties")
        }
    }

    @Test
    fun initClassSuccess() {
        Loader.initBootstrapClass("cn.nagico.week2.pojo.Student")
        assert(Loader.objectList.size == 1)
        assert(Loader.objectList[0] is Student)

        Loader.initBootstrapClass("cn.nagico.week2.pojo.Teacher")
        assert(Loader.objectList.size == 2)
        assert(Loader.objectList[1] is Teacher)
    }

    @Test
    fun initClassNotFound() {
        assertThrows<ClassNotFoundException> {
            Loader.initBootstrapClass("cn.nagico.week2.pojo.Student1")
        }
    }

    @Test
    fun initClassWithNull() {
        assertThrows<NullPointerException> {
            Loader.initBootstrapClass(null)
        }
    }

    @Test
    fun initClassErrorFormat() {
        assertThrows<ClassFormatError> {
            Loader.initBootstrapClass("cn.nagico.week2.pojo.Course")
        }
    }
}
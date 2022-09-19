package cn.nagico.week2.pojo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class CourseTest {
    @Test
    fun createCourseSuccess() {
        assertDoesNotThrow {
            Course()
        }
    }
}
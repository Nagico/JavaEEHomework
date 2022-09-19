package cn.nagico.week2.pojo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StudentTest {
    @Test
    fun createStudentSuccess() {
        val student = Student()
        assertThrows<UninitializedPropertyAccessException> {
            student.name
        }
        assert(student.age == 0)
    }

    @Test
    fun updateStudentSuccess() {
        val student = Student()
        student.name = "nagico"
        student.age = 18
        assert(student.name == "nagico")
        assert(student.age == 18)
    }

    @Test
    fun initStudentSuccess() {
        val student = Student()
        student.init()
        assert(student.name == "Test Student")
        assert(student.age == 10)
    }
}
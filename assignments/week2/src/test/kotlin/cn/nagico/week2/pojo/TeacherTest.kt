package cn.nagico.week2.pojo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TeacherTest {
    @Test
    fun createTeacherSuccess() {
        val teacher = Teacher()
        assertThrows<UninitializedPropertyAccessException> {
            teacher.name
        }
        assertThrows<UninitializedPropertyAccessException> {
            teacher.subject
        }
    }

    @Test
    fun updateTeacherSuccess() {
        val teacher = Teacher()
        teacher.name = "张三"
        teacher.subject = "语文"
        assert(teacher.name == "张三")
        assert(teacher.subject == "语文")
    }

    @Test
    fun initTeacherSuccess() {
        val teacher = Teacher()
        teacher.init()
        assert(teacher.name == "Test Teacher")
        assert(teacher.subject == "Test Subject")
    }
}
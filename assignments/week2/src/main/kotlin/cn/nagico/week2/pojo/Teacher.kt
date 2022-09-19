package cn.nagico.week2.pojo

import cn.nagico.week2.annotation.InitMethod

class Teacher : Loadable {
    lateinit var name: String
    lateinit var subject: String

    override fun toString(): String {
        return "Teacher(name='$name', subject='$subject')"
    }

    @InitMethod
    fun init() {
        name = "Test Teacher"
        subject = "Test Subject"
        val now = System.currentTimeMillis()
        println("$this init at $now")
    }

}
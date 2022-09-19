package cn.nagico.week2.pojo

import cn.nagico.week2.annotation.InitMethod

open class Student : Loadable {
    lateinit var name: String
    var age: Int = 0

    override fun toString(): String {
        return "Student(name='$name', age=$age)"
    }

    @InitMethod
    fun init() {
        name = "Test Student"
        age = 10
        val now = System.currentTimeMillis()
        println("$this init at $now")
    }
}
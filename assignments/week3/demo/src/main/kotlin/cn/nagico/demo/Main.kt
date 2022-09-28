package cn.nagico.demo

import cn.nagico.minispringframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val ctx = ClassPathXmlApplicationContext("applicationContext.xml")
    println(ctx.getBean("bookDao"))
    println(ctx.getBean("bookService"))
}
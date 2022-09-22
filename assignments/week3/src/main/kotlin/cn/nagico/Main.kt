package cn.nagico

import cn.nagico.dao.impl.BookDaoImpl
import cn.nagico.minispringframework.beans.factory.reader.XmlBeanDefinitionReader
import cn.nagico.minispringframework.context.support.ClassPathXmlApplicationContext
import cn.nagico.service.impl.BookServiceImpl

fun main(args: Array<String>) {
    val ctx = ClassPathXmlApplicationContext("applicationContext.xml")
    println(ctx.getBean("bookDao"))
    println(ctx.getBean("bookService"))
}
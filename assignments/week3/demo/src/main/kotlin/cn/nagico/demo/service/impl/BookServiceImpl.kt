package cn.nagico.demo.service.impl

import cn.nagico.demo.dao.BookDao
import cn.nagico.demo.service.BookService

class BookServiceImpl : BookService {
    lateinit var name: String
    lateinit var bookDao: BookDao

    override fun save() {
        println("BookServiceImpl save")
        bookDao.save()
    }

    override fun toString(): String {
        return "BookServiceImpl(name='$name', bookDao=$bookDao)"
    }
}
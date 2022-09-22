package cn.nagico.service.impl

import cn.nagico.dao.BookDao
import cn.nagico.service.BookService

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
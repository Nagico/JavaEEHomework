package cn.nagico.dao.impl

import cn.nagico.dao.BookDao

class BookDaoImpl : BookDao {
    lateinit var name: String
    lateinit var connectUrl: String

    override fun save() {
        println("BookDaoImpl save")
    }

    override fun toString(): String {
        return "BookDaoImpl(name='$name', connectUrl='$connectUrl')"
    }
}
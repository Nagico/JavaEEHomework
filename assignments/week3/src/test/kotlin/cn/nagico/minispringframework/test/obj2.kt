package cn.nagico.minispringframework.test

class obj2 {
    lateinit var name: String
    lateinit var bookDao: obj1

    fun save() {
        println("BookServiceImpl save")
        bookDao.save()
    }

    override fun toString(): String {
        return "BookServiceImpl(name='$name', bookDao=$bookDao)"
    }
}
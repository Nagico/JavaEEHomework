package cn.nagico.minispringframework.test

class obj1() {
    lateinit var name: String
    lateinit var connectUrl: String

    fun save() {
        println("BookDaoImpl save")
    }

    override fun toString(): String {
        return "BookDaoImpl(name='$name', connectUrl='$connectUrl')"
    }
}
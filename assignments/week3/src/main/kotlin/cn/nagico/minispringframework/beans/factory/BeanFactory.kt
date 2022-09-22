package cn.nagico.minispringframework.beans.factory

class BeanFactory {
    private val beanMap = mutableMapOf<String, Any>()

    fun registerBean(name: String, bean: Any) {
        if (name in beanMap) {
            throw BeanFactoryException("Bean $name already exists")
        }
        beanMap[name] = bean
    }

    fun getBean(name: String): Any {
        return beanMap[name] ?: throw RuntimeException("Bean $name not found")
    }

}
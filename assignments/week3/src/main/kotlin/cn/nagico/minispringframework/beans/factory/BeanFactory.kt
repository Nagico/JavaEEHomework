package cn.nagico.minispringframework.beans.factory

import cn.nagico.minispringframework.beans.exceptions.BeanAlreadyExistException
import cn.nagico.minispringframework.beans.exceptions.BeanNotFoundException

class BeanFactory {
    private val beanMap = mutableMapOf<String, Any>()

    fun registerBean(name: String, bean: Any) {
        if (name in beanMap) {
            throw BeanAlreadyExistException("Bean $name already exists")
        }
        beanMap[name] = bean
    }

    fun getBean(name: String): Any {
        return beanMap[name] ?: throw BeanNotFoundException("Bean $name not found")
    }

}
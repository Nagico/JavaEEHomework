package cn.nagico.minispringframework.context

import cn.nagico.minispringframework.beans.factory.BeanFactory

open class ApplicationContext {
    lateinit var beanFactory: BeanFactory

    fun getBean(name: String): Any {
        return beanFactory.getBean(name)
    }
}
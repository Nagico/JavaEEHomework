package cn.nagico.minispringframework.beans.factory

import cn.nagico.minispringframework.beans.exceptions.BeanAlreadyExistException
import cn.nagico.minispringframework.beans.exceptions.BeanNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class BeanFactoryTest {

    @Test
    fun registerBeanSuccess() {
        val beanFactory = BeanFactory()
        assertDoesNotThrow { beanFactory.registerBean("test", "test") }
    }

    @Test
    fun registerBeanExists() {
        val beanFactory = BeanFactory()
        beanFactory.registerBean("test", "test")
        assertThrows<BeanAlreadyExistException> { beanFactory.registerBean("test", "test") }
    }

    @Test
    fun getBeanSuccess() {
        val beanFactory = BeanFactory()
        val obj = arrayListOf("123", "234")
        beanFactory.registerBean("obj", obj)
        assert(beanFactory.getBean("obj") == obj)
    }

    @Test
    fun getBeanNotFound() {
        val beanFactory = BeanFactory()
        assertThrows<BeanNotFoundException> { beanFactory.getBean("obj") }
    }
}
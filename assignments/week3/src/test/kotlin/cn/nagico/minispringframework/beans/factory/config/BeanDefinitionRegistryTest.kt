package cn.nagico.minispringframework.beans.factory.config

import cn.nagico.minispringframework.beans.exceptions.BeanNotFoundException
import cn.nagico.minispringframework.beans.factory.BeanFactory
import cn.nagico.minispringframework.beans.factory.config.exceptions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BeanDefinitionRegistryTest {

    @Test
    fun registerBeanDefinitionsSuccess() {
        val obj1Def = BeanDefinition()
        obj1Def.beanId = "obj1"
        obj1Def.beanClassName = "cn.nagico.minispringframework.test.obj1"
        obj1Def.fields = mapOf("name" to "obj1", "connectUrl" to "jdbc:mysql://localhost:3306/test")
        obj1Def.refs = mapOf()

        val obj2Def = BeanDefinition()
        obj2Def.beanId = "obj2"
        obj2Def.beanClassName = "cn.nagico.minispringframework.test.obj2"
        obj2Def.fields = mapOf("name" to "obj2")
        obj2Def.refs = mapOf("bookDao" to "obj1")

        val factory = BeanFactory()
        val registry = BeanDefinitionRegistry(factory)
        registry.registerBeanDefinitions(listOf(obj1Def, obj2Def))

        val obj1 = factory.getBean("obj1") as cn.nagico.minispringframework.test.obj1
        assert(obj1.name == "obj1")
        assert(obj1.connectUrl == "jdbc:mysql://localhost:3306/test")

        val obj2 = factory.getBean("obj2") as cn.nagico.minispringframework.test.obj2
        assert(obj2.name == "obj2")
        assert(obj2.bookDao == obj1)
    }

    @Test
    fun registerBeanClassNotFound() {
        val obj1Def = BeanDefinition()
        obj1Def.beanId = "obj"
        obj1Def.beanClassName = "cn.nagico.minispringframework.test.obj"
        obj1Def.fields = mapOf()
        obj1Def.refs = mapOf()

        val factory = BeanFactory()
        val registry = BeanDefinitionRegistry(factory)

        assertThrows<BeanDefinitionClassException> { registry.registerBeanDefinitions(listOf(obj1Def)) }
    }

    @Test
    fun registerBeanNoSuchField() {
        val obj1Def = BeanDefinition()
        obj1Def.beanId = "obj"
        obj1Def.beanClassName = "cn.nagico.minispringframework.test.obj3"
        obj1Def.fields = mapOf("name" to "12.3")
        obj1Def.refs = mapOf()

        val factory = BeanFactory()
        val registry = BeanDefinitionRegistry(factory)

        assertThrows<BeanDefinitionFieldException> { registry.registerBeanDefinitions(listOf(obj1Def)) }
    }

    @Test
    fun registerBeanInjectError() {
        val obj1Def = BeanDefinition()
        obj1Def.beanId = "obj"
        obj1Def.beanClassName = "cn.nagico.minispringframework.test.obj3"
        obj1Def.fields = mapOf("id" to "12.3")
        obj1Def.refs = mapOf()

        val factory = BeanFactory()
        val registry = BeanDefinitionRegistry(factory)

        val ex = assertThrows<BeanDefinitionInjectException> { registry.registerBeanDefinitions(listOf(obj1Def)) }
        assert(ex.cause is NumberFormatException)
    }

    @Test
    fun registerBeanInvalidRef() {
        val obj2Def = BeanDefinition()
        obj2Def.beanId = "obj2"
        obj2Def.beanClassName = "cn.nagico.minispringframework.test.obj2"
        obj2Def.fields = mapOf("name" to "obj2")
        obj2Def.refs = mapOf("bookDao" to "obj1")

        val factory = BeanFactory()
        val registry = BeanDefinitionRegistry(factory)

        val ex = assertThrows<BeanDefinitionRefException> { registry.registerBeanDefinitions(listOf(obj2Def)) }
        assert(ex.cause is BeanNotFoundException)
    }
}
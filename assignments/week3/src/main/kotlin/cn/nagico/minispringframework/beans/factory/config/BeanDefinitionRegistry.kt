package cn.nagico.minispringframework.beans.factory.config

import cn.nagico.minispringframework.beans.factory.BeanFactory
import java.lang.reflect.Field

class BeanDefinitionRegistry(private val beanFactory: BeanFactory) {

    fun registerBeanDefinitions(beanDefinitions: List<BeanDefinition>) {
        beanDefinitions.forEach { registerBeanDefinitionField(it) }
        beanDefinitions.forEach { registerBeanDefinitionRef(it) }
    }

    private fun registerBeanDefinitionRef(it: BeanDefinition) {
        try {
            val bean = beanFactory.getBean(it.beanId)
            it.refs.forEach { ref ->
                val field = bean.javaClass.getDeclaredField(ref.key)
                field.isAccessible = true
                field.set(bean, beanFactory.getBean(field.name))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun registerBeanDefinitionField(it: BeanDefinition) {
        try {
            val loadClass = Class.forName(it.beanClassName)
            val instance = loadClass.getDeclaredConstructor().newInstance()

            for (property in it.fields) {
                val field = loadClass.getDeclaredField(property.key)
                field.isAccessible = true
                injectField(instance, field, property.value)
            }

            beanFactory.registerBean(it.beanId, instance)
        }
        catch (ex: Exception) {
            throw BeanDefinitionException("Failed to register bean definition for class [${it.beanClassName}]")
        }
    }

    private fun injectField(instance: Any, field: Field, value: String) {
        when (field.type) {
            String::class.java -> {
                field.set(instance, value)
            }
            Int::class.java -> {
                field.set(instance, value.toInt())
            }
            Boolean::class.java -> {
                field.set(instance, value.toBoolean())
            }
            Long::class.java -> {
                field.set(instance, value.toLong())
            }
            Float::class.java -> {
                field.set(instance, value.toFloat())
            }
            Double::class.java -> {
                field.set(instance, value.toDouble())
            }
            Short::class.java -> {
                field.set(instance, value.toShort())
            }
            Byte::class.java -> {
                field.set(instance, value.toByte())
            }
            Char::class.java -> {
                field.set(instance, value.toCharArray()[0])
            }
            else -> {
                throw BeanDefinitionException("Unsupported field type [${field.type.name}]")
            }
        }
    }


}

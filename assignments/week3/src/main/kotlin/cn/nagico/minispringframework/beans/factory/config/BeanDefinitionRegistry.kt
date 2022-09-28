package cn.nagico.minispringframework.beans.factory.config

import cn.nagico.minispringframework.beans.factory.BeanFactory
import cn.nagico.minispringframework.beans.factory.config.exceptions.BeanDefinitionClassException
import cn.nagico.minispringframework.beans.factory.config.exceptions.BeanDefinitionFieldException
import cn.nagico.minispringframework.beans.factory.config.exceptions.BeanDefinitionInjectException
import cn.nagico.minispringframework.beans.factory.config.exceptions.BeanDefinitionRefException
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
                field.set(bean, beanFactory.getBean(ref.value))
            }
        } catch (ex: Exception) {
            throw BeanDefinitionRefException("register bean definition ref error", ex)
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
        catch (ex: ClassNotFoundException) {
            throw BeanDefinitionClassException("Failed to find class ${it.beanClassName}", ex)
        }
        catch (ex: NoSuchFieldException) {
            throw BeanDefinitionFieldException("Failed to register bean definition for class ${it.beanClassName}, $ex", ex)
        }
    }

    private fun injectField(instance: Any, field: Field, value: String) {
        try {
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
                    throw BeanDefinitionInjectException("Unsupported field type [${field.type.name}]")
                }
            }
        }
        catch (ex: BeanDefinitionInjectException) {
            throw ex
        }
        catch (ex: Exception) {
            throw BeanDefinitionInjectException("Failed to inject field [${field.name}] for class [${instance.javaClass.name}]", ex)
        }
    }

}

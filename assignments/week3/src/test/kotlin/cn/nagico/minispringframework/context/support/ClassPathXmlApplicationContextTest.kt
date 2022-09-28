package cn.nagico.minispringframework.context.support

import cn.nagico.minispringframework.beans.exceptions.BeanNotFoundException
import cn.nagico.minispringframework.beans.factory.config.exceptions.*
import cn.nagico.minispringframework.beans.factory.reader.BeanReaderException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ClassPathXmlApplicationContextTest {

    @Test
    fun initSuccess() {
        val context = ClassPathXmlApplicationContext("success.xml")
        val obj1 = context.getBean("bookDao") as cn.nagico.minispringframework.test.obj1
        assertEquals("mysql", obj1.name)
        assertEquals("jdbc:mysql://127.0.0.1/test_db?useUnicode=true&characterEncoding=UTF-8", obj1.connectUrl)

        val obj2 = context.getBean("bookService") as cn.nagico.minispringframework.test.obj2
        assertEquals("first_service", obj2.name)
        assertEquals(obj1, obj2.bookDao)
    }

    @Test
    fun initFileNotFound() {
        val ex = assertThrows<BeanReaderException> {
            ClassPathXmlApplicationContext("not_found.xml")
        }
        assertEquals("Error loading xml file not_found.xml", ex.message)
    }

    @Test
    fun initWrongClass() {
        val ex = assertThrows<BeanDefinitionClassException> {
            ClassPathXmlApplicationContext("wrong_class.xml")
        }
        assertEquals("Failed to find class cn.nagico.minispringframework.test.obj", ex.message)
    }

    @Test
    fun initInvalidFieldName() {
        assertThrows<BeanDefinitionFieldException> {
            ClassPathXmlApplicationContext("invalid_field_name.xml")
        }
    }

    @Test
    fun initInvalidFieldType() {
        assertThrows<BeanDefinitionInjectException> {
            ClassPathXmlApplicationContext("invalid_field_type.xml")
        }
    }

    @Test
    fun initInvalidRef() {
        val ex = assertThrows<BeanDefinitionRefException> {
            ClassPathXmlApplicationContext("invalid_ref.xml")
        }
        assert(ex.cause is BeanNotFoundException)
    }
}
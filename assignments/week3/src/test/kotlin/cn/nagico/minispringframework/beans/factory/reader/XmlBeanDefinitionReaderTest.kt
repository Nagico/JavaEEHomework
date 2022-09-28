package cn.nagico.minispringframework.beans.factory.reader

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.NullPointerException

class XmlBeanDefinitionReaderTest {

    @Test
    fun loadBeanDefinitionsSuccess() {
        val objs = XmlBeanDefinitionReader().loadBeanDefinitions("success.xml")
        assert(objs.size == 2)

        assert(objs[0].beanId == "bookDao")
        assert(objs[0].beanClassName == "cn.nagico.minispringframework.test.obj1")
        assert(objs[0].fields.size == 2)
        assert(objs[0].fields["name"] == "mysql")
        assert(objs[0].fields["connectUrl"] == "jdbc:mysql://127.0.0.1/test_db?useUnicode=true&characterEncoding=UTF-8")
        assert(objs[0].refs.isEmpty())

        assert(objs[1].beanId == "bookService")
        assert(objs[1].beanClassName == "cn.nagico.minispringframework.test.obj2")
        assert(objs[1].fields.size == 1)
        assert(objs[1].fields["name"] == "first_service")
        assert(objs[1].refs.size == 1)
        assert(objs[1].refs["bookDao"] == "bookDao")
    }

    @Test
    fun loadBeanDefinitionsNotFound() {
        val ex = assertThrows<BeanReaderException> {
            XmlBeanDefinitionReader().loadBeanDefinitions("not_found.xml")
        }
        assert(ex.cause is NullPointerException)
        assert(ex.message == "Error loading xml file not_found.xml")
    }

    @Test
    fun loadBeanDefinitionsInvalid() {
        val ex = assertThrows<BeanReaderException> {
            XmlBeanDefinitionReader().loadBeanDefinitions("definition_invalid.xml")
        }
        assert(ex.cause is NullPointerException)
    }
}
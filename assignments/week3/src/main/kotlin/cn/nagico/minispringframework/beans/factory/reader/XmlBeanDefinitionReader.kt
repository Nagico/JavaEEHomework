package cn.nagico.minispringframework.beans.factory.reader

import cn.nagico.minispringframework.beans.factory.config.BeanDefinition
import org.dom4j.io.SAXReader
import java.io.File
import java.util.*


open class XmlBeanDefinitionReader {
    private fun loadXMLDocument(configLocation: String): org.dom4j.Document {
        try {
            val xmlPath = Thread.currentThread().contextClassLoader.getResource(configLocation)?.path!!
            val saxReader = SAXReader()
            return saxReader.read(File(xmlPath))
        } catch (ex: Exception) {
            throw BeanReaderException("Error loading xml file")
        }
    }

    private fun loadBeanList(document: org.dom4j.Document): List<org.dom4j.Element> {
        val root = document.rootElement
        return root.elements("bean")
    }

    private fun loadPropertyList(beanElement: org.dom4j.Element): Pair<Map<String, String>, Map<String, String>> {
        val fields = mutableMapOf<String, String>()
        val refs = mutableMapOf<String, String>()
        val propertyList = beanElement.elements("property")
        for (property in propertyList) {
            val name = property.attributeValue("name")
            if (property.attributeValue("ref") != null) {
                refs[name] = property.attributeValue("ref")
            } else {
                fields[name] = property.attributeValue("value")
            }
        }
        return Pair(fields, refs)
    }

    private fun loadBeanDefinition(beanElement: org.dom4j.Element): BeanDefinition {
        try {
            val beanDefinition = BeanDefinition()
            beanDefinition.beanId = beanElement.attributeValue("id")
            beanDefinition.beanClassName = beanElement.attributeValue("class")
            val properties = loadPropertyList(beanElement)
            beanDefinition.fields = properties.first
            beanDefinition.refs = properties.second

            return beanDefinition
        }
        catch (ex: Exception) {
            throw BeanReaderException("Error loading bean definitions")
        }

    }

    open fun loadBeanDefinitions(configLocation: String): List<BeanDefinition> {
        val document = loadXMLDocument(configLocation)
        val beansElement = loadBeanList(document)

        return beansElement.map { loadBeanDefinition(it) }
    }

}

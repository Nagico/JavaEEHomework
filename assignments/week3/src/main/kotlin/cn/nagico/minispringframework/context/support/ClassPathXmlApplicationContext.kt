package cn.nagico.minispringframework.context.support

import cn.nagico.minispringframework.beans.factory.BeanFactory
import cn.nagico.minispringframework.beans.factory.config.BeanDefinitionRegistry
import cn.nagico.minispringframework.beans.factory.reader.XmlBeanDefinitionReader
import cn.nagico.minispringframework.context.ApplicationContext

class ClassPathXmlApplicationContext(configLocation: String) : ApplicationContext() {
    init {
        beanFactory = BeanFactory()

        val beanDefinitions = XmlBeanDefinitionReader().loadBeanDefinitions(configLocation)

        BeanDefinitionRegistry(beanFactory).registerBeanDefinitions(beanDefinitions)
    }
}
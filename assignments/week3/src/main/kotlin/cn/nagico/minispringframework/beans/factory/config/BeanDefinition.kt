package cn.nagico.minispringframework.beans.factory.config


class BeanDefinition {
    lateinit var beanId: String
    lateinit var beanClassName: String
    lateinit var fields: Map<String, String>
    lateinit var refs: Map<String, String>
}
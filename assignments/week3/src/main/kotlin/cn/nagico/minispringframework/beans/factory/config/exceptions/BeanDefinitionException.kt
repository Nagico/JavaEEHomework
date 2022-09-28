package cn.nagico.minispringframework.beans.factory.config.exceptions

import cn.nagico.minispringframework.beans.exceptions.BeanFactoryException

open class BeanDefinitionException(msg: String, cause: Throwable? = null) : BeanFactoryException(msg, cause)
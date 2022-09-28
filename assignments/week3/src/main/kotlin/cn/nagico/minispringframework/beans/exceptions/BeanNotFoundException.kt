package cn.nagico.minispringframework.beans.exceptions

open class BeanNotFoundException(msg: String, cause: Throwable? = null) : BeanFactoryException(msg, cause)
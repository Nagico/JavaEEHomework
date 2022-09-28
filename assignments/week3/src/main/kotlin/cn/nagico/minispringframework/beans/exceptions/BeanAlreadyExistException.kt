package cn.nagico.minispringframework.beans.exceptions

open class BeanAlreadyExistException(msg: String, cause: Throwable? = null) : BeanFactoryException(msg, cause)
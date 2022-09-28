package cn.nagico.minispringframework.beans.factory.reader

import cn.nagico.minispringframework.beans.exceptions.BeanFactoryException

open class BeanReaderException(msg: String, cause: Throwable? = null) : BeanFactoryException(msg, cause)
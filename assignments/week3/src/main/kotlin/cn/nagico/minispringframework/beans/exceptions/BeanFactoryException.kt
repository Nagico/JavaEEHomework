package cn.nagico.minispringframework.beans.exceptions

import cn.nagico.minispringframework.beans.BeanException

open class BeanFactoryException(msg: String, cause: Throwable? = null) : BeanException(msg, cause)
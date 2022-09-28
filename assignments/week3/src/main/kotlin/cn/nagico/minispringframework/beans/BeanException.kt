package cn.nagico.minispringframework.beans

import cn.nagico.minispringframework.MiniSpringException

open class BeanException(msg: String, cause: Throwable? = null) : MiniSpringException(msg, cause)
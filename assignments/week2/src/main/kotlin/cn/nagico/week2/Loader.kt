package cn.nagico.week2

import cn.nagico.week2.annotation.InitMethod
import cn.nagico.week2.pojo.Loadable
import java.io.File
import java.util.*
import kotlin.reflect.full.isSuperclassOf

class Loader {
    companion object {
        val objectList = mutableListOf<Loadable>()

        /**
         * 加载配置文件
         * @param filename: 配置文件名
         * @return: 配置文件对象
         * @exception NoSuchFileException: 文件不存在
         */
        fun loadProperties(filename: String): Properties {
            try {
                val properties = Properties()
                properties.load(Thread.currentThread().contextClassLoader.getResourceAsStream(filename))

                return properties
            }
            catch (e: NullPointerException) {
                throw NoSuchFileException(File(filename))
            }

        }

        /**
         * 初始化bootstrap对象
         * @param className: 类名
         * @return bootstrap对象
         * @exception NullPointerException: bootstrapClass配置不存在
         * @exception ClassNotFoundException: 配置文件中的类不存在
         * @exception ClassFormatError: 配置文件中的类不是Loadable的子类
         */
        fun initBootstrapClass(className: String?): Loadable {
            val bootstrapClass = Class.forName(className)

            if (!Loadable::class.isSuperclassOf(bootstrapClass.kotlin)) {
                throw ClassFormatError("Bootstrap class must implement Loadable interface")
            }

            val instance = bootstrapClass.getDeclaredConstructor().newInstance()

            for (function in bootstrapClass.declaredMethods) {
                if (function.isAnnotationPresent(InitMethod::class.java)) {
                    function.invoke(instance)
                }
            }

            objectList.add(instance as Loadable)

            return instance
        }
    }
}
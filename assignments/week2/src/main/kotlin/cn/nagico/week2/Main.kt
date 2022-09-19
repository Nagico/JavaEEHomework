package cn.nagico.week2


fun main(args: Array<String>) {
    val filename = args.getOrNull(0) ?: "week2.properties"
    val propertyKey = args.getOrNull(1) ?: "bootstrapClass"

    try {
        val prop = Loader.loadProperties(filename)
        val bootstrapClass = prop.getProperty(propertyKey)
        val instance = Loader.initBootstrapClass(bootstrapClass)
        println(instance)
    }
    catch (e: NoSuchFileException) {
        println("No property file found")
    }
    catch (e: NullPointerException) {
        println("No bootstrap class field found")
    }
    catch (e: ClassNotFoundException) {
        println("No class found")
    }
    catch (e: ClassFormatError) {
        println("Bootstrap class must implement Loadable interface")
    }
}
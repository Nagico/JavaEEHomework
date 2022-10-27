package cn.nagico.week6

import com.baomidou.mybatisplus.generator.FastAutoGenerator
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Consumer


fun main(args: Array<String>) {
    generator()
}

// kotlin 代码生成器
fun generator() {

    //快速构建 生成mybatis代码
    FastAutoGenerator.create(
        "jdbc:mysql://vps.coyangjr.cn:3306/order_system_spring?characterEncoding=utf8&serverTimezone=GMT%2B8",
        "springboot",
        "springboot"
    )
        .globalConfig(Consumer {//全局配置
            it.author("nagico")
                .enableKotlin()
                .disableOpenDir()
                //开启 swagger 模式: 需要引入swagger包
                //.enableSwagger()
                .outputDir("src/main/kotlin")
        })
        //包配置
        .packageConfig(Consumer {
            //设置父包名
            it.parent("cn.nagico.week6")
                .entity("model")
                .mapper("dao")
            //.moduleName("system") //设置父模块名称
            //mapperXml 生成路径 绝对路径
            //.pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\new_pztWorkpace\\springboot-kotlin\\user-api\\src\\main\\resources\\userapi"))
        })
        // 策略配置
        /*.strategyConfig(Consumer {
            // 生成表， 可以另写一个使用Scanner 接入一串输入的表
            it.addInclude("ts_user_role", "ts_menu", "ts_role", "ts_role_menu", "ts_user")
                //设置过滤表前缀
                //.addFieldPrefix("ts")
                // 设置实体策略
                .entityBuilder()
                //开启 lombok
                .enableLombok()

        })*/
        //交互手动输入
        .strategyConfig(BiConsumer { scanner, builder ->
            builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))!!)
                .entityBuilder().enableLombok().build()
        })
        //使用Freemarker引擎模板，默认的是Velocity引擎模板
        .templateEngine(FreemarkerTemplateEngine())
        // 执行
        .execute()
}

// 处理 all 情况
fun getTables(tables: String): List<String?>? {
    return if ("all" == tables) Collections.emptyList() else tables.split(",")
}

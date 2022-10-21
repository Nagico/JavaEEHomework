package cn.nagico.week5

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class OrderSystemApplication

fun main(args: Array<String>) {
	runApplication<OrderSystemApplication>(*args)
}

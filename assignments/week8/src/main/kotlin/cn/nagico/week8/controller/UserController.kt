package cn.nagico.week8.controller

import cn.nagico.week8.model.user.User
import cn.nagico.week8.model.user.UserDetail
import cn.nagico.week8.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/users")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @GetMapping("")
    fun getCurrentUser() : User {
        val user = SecurityContextHolder.getContext().authentication.principal as UserDetail
        return userService.getUserById(user.getId())
    }

}
package cn.nagico.week8.controller


import cn.nagico.week8.util.exception.ApiException
import cn.nagico.week8.util.response.ApiResponseType
import cn.nagico.week8.model.user.User
import cn.nagico.week8.model.user.UserLoginVo
import cn.nagico.week8.security.JwtTokenUtil
import cn.nagico.week8.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/auth")
class AuthenticationController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/login")
    fun login(@RequestBody user: User): UserLoginVo {
        return try {
            //使用认证管理器认证用户
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(user.username, user.password)
            )
            //认证通过后，生成token返回
            val userDetails = userService.getUserDetailByName(user.username!!)
            val token = jwtTokenUtil.generateToken(userDetails!!)
            UserLoginVo(userDetails.getId(), userDetails.username, userDetails.getRolesList(), token)
        } catch (e: BadCredentialsException) {
            throw ApiException(ApiResponseType.USER_LOGIN_FAILED, e)
        }
    }
}
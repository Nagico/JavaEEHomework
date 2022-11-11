package cn.nagico.week8.security

import cn.nagico.week8.util.annotation.Slf4j
import cn.nagico.week8.util.annotation.Slf4j.Companion.logger
import cn.nagico.week8.util.response.ApiResponse
import cn.nagico.week8.util.response.ApiResponseType
import com.alibaba.fastjson.JSON
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
class AuthenticationExceptionHandler : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val res = ApiResponse.fail<Nothing>(ApiResponseType.NOT_LOGIN)
        response.contentType = "application/json;charset=UTF-8"
        response.status = res.httpStatus.value()
        response.writer.write(JSON.toJSONString(res))
    }

}

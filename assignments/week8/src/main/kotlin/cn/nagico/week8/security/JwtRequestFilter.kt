package cn.nagico.week8.security

import cn.nagico.week8.service.UserService
import cn.nagico.week8.util.exception.ApiException
import cn.nagico.week8.util.response.ApiResponseType
import io.jsonwebtoken.Claims
import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (Strings.isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }
        try {
            val token = header.substring(7)
            //解析token，如果token不合法会抛出异常
            val claims: Claims = jwtTokenUtil.getClaimFromToken(token)
            //验证声明
            val userDetails = userService.getUserDetailById(claims.subject.toLong())
            if (userDetails != null && jwtTokenUtil.validateClaim(claims, userDetails)) {
                //创建一个身份令牌放入context中，后面的过滤器可以使用
                val authenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        } catch (e: Exception) {
            //解析token时产生的异常记入日志，不用抛出。因为后续的过滤器没有身份信息，会返回认证错误
            logger.warn(e)
        }
        chain.doFilter(request, response)
    }
}
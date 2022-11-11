package cn.nagico.week8.security

import cn.nagico.week8.util.annotation.Slf4j.Companion.logger
import cn.nagico.week8.util.exception.ApiException
import cn.nagico.week8.util.response.ApiResponseType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig {
    @Autowired
    lateinit var  jwtRequestFilter //注入JWT过滤器Bean
            : JwtRequestFilter

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    /**
     * 使用HttpSecurity来配置认证和授权
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable() //关闭csrf过滤器
        http.authorizeRequests()
            .antMatchers("/auth/**").permitAll() //login接口直接放行
            .anyRequest().authenticated() //其他接口需要认证
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.exceptionHandling()
            .authenticationEntryPoint(AuthenticationExceptionHandler())
        //添加jwtRequestFilter过滤器到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    /**
     * 密码加密的Bean
     *
     * @return
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder() //BCrypt加密算法
    }
}
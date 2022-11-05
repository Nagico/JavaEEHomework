package cn.nagico.week7.util.aspect

import cn.nagico.week7.model.log.ExceptionLogDto
import cn.nagico.week7.model.log.RequestLog
import cn.nagico.week7.service.IExceptionLogService
import cn.nagico.week7.service.IRequestLogService
import cn.nagico.week7.util.annotation.Slf4j
import cn.nagico.week7.util.annotation.Slf4j.Companion.logger
import cn.nagico.week7.util.exception.ApiException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.concurrent.thread


@Aspect
@Component
@Slf4j
open class LogAspect {
    @Autowired
    lateinit var requestLogService: IRequestLogService
    @Autowired
    lateinit var exceptionLogService: IExceptionLogService

    @Around("execution(* cn.nagico.week7.controller.*.*(..)) && " +
            "!execution(* cn.nagico.week7.controller.LogController.*(..))")
    fun aroundController(joinPoint: ProceedingJoinPoint): Any? {
        logger.info("Controller: ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}")
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        val timeSt = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val timeEd = System.currentTimeMillis()

        thread {
            val log = RequestLog()
            prepareRequestLog(log, joinPoint, request)
            log.time = (timeEd - timeSt) / 1000.0
            log.success = 1
            log.createTime = LocalDateTime.now()
            requestLogService.addRequestLog(log)
        }

        return result
    }

    @AfterThrowing(pointcut = "execution(* cn.nagico.week7.controller.*.*(..))" +
            " && !execution(* cn.nagico.week7.controller.LogController.*(..))",
        throwing = "ex")
    fun afterThrowingController(joinPoint: JoinPoint, ex: Throwable) {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        thread {
            val log = ExceptionLogDto()
            prepareRequestLog(log, joinPoint, request)
            log.time = null
            log.success = 0
            log.createTime = LocalDateTime.now()
            log.classType = ex::class.java.name
            if (ex is ApiException) {
                log.exceptionCode = ex.code
                log.exceptionType = ex.type.toString()
            }
            log.exceptionMsg = ex.message
            log.exceptionStack = ex.stackTraceToString()

            exceptionLogService.addExceptionLog(log)
        }
    }

    private fun prepareRequestLog(
        log: RequestLog,
        joinPoint: JoinPoint,
        request: HttpServletRequest
    ) {
        log.controllerMethod = "${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}"
        log.path = request.requestURI
        log.method = request.method
        log.ip = getIpAddr(request)

        val headerNames: Enumeration<String> = request.headerNames
        val headers = mutableMapOf<String, String>()
        while (headerNames.hasMoreElements()) {
            val key = headerNames.nextElement()
            headers[key] = request.getHeader(key)
        }
        log.header = headers.toString()
    }

    /**
     * 获取真实ip地址,不返回内网地址
     *
     * @param request
     * @return
     */
    fun getIpAddr(request: HttpServletRequest): String {
        //目前则是网关ip
        var ip = request.getHeader("X-Real-IP")
        if (ip != null && "" != ip && !"unknown".equals(ip, ignoreCase = true)) {
            return ip
        }
        ip = request.getHeader("X-Forwarded-For")
        return if (ip != null && "" != ip && !"unknown".equals(ip, ignoreCase = true)) {
            val index = ip.indexOf(',')
            if (index != -1) {
                //只获取第一个值
                ip.substring(0, index)
            } else {
                ip
            }
        } else {
            //取不到真实ip则返回空，不能返回内网地址。
            request.remoteAddr
        }
    }

}
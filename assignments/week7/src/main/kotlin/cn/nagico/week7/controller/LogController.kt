package cn.nagico.week7.controller;

import cn.nagico.week7.model.log.ExceptionLogDto
import cn.nagico.week7.model.log.LogStatusDto
import cn.nagico.week7.model.log.RequestLog
import cn.nagico.week7.service.IExceptionLogService
import cn.nagico.week7.service.IRequestLogService
import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nagico
 * @since 2022-11-04
 */
@RestController
@RequestMapping("/logs")
class LogController {
    @Autowired
    lateinit var requestLogService: IRequestLogService
    @Autowired
    lateinit var exceptionLogService: IExceptionLogService

    @GetMapping("/requests")
    fun getRequestLogList(
        path: String?,
        method: String?,
        ip: String?,
        controllerMethod: String?,
        success: Int?,
        @RequestParam(defaultValue = "1") page: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<RequestLog> {
        val condition = mutableMapOf<String, Any>()
        path?.let { condition["path"] = it }
        method?.let { condition["method"] = it }
        ip?.let { condition["ip"] = it }
        controllerMethod?.let { condition["controllerMethod"] = it }
        success?.let { condition["success"] = it }
        return requestLogService.queryRequestLogList(condition, page, pageSize)
    }

    @GetMapping("/requests/{id}")
    fun getRequestLog(@PathVariable id: Long): RequestLog {
        return requestLogService.getRequestLog(id)
    }

    @GetMapping("/exceptions")
    fun getExceptionLogList(
        path: String?,
        method: String?,
        ip: String?,
        controllerMethod: String?,
        success: Int?,
        exceptionCode: String?,
        exceptionType: String?,
        className: String?,
        @RequestParam(defaultValue = "1") page: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<ExceptionLogDto> {
        val condition = mutableMapOf<String, Any>()
        path?.let { condition["path"] = it }
        method?.let { condition["method"] = it }
        ip?.let { condition["ip"] = it }
        controllerMethod?.let { condition["controllerMethod"] = it }
        success?.let { condition["success"] = it }
        exceptionCode?.let { condition["exceptionCode"] = it }
        exceptionType?.let { condition["exceptionType"] = it }
        className?.let { condition["className"] = it }
        return exceptionLogService.queryExceptionLogList(condition, page, pageSize)
    }

    @GetMapping("/exceptions/{id}")
    fun getExceptionLog(@PathVariable id: Long): ExceptionLogDto {
        return exceptionLogService.getExceptionLog(id)
    }

    @GetMapping("/status")
    fun getStatus(
        controllerMethod: String?,
        @RequestParam(defaultValue = "1") page: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<LogStatusDto> {
        return requestLogService.queryStatus(controllerMethod, page, pageSize)
    }
}

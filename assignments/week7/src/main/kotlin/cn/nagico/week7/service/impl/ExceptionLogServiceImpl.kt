package cn.nagico.week7.service.impl;

import cn.nagico.week7.model.log.ExceptionLog;
import cn.nagico.week7.dao.ExceptionLogMapper;
import cn.nagico.week7.model.log.ExceptionLogDto
import cn.nagico.week7.model.log.RequestLog
import cn.nagico.week7.service.IExceptionLogService;
import cn.nagico.week7.service.IRequestLogService
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nagico
 * @since 2022-11-04
 */
@Service
open class ExceptionLogServiceImpl : ServiceImpl<ExceptionLogMapper, ExceptionLog>(), IExceptionLogService {
    @Autowired
    lateinit var requestLogService: IRequestLogService

    override fun queryExceptionLogList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<ExceptionLogDto> {
        val pageWrapper = Page<ExceptionLogDto>(page, pageSize).addOrder(OrderItem("id", false))

        val queryWrapper = KtQueryWrapper(ExceptionLogDto::class.java)
        for (key in condition.keys) {
            when (key) {
                "path" -> queryWrapper.like(RequestLog::path, condition["path"])
                "method" -> queryWrapper.eq(RequestLog::method, condition["method"])
                "ip" -> queryWrapper.like(RequestLog::ip, condition["ip"])
                "controllerMethod" -> queryWrapper.like(RequestLog::controllerMethod, condition["controllerMethod"])
                "success" -> queryWrapper.eq(RequestLog::success, condition["success"])
                "exceptionCode" -> queryWrapper.eq(ExceptionLog::exceptionCode, condition["exceptionCode"])
                "exceptionType" -> queryWrapper.eq(ExceptionLog::exceptionType, condition["exceptionType"])
                "classType" -> queryWrapper.like(ExceptionLog::classType, condition["classType"])
            }
        }
        return baseMapper.queryExceptionLog(pageWrapper, queryWrapper)
    }

    override fun getExceptionLog(id: Long): ExceptionLogDto {
        if (baseMapper.selectById(id) == null) {
            throw Exception("ExceptionLog not found")
        }

        return baseMapper.getExceptionLogById(id)
    }

    override fun addExceptionLog(exceptionLog: ExceptionLogDto): ExceptionLogDto {
        requestLogService.addRequestLog(exceptionLog)

        baseMapper.insert(ExceptionLog(exceptionLog))

        return exceptionLog
    }
}

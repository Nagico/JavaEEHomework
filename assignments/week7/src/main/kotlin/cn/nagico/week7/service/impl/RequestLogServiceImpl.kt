package cn.nagico.week7.service.impl;

import cn.nagico.week7.model.log.RequestLog;
import cn.nagico.week7.dao.RequestLogMapper;
import cn.nagico.week7.model.log.LogStatusDto
import cn.nagico.week7.service.IRequestLogService;
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
open class RequestLogServiceImpl : ServiceImpl<RequestLogMapper, RequestLog>(), IRequestLogService {
    override fun queryRequestLogList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<RequestLog> {
        val pageWrapper = Page<RequestLog>(page, pageSize).addOrder(OrderItem("id", false))

        val queryWrapper = KtQueryWrapper(RequestLog::class.java)

        for (key in condition.keys) {
            when (key) {
                "path" -> queryWrapper.like(RequestLog::path, condition["path"])
                "method" -> queryWrapper.eq(RequestLog::method, condition["method"])
                "ip" -> queryWrapper.like(RequestLog::ip, condition["ip"])
                "controllerMethod" -> queryWrapper.like(RequestLog::controllerMethod, condition["controllerMethod"])
                "success" -> queryWrapper.eq(RequestLog::success, condition["success"])
            }
        }
        return baseMapper.selectPage(pageWrapper, queryWrapper)
    }

    override fun getRequestLog(id: Long): RequestLog {
        if (baseMapper.selectById(id) == null) {
            throw Exception("RequestLog not found")
        }

        return baseMapper.selectById(id)
    }

    override fun addRequestLog(requestLog: RequestLog): RequestLog {
        baseMapper.insert(requestLog)
        return getRequestLog(requestLog.id!!)
    }

    override fun queryStatus(controllerMethod: String?, page: Long, pageSize: Long): IPage<LogStatusDto> {
        val pageWrapper = Page<LogStatusDto>(page, pageSize).addOrder(OrderItem("controller_method", true))

        return if (controllerMethod == null) {
            baseMapper.getStatusAll(pageWrapper)
        } else {
            baseMapper.queryStatusByControllerMethod(controllerMethod, pageWrapper)
        }
    }
}

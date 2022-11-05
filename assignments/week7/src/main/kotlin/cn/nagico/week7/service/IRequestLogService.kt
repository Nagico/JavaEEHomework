package cn.nagico.week7.service;

import cn.nagico.week7.model.log.LogStatusDto
import cn.nagico.week7.model.log.RequestLog;
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nagico
 * @since 2022-11-04
 */
interface IRequestLogService : IService<RequestLog> {
    fun queryRequestLogList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<RequestLog>
    fun getRequestLog(id: Long): RequestLog
    fun addRequestLog(requestLog: RequestLog): RequestLog
    fun queryStatus(controllerMethod: String?, page: Long, pageSize: Long): IPage<LogStatusDto>
}

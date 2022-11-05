package cn.nagico.week7.service;

import cn.nagico.week7.model.log.ExceptionLog;
import cn.nagico.week7.model.log.ExceptionLogDto
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
interface IExceptionLogService : IService<ExceptionLog> {
    fun queryExceptionLogList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<ExceptionLogDto>
    fun getExceptionLog(id: Long): ExceptionLogDto
    fun addExceptionLog(exceptionLog: ExceptionLogDto): ExceptionLogDto
}

package cn.nagico.week7.dao;

import cn.nagico.week7.model.log.LogStatusDto
import cn.nagico.week7.model.log.RequestLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nagico
 * @since 2022-11-04
 */
@Mapper
interface RequestLogMapper : BaseMapper<RequestLog> {
    @Select("select controller_method, " +
                "count(*) as count, " +
                "count(if(success=0,create_time,null)) as failed_count, " +
                "avg(time) as avg_time, " +
                "max(time) as max_time, " +
                "min(time) as min_time " +
            "from request_log as r " +
            "group by controller_method")
    fun getStatusAll(page: IPage<LogStatusDto>): IPage<LogStatusDto>

    @Select("select controller_method, " +
                "count(*) as count, " +
                "count(if(success=0,time,null)) as failed_count, " +
                "avg(time) as avg_time, " +
                "max(time) as max_time, " +
                "min(time) as min_time " +
            "from request_log as r " +
            "where controller_method like '%' #{controllerMethod} '%'" +
            "group by controller_method")
    fun queryStatusByControllerMethod(controllerMethod: String, page: IPage<LogStatusDto>): IPage<LogStatusDto>
}

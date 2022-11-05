package cn.nagico.week7.dao;

import cn.nagico.week7.model.log.ExceptionLog;
import cn.nagico.week7.model.log.ExceptionLogDto
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
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
interface ExceptionLogMapper : BaseMapper<ExceptionLog> {
    @Select("select * from exception_log as e natural join request_log as r \${ew.customSqlSegment}")
    fun queryExceptionLog(page: IPage<ExceptionLogDto>, @Param(Constants.WRAPPER) wrapper: KtQueryWrapper<ExceptionLogDto>): IPage<ExceptionLogDto>

    @Select("select * from exception_log as e natural join request_log as r where e.id = #{id}")
    fun getExceptionLogById(id: Long): ExceptionLogDto
}

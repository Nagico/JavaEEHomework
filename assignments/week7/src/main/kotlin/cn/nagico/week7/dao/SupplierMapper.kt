package cn.nagico.week7.dao;

import cn.nagico.week7.model.supplier.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
@Mapper
interface SupplierMapper : BaseMapper<Supplier>

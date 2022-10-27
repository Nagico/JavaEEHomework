package cn.nagico.week6.service.impl;

import cn.nagico.week6.model.supplier.Supplier;
import cn.nagico.week6.dao.SupplierMapper;
import cn.nagico.week6.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
@Service
open class SupplierServiceImpl : ServiceImpl<SupplierMapper, Supplier>(), ISupplierService {

}

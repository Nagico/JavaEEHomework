package cn.nagico.week7.service;

import cn.nagico.week7.model.supplier.Supplier;
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
interface ISupplierService : IService<Supplier> {
    fun querySupplierList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<Supplier>

    fun getSupplier(id: Long): Supplier

    fun addSupplier(supplier: Supplier): Supplier

    fun removeSupplier(id: Long)

    fun updateSupplier(id: Long, supplier: Supplier): Supplier
}

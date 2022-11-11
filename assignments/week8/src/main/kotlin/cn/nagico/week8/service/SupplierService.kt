package cn.nagico.week8.service

import cn.nagico.week8.model.supplier.Supplier
import cn.nagico.week8.model.supplier.SupplierInfo
import cn.nagico.week8.util.pagenation.PagedList
import org.springframework.stereotype.Service

@Service
interface SupplierService {
    fun getSupplierList(name: String?, page: Int, pageSize: Int): PagedList<Supplier, SupplierInfo>

    fun getSupplier(id: Long): Supplier

    fun getSupplierInfo(id: Long): SupplierInfo

    fun addSupplier(supplier: Supplier): Supplier

    fun updateSupplier(id: Long, supplier: Supplier): Supplier

    fun deleteSupplier(id: Long)
}
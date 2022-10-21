package cn.nagico.week5.service.impl

import cn.nagico.week5.model.supplier.Supplier
import cn.nagico.week5.model.supplier.SupplierInfo
import cn.nagico.week5.repository.SupplierRepository
import cn.nagico.week5.service.SupplierService
import cn.nagico.week5.util.exception.ApiException
import cn.nagico.week5.util.pagenation.PagedList
import cn.nagico.week5.util.response.ApiResponseType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SupplierServiceImpl : SupplierService {
    @Autowired
    lateinit var supplierRepository: SupplierRepository

    override fun getSupplierList(name: String?, page: Int, pageSize: Int): PagedList<Supplier, SupplierInfo> {
        val supplierPage = if (name != null) {
            supplierRepository.findByNameContains(name, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id")))
        } else {
            supplierRepository.findAll(PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id")))
        }
        return PagedList(page, pageSize, supplierPage) { SupplierInfo(it) }
    }

    override fun getSupplier(id: Long): Supplier {
        return supplierRepository.findById(id).orElseThrow {
            ApiException(ApiResponseType.ID_NOT_EXIST, "供应商不存在")
        }
    }

    override fun getSupplierInfo(id: Long): SupplierInfo {
        return SupplierInfo(getSupplier(id))
    }

    override fun addSupplier(supplier: Supplier): Supplier {
        return supplierRepository.save(supplier)
    }

    override fun updateSupplier(id: Long, supplier: Supplier): Supplier {
        val oldSupplier = getSupplier(id)
        oldSupplier.name = supplier.name
        oldSupplier.description = supplier.description
        return supplierRepository.save(oldSupplier)
    }

    override fun deleteSupplier(id: Long) {
        supplierRepository.deleteById(id)
    }
}
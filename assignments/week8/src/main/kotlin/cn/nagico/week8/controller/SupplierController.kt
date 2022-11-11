package cn.nagico.week8.controller

import cn.nagico.week8.model.supplier.Supplier
import cn.nagico.week8.model.supplier.SupplierInfo
import cn.nagico.week8.service.SupplierService
import cn.nagico.week8.util.pagenation.PagedList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/suppliers")
class SupplierController {
    @Autowired
    lateinit var supplierService: SupplierService

    @GetMapping("")
    @PreAuthorize("hasAuthority('supplier:list')")
    fun getSuppliers(
        name: String?,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ) : PagedList<Supplier, SupplierInfo> {
        return supplierService.getSupplierList(name, page, pageSize)
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('supplier:retrieve')")
    fun getSupplier(@PathVariable id: Long): Supplier {
        return supplierService.getSupplier(id)
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('supplier:create')")
    fun addSupplier(@RequestBody supplier: Supplier): Supplier {
        return supplierService.addSupplier(supplier)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('supplier:update')")
    fun updateSupplier(@PathVariable id: Long, @RequestBody supplier: Supplier): Supplier {
        return supplierService.updateSupplier(id, supplier)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('supplier:delete')")
    fun deleteSupplier(@PathVariable id: Long) {
        supplierService.deleteSupplier(id)
    }
}
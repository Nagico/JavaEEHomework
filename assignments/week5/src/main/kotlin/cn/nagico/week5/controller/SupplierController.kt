package cn.nagico.week5.controller

import cn.nagico.week5.model.supplier.Supplier
import cn.nagico.week5.model.supplier.SupplierInfo
import cn.nagico.week5.service.SupplierService
import cn.nagico.week5.util.pagenation.PagedList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/suppliers")
class SupplierController {
    @Autowired
    lateinit var supplierService: SupplierService

    @GetMapping("")
    fun getSuppliers(
        name: String?,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ) : PagedList<Supplier, SupplierInfo> {
        return supplierService.getSupplierList(name, page, pageSize)
    }

    @GetMapping("{id}")
    fun getSupplier(@PathVariable id: Long): Supplier {
        return supplierService.getSupplier(id)
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addSupplier(@RequestBody supplier: Supplier): Supplier {
        return supplierService.addSupplier(supplier)
    }

    @PutMapping("{id}")
    fun updateSupplier(@PathVariable id: Long, @RequestBody supplier: Supplier): Supplier {
        return supplierService.updateSupplier(id, supplier)
    }

    @DeleteMapping("{id}")
    fun deleteSupplier(@PathVariable id: Long) {
        supplierService.deleteSupplier(id)
    }
}
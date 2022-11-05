package cn.nagico.week7.controller;

import cn.nagico.week7.model.supplier.Supplier
import cn.nagico.week7.service.ISupplierService
import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/suppliers")
class SupplierController {
    @Autowired
    lateinit var supplierService: ISupplierService

    @GetMapping("")
    fun getSupplierList(
        name: String?,
        description: String?,
        @RequestParam(defaultValue = "1") page: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Supplier> {
        val condition = mutableMapOf<String, Any>()
        name?.let { condition["name"] = it }
        description?.let { condition["description"] = it }

        return supplierService.querySupplierList(condition, page, pageSize)
    }

    @GetMapping("{id}")
    fun getSupplier(@PathVariable id: Long): Supplier {
        return supplierService.getSupplier(id)
    }

    @PostMapping
    fun addSupplier(@RequestBody supplier: Supplier): Supplier {
        return supplierService.addSupplier(supplier)
    }

    @PutMapping("{id}")
    fun updateSupplier(@PathVariable id: Long, @RequestBody supplier: Supplier): Supplier {
        return supplierService.updateSupplier(id, supplier)
    }

    @DeleteMapping("{id}")
    fun deleteSupplier(@PathVariable id: Long): ResponseEntity<Nothing> {
        supplierService.removeSupplier(id)
        return ResponseEntity.noContent().build()
    }
}

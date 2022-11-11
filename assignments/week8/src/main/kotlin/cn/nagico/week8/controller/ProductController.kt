package cn.nagico.week8.controller

import cn.nagico.week8.model.product.Product
import cn.nagico.week8.model.product.ProductInfo
import cn.nagico.week8.model.supplier.SupplierInfo
import cn.nagico.week8.service.ProductService
import cn.nagico.week8.util.pagenation.PagedList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController {
    @Autowired
    lateinit var productService: ProductService

    @GetMapping("")
    @PreAuthorize("hasAuthority('product:list')")
    fun getProducts(
        name: String?,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): PagedList<Product, ProductInfo> {
        return productService.getProductList(name, page, pageSize)
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('product:retrieve')")
    fun getProduct(@PathVariable id: Long): Product {
        return productService.getProduct(id)
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('product:create')")
    fun addProduct(@RequestBody product: Product): Product {
        return productService.addProduct(product)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('product:update')")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): Product {
        return productService.updateProduct(id, product)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('product:delete')")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Nothing> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("{id}/suppliers")
    @PreAuthorize("hasAuthority('product:supplier')")
    fun getProductSupplierInfo(@PathVariable id: Long): List<SupplierInfo> {
        return productService.getProductSupplierInfo(id)
    }
}
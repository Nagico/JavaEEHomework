package cn.nagico.week4.controller

import cn.nagico.week4.model.product.Product
import cn.nagico.week4.model.product.ProductInfo
import cn.nagico.week4.service.ProductService
import cn.nagico.week4.util.pagenation.PagedList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.NoSuchElementException

@RestController
@RequestMapping("/products")
class ProductController {
    @Autowired
    lateinit var productService: ProductService

    @GetMapping("")
    fun getProducts(
        name: String?,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): PagedList<ProductInfo> {
        return productService.getProductList(name, page, pageSize)
    }

    @GetMapping("{id}")
    fun getProduct(@PathVariable id: Int): Product {
        return productService.getProduct(id)
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@RequestBody product: Product): Product {
        return productService.addProduct(product)
    }

    @PutMapping("{id}")
    fun updateProduct(@PathVariable id: Int, @RequestBody product: Product): Product {
        return productService.updateProduct(id, product)
    }

    @DeleteMapping("{id}")
    fun deleteProduct(@PathVariable id: Int): ResponseEntity<Nothing> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}
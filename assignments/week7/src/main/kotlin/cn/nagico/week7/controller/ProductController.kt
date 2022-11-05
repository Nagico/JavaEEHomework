package cn.nagico.week7.controller;

import cn.nagico.week7.model.product.Product
import cn.nagico.week7.model.product.ProductDto
import cn.nagico.week7.model.product.ProductVo
import cn.nagico.week7.service.IProductService
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
@RequestMapping("/products")
class ProductController {
    @Autowired
    lateinit var productService: IProductService

    @GetMapping("")
    fun getProductList(
        name: String?,
        price: Long?,
        description: String?,
        @RequestParam(defaultValue = "1") page: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Product> {
        val condition = mutableMapOf<String, Any>()
        name?.let { condition["name"] = it }
        price?.let { condition["price"] = it }
        description?.let { condition["description"] = it }
        return productService.queryProductList(condition, page, pageSize)
    }

    @GetMapping("{id}")
    fun getProduct(@PathVariable id: Long): ProductDto {
        return productService.getProductWithSupplier(id)
    }

    @PostMapping
    fun addProduct(@RequestBody product: ProductVo): ProductDto {
        return productService.addProduct(product)
    }

    @PutMapping("{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: ProductVo): ProductDto {
        return productService.updateProduct(id, product)
    }

    @DeleteMapping("{id}")
    fun removeProduct(@PathVariable id: Long): ResponseEntity<Nothing> {
        productService.removeProduct(id)
        return ResponseEntity.noContent().build()
    }
}

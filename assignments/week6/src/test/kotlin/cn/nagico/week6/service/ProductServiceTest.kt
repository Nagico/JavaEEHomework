package cn.nagico.week6.service

import cn.nagico.week6.Week6Application
import cn.nagico.week6.model.product.Product
import cn.nagico.week6.model.product.ProductDto
import cn.nagico.week6.model.supplier.Supplier
import cn.nagico.week6.util.annotation.Slf4j
import cn.nagico.week6.util.annotation.Slf4j.Companion.logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Slf4j
@SpringBootTest(classes = [Week6Application::class])
@Transactional
class ProductServiceTest {
    @Autowired
    lateinit var productService: IProductService
    @Autowired
    lateinit var supplierService: ISupplierService

    final var supplier1 = Supplier(5L, "供应商10", "123", LocalDateTime.now(), LocalDateTime.now())
    final var supplier2 = Supplier(6L, "供应商20", "1234", LocalDateTime.now(), LocalDateTime.now())

    final var product1 = Product(5L, "产品10", "测试", "image",123.32, LocalDateTime.now(), LocalDateTime.now())
    final var product2 = Product(6L, "产品20", "测试", "image2",12.23, LocalDateTime.now(), LocalDateTime.now())

    final var product1Dto = ProductDto(product1, mutableListOf(supplier1, supplier2))
    final var product2Dto = ProductDto(product2, mutableListOf(supplier1))

    @BeforeEach
    fun setUp() {
        supplierService.save(supplier1)
        supplierService.save(supplier2)
        productService.addProduct(product1Dto)
    }

    @Test
    fun getByPage() {
        val res = productService.queryProductList(mapOf(), 1, 1)
        logger.info("{}", res)
        assert(res.current == 1L)
        assert(res.records.size == 1)
        assert(res.records[0].name == product1.name)
    }

    @Test
    fun queryName() {
        var res = productService.queryProductList(mapOf("name" to "产品"), 1, 10)
        logger.info("{}", res)
        assert(res.records.size == 3)
        res = productService.queryProductList(mapOf("name" to "产品2"), 1, 10)
        logger.info("{}", res)
        assert(res.records.size == 1)
    }

    @Test
    fun queryDescription() {
        var res = productService.queryProductList(mapOf("description" to "测试"), 1, 10)
        logger.info("{}", res)
        assert(res.records.size == 1)
        res = productService.queryProductList(mapOf("description" to "测试2"), 1, 10)
        logger.info("{}", res)
        assert(res.records.size == 0)
    }

    @Test
    fun getWithSupplier() {
        val res = productService.getProductWithSupplier(5)
        logger.info("{}", res)
        assert(res.suppliers.size == 2)
        assert(res.suppliers[0].id == supplier1.id)
        assert(res.suppliers[1].id == supplier2.id)
    }

    @Test
    fun addProduct() {
        productService.addProduct(product2Dto)
        val res = productService.queryProductList(mapOf(), 1, 10)
        logger.info("{}", res)
        assert(res.records.size == 1)
        assert(res.records[0].name == product1.name)
    }

    @Test
    fun updateProduct() {
        val productDto = productService.getProductWithSupplier(5)
        productDto.name = "产品10修改"
        productDto.suppliers.removeAt(0)
        productService.updateProduct(productDto)
        val res = productService.getProductWithSupplier(5)
        logger.info("{}", res)
        assert(res.name == "产品10修改")
        assert(res.suppliers.size == 1)
        assert(res.suppliers[0].id == supplier2.id)
    }

    @Test
    fun deleteProduct() {
        productService.removeProduct(5)
        val res = productService.queryProductList(mapOf("name" to "产品10"), 1, 10)
        logger.info("{}", res)
        assert(res.records.size == 0)
    }


}
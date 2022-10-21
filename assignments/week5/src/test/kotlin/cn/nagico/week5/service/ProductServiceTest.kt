package cn.nagico.week5.service

import cn.nagico.week5.model.product.Product
import cn.nagico.week5.model.supplier.Supplier
import cn.nagico.week5.util.exception.ApiException
import cn.nagico.week5.util.response.ApiResponseType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var supplierService: SupplierService

    final var supplier1 = Supplier(null, "供应商1", "123", mutableListOf(), Date(), Date())
    final var supplier2 = Supplier(null, "供应商2", "1234", mutableListOf(), Date(), Date())

    final var product1 = Product(null, "产品1", 123.32, "123", "image", mutableListOf(supplier1), Date(), Date())
    final var product2 = Product(null, "产品2", 12.23, "1232", "image2", mutableListOf(supplier1, supplier2), Date(), Date())

    @BeforeEach
    fun setUp() {
        supplierService.addSupplier(supplier1)
        supplierService.addSupplier(supplier2)
        productService.addProduct(product1)
    }

    @Test
    fun searchProduct() {
        val res = productService.getProductList("产品", 1, 10)
        assert(res.count == 1L)
    }

    @Test
    fun addProduct() {
        productService.addProduct(product2)
        assert(productService.getProduct(product2.id!!) == product2)
    }

    @Test
    fun updateProduct() {
        product1.name = "产品1改"
        productService.updateProduct(product1.id!!, product1)
        assert(productService.getProduct(product1.id!!).name == product1.name)
    }

    @Test
    fun deleteProduct() {
        productService.deleteProduct(product1.id!!)
        val ex = assertThrows<ApiException> { productService.getProduct(product1.id!!) }
        assert(ex.type == ApiResponseType.ID_NOT_EXIST)
    }
}
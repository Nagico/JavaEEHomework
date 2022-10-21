package cn.nagico.week5.service

import cn.nagico.week5.model.product.Product
import cn.nagico.week5.model.supplier.Supplier
import cn.nagico.week5.repository.SupplierRepository
import cn.nagico.week5.service.impl.SupplierServiceImpl
import cn.nagico.week5.util.exception.ApiException
import cn.nagico.week5.util.response.ApiResponseType
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.Date
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class SupplierServiceTest {
    @Autowired
    lateinit var supplierService: SupplierService

    var supplier1 = Supplier(null, "供应商1", "123", mutableListOf(), Date(), Date())
    var supplier2 = Supplier(null, "供应商2", "1234", mutableListOf(), Date(), Date())

    @BeforeEach
    fun setUp() {
        supplierService.addSupplier(supplier1)
    }

    @Test
    fun searchSupplier() {
        val res = supplierService.getSupplierList("供应商", 1, 10)
        assert(res.count >= 1L)
    }

    @Test
    fun addSupplier() {
        supplierService.addSupplier(supplier2)
        assert(supplierService.getSupplier(supplier2.id!!) == supplier2)
    }

    @Test
    fun updateSupplier() {
        supplier1.name = "供应商1改"
        supplierService.updateSupplier(supplier1.id!!, supplier1)
        assert(supplierService.getSupplier(supplier1.id!!).name == supplier1.name)
    }

    @Test
    fun deleteSupplier() {
        supplierService.deleteSupplier(supplier1.id!!)
        val ex = assertThrows<ApiException> { supplierService.getSupplier(supplier1.id!!) }
        assert(ex.type == ApiResponseType.ID_NOT_EXIST)
    }
}
package cn.nagico.week4.controller

import cn.nagico.week4.model.product.Product
import cn.nagico.week4.service.ProductService
import cn.nagico.week4.util.annotation.Slf4j
import cn.nagico.week4.util.annotation.Slf4j.Companion.logger
import cn.nagico.week4.util.extension.checkCode
import cn.nagico.week4.util.pagenation.PagedList
import cn.nagico.week4.util.response.ApiResponseType
import com.alibaba.fastjson.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@Slf4j
@WebMvcTest(ProductController::class, ProductService::class)
internal class ProductControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var productService: ProductService

    private var product: Product = Product(1, "test", 1.0, "1234", "test", Date(), Date())

    @AfterEach
    fun tearDown() {
        productService.clearAll()
    }

    @Test
    fun getProductsEmpty() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/products"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isOk )
            .checkCode()
            .andExpect ( jsonPath("$.data.count").value(0) )
    }

    @Test
    fun getProducts() {
        productService.addProduct(product)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/products"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isOk )
            .checkCode()
            .andExpect ( jsonPath("$.data.results[0].id").value(product.id) )
            .andExpect ( jsonPath("$.data.results[0].name").value(product.name) )
            .andExpect ( jsonPath("$.data.results[0].price").value(product.price) )
            .andExpect ( jsonPath("$.data.results[0].image").value(product.image) )

        mockMvc.perform(
            MockMvcRequestBuilders.get("/products?page=2"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isOk )
            .checkCode()
            .andExpect ( jsonPath("$.data.results[0]").doesNotExist() )
    }

    @Test
    fun getProduct() {
        productService.addProduct(product)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/products/1"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isOk )
            .checkCode()
            .andExpect ( jsonPath("$.data.id").value(product.id) )
            .andExpect ( jsonPath("$.data.name").value(product.name) )
            .andExpect ( jsonPath("$.data.price").value(product.price) )
            .andExpect ( jsonPath("$.data.description").value(product.description) )
            .andExpect ( jsonPath("$.data.image").value(product.image) )
    }

    @Test
    fun getProductNotFound() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/products/1"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isNotFound )
            .checkCode(ApiResponseType.ID_NOT_EXIST)
    }

    @Test
    fun addProduct() {
        val postData = JSONObject()
        postData["name"] = product.name
        postData["price"] = product.price
        postData["image"] = product.image
        postData["description"] = product.description

        mockMvc.perform(
            MockMvcRequestBuilders.post("/products")
                .contentType("application/json")
                .content(postData.toJSONString()))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isCreated )
            .checkCode()
            .andExpect ( jsonPath("$.data.id").value(product.id) )
            .andExpect ( jsonPath("$.data.name").value(product.name) )
            .andExpect ( jsonPath("$.data.price").value(product.price) )
            .andExpect ( jsonPath("$.data.description").value(product.description) )
            .andExpect ( jsonPath("$.data.image").value(product.image) )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/products")
                .contentType("application/json")
                .content(postData.toJSONString()))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isCreated )
            .checkCode()
            .andExpect ( jsonPath("$.data.id").value(product.id?.plus(1)) )
    }

    @Test
    fun addProductInvalid() {
        val postData = JSONObject()
        postData["id"] = product.id
        postData["name"] = product.name
        postData["price"] = product.price
        postData["image"] = product.image
        postData["description"] = product.description

        mockMvc.perform(
            MockMvcRequestBuilders.post("/products")
                .contentType("application/json")
                .content(postData.toJSONString()))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isCreated )
            .checkCode()
            .andExpect ( jsonPath("$.data.id").value(product.id) )
            .andExpect ( jsonPath("$.data.name").value(product.name) )
            .andExpect ( jsonPath("$.data.price").value(product.price) )
            .andExpect ( jsonPath("$.data.description").value(product.description) )
            .andExpect ( jsonPath("$.data.image").value(product.image) )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/products")
                .contentType("application/json")
                .content(postData.toJSONString()))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isBadRequest )
            .checkCode(ApiResponseType.PARAMETER_ERROR)
    }

    @Test
    fun updateProduct() {
        productService.addProduct(Product(1, "test123", 1123.0, "123456", "test123", Date(), Date()))
        val postData = JSONObject()
        postData["name"] = product.name
        postData["price"] = product.price
        postData["image"] = product.image
        postData["description"] = product.description

        mockMvc.perform(
            MockMvcRequestBuilders.put("/products/1")
                .contentType("application/json")
                .content(postData.toJSONString()))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isOk )
            .checkCode()
            .andExpect ( jsonPath("$.data.id").value(product.id) )
            .andExpect ( jsonPath("$.data.name").value(product.name) )
            .andExpect ( jsonPath("$.data.price").value(product.price) )
            .andExpect ( jsonPath("$.data.description").value(product.description) )
            .andExpect ( jsonPath("$.data.image").value(product.image) )
    }

    @Test
    fun updateProductNotFound() {
        val postData = JSONObject()
        postData["name"] = product.name
        postData["price"] = product.price
        postData["image"] = product.image
        postData["description"] = product.description

        mockMvc.perform(
            MockMvcRequestBuilders.put("/products/1")
                .contentType("application/json")
                .content(postData.toJSONString()))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isNotFound )
            .checkCode(ApiResponseType.ID_NOT_EXIST)
    }

    @Test
    fun deleteProduct() {
        productService.addProduct(product)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/products/1"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isNoContent )
    }

    @Test
    fun deleteProductNotFound() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/products/1"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isNotFound )
            .checkCode(ApiResponseType.ID_NOT_EXIST)
    }
}
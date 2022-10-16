package cn.nagico.week4.service

import cn.nagico.week4.model.product.Product
import cn.nagico.week4.model.product.ProductInfo
import cn.nagico.week4.util.exception.ApiException
import cn.nagico.week4.util.pagenation.PagedList
import cn.nagico.week4.util.response.ApiResponseType
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.lang.Integer.min
import java.util.*

@Service
class ProductService {
    private val productList = Collections.synchronizedList(mutableListOf<Product>())

    fun addProduct(product: Product): Product {
        if (product.id == null) {
            if (productList.isEmpty()) {
                product.id = 1
            } else {
                product.id = productList.maxOf { it.id!! } + 1
            }
        }

        if (productList.find { it.id == product.id } != null) {
            throw ApiException(ApiResponseType.PARAMETER_ERROR, "商品id已存在")
        }
        product.createTime = Date()
        product.updateTime = Date()
        productList.add(product)
        return product
    }

    fun getProductList(name: String?, page: Int, pageSize: Int): PagedList<ProductInfo> {
        val list = productList.filter { name == null || it.name.contains(name) }.map { ProductInfo(it) }
        val uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()
        return PagedList(page, pageSize, list, uri)
    }

    fun getProduct(id: Int): Product {
        return productList.find { it.id == id } ?: throw ApiException(ApiResponseType.ID_NOT_EXIST)
    }

    fun updateProduct(id: Int, product: Product): Product {
        val oldProduct = productList.find { it.id == id } ?: throw ApiException(ApiResponseType.ID_NOT_EXIST)
        oldProduct.name = product.name
        oldProduct.price = product.price
        oldProduct.description = product.description
        oldProduct.image = product.image
        oldProduct.updateTime = Date()
        return oldProduct
    }

    fun deleteProduct(id: Int) {
        productList.find { it.id == id } ?: throw ApiException(ApiResponseType.ID_NOT_EXIST)
        productList.removeIf { it.id == id }
    }
}
package cn.nagico.week5.service

import cn.nagico.week5.model.product.Product
import cn.nagico.week5.model.product.ProductInfo
import cn.nagico.week5.model.supplier.Supplier
import cn.nagico.week5.model.supplier.SupplierInfo
import cn.nagico.week5.util.pagenation.PagedList
import org.springframework.stereotype.Service

@Service
interface ProductService {
    fun getProductList(name: String?, page: Int, pageSize: Int): PagedList<Product, ProductInfo>

    fun getProduct(id: Long): Product

    fun getProductInfo(id: Long): ProductInfo

    fun getProductSupplierInfo(id: Long): List<SupplierInfo>

    fun addProduct(product: Product): Product

    fun updateProduct(id: Long, product: Product): Product

    fun deleteProduct(id: Long)
}
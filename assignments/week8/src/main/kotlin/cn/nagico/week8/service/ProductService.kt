package cn.nagico.week8.service

import cn.nagico.week8.model.product.Product
import cn.nagico.week8.model.product.ProductInfo
import cn.nagico.week8.model.supplier.Supplier
import cn.nagico.week8.model.supplier.SupplierInfo
import cn.nagico.week8.util.pagenation.PagedList
import org.springframework.stereotype.Service
import java.awt.print.Pageable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
interface ProductService {
    fun getProductList(name: String?, page: Int, pageSize: Int): PagedList<Product, ProductInfo>

    /**
     * 多条件查询 Map
     * 跨表查询 root.join('类属性名').get('关联实体的属性')
     */
    //fun getProductList2(condition: Map<String, Any>, pageable: Pageable): PagedList<Product, ProductInfo>


    fun getProduct(id: Long): Product

    fun getProductInfo(id: Long): ProductInfo

    fun getProductSupplierInfo(id: Long): List<SupplierInfo>

    fun addProduct(product: Product): Product

    fun updateProduct(id: Long, product: Product): Product

    fun deleteProduct(id: Long)
}
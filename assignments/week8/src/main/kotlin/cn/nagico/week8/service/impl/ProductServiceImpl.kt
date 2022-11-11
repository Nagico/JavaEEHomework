package cn.nagico.week8.service.impl

import cn.nagico.week8.model.product.Product
import cn.nagico.week8.model.product.ProductInfo
import cn.nagico.week8.model.supplier.Supplier
import cn.nagico.week8.model.supplier.SupplierInfo
import cn.nagico.week8.repository.ProductRepository
import cn.nagico.week8.service.ProductService
import cn.nagico.week8.service.SupplierService
import cn.nagico.week8.util.exception.ApiException
import cn.nagico.week8.util.pagenation.PagedList
import cn.nagico.week8.util.response.ApiResponseType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.criteria.Predicate

@Service
class ProductServiceImpl : ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    override fun getProductList(name: String?, page: Int, pageSize: Int)
        : PagedList<Product, ProductInfo> {
        val result = productRepository.findAll ({ root, _, cb ->
            val predicates = mutableListOf<Predicate>()
            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%$name%"))
            }
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id")))
        return PagedList(page, pageSize, result) { ProductInfo(it) }
    }

    override fun getProduct(id: Long): Product {
        return productRepository.findById(id).orElseThrow {
            ApiException(ApiResponseType.ID_NOT_EXIST, "商品不存在")
        }
    }

    override fun getProductInfo(id: Long): ProductInfo {
        return ProductInfo(getProduct(id))
    }

    override fun getProductSupplierInfo(id: Long): List<SupplierInfo> {
        return getProduct(id).suppliers.map { SupplierInfo(it) }
    }

    override fun addProduct(product: Product): Product {
        product.createTime = LocalDateTime.now()
        product.updateTime = LocalDateTime.now()
        return productRepository.save(product)
    }

    override fun updateProduct(id: Long, product: Product): Product {
        val oldProduct = getProduct(id)
        oldProduct.name = product.name
        oldProduct.price = product.price
        oldProduct.description = product.description
        oldProduct.image = product.image
        oldProduct.suppliers = product.suppliers
        oldProduct.updateTime = LocalDateTime.now()
        return productRepository.save(oldProduct)
    }

    override fun deleteProduct(id: Long) {
        productRepository.deleteById(id)
    }

}
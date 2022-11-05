package cn.nagico.week7.service.impl;

import cn.nagico.week7.model.product.Product;
import cn.nagico.week7.dao.ProductMapper;
import cn.nagico.week7.model.product.ProductDto
import cn.nagico.week7.model.product.ProductVo
import cn.nagico.week7.model.supplier.Supplier
import cn.nagico.week7.service.IProductService;
import cn.nagico.week7.util.exception.ApiException
import cn.nagico.week7.util.response.ApiResponseType
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
@Service
open class ProductServiceImpl : ServiceImpl<ProductMapper, Product>(), IProductService {
    override fun queryProductList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<Product> {
        val pageWrapper = Page<Product>(page, pageSize).addOrder(OrderItem("id", false))

        val queryWrapper = KtQueryWrapper(Product::class.java)
        if (condition.containsKey("name")) {
            queryWrapper.like(Product::name, condition["name"])
        }
        if (condition.containsKey("price")) {
            queryWrapper.eq(Product::price, condition["price"])
        }
        if (condition.containsKey("description")) {
            queryWrapper.like(Product::description, condition["description"])
        }
        return baseMapper.selectPage(pageWrapper, queryWrapper)
    }

    override fun getProductWithSupplier(id: Long): ProductDto {
        val product = baseMapper.selectById(id) ?: throw ApiException(ApiResponseType.ID_NOT_EXIST, "id不存在")

        val productDto = ProductDto(product)

        productDto.suppliers = baseMapper.getSuppliersByProductId(id) as MutableList<Supplier>

        return productDto
    }

    @Transactional  // 添加时需要注意
    override fun addProduct(product: ProductVo): ProductDto {
        if (baseMapper.selectById(product.id) != null)
            throw ApiException(ApiResponseType.PARAMETER_ERROR, "id已存在")

        product.createTime = LocalDateTime.now()
        product.updateTime = LocalDateTime.now()

        baseMapper.insert(Product(product))
        for (supplier in product.suppliers) {
            product.id?.let { baseMapper.addSupplierToProduct(it, supplier) }
        }
        return getProductWithSupplier(product.id!!)
    }

    @Transactional
    override fun removeProduct(id: Long) {
        if (baseMapper.selectById(id) == null) {
            throw ApiException(ApiResponseType.ID_NOT_EXIST, "id不存在")
        }

        baseMapper.deleteById(id)
        baseMapper.removeAllSuppliersFromProduct(id)
    }

    @Transactional
    override fun updateProduct(id: Long, product: ProductVo): ProductDto {
        if (id != product.id) {
            throw ApiException(ApiResponseType.PARAMETER_ERROR, "id不匹配")
        }
        if (baseMapper.selectById(id) == null) {
            throw ApiException(ApiResponseType.ID_NOT_EXIST, "id不存在")
        }

        product.updateTime = LocalDateTime.now()

        baseMapper.updateById(Product(product))
        baseMapper.removeAllSuppliersFromProduct(product.id!!)
        for (supplier in product.suppliers) {
            product.id?.let { baseMapper.addSupplierToProduct(it, supplier) }
        }
        return getProductWithSupplier(product.id!!)
    }
}

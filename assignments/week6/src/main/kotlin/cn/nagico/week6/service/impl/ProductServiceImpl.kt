package cn.nagico.week6.service.impl;

import cn.nagico.week6.model.product.Product;
import cn.nagico.week6.dao.ProductMapper;
import cn.nagico.week6.model.product.ProductDto
import cn.nagico.week6.model.supplier.Supplier
import cn.nagico.week6.service.IProductService;
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    override fun queryProductList(condition: Map<String, Any>, page: Long, pageSize: Long): Page<Product> {
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
        val productDto = ProductDto(baseMapper.selectById(id))

        productDto.suppliers = baseMapper.getSuppliersByProductId(id) as MutableList<Supplier>

        return productDto
    }

    override fun addProduct(product: ProductDto): ProductDto {
        baseMapper.insert(product)
        for (supplier in product.suppliers) {
            product.id?.let { supplier.id?.let { it1 -> baseMapper.addSupplierToProduct(it, it1) } }
        }
        return getProductWithSupplier(product.id!!)
    }

    override fun removeProduct(id: Long) {
        baseMapper.deleteById(id)
        baseMapper.removeAllSuppliersFromProduct(id)
    }

    override fun updateProduct(product: ProductDto): ProductDto {
        baseMapper.updateById(product)
        baseMapper.removeAllSuppliersFromProduct(product.id!!)
        for (supplier in product.suppliers) {
            product.id?.let { supplier.id?.let { it1 -> baseMapper.addSupplierToProduct(it, it1) } }
        }
        return getProductWithSupplier(product.id!!)
    }
}

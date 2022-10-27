package cn.nagico.week6.service;

import cn.nagico.week6.model.product.Product;
import cn.nagico.week6.model.product.ProductDto
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
interface IProductService : IService<Product> {
    fun queryProductList(condition: Map<String, Any>, page: Long, pageSize: Long): Page<Product>

    fun getProductWithSupplier(id: Long): ProductDto

    fun addProduct(product: ProductDto): ProductDto

    fun removeProduct(id: Long)

    fun updateProduct(product: ProductDto): ProductDto
}
